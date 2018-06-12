import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
* Processor: 处理类(单例模式), 有一个定长线程池, 用类处理指定数量的请求
* 处理的请求包括登录请求、温控请求、状态信息处理请求等
 */
public class Processor {
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
    private static Processor instance = null;

    public static Processor getInstance() {
        if (instance == null) {
            instance = new Processor();
        }
        return instance;
    }

    // 传入一个Runnable对象, 表示需要处理的请求
    public void runTask(Runnable task) {
        if (task != null)
            fixedThreadPool.execute(task);
    }

    private Processor() {}
}
