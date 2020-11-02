import java.util.Random;

/**
 * Producto escalar paralelo de dos vectores de reales con particion manual de
 * datos, mediante herencia de la clase Thread
 * 
 * @author Nicolas Ruiz Requejo
 */

public class prodEscalarParalelo extends Thread {

    private static final int N = 1000000;
    public static double[] productoParcial = null;
    public static double[] vectorA = new double[N];
    public static double[] vectorB = new double[N];

    private int idHebra, inicio, fin;

    public prodEscalarParalelo(int idHebra, int inicio, int fin) {
        this.idHebra = idHebra;
        this.inicio = inicio;
        this.fin = fin;
    }

    public void run() {
        double resultadoParcial = 0.0;
        for (int i = inicio; i < fin; i++) {
            resultadoParcial += vectorA[i] * vectorB[i];
        }
        productoParcial[idHebra] = resultadoParcial;
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1) {
            System.err.println("Uso: prodEscalarParalelo num_hilos");
            System.exit(-1);
        }

        // rellenar vectores
        fillSequentialVector(vectorA);
        fillSequentialVector(vectorB);
        // inicializar vector de productos parciales
        // con tantos elementos como numero de hilos
        int numHilos = Integer.parseInt(args[0]);
        productoParcial = new double[numHilos];
        // toma de tiempo inicial
        long tini = System.nanoTime();
        // vector de hilos a lanzar
        Thread[] tareas = new prodEscalarParalelo[numHilos];
        // reparto de tareas
        int nVentana = N / numHilos;
        int ini = 0;
        int fini = ini + (nVentana - 1);
        for (int i = 0; i < numHilos; i++) {
            tareas[i] = new prodEscalarParalelo(i, ini, fini);
            ini = fini + 1;
            fini += nVentana;
        }

        // co-rutina
        for (int i = 0; i < numHilos; i++) {
            tareas[i].start();
        }

        for (int i = 0; i < numHilos; i++) {
            tareas[i].join();
        }

        double productoEscalar = 0.0;
        // reduce
        for (int i = 0; i < numHilos; i++) {
            productoEscalar += productoParcial[i];
        }

        double exetime = ((double) (System.nanoTime() - tini)) / 1.0e12;

        System.out.println("Resultado del producto escalar = " + productoEscalar);
        System.out.println("Tiempo de ejecución = " + exetime + " segundos");
    }

    /**
     * Rellena un vector de double con datos aleatorios
     * 
     * @param v vector de double
     */
    private static void fillRandomVector(double[] v) {
        Random generador = new Random(System.nanoTime());
        for (int i = 0; i < v.length; i++)
            v[i] = generador.nextDouble();
    }

    /**
     * Rellena en vector de double con los n primeros enteros positivos
     * 
     * @param v vector de double
     */
    private static void fillSequentialVector(double[] v) {
        for (int i = 0; i < v.length; i++) {
            v[i] = i + 1;
        }
    }
}