/**
 * @author  Nicolás Ruiz Requejo
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.*;

class tareaPrimos implements Runnable {

    private final long linf;
    private final long lsup;
    private AtomicLong miTotal;

    public tareaPrimos(long linf, long lsup, AtomicLong total) {
        this.linf = linf;
        this.lsup = lsup;
        this.miTotal = total;
    }

    public boolean esPrimo(long n) {
        if (n <= 1)
            return (false);
        for (long i = 2; i <= Math.sqrt(n); i++)
            if (n % i == 0)
                return (false);
        return (true);
    }

    @Override
    public void run() {
        for (long i = linf; i <= lsup; i++)
            if (esPrimo(i))
                miTotal.incrementAndGet();
    }

}

public class primosRunnable {
    public static void main(String[] args) {
        // Uso: primosRunnable numhilos enteroinferior enterosuperior
        if (args.length != 3) {
            System.err.println("Uso: primosRunnable numhilos enteroinferior enterosuperior");
            System.exit(-1);
        }
        // numero de tareas
        int nThreads = Integer.parseInt(args[0]);
        // entero inferior del rango de busqueda
        int rangeInf = Integer.parseInt(args[1]);
        // entero superior del rango de busqueda
        int rangeSup = Integer.parseInt(args[2]);
        // para acumular num total de primos encontrados
        AtomicLong primosTotal = new AtomicLong();
        
        // tomar tiempo
        long inicioComputacion = System.nanoTime();
        
        // pool
        ExecutorService mipool = Executors.newFixedThreadPool(nThreads);
        // reparto de tareas
        long tVentana = (rangeSup - rangeInf) / nThreads;
        long linf = rangeInf;
        long lsup = rangeInf + tVentana;
        
        // submit tasks
        for (int i = 0; i < nThreads; ++i) {
            //System.out.println("iteracion " + i + " linf: " + linf +  " lsup: " + lsup);
            mipool.execute(new tareaPrimos(linf, lsup, primosTotal));
            linf = lsup + 1;
            lsup += tVentana;
        }
        // esperar a terminar
        mipool.shutdown();
        while (!mipool.isTerminated())
            ;
        long tiempoTotal = (System.nanoTime() - inicioComputacion) / (long) 1.0e9;

        System.out.println("Primos hallados: " + primosTotal);
        System.out.println("Calculo finalizado en " + tiempoTotal + " segundos");
    }

}