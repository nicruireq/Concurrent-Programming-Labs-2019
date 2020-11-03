import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * triatlonBarreras
 * 
 * @author Nicolas Ruiz Requejo
 */

public class triatlonBarreras implements Runnable {

    private static final int N = 100;
    private static double[] marcas = new double[N];

    private CyclicBarrier barrera;
    private int dorsal;

    public triatlonBarreras(int dorsal, CyclicBarrier b) {
        barrera = b;
        this.dorsal = dorsal;
    }

    @Override
    public void run() {
        long ini, fini;
        try {
            // posta natacion
            System.out.println("Competidor " + dorsal + " esperando a comenzar posta de natacion");
            barrera.await();
            barrera.reset();
            ini = System.currentTimeMillis();
            Thread.sleep(ThreadLocalRandom.current().nextLong(500)+1);
            fini = System.currentTimeMillis();
            marcas[dorsal] += fini - ini;
            // posta carrera ciclista
            System.out.println("Competidor " + dorsal + " esperando a comenzar carrera ciclista");
            barrera.await();
            barrera.reset();
            ini = System.currentTimeMillis();
            Thread.sleep(ThreadLocalRandom.current().nextLong(500)+1);
            fini = System.currentTimeMillis();
            marcas[dorsal] += fini - ini;
            // posta carrera a pie
            System.out.println("Competidor " + dorsal + " esperando a comenzar carrera a pie");
            barrera.await();
            barrera.reset();
            ini = System.currentTimeMillis();
            Thread.sleep(ThreadLocalRandom.current().nextLong(500)+1);
            fini = System.currentTimeMillis();
            marcas[dorsal] += fini - ini;
            marcas[dorsal] = marcas[dorsal] / 1.0e3;
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier barrera = new CyclicBarrier(N);

        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < N; i++) {
            pool.execute(new triatlonBarreras(i, barrera));
        }

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.DAYS);

        double min = Double.MAX_VALUE;
        int ganador = 0;
        for (int i = 0; i < N; i++) {
            if (marcas[i] < min) {
                min = marcas[i];
                ganador = i;
            }
        }

        System.out.println("El gangador es: " + ganador);
        System.out.println(Arrays.toString(marcas));

    }

}