package bpmn.impl;

import bpmn.BaseElement;
import bpmn.BpmnBaseService;
import bpmn.Event;
import bpmn.model.EndEventImpl;
import bpmn.model.ParallelGatewayImpl;
import bpmn.model.SequenceFlowImpl;
import bpmn.model.StartEventImpl;
import bpmn.model.TaskImpl;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowElement;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author xuhongyu
 * @create 2022-08-26 5:45 下午
 */

@Service
public class BpmnBaseServiceImpl implements BpmnBaseService {


    @Override
    public List<BaseElement> parseBpmnFile(InputStream inputStream) {

        // 解析文件
        BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(inputStream);

        Map<String, BaseElement> baseElementMap = doParseBpmnFile(bpmnModelInstance);

        Collection<BaseElement> baseElements = baseElementMap.values();

        return new ArrayList<>(baseElements);
    }

    @Override
    public List<BaseElement> relationCreatBpmn(List<BaseElement> baseElements) {

        Map<String, BaseElement> baseElementMap = baseElements.stream().collect(Collectors.toMap(BaseElement::getId, BaseElement -> BaseElement));

        List<BaseElement> baseElementFilters = baseElements.stream().filter(po -> po instanceof SequenceFlowImpl).collect(Collectors.toList());
        // 找线
        for (BaseElement baseElement : baseElementFilters) {
            SequenceFlowImpl sequenceFlow = (SequenceFlowImpl) baseElement;
            String sourceRefId = sequenceFlow.getSourceRefId();
            String targetRefId = sequenceFlow.getTargetRefId();

            BaseElement baseElementSource = baseElementMap.get(sourceRefId);
            BaseElement baseElementTarget = baseElementMap.get(targetRefId);
            // 将节点链接
            Event baseElementSourceEvent = (Event) baseElementSource;
            Event baseElementTargetEvent = (Event) baseElementTarget;

            baseElementSourceEvent.addOutgoing(baseElementTarget);
            baseElementTargetEvent.addIncoming(baseElementSource);
        }

        List<BaseElement> rootElements = baseElements.stream().filter(vo -> vo instanceof StartEventImpl).collect(Collectors.toList());
        return rootElements;
    }


    /**
     * 解析BPMN文件流
     *
     * @param bpmnModelInstance
     * @return
     */
    private Map<String, BaseElement> doParseBpmnFile(BpmnModelInstance bpmnModelInstance) {

        Collection<Process> modelElementsByType = bpmnModelInstance.getModelElementsByType(Process.class);
        Map<String, BaseElement> baseElementMap = new LinkedHashMap<>();

        // 遍历
        for (Process p : modelElementsByType) {
            Collection<FlowElement> flowElements = p.getFlowElements();

            for (FlowElement flowElement : flowElements) {

                BaseElement baseElement = null;

                // 开始节点
                if (flowElement instanceof org.camunda.bpm.model.bpmn.impl.instance.StartEventImpl) {
                    baseElement = new StartEventImpl();
                }
                // 结束节点
                if (flowElement instanceof org.camunda.bpm.model.bpmn.impl.instance.EndEventImpl) {
                    baseElement = new EndEventImpl();
                }

                // 任务节点
                if (flowElement instanceof org.camunda.bpm.model.bpmn.impl.instance.TaskImpl) {
                    baseElement = new TaskImpl();
                }

                // 网关节点
                if (flowElement instanceof org.camunda.bpm.model.bpmn.impl.instance.ParallelGatewayImpl || flowElement instanceof org.camunda.bpm.model.bpmn.impl.instance.ExclusiveGatewayImpl) {
                    baseElement = new ParallelGatewayImpl();
                }

                // 边节点
                if (flowElement instanceof org.camunda.bpm.model.bpmn.impl.instance.SequenceFlowImpl) {
                    org.camunda.bpm.model.bpmn.impl.instance.SequenceFlowImpl sequenceFlowResource = (org.camunda.bpm.model.bpmn.impl.instance.SequenceFlowImpl) flowElement;
                    SequenceFlowImpl sequenceFlow = new SequenceFlowImpl();
                    sequenceFlow.setSourceRefId(sequenceFlowResource.getSource().getId());
                    sequenceFlow.setTargetRefId(sequenceFlowResource.getTarget().getId());

                    baseElement = sequenceFlow;
                }
                if (baseElement == null) {
                    System.out.println("bpmn类型暂不支持！->" + flowElement.getName());
                    throw new RuntimeException();
                }

                String id = flowElement.getId();


                String name = StringUtils.isEmpty(flowElement.getName()) ? BpmnBaseServiceImpl.createUUID() : flowElement.getName();
                baseElement.setId(id);
                baseElement.setName(name);
                baseElementMap.put(id, baseElement);
            }
        }

        return baseElementMap;
    }


    /**
     * 获取UUID
     *
     * @return
     */
    public static String createUUID() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }

}
