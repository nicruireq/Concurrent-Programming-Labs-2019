import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Nicolas Ruiz Requejo
 */

enum TipoHilo {
    LECTOR, ESCRITOR
}

public class usalectorEscritor implements Runnable {

    private TipoHilo tipoHilo;
    private lectorEscritor keeper;

    public usalectorEscritor(TipoHilo tipoHilo, lectorEscritor keeper) {
        this.tipoHilo = tipoHilo;
        this.keeper = keeper;
    }

    @Override
    public void run() {
        switch (tipoHilo) {
            case LECTOR:
                keeper.iniciaLectura();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                keeper.finLectura();
                break;
            case ESCRITOR:
                keeper.iniciaEscritura();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                keeper.finEscritura();
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        lectorEscritor monitor = new lectorEscritor();

        ExecutorService pool = Executors.newCachedThreadPool();
        
        for (int i = 0; i < 10; i++) {
            pool.execute(new usalectorEscritor(TipoHilo.LECTOR, monitor));
        }
        for (int i = 0; i < 3; i++) {
            pool.execute(new usalectorEscritor(TipoHilo.ESCRITOR, monitor));
        }

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.DAYS);
    }
}