import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * algoLamport
 * 
 * Adaptado de Palma
 * 
 * @author Nicolas Ruiz Requejo
 */

public class algoLamport implements Runnable {

    public static final int iteraciones = 10000;
    public static volatile int contador = 0;

    // Numero de procesos
    public static final int N = 2;
    // Array para indicar si un proceso ha cogido numero o no
    public static volatile boolean[] C = new boolean[N];
    // Array con el numero cogido por cada proceso
    public static volatile int[] numero = new int[N];
    // id asignado al proceso
    private int id;

    public algoLamport(int i) {
        if (i < 0 || i >= N)
            throw new IllegalArgumentException();
        id = i;
    }

    private int max(int[] vec) {
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < vec.length; ++i) {
            if (vec[i] > res)
                res = vec[i];
        }

        return res;
    }

    @Override
    public void run() {
        for (int k = 0; k < iteraciones; k++) {
            C[id] = true;
            numero[id] = 1 + max(numero);
            C[id] = false;
            for (int j = 0; j < N; j++) {
                // El proceso espera hasta que todos los demas cojan numero
                while (C[j] == false)
                    ;
                // el proceso espera mientras haya alguno que no tenga numero y
                // haya alguno con numero o id de proceso menor que el suyo
                while ( (numero[j] != 0) && ( (numero[id] > numero[j]) || ((numero[id] == numero[j]) && (id > j)) ) )
                    ;
            }
            // seccion critica
            contador++;
            // fin S.c
            numero[id] = 0;
            // resto
        }
    }

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < N; i++) {
            C[i] = false;
            numero[i] = 0;
        }

        ExecutorService pool = Executors.newFixedThreadPool(N);

        for (int i = 0; i < N; i++) {
            pool.execute(new algoLamport(i));
        }

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.DAYS);

        System.out.println("Contador vale = " + contador);
        System.out.println("Tiene que valer " + (N * iteraciones));
    }

}