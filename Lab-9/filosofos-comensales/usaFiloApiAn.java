import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * usaFiloApiAn
 * 
 * @author Nicolas Ruiz Requejo
 */

public class usaFiloApiAn implements Runnable {

    private filoApiAN mesa;
    private int filo;

    public usaFiloApiAn(filoApiAN mesa, int filo) {
        this.mesa = mesa;
        this.filo = filo;
    }

    private void pensar(int i) {
        System.out.println("El filosofo " + i + " esta pensando.");
        for (int j = 0; j < 100000; j++)
            ;
    }

    private void comer(int i) {
        System.out.println("El filosofo " + i + " esta comiendo.");
        for (int j = 0; j < 100000; j++)
            ;
    }

    @Override
    public void run() {
        for (;;) {
            pensar(filo);
            mesa.tomarPalillos(filo);
            comer(filo);
            mesa.soltarPalillos(filo);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        filoApiAN mesa = new filoApiAN();
        ExecutorService pool = Executors.newFixedThreadPool(filoApiAN.filosofos);

        for (int i = 0; i < filoApiAN.filosofos; i++) {
            pool.execute(new usaFiloApiAn(mesa, i));
        }

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.DAYS);
    }

}