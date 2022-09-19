package bpmn.model;


import bpmn.BaseElement;

import java.util.List;

/**
 * @author xuhongyu
 * @create 2022-08-29 2:41 下午
 */

public class EndEventImpl extends EventAbstract  {


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
}
