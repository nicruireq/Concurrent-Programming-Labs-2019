import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ImpMonitor
 * 
 * @author Nicolas Ruiz Requejo
 */

public class ImpMonitor {
    private static final int NUM_libres = 3;

    private boolean[] libres;
    private int impresoras;
    private ReentrantLock cerrojo;
    private Condition condImpresoras;

    public ImpMonitor() {
        cerrojo = new ReentrantLock();
        condImpresoras = cerrojo.newCondition();
        libres = new boolean[NUM_libres];
        for (int i = 0; i < libres.length; i++) {
            libres[i] = true;
        }
        impresoras = NUM_libres;

    }

    public int adquirirImpresora(int proc) {
        cerrojo.lock();
        try {
            System.out.println("Tarea " + proc + " quiere usar una impresora.");
            while (impresoras <= 0) {
                try {
                    System.out.println("Tarea " + proc + " se bloquea, todas las impresoras estan ocupadas.");
                    condImpresoras.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int i;
            for (i = 0; i < libres.length && !libres[i]; i++)
                ;
            libres[i] = false;
            impresoras--;
            System.out.println("Tarea " + proc + " se le asigna la impresora " + i + ", puede usarla.");
            return i;
        } finally {
            cerrojo.unlock();
        }
    }

    public void liberarImpresora(int proc, int i) {
        cerrojo.lock();
        try {
            libres[i] = true;
            impresoras++;
            condImpresoras.signalAll();
            System.out.println(
                    "Tarea " + proc + " se ha liberado la impresora " + i + ", despertando a tareas bloqueadas.");
        } finally {
            cerrojo.unlock();
        }
    }
}
