package bpmn.model;


import bpmn.BaseElement;
import bpmn.Gateway;

import java.util.List;


/**
 * 网关
 *
 * @author xuhongyu
 * @create 2022-08-29 3:00 下午
 */
public class ParallelGatewayImpl extends EventAbstract  implements BaseElement, Gateway {

    private String id;

    private String name;


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
}
