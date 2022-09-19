package bpmn.bo;

import bpmn.BaseElement;
import bpmn.Event;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        this.currentBaseElements = new ArrayList<>();
        this.consumptionTimeEndMap = new HashMap<>();
        this.acquireFlagMap = new HashMap<>();
        this.eventWaitTimeMap = new HashMap<>();
        this.semaphoreMap = new HashMap<>();
        this.startWaitTimeMap = new HashMap<>();

        this.initProperty(baseElementsRoot);
    }

    private void initProperty(Event baseElementsRoot) {
        this.currentBaseElements.add(baseElementsRoot);
        this.consumptionTimeEndMap.put(baseElementsRoot.getId(), 0);
        this.acquireFlagMap.put(baseElementsRoot.getId(), true);
    }

    /**
     * 当前运行组件Id
     */
    private String currentRunEventId;

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
    private List<Event> currentBaseElements;


    /**
     * 当前资源占用结束时间
     * key eventId
     */
    private Map<String, Integer> consumptionTimeEndMap;

    /**
     * 占用信号量
     * key eventId
     */
    private Map<String, Semaphore> semaphoreMap;


    /**
     * 抢占信号量标示,true:抢占成功,false:抢占失败
     * key eventId
     */
    private Map<String, Boolean> acquireFlagMap;

    /**
     * 开始等待时间
     * key eventId
     */
    private Map<String, Integer> startWaitTimeMap;

    /**
     * 节点等待时间，Key 节点Id ， value等待时间
     */
    private Map<String, Integer> eventWaitTimeMap;

    public void setStartWaitTime() {
        this.startWaitTimeMap.put(this.currentRunEventId, this.timeLine);
    }

    public void setAcquireFlag(boolean acquireFlag) {
        this.acquireFlagMap.put(this.currentRunEventId, acquireFlag);
    }

    public void releaseSemaphore() {
        Semaphore semaphore = this.semaphoreMap.get(this.currentRunEventId);
        if (semaphore != null) {
            semaphore.release();
        }
        this.acquireFlagMap.remove(this.currentRunEventId);
    }

    public boolean getAcquireFlag() {
        if (this ==null || this.currentRunEventId == null || this.acquireFlagMap == null) {
            System.out.println();
        }
        if (acquireFlagMap.containsKey(currentRunEventId)) {
            return this.acquireFlagMap.get(this.currentRunEventId);
        }
        return false;

    }

    /**
     * 结束等待
     */
    public void endWaitTime() {
        String id = this.currentRunEventId;
        Integer waitTime = this.timeLine - this.startWaitTimeMap.get(id);
        if (this.eventWaitTimeMap.containsKey(id)) {
            this.eventWaitTimeMap.put(id, waitTime + this.eventWaitTimeMap.get(id));
        } else {
            this.eventWaitTimeMap.put(id, waitTime);
        }
        this.startWaitTimeMap.put(id, null);
    }

    public Semaphore getSemaphore() {
        return this.semaphoreMap.get(this.currentRunEventId);
    }


    public void setConsumptionTimeEnd(int i) {
        this.consumptionTimeEndMap.put(this.currentRunEventId, i);
    }

    public Integer getConsumptionTimeEnd() {
        return this.consumptionTimeEndMap.get(this.currentRunEventId);
    }

    public void setSemaphore(Semaphore o) {
        this.semaphoreMap.put(this.currentRunEventId, o);
    }

    public void removeSemaphore() {
        this.semaphoreMap.remove(this.currentRunEventId);
    }
}
