/**
 * Producto de matrices cuadradas paralelizado
 * por división del número de elementos de la 
 * matriz resultante entre el número de hilos
 * 
 * @author Nicolas Ruiz Requejo
 */

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;

public class prodMatricesParalelo implements Runnable {
    public static final int nfilas = 1000;
    public static final int ncols = 1000;
    public static double[][] matA = new double[nfilas][ncols];
    public static double[][] matB = new double[nfilas][ncols];;
    public static double[][] matProd = new double[nfilas][ncols];

    private int firstInd, lastInd, dim;

    public prodMatricesParalelo(int firstInd, int lastInd, int dim) {
        this.firstInd = firstInd;
        this.lastInd = lastInd;
        this.dim = dim;
    }

    @Override
    public void run() {
        for (int elem = firstInd; elem <= lastInd; elem++) {
            productoEscalar(elem);
        }
    }

    private void productoEscalar(int currentInd) {
        int[] subscripts = ind2sub(currentInd, dim);
        int row = subscripts[0];
        int col = subscripts[1];
        for (int j = 0; j < dim; j++) {
            matProd[row][col] += matA[row][j] * matB[j][col];
        }
    }

    public static int sub2ind(int i, int j, int dim) {
        return i * dim + j;
    }

    public static int[] ind2sub(int ind, int dim) {
        int[] subscripts = new int[2];
        subscripts[0] = ind / dim;
        subscripts[1] = ind % dim;
        return subscripts;
    }

    public static void main(String[] args) {
        // rellenar matrices
        rellenoAleatorio(matA);
        rellenoAleatorio(matB);
        // rellenoDummy(matA);
        // rellenoDummy(matB);
        // calcular hilos del pool
        int Cb = 0;
        int nucleos = Runtime.getRuntime().availableProcessors();
        int numHilos = nucleos / (1 - Cb);
        // para introducir el numero de hilos por linea de comandos
        if (args.length > 0) {
            numHilos = Integer.parseInt(args[0]);
        }
        double tiempoInicio, tiempoFinal;
        tiempoInicio = System.nanoTime();
        // crear pool
        ThreadPoolExecutor mipool = new ThreadPoolExecutor(numHilos, numHilos, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        mipool.prestartAllCoreThreads();
        // Reparto de tareas
        prodMatricesParalelo[] tareas = new prodMatricesParalelo[numHilos];
        int nelems = nfilas * ncols;
        int tamTarea = nelems / numHilos;
        int ini = 0;
        int fini = ini + (tamTarea - 1);
        for (int i = 0; i < (numHilos - 1); i++) {
            // System.out.println("Tarea " + i + " trabaja en ini=" + ini + ", fini=" +
            // fini);
            tareas[i] = new prodMatricesParalelo(ini, fini, nfilas);
            ini = fini + 1;
            fini += tamTarea;
        }
        if (nelems % numHilos != 0) {
            // System.out.println("RESTO trabaja en ini=" + ini + ", fini=" + ((ini - 1) +
            // tamTarea + (nelems % numHilos)) );
            tareas[numHilos - 1] = new prodMatricesParalelo(ini, (ini - 1) + tamTarea + (nelems % numHilos), nfilas);
        } else {
            // System.out.println("EXACTO trabaja en ini=" + ini + ", fini=" + fini);
            tareas[numHilos - 1] = new prodMatricesParalelo(ini, fini, nfilas);
        }

        // Lanzar tareas en el pool
        for (int i = 0; i < tareas.length; i++) {
            mipool.execute(tareas[i]);
        }
        // shutdown impide que se añadan mas tareas a la pool pero
        // terminan las que ya estan submited
        mipool.shutdown();
        // bucle de espera ocupada
        while (!mipool.isTerminated())
            ;
        // tomar tiempo final
        tiempoFinal = System.nanoTime();
        System.out.println("Numero de Nucleos: " + nucleos);
        System.out.println("Coficiente de Bloqueo: " + Cb);
        System.out.println("Tamano del Pool: " + mipool.getCorePoolSize());
        System.out.println("Tiempo Total (segundos): " + (tiempoFinal - tiempoInicio) / 1.0e9);
        // resultado
        System.out.println("Matriz A: \n");
        // printMatrix(matA);
        System.out.println("Matriz B: \n");
        // printMatrix(matB);
        System.out.println("Matriz Resultado: \n");
        // printMatrix(matProd);
    }

    private static void rellenoAleatorio(double[][] mat) {
        Random ran = new Random(System.nanoTime());

        for (int i = 0; i < mat.length; ++i)
            for (int j = 0; j < mat[0].length; ++j)
                mat[i][j] = ran.nextDouble() * 10;
    }

    /**
     * Rellena con UNOS una matriz y un vector de double
     * 
     * @param mat  matriz de double
     * @param vect vector de double
     */
    private static void rellenoDummy(double[][] mat) {
        for (int i = 0; i < mat.length; ++i)
            for (int j = 0; j < mat[0].length; ++j)
                mat[i][j] = 1;
    }

    // Imprime un vector de double
    private static void printVector(double[] v) {
        System.out.print("[ ");
        for (int i = 0; i < v.length; ++i) {
            if (i == v.length - 1)
                System.out.print(String.format(Locale.ROOT, "%.2f", v[i]) + " ]");
            else
                System.out.print(" " + String.format(Locale.ROOT, "%.2f", v[i]) + ", ");
        }
    }

    // imprime matriz de double
    private static void printMatrix(double[][] mat) {
        int rows = mat.length;
        for (int i = 0; i < rows; ++i) {
            printVector(mat[i]);
            System.out.print('\n');
        }

    }

}