package bpmn.model;


import bpmn.BaseElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 并行网关
 *
 * @author xuhongyu
 * @create 2022-08-29 3:00 下午
 */
public class ParallelGatewayImpl extends  GatewayAbstract{

    @Override
    public void addEventFrequency(String eventId, Integer frequency) {

    }


    @Override
    public List<BaseElement> getRunNextElement() {
        boolean endGatewayFlag = this.getEndGatewayFlag();

        // 如果是开始网关
        if(! endGatewayFlag){
            return this.outgoing;
        }

        Set<String> prepareEventId = this.getPrepareEventId();
        // 结束网关并且前置资源准备完毕
        if (prepareEventId.size() == 0) {
            return this.outgoing;
        }
        return new ArrayList<>();
    }
}
