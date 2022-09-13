package bpmn;


/**
 *
 * 线属性
 * @author xuhongyu
 * @create 2022-08-29 2:52 下午
 */
public interface SequenceFlow {


    /**
     * 获取来源
     * @return
     */
    String getSourceRefId();

    /**
     * 设置来源
     * @param sourceRefId
     */
    void setSourceRefId(String sourceRefId);


    /**
     * 获取目标
     * @return
     */
    String getTargetRefId();

    /**
     * 设置目标
     * @param targetRefId
     */
    void setTargetRefId(String targetRefId);
}
