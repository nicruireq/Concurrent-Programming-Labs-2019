import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ficheroSeguro.java
 * 
 * @author Nicolas Ruiz Requejo
 */

public class ficheroSeguro implements Runnable {

    static Scanner sc = new Scanner(System.in);
    static RandomAccessFile fichero = null;
    private Random gcl;
    private int numEnteros;

    public ficheroSeguro(int cuenta) {
        gcl = new Random();
        this.numEnteros = cuenta;
    }

    @Override
    public void run() {
        synchronized (ficheroSeguro.class) {
            try {
                for (int i = 0; i <= numEnteros; ++i)
                    fichero.writeInt(gcl.nextInt());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        String pathFichero = null;
        int rango = 0;
        if (args.length != 2) {
            System.err.println("USO: ficheroSeguro num_enteros path_fichero");
            System.exit(-1);
        } else {
            rango = Integer.parseInt(args[0]);
            pathFichero = args[1];
        }

        long initime = System.nanoTime();
        try {
            fichero = new RandomAccessFile(pathFichero, "rw");
            int nucleos = Runtime.getRuntime().availableProcessors();
            double cb = 0.7;
            int numHilos = (int) (nucleos / (1 - cb));
            int tamTarea = rango / numHilos;
            int restoTarea = rango % numHilos;
            ExecutorService mipool = Executors.newFixedThreadPool(numHilos);
            // reapartir tareas
            List<Runnable> tareas = new LinkedList<Runnable>();
            for (int i = 0; i < (numHilos - 1); i++) {
                tareas.add(new ficheroSeguro(tamTarea));
            }
            if (restoTarea != 0) {
                tareas.add(new ficheroSeguro(tamTarea + restoTarea));
            } else {
                tareas.add(new ficheroSeguro(tamTarea));
            }

            for (Runnable runnable : tareas) {
                mipool.execute(runnable);
            }

            mipool.shutdown();
            mipool.awaitTermination(10, TimeUnit.DAYS);
            double exetime = ((double)(System.nanoTime() - initime)) / 1.0e9;

            System.out.println("Nucleos: " + nucleos + " Coeficiente de bloqueo: " + cb);
            System.out.println("Numero de hilos en pool: " + numHilos);
            System.out.println("Numero total de enteros a escribir: " + rango);
            System.out.println("Tiempo de ejecucion: " + exetime + " segundos");

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (fichero != null) {
                    fichero.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}