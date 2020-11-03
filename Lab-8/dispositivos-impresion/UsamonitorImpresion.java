import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Nicolas Ruiz Requejo
 */

public class UsamonitorImpresion implements Runnable {

    private monitorImpresion monitor;
    private int id;

    public UsamonitorImpresion(int id, monitorImpresion mon) {
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
        monitorImpresion monitor = new monitorImpresion();

        ExecutorService pool = Executors.newCachedThreadPool();

        for (int i = 0; i < 6; i++) { 
            pool.execute(new UsamonitorImpresion(i, monitor));
        }

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.DAYS);
        
    }

}