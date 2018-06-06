import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Processor {
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
    private static Processor instance = null;

    public static Processor getInstance() {
        if (instance == null) {
            instance = new Processor();
        }
        return instance;
    }

    public void runTask(BaseTask task) {
        if (task != null)
            fixedThreadPool.execute(task);
    }

    private Processor() {}
}
