package bpmn;

import java.util.Set;

/**
 * 网关
 * @author xuhongyu
 * @create 2022-08-30 9:55 上午
 */
public interface Gateway extends BaseElement ,Event{


    /**
     * 增加事件概率
     * @param eventId
     * @param frequency
     */
     void addEventFrequency(String eventId, Integer frequency);


    /**
     * 设置开始网关标识
     * @param flag
     */
     void setStartGatewayFlag(boolean flag);

    /**
     * 获取是否是结束网关
     * @return
     */
    boolean getEndGatewayFlag();

    /**
     * 设置能够继续运行的所需条件
     * @param eventIdIds
     */
    void setPrepareEventId(Set<String> eventIdIds);

    /**
     * 获取能够继续运行的所需条件
     * @return
     */
    Set<String> getPrepareEventId();

    /**
     * 完成一个准备事件
     * @param id
     */
    void finishPrepareEvent(String id);
}
