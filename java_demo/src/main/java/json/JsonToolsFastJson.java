package json;

import com.alibaba.fastjson.JSONObject;
/**
 * @author xuhongyu
 * @create 2022-05-11 12:10 下午
 */
public class JsonToolsFastJson {

    private static final String JSON_VALUE_SPLIT = ".";

    public static String JSON_STR = "{\n" +
            "    \"format_version\":2,\n" +
            "    \"drift\":1,\n" +
            "    \"session\":{\n" +
            "        \"plan\":2\n" +
            "    },\n" +
            "    \"span_id\":\"8575671047394760650\",\n" +
            "    \"trace_id\":\"1548153819661724856\",\n" +
            "    \"error\":{\n" +
            "        \"button-label\":\"Back to Login\",\n" +
            "        \"error-401.title\":\"Access Denied\"\n" +
            "    }\n" +
            "}";


    public static void main(String[] args) {
        Object format_version = getJsonValue(JSON_STR, "format_version");
        System.out.println(format_version.toString());


        Object jsonValue = getJsonValue(JSON_STR, "error.button-label");
        System.out.println(jsonValue.toString());
    }

    public static Object getJsonValue(Object jsonObj, String valueName) {
        JSONObject jsonObjectResource = null;
        if (jsonObj instanceof String) {
            jsonObjectResource = JSONObject.parseObject((String) jsonObj);
        }
        if (jsonObj instanceof JSONObject) {
            jsonObjectResource = (JSONObject) jsonObj;
        }

        boolean contains = valueName.contains(JSON_VALUE_SPLIT);

        if (contains) {
            String valueNameFirst = valueName.substring(0, valueName.indexOf(JSON_VALUE_SPLIT));
            String valueNameLast = valueName.substring(valueName.indexOf(JSON_VALUE_SPLIT) + 1);

            Object obj = jsonObjectResource.get(valueNameFirst);
            Object jsonValue = getJsonValue(obj, valueNameLast);
            return  jsonValue;
        }

        String value = jsonObjectResource.get(valueName).toString();
        return value;
    }

}
