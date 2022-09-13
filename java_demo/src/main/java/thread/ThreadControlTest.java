package thread;

/**
 * 线程控制
 * @author xuhongyu
 * @create 2022-09-09 11:27 上午
 */
public class ThreadControlTest {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ThreadTest th1 = new ThreadTest();
        Thread t1 = new Thread(th1);
        t1.start();
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //thread线程暂停
        th1.waitThread();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //thread线程继续运行
        th1.startThread();
        //th1.wait1();
        //th1.start1();
    }
}
