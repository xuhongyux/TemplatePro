package bpmn.bo;

import lombok.Data;

import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 节点在运行占用的资源信息
 *
 * @author xuhongyu
 * @create 2022-09-14 11:33
 */

@Data
public class ResourceBo {

    /**
     * 资源Id
     */
    private String resourceId;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源数量
     */
    private Integer resourceNumber;

    /**
     * 消费时间 ：单位：分钟
     */
    private Integer consumptionTime;

//
//    /**
//     * 资源编码
//     */
//    private Integer resourceCode;


    /**
     * 当前资源信号量
     */
    private Semaphore semaphore;

    /**
     * 需要使用该资源的事件名称
     */
    private Set<String> eventNames;


}
