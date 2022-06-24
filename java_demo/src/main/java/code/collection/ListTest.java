package code.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xuhongyu
 * @create 2022-06-23 8:03 下午
 */
public class ListTest {

    public static void main(String[] args) {

        List<String> objects = new ArrayList<>();
        objects.add("12");

        Integer[] integer = new Integer[] { 1, 2, 3, 4 };
        List lists = Arrays.asList(integer);
        lists.add(5);
    }
}
