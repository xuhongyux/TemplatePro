package bpmn;

/**
 * 网关
 * @author xuhongyu
 * @create 2022-08-30 9:55 上午
 */
public interface Gateway extends BaseElement {

    /**
     * 获取下一个概率事件
     * @return 不存在时返回空
     */
    BaseElement nextProbabilityEvent();

    /**
     * 增加事件概率
     * @param eventId
     * @param frequency
     */
     void addEventFrequency(String eventId, Integer frequency);
}
