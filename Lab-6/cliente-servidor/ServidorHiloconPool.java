
/**
 * ServidorHiloconPool.java
 * 
 * @author Nicolas Ruiz Requejo
 */

import java.net.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.io.*;

public class ServidorHiloconPool implements Runnable {
    Socket enchufe;

    public ServidorHiloconPool(Socket s) {
        enchufe = s;
    }

    public void run() {

        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(enchufe.getInputStream()));
            String datos = entrada.readLine();
            int j;
            int i = Integer.valueOf(datos).intValue();
            for (j = 1; j <= 20; j++) {
                System.out.println("El hilo " + Thread.currentThread().getName() + " escribiendo el dato " + i);
                Thread.sleep(1000);
            }
            enchufe.close();
            System.out.println("El hilo " + Thread.currentThread().getName() + "cierra su conexion...");
        } catch (Exception e) {
            System.out.println("Error...");
        }

    }// run

    public static void main(String[] args) throws InterruptedException {
        int maxConexiones = 0;
        if (args.length != 1) {
            System.err.println("Uso: ServidorHiloconPool num_conexiones");
            System.exit(-1);
        } else {
            maxConexiones = Integer.parseInt(args[0]);
        }
        int i;
        int puerto = 2001;
        int numTareas = 600;
        int tamPool = 1000;

        long initime = System.nanoTime();

        ThreadPoolExecutor servidorHilos = new ThreadPoolExecutor(numTareas, tamPool, 60000L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        ServerSocket chuff = null;
        try {
            chuff = new ServerSocket(puerto, 3000);

            // while (true) {
            while (maxConexiones > 0) {
                System.out.println("Esperando solicitud de conexion...");
                Socket cable = chuff.accept();
                System.out.println("Recibida solicitud de conexion...");
                servidorHilos.execute(new ServidorHiloconPool(cable));
                maxConexiones--;
            } // while
        } catch (Exception e) {
            servidorHilos.shutdownNow();
            System.out.println("Error en sockets...");
        } finally {
            try {
                if (chuff != null)
                    chuff.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        servidorHilos.shutdown();
        servidorHilos.awaitTermination(1, TimeUnit.DAYS);
        long finaltime = System.nanoTime();

        System.out.println("Tiempo final: " + (((double) (finaltime - initime)) / 1.0e9) + " segundos");

    }// main

}// Servidor_Hilos
