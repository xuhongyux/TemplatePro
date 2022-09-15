package bpmn.model;


import bpmn.BaseElement;
import bpmn.Gateway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * 网关
 *
 * @author xuhongyu
 * @create 2022-08-29 3:00 下午
 */
public class ParallelGatewayImpl extends EventAbstract implements Gateway {


    public ParallelGatewayImpl() {
        this.eventFrequency = new HashMap<>();
    }

    private String id;

    private String name;

    /**
     * 事件概率
     * key: 孩子节点Id
     * value: 概率值 0~100
     */
    private Map<String, Integer> eventFrequency;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;

    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

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
    public void setOutgoing(List<BaseElement> outgoing) {
        super.setOutgoing(outgoing);
    }

    @Override
    public List<BaseElement> getOutgoing() {
        return super.getOutgoing();
    }

    @Override
    public void addOutgoing(BaseElement incoming) {
        super.addOutgoing(incoming);
    }


    @Override
    public BaseElement nextProbabilityEvent() {
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
    public BaseElement getRunNextElement() {
        return this.nextProbabilityEvent();
    }
}
