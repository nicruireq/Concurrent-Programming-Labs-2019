import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * RWMonitorAN
 * 
 * @author Nicolas Ruiz Requejo
 */

public class RWMonitorAN {
    private volatile int readers;
    private volatile boolean writing;
    private final ReentrantLock cerrojo;
    private final Condition condReader, condWriter;

    public RWMonitorAN() {
        cerrojo = new ReentrantLock();
        readers = 0;
        writing = false;
        condReader = cerrojo.newCondition();
        condWriter = cerrojo.newCondition();
    }

    public void StartRead() throws InterruptedException {
        cerrojo.lock();
        try {
            while (writing) {
                condReader.await();
            }
            ++readers;
            System.out.println("Lector inicia lectura...");
            condReader.signal();
        } finally {
            cerrojo.unlock();
        }
    }

    public void EndRead() {
        cerrojo.lock();
        try {
            --readers;
            if (readers == 0)
                condWriter.signalAll();
            System.out.println("Lector finaliza lectura...");
        } finally {
            cerrojo.unlock();
        }
    }

    public void StartWrite() throws InterruptedException {
        cerrojo.lock();
        try {
            while (writing || (readers != 0))
                condWriter.await();
            writing = true;
            System.out.println("Escritor inicia escritura...");
        } finally {
            cerrojo.unlock();
        }
    }

    public void EndWrite() {
        cerrojo.lock();
        try {
            writing = false;
            // tengo que despertar en ambas colas
            // si solo despierto en lectores para dar
            // prioridad real y no hubiera lectores bloqueados pero
            // si escritores y no llegan mas lectores, esos escritores
            // nunca serian despertados nunca podrian acabar
            // los metodos de ReentrantLock para comprobar el estado
            // de las colas Condition no nos aseguran un resultado correcto
            // y no deben usarse para sincronizacion
            // tampoco podemos usar contadores para esta tarea porque
            // ¿cuando los decrementamos? Cuando hacemos un signal() o signalAll()
            // no sabemos si es seguro que haya un hilo bloqueado que se vaya
            // a despertar... tenriamos valores negativos en el contador
            // por lo tanto la unica solucion justa es despertar en ambas colas
            condReader.signal();
            condWriter.signal();
            System.out.println("Escritor finaliza escritura...");
        } finally {
            cerrojo.unlock();
        }
    }

    private static int recurso = 0;

    public static void main(String[] args) throws InterruptedException {
        RWMonitorAN monitor = new RWMonitorAN();

        Thread l1 = new Thread(() -> {
            try {
                monitor.StartRead();
                Thread.sleep(2000);
                System.out.println("Lector 1 lee del recurso: " + recurso);
                monitor.EndRead();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        Thread l2 = new Thread(() -> {
            try {
                monitor.StartRead();
                Thread.sleep(2000);
                System.out.println("Lector 2 lee del recurso: " + recurso);
                monitor.EndRead();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        Thread l3 = new Thread(() -> {
            try {
                monitor.StartRead();
                Thread.sleep(2000);
                System.out.println("Lector 3 lee del recurso: " + recurso);
                monitor.EndRead();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        Thread e1 = new Thread(() -> {
            try {
                monitor.StartWrite();
                for (int i = 0; i < 100000; i++) {
                    recurso++;
                }
                System.out.println("Escritor 1 escribe en recurso");
                monitor.EndWrite();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        Thread e2 = new Thread(() -> {
            try {
                monitor.StartWrite();
                for (int i = 0; i < 100000; i++) {
                    recurso++;
                }
                System.out.println("Escritor 1 escribe en recurso");
                monitor.EndWrite();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        l1.start();l2.start();l3.start();
        e1.start();e2.start();

        l1.join();l2.join();l3.join();
        e1.join();e2.join();

        System.out.println("Recurso vale: " + recurso);

    }
}
