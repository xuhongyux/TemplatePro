package bpmn;


/**
 * 基础元素
 *
 * @author xuhongyu
 * @create 2022-08-29 2:13 下午
 */
public interface BaseElement {

    /**
     * 获取Id
     *
     * @return
     */
    String getId();


    /**
     * 设置Id
     *
     * @param id
     */
    void setId(String id);

    /**
     * 获取名称
     *
     * @return
     */
    String getName();

    /**
     * 设置名称
     *
     * @param name
     */
    void setName(String name);


}
