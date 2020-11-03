import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * semafMonitor
 * 
 * @author Nicolas Ruiz Requejo
 */

public class semafMonitor {
    private int s;
    private ReentrantLock cerrojo;
    private Condition bloqueados;

    public semafMonitor(int valorInicial) {
        s = valorInicial;
        cerrojo = new ReentrantLock();
        bloqueados = cerrojo.newCondition();
    }

    public void waitDijkstra() {
        cerrojo.lock();
        try {
            while (s == 0) {
                try {
                    System.out.println("Seccion critica ocupada, me bloqueo");
                    bloqueados.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            s--;
        } finally {
            cerrojo.unlock();
        }
    }

    public void signal() {
        cerrojo.lock();
        try {
            s++;
            bloqueados.signal();
        } finally {
            cerrojo.unlock();
        }
    }
}