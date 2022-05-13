package javaApi.string;

import json.JsonToolsFastJson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xuhongyu
 * @create 2022-05-12 5:50 下午
 */
public class TestString {
    String reg = "(?<=(?<!\\\\)\\$\\{)(.*?)(?=(?<!\\\\)\\})";

    @Test
    public void splitTest() {
        String name = "夏雨";
        String str = "你好啊！${error.button-label},很高兴${format_version}";

        String property = "带有${姓名}需要匹配的字符串";
        //懒匹配${}
        String regex = "\\$\\{(.*?)}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        //自旋进行最小匹配，直到无法匹配
        while ((matcher = pattern.matcher(str)).find()) {
            String group = matcher.group(1);

            Object format_version = JsonToolsFastJson.getJsonValue(JsonToolsFastJson.JSON_STR, group);

            //替换匹配内容
            str = str.replace(matcher.group(), format_version.toString());
        }
        System.out.println(str);

    }


    public static void main(String[] args) {
        String regex = "\\$\\{(.*?)\\}";
        Map<String, Object> map = new HashMap<>();
        map.put("name", "jake");
        map.put("age", 18);
        map.put("work", "学生");
        map.put("color", "");
        String mes = TestString.getMes("我的朋友是${name},他今年${age}岁,是一名${work},他喜欢的颜色是${color}!", regex, "${", "}", map);
        System.out.println(mes);
    }



    /**
     *  组装信息
     * @param soap 原始信息
     * @param regex  条件 占位符
     * @param startStr  删除掉的内容
     * @param endStr 删除掉的内容
     * @param map 根据原始信息占位符中的内容作为key,获取替换进占位符中的内容
     * @return
     */
    public static String getMes(String soap, String regex, String startStr, String endStr, Map<String, Object> map) {
        List<String> subUtil = getSubUtil(soap, regex);
        for (String s : subUtil) {
            if (map.containsKey(s) && null != map.get(s)) {
                soap = soap.replace(startStr + s + endStr, map.get(s).toString());
            }
        }
        return soap;
    }

    private static List<String> getSubUtil(String soap, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(soap);
        List<String> list = new ArrayList<>();
        int i = 1;
        while (matcher.find()) {
            list.add(matcher.group(i));
        }
        return list;
    }

}
