/**
 * algHyman Adaptado de Palma
 * 
 * @author Nicolas Ruiz Requejo
 */

public class algHyman {

    public static volatile int recurso = 0;
    public static final int iteraciones = 100000;
    public static volatile boolean entrarP0, entrarP1;
    public static volatile int turno;

    public static void main(String[] args) throws InterruptedException {

        // inicializaciones
        entrarP0 = false;
        entrarP1 = false;
        turno = 0;

        Runnable P0 = () -> {
            for (int i = 0; i < iteraciones; i++) {
                entrarP0 = true;
                while (turno != 0) {
                    while (entrarP1 == true)
                        ;
                    turno = 0;
                }
                // seccion critica
                recurso++;
                // fin s.c
                entrarP0 = false;
            }
        };

        Runnable P1 = () -> {
            for (int i = 0; i < iteraciones; i++) {

                entrarP1 = true;
                while (turno != 1) {
                    while (entrarP0 == true)
                        ;
                    turno = 1;
                }
                // seccion critica
                recurso--;
                // fin s.c
                entrarP1 = false;
            }
        };

        Thread t0 = new Thread(P0);
        Thread t1 = new Thread(P1);
        t0.start();
        t1.start();
        t0.join();
        t1.join();

        System.out.println("Recurso vale = " + recurso);
        System.out.println("recurso tiene que valer 0.");
    }

}