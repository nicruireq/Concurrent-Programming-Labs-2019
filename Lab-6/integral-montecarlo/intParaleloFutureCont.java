import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * intParalelauniCont.java
 * 
 * @author Nicolas Ruiz Requejo
 */

public class intParaleloFutureCont implements Callable<Integer> {

    static int cuentaPuntos = 0;

    static double areaSeno(double x0, double x1) {
        return (x1 - x0) * Math.max(Math.abs(Math.sin(x0)), Math.abs(Math.sin(x1)));
    }

    private int cantidad;
    private Random gen;
    private int contadorLocal;

    public intParaleloFutureCont(int cantidad) {
        this.cantidad = cantidad;
        gen = new Random();
        contadorLocal = 0;
    }

    public int getCuenta() {
        return contadorLocal;
    }

    @Override
    public Integer call() throws Exception {
        double xaleat, yaleat;

        for (int i = 0; i < cantidad; i++) {
            xaleat = gen.nextDouble();
            yaleat = gen.nextDouble();
            if (Math.sin(xaleat) <= yaleat) {
                ++contadorLocal;
            }
        }
        return contadorLocal;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int maxPuntos = 0;
        if (args.length != 1) {
            System.err.println("Uso: intParalelauniCont num_puntos");
            System.exit(-1);
        } else {
            maxPuntos = Integer.parseInt(args[0]);
        }

        long initime = System.nanoTime();

        int nucleos = Runtime.getRuntime().availableProcessors();
        int cb = 0;
        int numHilos = (int) (nucleos / (1 - cb));
        int numTareas = maxPuntos / numHilos;
        int restoTareas = maxPuntos % numHilos;

        ExecutorService mipool = Executors.newFixedThreadPool(numHilos);
        intParaleloFutureCont[] tareas = new intParaleloFutureCont[numHilos];

        // reparto
        for (int i = 0; i < (numHilos - 1); i++) {
            tareas[i] = new intParaleloFutureCont(numTareas);
        }
        if (restoTareas != 0) {
            tareas[numHilos - 1] = new intParaleloFutureCont(numTareas + restoTareas);
        } else {
            tareas[numHilos - 1] = new intParaleloFutureCont(numTareas);
        }

        List<Future<Integer>> resultadosParciales = new LinkedList<Future<Integer>>();
        for (intParaleloFutureCont t : tareas) {
            resultadosParciales.add(mipool.submit(t));
        }

        mipool.shutdown();
        
        // recolectar resultados parciales
        for (Future<Integer> future : resultadosParciales) {
            cuentaPuntos += future.get();
        }
        // calcular aproximacion
        double resultado = (((double) cuentaPuntos) / ((double) maxPuntos)) * areaSeno(0, 1);

        double exetime = ((double) (System.nanoTime() - initime)) / 1.0e9;

        System.out.println("Nucleos: " + nucleos + " Coeficiente de bloqueo: " + cb);
        System.out.println("Numero de hilos en pool: " + numHilos);
        System.out.println("Aproximación: " + resultado);
        System.out.println("Tiempo de ejecucion: " + exetime + " segundos");

    }

}