import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Nicolas Ruiz Requejo
 */

public class UsamonitorImpresion implements Runnable {

    private ImpMonitor monitor;
    private int id;

    public UsamonitorImpresion(int id, ImpMonitor mon) {
        monitor = mon;
        this.id = id;
    }

    @Override
    public void run() {
        int miImpresora = monitor.adquirirImpresora(id);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        monitor.liberarImpresora(id, miImpresora);
    }

    public static void main(String[] args) throws InterruptedException {
        ImpMonitor monitor = new ImpMonitor();

        ExecutorService pool = Executors.newCachedThreadPool();

        for (int i = 0; i < 6; i++) { 
            pool.execute(new UsamonitorImpresion(i, monitor));
        }

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.DAYS);
        
    }

}