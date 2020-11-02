import java.util.Locale;
import java.util.Random;

/**
 * Multiplicación de matrices cuadradas secuencial
 * 
 * @author Nicolas Ruiz Requejo
 */

public class prodMatrices {
    public static final int nfilas = 1000;
    public static final int ncols = 1000;
    public static double[][] matA = new double[nfilas][ncols];
    public static double[][] matB = new double[nfilas][ncols];;
    public static double[][] matProd = new double[nfilas][ncols];

    /**
     * Multiplicación de matrices cuadradas de dimension dim Algoritmo O(n^3)
     * 
     * @param m1
     * @param m2
     * @param dim
     */
    public static void productoMatrices(double[][] m1, double[][] m2, int dim) {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                for (int k = 0; k < dim; k++) {
                    matProd[i][j] += matA[i][k] * matB[k][j];
                }
            }
        }
    }

    public static void main(String[] args) {

        // rellenoAleatorio(matA);
        // rellenoAleatorio(matB);
        rellenoDummy(matA);
        rellenoDummy(matB);
        long tiempoInicio = System.nanoTime();
        productoMatrices(matA, matB, nfilas);
        double exetime = ((double) (System.nanoTime() - tiempoInicio)) / 1.0e9;

        System.out.println("Dimension: " + nfilas + " x " + ncols);
        System.out.println("Tiempo de ejecucion: " + exetime + " segundos.");
        // resultado
        /*
         * System.out.println("Matriz A: \n"); printMatrix(matA);
         * System.out.println("Matriz B: \n"); printMatrix(matB);
         * System.out.println("Matriz Resultado: \n"); printMatrix(matProd);
         */
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