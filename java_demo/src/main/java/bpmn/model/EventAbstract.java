package bpmn.model;


import bpmn.BaseElement;
import bpmn.Event;
import bpmn.Gateway;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xuhongyu
 * @create 2022-08-29 5:53 下午
 */
public abstract class EventAbstract implements Event {

    /**
     * 所属资源名称
     */
    public String resourceName;


    /**
     * 消费资源时间 ：单位：分钟
     */
    private Integer consumptionTime;

    /**
     * 入边
     */
    public List<BaseElement> incoming = new ArrayList<>();

    /**
     * 出边
     */
    public List<BaseElement> outgoing = new ArrayList<>();


    @Override
    public void setIncoming(List<BaseElement> incoming) {
        this.incoming = incoming;
    }

    @Override
    public void addIncoming(BaseElement incoming) {
        this.incoming.add(incoming);
    }

    @Override
    public List<BaseElement> getIncoming() {
        return this.incoming;
    }

    @Override
    public void setOutgoing(List<BaseElement> outgoing) {
        this.outgoing = outgoing;
    }

    @Override
    public List<BaseElement> getOutgoing() {
        return this.outgoing;
    }

    @Override
    public void addOutgoing(BaseElement incoming) {
        this.outgoing.add(incoming);
    }

    @Override
    public BaseElement getOutGoingByName(String name) {

        // todo 待改造网关也是节点
        List<BaseElement> baseElements = new ArrayList<>();
        this.outgoing.stream().forEach(
                out -> {
                    if (out instanceof Gateway) {
                        EventAbstract eventAbstract = (EventAbstract) out;
                        baseElements.addAll(eventAbstract.getOutgoing());
                    } else {
                        baseElements.add(out);
                    }
                }
        );

        List<BaseElement> collect = baseElements.stream().filter(
                vo -> Objects.equals(vo.getName(), name)
        ).collect(Collectors.toList());

        // 检查
        if (CollectionUtils.isEmpty(collect)) {
            return null;
        }
        return collect.get(0);
    }

    @Override
    public BaseElement getOutGoingById(String id) {
        List<BaseElement> collect = this.outgoing.stream().filter(
                vo -> Objects.equals(vo.getId(), id)
        ).collect(Collectors.toList());

        // 检查
        if (CollectionUtils.isEmpty(collect)) {
            return null;
        }
        return collect.get(0);
    }


    @Override
    public BaseElement getOneOutGoing() {
        if (CollectionUtils.isEmpty(this.outgoing)) {
            return null;
        }
        return this.outgoing.get(0);
    }

    @Override
    public boolean checkLastOutGoingIsEnd() {
        if (CollectionUtils.isEmpty(this.outgoing)) {
            return true;
        }
        return doCheckLastOutGoingIsEnd(this.outgoing);
    }

    @Override
    public BaseElement getRunNextElement() {
        if (CollectionUtils.isEmpty(this.outgoing)) {
            return new EndEventImpl();
        }
        if (this.outgoing.size() != 1) {
            throw new RuntimeException("下一个节点数量异常");
        }
        BaseElement baseElement = this.outgoing.get(0);

        return baseElement;
    }


    /**
     * 获取所属资源名称
     *
     * @return
     */
    @Override
    public String getResourceName() {
        return this.resourceName;
    }


    /**
     * 设置资源名称
     */
    @Override
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * 获取消费时间
     *
     * @return
     */
    @Override
    public Integer getConsumptionTime() {
        return this.consumptionTime;
    }

    /**
     * 设置消费时间
     *
     * @param consumptionTime
     */
    @Override
    public void setConsumptionTime(Integer consumptionTime) {
        this.consumptionTime = consumptionTime;
    }


    /**
     * 检查是否是结束节点
     *
     * @param outgoing
     * @return
     */
    private boolean doCheckLastOutGoingIsEnd(List<BaseElement> outgoing) {
        for (BaseElement baseElement : outgoing) {
            if (baseElement instanceof EndEventImpl) {
                return true;
            }

            if (baseElement instanceof ParallelGatewayImpl) {
                ParallelGatewayImpl parallelGateway = (ParallelGatewayImpl) baseElement;
                return doCheckLastOutGoingIsEnd(parallelGateway.getOutgoing());
            }
        }
        return false;
    }


}
