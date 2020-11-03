/**
 * @author Nicolas Ruiz Requejo
 */
public class usaMutex {

    public static int num = 0;

    public static void main(String[] args) throws InterruptedException {
        mutex sem = new mutex(1);

        Thread t1 = new Thread(() -> {
            sem.waitDijkstra();
            System.out.println("t1 esta en la seccion critica");
            for (int i = 0; i < 100000; i++) {
                ++num;
            }
            sem.signal();
            System.out.println("t1 sale de la seccion critica");

        });

        Thread t2 = new Thread(() -> {
            sem.waitDijkstra();
            System.out.println("t2 esta en la seccion critica");
            for (int i = 0; i < 100000; i++) {
                ++num;
            }
            sem.signal();
            System.out.println("t2 sale de la seccion critica");
        });

        Thread t3 = new Thread(() -> {
            sem.waitDijkstra();
            System.out.println("t3 esta en la seccion critica");
            for (int i = 0; i < 100000; i++) {
                ++num;
            }
            sem.signal();
            System.out.println("t3 sale de la seccion critica");
        });

        t1.start();
        t2.start();t3.start();
        t1.join();t2.join();t3.join();
        
        System.out.println("Resultado de num: " + num);
    }

}