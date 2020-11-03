/**
 *  @author Nicolas Ruiz Requejo
 */

/**
 * monitorImpresion
 */
public class monitorImpresion {

    private static final int NUM_libres = 3;

    private boolean[] libres;
    private int impresoras;

    public monitorImpresion() {
        libres = new boolean[NUM_libres];
        for (int i = 0; i < libres.length; i++) {
            libres[i] = true;
        }
        impresoras = NUM_libres;

    }

    public synchronized int adquirirImpresora(int proc) {
        System.out.println("Tarea " + proc + " quiere usar una impresora.");
        while (impresoras <= 0) {
            try {
                System.out.println("Tarea " + proc + " se bloquea, todas las impresoras estan ocupadas.");
                wait();
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
    }

    public synchronized void liberarImpresora(int proc, int i) {
        libres[i] = true;
        impresoras++;
        notifyAll();
        System.out.println("Tarea " + proc + " se ha liberado la impresora " + i + ", despertando a tareas bloqueadas.");
    }
}