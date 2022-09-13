package javaApi.string;

/**
 * @author xuhongyu
 * @create 2022-09-09 5:12 下午
 */
public class StringLongTest {
    public static void main(String[] args) {
        StringLongTest stringLongTest = new StringLongTest();
        String s = stringLongTest.buildString(655350);
        System.out.println(s);
    }

    public String buildString(Integer intSize) {
        String str = "";
        for (Integer i = 0; i < intSize; i++) {
            str += "s";
        }
        return str;
    }
}
