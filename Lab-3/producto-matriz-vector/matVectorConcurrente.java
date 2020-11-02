import java.util.Random;

/**
 * matVectorConcurrente.java
 * 
 * Producto de una matriz por un vector A*b = y versión multihebra, reparto
 * manual de filas por hebra y co-rutina
 * 
 * FORMATO comando: matVectorConcurrente num_hebras
 * 
 * @author Nicolás Ruiz Requejo
 */

public class matVectorConcurrente implements Runnable {

    // static fields
    private static int N = 10000;
    private static int M = 10000;
    public static double[][] matrizA = new double[N][M];
    public static double[] vectorB = new double[M];
    public static double[] vectorY = new double[N];

    // instance fields
    private int inicio, fin;

    /**
     * Constructor
     * 
     * @param inicio fila inicial de la computacion
     * @param fin    fila final de la computacion
     */
    public matVectorConcurrente(int inicio, int fin) {
        this.inicio = inicio;
        this.fin = fin;
    }

    @Override
    public void run() {
        for (int fila = inicio; fila <= fin; fila++) {
            vectorY[fila] = prodEscalar(matrizA[fila], vectorB);
        }
    }

    /**
     * Producto escalar de dos vectores de double
     * 
     * @param v1 vector de double
     * @param v2 vector de double
     * @return producto escalar v1*v2
     */
    private double prodEscalar(double[] v1, double[] v2) {
        double result = 0.0;
        for (int i = 0; i < v2.length; i++) {
            result += v1[i] * v2[i];
        }
        return result;
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1) {
            System.err.println("Uso: matVectorConcurrente num_hebras");
            System.exit(-1);
        }
        int numHilos = Integer.parseInt(args[0]);
        if (numHilos > N) {
            System.err.println("Pruebe con menos hilos");
            System.exit(-2);
        }

        // relleno matriz y vector
        rellenoAleatorio(matrizA, vectorB);
        // rellenoDummy(matrizA, vectorB);
        // toma tiempo inicial
        long tini = System.nanoTime();
        // reparto de tareas
        int nfilas = N / numHilos;
        int ini = 0;
        int fini = ini + (nfilas - 1);
        matVectorConcurrente[] tareas = new matVectorConcurrente[numHilos];
        for (int i = 0; i < numHilos - 1; i++) {
            //System.out.println("En iter " + i + ", ini=" + ini + " fini=" + fini);
            tareas[i] = new matVectorConcurrente(ini, fini);
            ini = fini + 1;
            fini += nfilas;
        }
        // si el reparto de filas entre hilos no es exacto
        // el ultimo hilo hace lo suyo mas el resto
        // System.out.println("Como acaba ini=" + ini + ", fini=" + fini);
        if (N % numHilos != 0) {
            // System.out.println("RESTO En iter " + (numHilos - 1) + ", ini=" + (ini) + "
            // fini="
            // + ((ini - 1) + nfilas + (N % numHilos)));
            tareas[numHilos - 1] = new matVectorConcurrente(ini, (ini - 1) + nfilas + (N % numHilos));
        } else {
            // System.out.println("EXACTO En iter " + (numHilos - 1) + ", ini=" + ini + "
            // fini=" + (fini));
            tareas[numHilos - 1] = new matVectorConcurrente(ini, fini);
        }

        Thread[] hilos = new Thread[numHilos];
        // co-rutina
        for (int i = 0; i < numHilos; i++) {
            hilos[i] = new Thread(tareas[i]);
            hilos[i].start();
        }

        for (int i = 0; i < numHilos; i++) {
            hilos[i].join();
        }
        // en milisegundos
        double exetime = ((double) (System.nanoTime() - tini)) / 1.0e6;

        // comentar para estudiar rendimiento:
        // printVector(vectorY);
        System.out.println("Con " + numHilos + " hilos, ha tardado: " + exetime + " milisegundos");

    }

    /**
     * Rellena con numeros aleatorios una matriz y un vector de double
     * 
     * @param mat  matriz de double
     * @param vect vector de double
     */
    private static void rellenoAleatorio(double[][] mat, double[] vect) {
        Random ran = new Random(System.nanoTime());

        for (int i = 0; i < mat.length; ++i)
            for (int j = 0; j < mat[0].length; ++j)
                mat[i][j] = ran.nextDouble() * 10;

        for (int i = 0; i < vect.length; ++i)
            vect[i] = ran.nextDouble() * 10;
    }

    /**
     * Rellena con UNOS una matriz y un vector de double
     * 
     * @param mat  matriz de double
     * @param vect vector de double
     */
    private static void rellenoDummy(double[][] mat, double[] vect) {

        for (int i = 0; i < mat.length; ++i)
            for (int j = 0; j < mat[0].length; ++j)
                mat[i][j] = 1;

        for (int i = 0; i < vect.length; ++i)
            vect[i] = 1;
    }

    // Imprime un vector de double
    private static void printVector(double[] v) {
        System.out.print("[ ");
        for (int i = 0; i < v.length; ++i) {
            if (i == v.length - 1)
                System.out.print(v[i] + " ]");
            else
                System.out.print(" " + v[i] + ", ");
        }
    }
}