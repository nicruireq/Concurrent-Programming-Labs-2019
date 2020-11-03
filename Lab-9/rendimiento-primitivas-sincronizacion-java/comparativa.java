import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * comparativa
 * 
 * @author Nicolas Ruiz Requejo
 */

public class comparativa {

    private int n = 0;
    private final ReentrantLock lock = new ReentrantLock();
    private final Semaphore sem = new Semaphore(1);
    private AtomicInteger nat = new AtomicInteger(0);

    public long criticalSectionSynchro(long iter) {
        long ini = System.nanoTime();
        for (long i = 0; i < iter; i++) {
            synchronized (this) {
                n++; // seccion critica
            }
        }
        long fin = System.nanoTime();
        return (fin - ini);
    }

    public long criticalSectionLock(long iter) {
        long ini = System.nanoTime();
        for (long i = 0; i < iter; i++) {
            synchronized (comparativa.class) {
                lock.lock();
                try {
                    n++;
                } finally {
                    lock.unlock();
                }
            }
        }
        long fin = System.nanoTime();
        return (fin - ini);
    }

    public long criticalSectionSem(long iter) {
        long ini = System.nanoTime();
        for (long i = 0; i < iter; i++) {
            try {
                sem.acquire();
            } catch (InterruptedException e) {
            }
            n++;
            sem.release();
        }
        long fin = System.nanoTime();
        return (fin - ini);
    }

    public long criticalSectionAtom(long iter) {
        long ini = System.nanoTime();
        for (long i = 0; i < iter; i++) {
            synchronized (comparativa.class) {
                nat.incrementAndGet();
            }
        }
        long fin = System.nanoTime();
        return (fin - ini);
    }

    public synchronized void reset() {
        n = 0;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Uso: comparativa num_iter");
            System.exit(-1);
        }
        int iters = Integer.parseInt(args[0]);
        comparativa comp = new comparativa();

        long tiempoSynchronized = comp.criticalSectionSynchro(iters);
        comp.reset();
        long tiempoLock = comp.criticalSectionLock(iters);
        comp.reset();
        long tiempoSem = comp.criticalSectionSem(iters);
        comp.reset();
        long tiempoAtomic = comp.criticalSectionAtom(iters);

        System.out.println("Tiempo con synchronized: " + (tiempoSynchronized / 1.0e6));
        System.out.println("Tiempo con ReentrantLock: " + (tiempoLock / 1.0e6));
        System.out.println("Tiempo con Semaphore: " + (tiempoSem / 1.0e6));
        System.out.println("Tiempo con AtomicInteger: " + (tiempoAtomic / 1.0e6));
    }

}