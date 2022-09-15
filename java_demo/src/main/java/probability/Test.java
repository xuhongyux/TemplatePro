package probability;

/**
 * 概率事件
 *
 * @author xuhongyu
 * @create 2022-09-13 18:10
 */
public class Test {

//
//    public static void main(String[] args) {
//
//        Map m = new HashMap<>();
//        m.put(0, 10);
//        m.put(1, 20);
//        m.put(2, 30);
//        m.put(3, 40);
//
//        int[] h2 = new int[4];
//
//        for (int i = 0; i < h2.length; i++) {
//
//            h2[i] = 1;
//        }
//
//        Random random = new Random();
//
//        for (int x = 0; x < 200000000; x++) {
//
//            int i = 0;
//            int j = 0;
//            int r = random.nextInt(99);
//
//            for (Map.Entry entry : m.entrySet()) {
//
//                j = i + entry.getValue() - 1;
//
//                if (r >= i && r <= j) {
//
//                    h2[entry.getKey()]++;
//
//                    break;
//
//                }
//
//                i += entry.getValue();
//
//            }
//
//        }
//
//        for (int i : h2) {
//
//            System.out.printf("%f ", i / 2000000.0);
//
//        }
//
//    }
}
