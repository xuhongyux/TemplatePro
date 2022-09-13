package bpmn.model;


import bpmn.BaseElement;
import bpmn.SequenceFlow;

/**
 * 线
 *
 * @author xuhongyu
 * @create 2022-08-29 2:57 下午
 */

public class SequenceFlowImpl implements BaseElement, SequenceFlow {

    private String id;

    private String name;

    private String targetRefId;

    private String sourceRefId;

    @Override
    public String getSourceRefId() {
        return this.sourceRefId;
    }

    @Override
    public void setSourceRefId(String baseElement) {
        this.sourceRefId = baseElement;
    }

    @Override
    public String getTargetRefId() {
        return this.targetRefId;
    }

    @Override
    public void setTargetRefId(String targetRefId) {
        this.targetRefId = targetRefId;
    }

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
}
