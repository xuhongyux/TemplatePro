package simulation;

import bpmn.BaseElement;
import bpmn.Event;
import bpmn.Gateway;
import bpmn.bo.ProcessRunBo;
import bpmn.bo.ResourceBo;
import bpmn.bo.ResourceInfoBo;
import bpmn.impl.BpmnBaseServiceImpl;
import bpmn.model.EndEventImpl;
import bpmn.model.EventAbstract;
import bpmn.model.ExclusiveGatewayImpl;
import bpmn.model.GatewayAbstract;
import org.apache.commons.lang3.SerializationUtils;
import util.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

/**
 * @author xuhongyu
 * @create 2022-09-13 16:18
 */
public class Test {

    private static volatile Map<String, Semaphore> semaphoreMap;

    /**
     * 时间线
     */
    private static volatile Integer timeLine = new Integer(0);


    @org.junit.Test
    public void testClone() throws CloneNotSupportedException {
        ExclusiveGatewayImpl exclusiveGateway = new ExclusiveGatewayImpl();
        HashSet<String> objects = new HashSet<>();
        objects.add("1123123");
        exclusiveGateway.setPrepareEventId(objects);

        GatewayAbstract clone = exclusiveGateway.clone();

        System.out.println();
    }

    public static void main(String[] args) throws InterruptedException, CloneNotSupportedException {
        String filePath = "/Users/idea_code/TemplatePro/java_demo/src/main/resources/bpmn/流程仿真模版.bpmn";

        String fileCode = FileUtils.readFile(new File(filePath));

        BpmnBaseServiceImpl bpmnBaseService = new BpmnBaseServiceImpl();
        InputStream is = new ByteArrayInputStream(fileCode.getBytes());

        // 解析
        List<BaseElement> baseElements = bpmnBaseService.parseBpmnFile(is);
        EventAbstract baseElementsRoot = (EventAbstract) bpmnBaseService.relationCreatBpmn(baseElements).get(0);


        // 数据初始化
        dataInit(baseElements);

        ResourceInfoBo resourceInfoBo = ResourceInfoBo.buildReResourceBo();

        List<ResourceBo> resourceBos = resourceInfoBo.getResourceBos();

        // 信号量 Key:  resourceName  value 信号量
        semaphoreMap = resourceBos.stream().collect(Collectors.toMap(ResourceBo::getResourceName, ResourceBo::getSemaphore));
        Map<String, ResourceBo> resourceBoMap = resourceBos.stream().collect(Collectors.toMap(ResourceBo::getResourceName, resourceBo -> resourceBo));


        List<ProcessRunBo> processRunBos = new ArrayList<>();
        Map<String, ProcessRunBo> processRunBoMap = new HashMap<>();
        // 创建资源
        for (int i = 0; i <= 3; i++) {

            String caseId = "case" + i;
            // 拷贝对象
            BaseElement clone = SerializationUtils.clone(baseElementsRoot);

            ProcessRunBo processRunBo = new ProcessRunBo(caseId, (Event)clone);
            processRunBo.setTimeLine(timeLine);
            processRunBos.add(processRunBo);
            processRunBoMap.put(caseId, processRunBo);
        }
        List<Object> completes = new ArrayList<>();

        // 开始处理
        for (Integer i = 60 * 24 * 7 * 12; i > timeLine; timeLine++) {
            if (processRunBos.size() == 0) {
                System.out.println("运行完毕：当前时间线为：" + timeLine);
                break;
            }

            if (timeLine % 1011 == 1) {
                for (Map.Entry<String, Semaphore> stringSemaphoreEntry : semaphoreMap.entrySet()) {
                    System.out.println("剩余资源情况：id:" + stringSemaphoreEntry.getKey() + "### 剩余量：  " + stringSemaphoreEntry.getValue().availablePermits());
                }
            }
//            if (timeLine % 101 == 1) {
//                ProcessRunBo processRunBo = new ProcessRunBo(timeLine.toString(), (Event) baseElementsRoot);
//                processRunBo.setTimeLine(timeLine);
//                processRunBos.add(processRunBo);
//            }

            // 开始遍历每一个流程
            processRun:
            for (int j = 0; j < processRunBos.size(); j++) {

                ProcessRunBo processRunBo = processRunBos.get(j);
                processRunBo.setTimeLine(timeLine);

                List<Event> currentBaseElements = processRunBo.getCurrentBaseElements();

                // 收集本次运行剩下的节点信息
                List<Event> runNextAllElements = new ArrayList<>();
                // 开始遍历当前运行节点
                for (Event currentBaseElement : currentBaseElements) {
                    // 设置当前运行的eventId
                    processRunBo.setCurrentRunEventId(currentBaseElement.getId());

                    ResourceBo resourceBo = resourceBoMap.get(currentBaseElement.getResourceName());
                    if (!processRunBo.getAcquireFlag()) {
                        // 尝试占用资源，如果失败下一次轮训的时候再尝试抢占
                        if (processRunBo.getSemaphore().tryAcquire()) {
                            processRunBo.setConsumptionTimeEnd(timeLine + resourceBo.getConsumptionTime());
                            processRunBo.setAcquireFlag(true);

                            // 结束等待
                            processRunBo.endWaitTime();
                        }
                        runNextAllElements.add(currentBaseElement);
                        continue;
                    }

                    // 检查当前节点是否结束
                    Integer consumptionTimeEnd = processRunBo.getConsumptionTimeEnd();

                    // 如果资源占用结束
                    if (consumptionTimeEnd <= timeLine) {
                        // 释放当前资源
                        if (processRunBo.getSemaphore() != null) {
                            processRunBo.releaseSemaphore();
                            System.out.println("释放资源：" + processRunBo.getCaseId()+"事件名称："+ currentBaseElement.getName());
                            processRunBo.removeSemaphore();
                        }
                        // 当前任务完成，通知下一个资源
                        currentBaseElement.runFinish();
                        // 获取下一个资源
                        List<BaseElement> runNextElements = currentBaseElement.getRunNextElement();

                        for (BaseElement runNextElement : runNextElements) {

                            if (runNextElement instanceof EndEventImpl) {

                                processRunBos.remove(processRunBo);
                                completes.add(processRunBo);

                                processRunBo.releaseSemaphore();
                                System.out.println("释放资源：" + processRunBo.getCaseId()+"事件名称："+ currentBaseElement.getName());

                                j--;
                                System.out.println("节点运行完毕：" + processRunBo.getCaseId());
                                continue processRun;
                            }
                            runNextAllElements.add((Event) runNextElement);
                        }

                        // 下一个任务抢占资源
                        for (Event runNextAllElement : runNextAllElements) {
                            processRunBo.setCurrentRunEventId(runNextAllElement.getId());
                            ResourceBo nextResourceBo = resourceBoMap.get(runNextAllElement.getResourceName());
                            Semaphore semaphore = nextResourceBo.getSemaphore();
                            processRunBo.setSemaphore(semaphore);
                            // 尝试占用资源，如果失败下一次轮训的时候再尝试抢占
                            if (nextResourceBo.getSemaphore().tryAcquire()) {
                                processRunBo.setConsumptionTimeEnd(timeLine + runNextAllElement.getConsumptionTime());
                                processRunBo.setAcquireFlag(true);
                            } else {
                                System.out.println("请求资源失败：等待下次尝试：" + runNextAllElement.getName());
                                processRunBo.setAcquireFlag(false);
                                processRunBo.setStartWaitTime();
                            }
                        }

                        // 运行未结束是进行节点收集
                    } else {
                        runNextAllElements.add(currentBaseElement);
                    }
                }
                // 当前节点刷新
                processRunBo.setCurrentBaseElements(runNextAllElements);
            }
        }


    }


    private static void dataInit(List<BaseElement> baseElements) {
        // 设置概率
        Map<String, BaseElement> collect = baseElements.stream().collect(Collectors.toMap(BaseElement::getName, BaseElement -> BaseElement));
        Gateway baseElement = (Gateway) collect.get("选择客服处理");
        baseElement.addEventFrequency("Activity_171g4p6", 20);
        baseElement.addEventFrequency("Activity_1k0vp31", 80);

        Gateway baseElement1 = (Gateway) collect.get("并行网关开始");
        baseElement1.addEventFrequency("Activity_1ys2ruu", 20);
        baseElement1.addEventFrequency("Activity_1y2fpu1", 80);


        Event baseElement2 = (Event) collect.get("接到订单");
        baseElement2.setConsumptionTime(20);
        baseElement2.setResourceName("资源二（客服）");


        Event baseElement3 = (Event) collect.get("客服1");
        baseElement3.setConsumptionTime(10);
        baseElement3.setResourceName("资源二（客服）");

        Event baseElement4 = (Event) collect.get("客服2");
        baseElement4.setConsumptionTime(10);
        baseElement4.setResourceName("资源二（客服）");

        Event baseElement5 = (Event) collect.get("用户使用产品");
        baseElement5.setConsumptionTime(30);
        baseElement5.setResourceName("资源一(用户)");

        Event baseElement6 = (Event) collect.get("客户使用完成");
        baseElement6.setConsumptionTime(10);
        baseElement6.setResourceName("资源一(用户)");

        Event baseElement7 = (Event) collect.get("客户使用回馈");
        baseElement7.setConsumptionTime(10);
        baseElement7.setResourceName("资源一(用户)");


        Event baseElement8 = (Event) collect.get("经理查看订单信息");
        baseElement8.setConsumptionTime(10);
        baseElement8.setResourceName("资源三（经理）");
    }


}
