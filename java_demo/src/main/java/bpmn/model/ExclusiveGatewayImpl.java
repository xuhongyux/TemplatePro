package bpmn.model;

import bpmn.BaseElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 专行网关
 *
 * @author xuhongyu
 * @create 2022-09-16 14:29
 */
public class ExclusiveGatewayImpl extends GatewayAbstract {

    public ExclusiveGatewayImpl() {
        this.eventFrequency = new HashMap<>();
    }

    /**
     * 事件概率
     * key: 孩子节点Id
     * value: 概率值 0~100
     */
    private Map<String, Integer> eventFrequency;


    @Override
    public void setIncoming(List<BaseElement> incoming) {
        super.setIncoming(incoming);
    }

    @Override
    public void addIncoming(BaseElement incoming) {
        super.addIncoming(incoming);
    }

    @Override
    public List<BaseElement> getIncoming() {
        return super.getIncoming();
    }


    @Override
    public List<BaseElement> getOutgoing() {
        return super.getOutgoing();
    }


    /**
     * 添加一个概率
     *
     * @param eventId
     * @param frequency
     */
    @Override
    public void addEventFrequency(String eventId, Integer frequency) {
        this.eventFrequency.put(eventId, frequency);
    }


    @Override
    public List<BaseElement> getRunNextElement() {
        List<BaseElement> objects = new ArrayList<>();
        objects.add(this.nextProbabilityEvent());
        return objects;
    }


    /**
     * 获取下一个概率事件
     *
     * @return
     */
    private BaseElement nextProbabilityEvent() {
        if (this.getOutgoing().size() == 1) {
            return this.getOneOutGoing();
        }
        List<Integer> ruler = new ArrayList<>();
        ruler.add(0);

        Random random = new Random();
        int r = random.nextInt(99);

        String elementId = "";
        for (Map.Entry<String, Integer> stringIntegerEntry : this.eventFrequency.entrySet()) {
            Integer value = stringIntegerEntry.getValue();
            Integer ruleVale = ruler.get(ruler.size() - 1);

            // next ruleValue
            Integer nextRuleValue = value + ruleVale;
            if (nextRuleValue > r) {
                elementId = stringIntegerEntry.getKey();
                continue;
            }
            ruler.add(nextRuleValue);
        }
        BaseElement outGoingById = this.getOutGoingById(elementId);
        return outGoingById;
    }
}
