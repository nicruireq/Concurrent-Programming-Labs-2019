import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Nicolas Ruiz Requejo
 */

public class drakkarVikingo {
    private int marmita;
    private final int anguilas;

    public drakkarVikingo(int m) {
        anguilas = m;
        marmita = m;
    }

    public synchronized void comer(long vikingo) {
        System.out.println("Vikingo " + vikingo + " quiere comer.");
        while (marmita == 0) {
            notifyAll();
            System.out.println("Vikingo " + vikingo + " no puede comer, la marmita vacia. Despertando a cocinero.");
            try {
                System.out.println("Vikingo " + vikingo + " se duerme esperando");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        marmita--;
        System.out.println("Vikingo " + vikingo + " se ha comido una anguila. Quedan: " + marmita);
    }

    public synchronized void cocinar(long cocinero) {
        while (marmita > 0) {
            try {
                System.out.println("El cocinero " + cocinero + " ve que hay comida y se va a dormir.");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("El cocinero " + cocinero + " se despierta y cocina.");
        marmita = anguilas;
        notifyAll();
        System.out.println("El cocinero " + cocinero + " va a llamar a los vikingos que estaban esperando");
    }

    public static void main(String[] args) throws InterruptedException {
        int m = 5;
        drakkarVikingo drakkar = new drakkarVikingo(m);

        Runnable vikingo = () -> {
            for (;;) {
                drakkar.comer(Thread.currentThread().getId());
                for (int i = 0; i < 100000; i++)
                    ;
            }
        };

        Runnable cocinero = () -> {
            for (;;) {
                drakkar.cocinar(Thread.currentThread().getId());
                for (int i = 0; i < 100000; i++)
                    ;
            }
        };

        Thread t1 = new Thread(vikingo);
        Thread t3 = new Thread(cocinero);
        t1.start();
        t3.start();
        t1.join();
        t3.join();

    }

}