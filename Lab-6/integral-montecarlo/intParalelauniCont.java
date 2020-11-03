import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * intParalelauniCont.java
 * 
 * @author Nicolas Ruiz Requejo
 */

public class intParalelauniCont implements Runnable {

    static int cuentaPuntos = 0;

    static double areaSeno(double x0, double x1) {
        return (x1 - x0) * Math.max(Math.abs(Math.sin(x0)), Math.abs(Math.sin(x1)));
    }

    private int cantidad;

    public intParalelauniCont(int cantidad) {
        this.cantidad = cantidad;

    }

    @Override
    public void run() {
        Random gen = new Random();
        double xaleat, yaleat;

        for (int i = 0; i < cantidad; i++) {
            xaleat = gen.nextDouble();
            yaleat = gen.nextDouble();
            if (Math.sin(xaleat) <= yaleat) {
                synchronized (intParalelauniCont.class) {
                    ++cuentaPuntos;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
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
        intParalelauniCont[] tareas = new intParalelauniCont[numHilos];

        // reparto
        for (int i = 0; i < (numHilos - 1); i++) {
            tareas[i] = new intParalelauniCont(numTareas);
        }
        if (restoTareas != 0) {
            tareas[numHilos - 1] = new intParalelauniCont(numTareas + restoTareas);
        } else {
            tareas[numHilos - 1] = new intParalelauniCont(numTareas);
        }

        for (intParalelauniCont t : tareas) {
            mipool.execute(t);
        }

        mipool.shutdown();
        mipool.awaitTermination(10, TimeUnit.DAYS);
        
        // calcular resultado
        double resultado = (((double)cuentaPuntos) / ((double)maxPuntos)) * areaSeno(0, 1);

        double exetime = ((double)(System.nanoTime()-initime)) / 1.0e9;

        System.out.println("Nucleos: " + nucleos + " Coeficiente de bloqueo: " + cb);
        System.out.println("Numero de hilos en pool: " + numHilos);
        System.out.println("Aproximación: " + resultado);
        System.out.println("Tiempo de ejecucion: " + exetime + " segundos");

    }

}