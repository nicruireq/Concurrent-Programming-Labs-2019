import java.lang.reflect.Array;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * prodCons
 * 
 * @author Nicolas Ruiz Requejo
 */

public class prodCons {

    private int tam;
    private int[] buffer;
    private int frente, cola;
    Semaphore mutex, vacios, llenos;

    public prodCons(int tamanio) {
        tam = tamanio;
        buffer = new int[tam];
        frente = cola = 0;
        mutex = new Semaphore(1);
        vacios = new Semaphore(tam);
        llenos = new Semaphore(0);
    }

    public void insertar(int e) {
        try {
            vacios.acquire();
            mutex.acquire();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        buffer[cola] = e;
        cola = (cola + 1) % tam;
        mutex.release();
        llenos.release();
    }

    public int extraer() {
        int e;
        try {
            llenos.acquire();
            mutex.acquire();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        e = buffer[frente];
        frente = (frente + 1) % tam;
        mutex.release();
        vacios.release();
        return e;
    }

    public static void main(String[] args) throws InterruptedException {
        prodCons buff = new prodCons(10);

        Thread productor = new Thread(() -> {
            Random gen = new Random();
            for (;;) {
                int e = gen.nextInt();
                buff.insertar(e);
                System.out.println("Productor inserta: " + e);
            }
        });

        Thread consumidor = new Thread(() -> {
            for (;;) {
                int e = buff.extraer();
                System.out.println("Consumidor extrae: " + e);
            }
        });

        productor.start(); consumidor.start();
        productor.join(); consumidor.join();
    }

}