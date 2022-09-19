package bpmn.model;

import bpmn.BaseElement;
import bpmn.Gateway;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xuhongyu
 * @create 2022-09-16 11:52
 */
public abstract class GatewayAbstract extends EventAbstract implements Gateway, Cloneable {

    /**
     * 重写clone方法，将前置资源复制
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public GatewayAbstract clone() throws CloneNotSupportedException {
        GatewayAbstract gatewayAbstract = (GatewayAbstract) super.clone();
        Set<String> prepareEventId = gatewayAbstract.getPrepareEventId();
        Set<String> objects = new HashSet<>();
        objects.addAll(prepareEventId);
        gatewayAbstract.prepareEventIds = objects;
        return gatewayAbstract;
    }


    /**
     * 开始网关标识符
     */
    private Boolean startGatewayFlag;

    /**
     * 需要满足该集合的event已经运行完成，该网关才继续运行，
     */
    private Set<String> prepareEventIds;

    @Override
    public void setPrepareEventId(Set<String> eventIdIds) {
        this.prepareEventIds = eventIdIds;
    }

    @Override
    public Set<String> getPrepareEventId() {
        return this.prepareEventIds;
    }

    @Override
    public void setOutgoing(List<BaseElement> outgoing) {
        super.setOutgoing(outgoing);
        if (outgoing.size() > 1) {
            startGatewayFlag = true;
        }
    }


    @Override
    public void addOutgoing(BaseElement incoming) {
        super.addOutgoing(incoming);
        if (this.outgoing.size() > 1) {
            startGatewayFlag = true;
        }
    }


    @Override
    public void setStartGatewayFlag(boolean flag) {
        this.startGatewayFlag = flag;
    }

    @Override
    public boolean getEndGatewayFlag() {
        if (this.startGatewayFlag == null || this.startGatewayFlag == true) {
            return false;
        }
        return true;
    }

    @Override
    public void finishPrepareEvent(String id) {
        this.prepareEventIds.remove(id);
    }
}
