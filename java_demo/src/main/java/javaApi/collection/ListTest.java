package javaApi.collection;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author xuhongyu
 * @create 2021-04-16 18:32
 */
public class ListTest {

    @Test
    public void retainAllTest(){
        List<Integer> stringList = Lists.newArrayList();
        stringList.add(1);
        stringList.add(2);
        stringList.add(3);

        List<Integer> v = Lists.newArrayList();
        v.add(1);
        stringList.retainAll(v);
        System.out.println(stringList);
    }

    public static void main(String[] args) {
        List<Integer> stringList = Lists.newArrayList();
        Random random1 = new Random();
        Random random2 = new Random();
        Random random3 = new Random();
        Random random4 = new Random();
        Random random5 = new Random();
        for (int i = 0; i <10000000; i++) {
            stringList.add(random1.nextInt(100));
            stringList.add(random2.nextInt(100));
            stringList.add(random3.nextInt(100));
            stringList.add(random4.nextInt(100));
            stringList.add(random5.nextInt(100));
        }
        System.out.println("List生成完毕");

        Date date = new Date();
        Map<Integer, Integer> objectObjectMap = Maps.newHashMap();
        for (int i = stringList.size()-1; i >=0 ; i--) {
            Integer integer = stringList.remove(i);
            if(objectObjectMap.containsKey(integer)){
                Integer value = objectObjectMap.get(integer)+1;
                objectObjectMap.put(integer,value);
            } else {
                objectObjectMap.put(integer,1);
            }
        }

        System.out.println(stringList);


        Date date1 = new Date();
        System.out.println((date1.getTime() - date.getTime())/1000);


    }
}
