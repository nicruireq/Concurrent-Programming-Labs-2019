/**
 * algDekker
 * 
 * Dekker con tres procesos
 * 
 * @author Nicolas Ruiz Requejo
 */

public class algDekker {
    /* Iteraciones que dara cada hilo */
    static final int iteraciones = 200;
    /* Recurso compartido */
    static volatile int enteroCompartido = 0;
    /* Representa el deseo del hilo P de entrar en la seccion critica */
    static volatile boolean wantp = false;
    /* Representa el deseo del hilo Q de entrar en la seccion critica */
    static volatile boolean wantq = false;
    /* Representa el deseo del hilo R de entrar en la seccion critica */
    static volatile boolean wantr = false;
    /* Representa de quien es el turno */
    static volatile int turn = 1;

    class P extends Thread {
        public void run() {
            for (int i = 0; i < iteraciones; ++i) {
                /* Seccion no critica */
                wantp = true;
                while (wantq || wantr) {
                    if (turn == 2 || turn == 3) {
                        wantp = false;
                        while (turn == 2 || turn == 3) {
                            System.out.println("Soy P y me bloqueo");
                            Thread.yield();
                        }
                        wantp = true;
                    }
                }

                /* Seccion critica */
                System.out.println("Soy P y entro");
                ++enteroCompartido;
                /* Fin Seccion critica */

                turn = 2;
                wantp = false;
            }
        }
    }

    class Q extends Thread {
        public void run() {
            for (int i = 0; i < iteraciones; ++i) {
                /* Seccion no critica */
                wantq = true;
                while (wantr || wantp) {
                    if (turn == 3 || turn == 1) {
                        wantq = false;
                        while (turn == 3 || turn == 1) {
                            System.out.println("Soy Q y me bloqueo");
                            Thread.yield();
                        }
                        wantq = true;
                    }
                }

                /* Seccion critica */
                System.out.println("Soy Q y entro");
                --enteroCompartido;
                /* Fin Seccion critica */

                turn = 3;
                wantq = false;
            }
        }
    }

    class R extends Thread {
        public void run() {
            for (int i = 0; i < iteraciones; ++i) {
                /* Seccion no critica */
                wantr = true;
                while (wantp || wantq) {
                    if (turn == 1 || turn == 2) {
                        wantr = false;
                        while (turn == 1 || turn == 2) {
                            System.out.println("Soy R y me bloqueo");
                            Thread.yield();
                        }
                        wantr = true;
                    }
                }

                /* Seccion critica */
                System.out.println("Soy R y entro");
                --enteroCompartido;
                /* Fin Seccion critica */

                turn = 1;
                wantr = false;
            }
        }
    }

    public algDekker() {
        Thread p = new P();
        Thread q = new Q();
        Thread r = new R();
        p.start();
        q.start();
        r.start();

        try {
            p.join();
            q.join();
            r.join();
            System.out.println("El valor del recurso compartido es " + enteroCompartido);
            System.out.println("Deberia ser -2000000.");
        } catch (InterruptedException e) {
        }
    }

    public static void main(String[] args) {
        new algDekker();
    }
}