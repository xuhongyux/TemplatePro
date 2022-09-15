package bpmn.bo;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * @author xuhongyu
 * @create 2022-09-13 17:52
 */
@Data
public class ResourceInfoBo {

    public ResourceInfoBo() {

    }

    public static ResourceInfoBo buildReResourceBo() {
        ResourceInfoBo resourceInfoBo = new ResourceInfoBo();

        resourceInfoBo.setResourceMap(Maps.newHashMap());

        resourceInfoBo.setResourceTypeNumber(3);

        List<ResourceBo> resourceBos = new ArrayList<>();
        resourceInfoBo.setResourceBos(resourceBos);

        final Semaphore semaphore = new Semaphore(5);


        ResourceBo resourceBo = new ResourceBo();
        resourceBo.setResourceName("资源一(用户)");
        resourceBo.setResourceNumber(5);
        resourceBo.setConsumptionTime(20);
        resourceBo.setSemaphore(semaphore);
        Set<String> eventNames = new HashSet<>();
        eventNames.add("接到订单");
        eventNames.add("用户使用产品");

        resourceBo.setEventNames(eventNames);

        ResourceBo resourceBo1 = new ResourceBo();
        resourceBo1.setResourceName("资源二（客服）");
        resourceBo1.setResourceNumber(3);
        resourceBo1.setConsumptionTime(10);
        final Semaphore semaphore1 = new Semaphore(3);
        resourceBo1.setSemaphore(semaphore1);


        ResourceBo resourceBo2 = new ResourceBo();
        resourceBo2.setResourceName("资源三（经理）");
        resourceBo2.setResourceNumber(2);
        resourceBo2.setConsumptionTime(10);
        final Semaphore semaphore2 = new Semaphore(2);
        resourceBo2.setSemaphore(semaphore2);


        resourceBos.add(resourceBo2);
        resourceBos.add(resourceBo1);
        resourceBos.add(resourceBo);

        return resourceInfoBo;
    }


    /**
     * Key 资源ID，value 资源个数
     */
    private Map<String, Integer> resourceMap;


    /**
     * 资源信息
     */
    private List<ResourceBo> resourceBos;


    /**
     * 资源类型数量
     */
    private Integer resourceTypeNumber;

}
