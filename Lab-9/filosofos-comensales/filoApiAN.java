import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * filoApiAN
 * 
 * @author Nicolas Ruiz Requejo
 */

public class filoApiAN {
    public static final int filosofos = 5;
    private final ReentrantLock cerrojo;
    private int[] palillos;
    private Condition[] puedeComer;

    public filoApiAN() {
        cerrojo = new ReentrantLock();
        palillos = new int[filosofos];
        // el i-esimo filosofo puede comer
        // si y solo si tiene dos palillos
        // un array de doses significa una promesa
        // al futuro para que el filosofo i
        // pueda comer, cuando coma tomara 1 de
        // su lado izquierdo (i-1) y derecho (i+1)
        // y asi sus vecinos no podran comer y tendran que esperar
        Arrays.fill(palillos, 2);
        puedeComer = new Condition[filosofos];
        Arrays.fill(puedeComer, cerrojo.newCondition());
    }

    public void tomarPalillos(int i) {
        cerrojo.lock();
        try {
            while (palillos[i] != 2) {
                try {
                    System.out.println("El filosofo " + i + " tiene que esperar para comer.");
                    puedeComer[i].await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            palillos[(i + 4) % filosofos]--;
            palillos[(i + 1) % filosofos]--;
        } finally {
            cerrojo.unlock();
        }
    }

    public void soltarPalillos(int i) {
        cerrojo.lock();
        try {
            int ant = (i + 4) % filosofos;
            int pos = (i + 1) % filosofos;
            palillos[ant]++;
            palillos[pos]++;
            if (palillos[ant] == 2)
                puedeComer[ant].signal();
            if (palillos[pos] == 2)
                puedeComer[pos].signal();
        } finally {
            cerrojo.unlock();
        }
    }
}