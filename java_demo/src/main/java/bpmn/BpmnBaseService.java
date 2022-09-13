package bpmn;

import java.io.InputStream;
import java.util.List;

/**
 * @author xuhongyu
 * @create 2022-08-26 5:45 下午
 */
public interface BpmnBaseService {

    /**
     * 处理BPMN文件
     * @param inputStream
     * @return
     */
    List<BaseElement> parseBpmnFile( InputStream inputStream);


    /**
     * 创建bpmn元素的关系
     * @param baseElements
     * @return 跟节点
     */
    List<BaseElement> relationCreatBpmn( List<BaseElement> baseElements);

}
