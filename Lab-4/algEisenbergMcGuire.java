import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * algEisenbergMcGuire
 * 
 * Adaptado de Palma
 * 
 * @author Nicolas Ruiz Requejo
 */

public class algEisenbergMcGuire implements Runnable {

    enum Estado {
        restoproceso, quiereentrar, enSC
    }

    public static final int N = 4;
    public static final int iteraciones = 1000000;
    public static volatile int contador = 0;
    public volatile static Estado[] indicador = new Estado[4];
    public volatile int indice;

    private final int id;

    public algEisenbergMcGuire(int i) {
        id = i;
    }

    @Override
    public void run() {
        int j;
        for (int i = 0; i < iteraciones; i++) {
            j = indice;
            while (j != id)
                if (indicador[j] != Estado.restoproceso)
                    j = indice;
                else
                    j = (j + 1) % N;
            indicador[id] = Estado.enSC;
            j = 0;
            while ((j < N) && ((j == id) || (indicador[j] != Estado.enSC)))
                j = j + 1;
            while ((j >= N) && ((indice == i) || (indicador[indice] == Estado.restoproceso)))
                ;
            indice = id;
            // Seccion critica
            contador++;
            // fin S.C
            j = (indice + 1) % N;
            while (indicador[j] == Estado.restoproceso)
                j = (j + 1) % N;
            indice = j;
            indicador[i] = Estado.restoproceso;
        }
    }

    public static void main(String[] args) {
        try {
            ExecutorService pool = Executors.newFixedThreadPool(2);

            pool.execute(new algEisenbergMcGuire(1));
            pool.execute(new algEisenbergMcGuire(2));

            pool.shutdown();
            pool.awaitTermination(10, TimeUnit.DAYS);

            System.out.println("Contador vale = " + contador);
            System.out.println("Y tenia que valer = " + (2 * iteraciones));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}