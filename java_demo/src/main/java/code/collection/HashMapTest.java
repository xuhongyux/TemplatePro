package code.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuhongyu
 * @create 2022-06-23 7:28 下午
 */
public class HashMapTest {

    public static void main(String[] args) {
        Map<Object, Object> hashMap = new HashMap<>();

        hashMap.put(1,1);

        System.out.println(hashMap.toString());
    }
}
