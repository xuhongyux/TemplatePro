package bpmn.bo;

import bpmn.BaseElement;
import bpmn.Event;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * 流程运行模块
 *
 * @author xuhongyu
 * @create 2022-09-14 11:39
 */
@Data
public class ProcessRunBo {

    public ProcessRunBo(String caseId, Event baseElementsRoot) {
        this.caseId = caseId;
        this.baseElementsRoot = baseElementsRoot;
        this.currentBaseElements = baseElementsRoot;
        this.consumptionTimeEnd = 0;
        this.acquireFlag = true;
        this.eventWaitTime = new HashMap<>();
    }

    /**
     * caseId
     */
    private String caseId;

    /**
     * 时间线
     */
    private Integer timeLine;
    /**
     * 流程图根节点
     */
    private BaseElement baseElementsRoot;


    /**
     * 当前运行节点
     */
    private Event currentBaseElements;


    /**
     * 当前资源占用结束时间
     */
    private Integer consumptionTimeEnd;

    /**
     * 占用信号量
     */
    private Semaphore semaphore;


    /**
     * 抢占信号量标示,true:抢占成功,false:抢占失败
     */
    private Boolean acquireFlag;

    /**
     * 开始等待时间
     */
    private Integer startWaitTime;

    /**
     * 节点等待时间，Key 节点Id ， value等待时间
     */
    private Map<String, Integer> eventWaitTime;

    public void setStartWaitTime() {
        this.startWaitTime = this.timeLine;
    }

    public void setAcquireFlag(boolean acquireFlag) {
        this.acquireFlag = acquireFlag;
    }


    public void releaseSemaphore() {
        if (this.semaphore != null) {
            System.out.println("释放资源：" + this.caseId);
            this.semaphore.release();
        }
    }

    /**
     * 结束等待
     */
    public void endWaitTime() {
        String id = this.currentBaseElements.getId();
        Integer waitTime = this.timeLine - this.startWaitTime;
        if (this.eventWaitTime.containsKey(id)) {
            this.eventWaitTime.put(id, waitTime + this.eventWaitTime.get(id));
        } else {
            this.eventWaitTime.put(id, waitTime);
        }
        this.startWaitTime = null;
    }
}
