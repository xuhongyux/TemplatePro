package bpmn;

import java.util.List;

/**
 * 事件属性
 * @author xuhongyu
 * @create 2022-08-29 3:19 下午
 */
public interface Event {

    /**
     * 设置来源
     * @param incoming
     */
    void setIncoming(List<BaseElement> incoming);


    /**
     * 添加一个来源
     * @param incoming
     */
    void addIncoming(BaseElement incoming);


    /**
     * 获取来源
     * @return
     */
    List<BaseElement> getIncoming();


    /**
     * 设置目标
     * @param outgoing
     */
    void setOutgoing(List<BaseElement> outgoing);

    /**
     * 获取目标
     * @return
     */
    List<BaseElement> getOutgoing();


    /**
     * 添加一个目标
     * @param incoming
     */
    void addOutgoing(BaseElement incoming);


    /**
     * 根据名称获取出目标节点
     * @param  name
     * @return
     */
    BaseElement getOutGoingByName(String name);



    /**
     * 获取一个目标节点
     * @return
     */
    BaseElement getOneOutGoing();


    /**
     * 检查下个节点是否是结束节点
     *
     * @return 当下一个节点为end节点，或者没有下个节点时返回true
     */
    boolean checkLastOutGoingIsEnd();
}
