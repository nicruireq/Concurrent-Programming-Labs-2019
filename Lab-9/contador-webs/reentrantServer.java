import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * reentrantServer
 * 
 * @author Nicolas Ruiz Requejo
 */

public class reentrantServer implements Runnable {
    private Socket enchufe;
    private static long contador = 0;
    private ReentrantLock cerrojo;

    public reentrantServer(Socket s, ReentrantLock l) {
        enchufe = s;
        cerrojo = l;
    }

    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(enchufe.getInputStream()));
            String datos = entrada.readLine();
            System.out.println("El hilo " + Thread.currentThread().getName() + " esta sirviendo la pagina web");
            Thread.sleep(1000);
            cerrojo.lock();
            try {
                contador++;
            } finally {
                cerrojo.unlock();
            }
            System.out.println("El hilo " + Thread.currentThread().getName() + " actualiza contador a: " + contador);
            enchufe.close();
            System.out.println("El hilo " + Thread.currentThread().getName() + "cierra su conexion...");
        } catch (Exception e) {
            System.out.println("Error...");
        }
    }// run

    public static void main(String[] args) {
        int puerto = 2001;
        int numTareas = 600;
        int tamPool = 1000;
        ThreadPoolExecutor servidorHilos = new ThreadPoolExecutor(numTareas, tamPool, 60000L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        ReentrantLock cerrojo = new ReentrantLock();
        try {
            ServerSocket chuff = new ServerSocket(puerto, 3000);

            while (true) {
                System.out.println("Contador de paginas actual = " + contador);
                System.out.println("Esperando solicitud de conexion...");
                Socket cable = chuff.accept();
                System.out.println("Recibida solicitud de conexion...");
                servidorHilos.execute(new reentrantServer(cable, cerrojo));
            } // while
        } catch (Exception e) {
            System.out.println("Error en sockets...");
        }
    }// main
}