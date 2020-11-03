/**
 * Abstraccion en monitor de buffer productor-consumidor
 * 
 * @author Nicolas Ruiz Requejo
 */

class Matriz {
    public int[][] matriz;
    private final int N, M;

    public Matriz(int dimN, int dimM) {
        N = dimN;
        M = dimM;
        matriz = new int[dimN][dimM];
    }

    @Override
    public String toString() {
        String m = "[";
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                m = m + matriz[i][j] + " ";
            }
            m = m + "\n";
        }
        m = m + "]\n";
        return m;
    }
}

public class monitorCadena_1 {

    private final int ranuras;
    private Matriz[] buffer;
    private int frente, fin, elems;

    public monitorCadena_1(int ranuras) {
        this.ranuras = ranuras;
        buffer = new Matriz[ranuras];
        frente = fin = elems = 0;
    }

    public synchronized void insertar(Matriz mat) {
        System.out.println("Proceso " + Thread.currentThread().getName() + " quiere insertar matriz.");
        while (elems == ranuras) {
            try {
                System.out.println("Proceso " + Thread.currentThread().getName()
                        + " tiene que bloquearse porque el buffer esta lleno.");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        buffer[fin] = mat;
        elems++;
        fin = (fin + 1) % ranuras;
        System.out.println("Proceso " + Thread.currentThread().getName() + " termina de introducir matriz en buffer.");
        notifyAll();
        System.out.println("Proceso " + Thread.currentThread().getName() + " despierta a otro proceso.");
    }

    public synchronized Matriz extraer() {
        System.out.println("Proceso " + Thread.currentThread().getName() + " quiere extraer del buffer.");
        while (elems == 0) {
            try {
                System.out.println(
                        "Proceso " + Thread.currentThread().getName() + " se bloquea porque no hay nada que extraer.");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Matriz mat = buffer[frente];
        elems--;
        frente = (frente + 1) % ranuras;
        System.out.println("Proceso " + Thread.currentThread().getName() + " extrae matriz del buffer.");
        notifyAll();
        System.out.println("Proceso " + Thread.currentThread().getName() + " despierta a otro proceso.");
        return mat;
    }

}