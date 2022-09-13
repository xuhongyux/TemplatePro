package thread;

/**
 * @author xuhongyu
 * @create 2022-09-09 1:11 下午
 */
public class ThreadTest implements Runnable  {
    private String flag = "start";
    private String control = "";

    @Override
    public void run() {
        // TODO Auto-generated method stub
        int i = 0;
        while (true) {
            if (flag.equals("start")) {
                i++;
                System.out.println("The thread1 is running" + i);
            } else if (flag.equals("wait")) {
                try {
                    System.out.println("===wait===");
                    synchronized (control) {
                        control.wait();
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public void waitThread() {
        this.flag = "wait";
    }

    public void startThread() {
        this.flag = "start";
        if ("start".equals(flag)) {
            synchronized (control) {
                control.notifyAll();
            }
        }
    }

}
