import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * atomicServer
 * 
 * @author Nicolas Ruiz Requejo
 */

public class atomicServer implements Runnable {
    private Socket enchufe;
    private AtomicLong contador;

    public atomicServer(Socket s, AtomicLong cont) {
        enchufe = s;
        contador = cont;
    }

    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(enchufe.getInputStream()));
            String datos = entrada.readLine();
            System.out.println("El hilo " + Thread.currentThread().getName() + " esta sirviendo la pagina web");
            Thread.sleep(1000);
            long val = contador.incrementAndGet();
            System.out.println("El hilo " + Thread.currentThread().getName() + " actualiza contador a: " + val);
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
        AtomicLong contadorPaginas = new AtomicLong(0);
        try {
            ServerSocket chuff = new ServerSocket(puerto, 3000);

            while (true) {
                System.out.println("Contador de paginas actual = " + contadorPaginas.get());
                System.out.println("Esperando solicitud de conexion...");
                Socket cable = chuff.accept();
                System.out.println("Recibida solicitud de conexion...");
                servidorHilos.execute(new atomicServer(cable, contadorPaginas));
            } // while
        } catch (Exception e) {
            System.out.println("Error en sockets...");
        }
    }// main
}