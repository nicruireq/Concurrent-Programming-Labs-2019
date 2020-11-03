/**
 * @author Nicolas Ruiz Requejo
 * 
 *         Monitor para el problema de lectores y escritores con prioridad en
 *         lectura
 */

public class lectorEscritor {
    private int lectores;
    private boolean escribiendo;

    public lectorEscritor() {
        lectores = 0;
        escribiendo = false;
    }

    public synchronized void iniciaLectura() {
        System.out.println("Lector " + Thread.currentThread().getId() + " quiere leer.");
        while (escribiendo) {
            try {
                System.out.println(
                        "Lector " + Thread.currentThread().getId() + " se bloquea porque ya estan escribiendo.");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lectores++;
        notifyAll();
        System.out.println("Lector " + Thread.currentThread().getId() + " puede leer y otros lectores tambien.");
    }

    public synchronized void finLectura() {
        lectores--;
        if (lectores == 0)
            notifyAll();
        System.out.println(
                "Lector " + Thread.currentThread().getId() + " deja de leer e intenta despertar algun escritor.");
    }

    public synchronized void iniciaEscritura() {
        System.out.println("Escritor " + Thread.currentThread().getId() + " quiere escribir.");
        while (lectores > 0 || escribiendo) {
            try {
                System.out.println("Escritor " + Thread.currentThread().getId()
                        + " se bloquea porque ya estan leyendo o escribiendo otros.");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        escribiendo = true;
        System.out.println("Escritor " + Thread.currentThread().getId() + " puede escribir.");
    }

    public synchronized void finEscritura() {
        escribiendo = false;
        notifyAll();
        System.out.println(
                "Escritor " + Thread.currentThread().getId() + " deja de escribir e intenta despertar algun lector.");
    }

}