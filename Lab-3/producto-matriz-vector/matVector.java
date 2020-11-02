
/**
 *  matVector.java
 * 
 *  Producto de una matriz por un vector
 *  versión secuencial
 * 
 *  @author Nicolás Ruiz Requejo
 */

import java.lang.*;
import java.util.*;

public class matVector {

    // static fields
    private static int N = 10000;
    private static int M = 10000;
    public static double[][] matrizA = new double[N][M];
    public static double[] vectorB = new double[M];

    /**
     * O(max{n,m}^2)
     * 
     * @param mat  matriz nxm de double
     * @param vect matriz de m elementos double
     * @return vector producto mat x vect de dimension n
     * @throws Exception Si cols(mat) != rows(vect)
     */
    public static double[] prodMatVector(double[][] mat, double[] vect) throws Exception {
        if (mat[0].length != vect.length)
            throw new Exception("pre: cols(mat) = rows(vect)");

        // mat: m x n; vect: n x 1; prod: m x 1
        int m = mat.length;
        int n = vect.length;

        double[] prod = new double[m];

        for (int k = 0; k < m; ++k)
            for (int i = 0; i < n; ++i)
                prod[k] += (mat[k][i] * vect[i]);

        return prod;
    }

    public static void main(String[] args) throws Exception {

        double tiempoInicio, tiempoFinal;
        tiempoInicio = System.nanoTime();

        rellenoAleatorio(matrizA, vectorB);
        // rellenoDummy(matrizA, vectorB);

        double[] vectorY = prodMatVector(matrizA, vectorB);

        tiempoFinal = System.nanoTime();

        System.out.println("\n\nProducto: ");
        // comentar para estudiar rendimiento:
        // printVector(vectorY);
        double tiempoTotal = (tiempoFinal - tiempoInicio) / 1.0e6;
        System.out.println("\nTiempo ejecucion: " + tiempoTotal + " milisegundos");
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

    // imprime matriz de double
    private static void printMatrix(double[][] mat) {
        int rows = mat.length;
        for (int i = 0; i < rows; ++i) {
            printVector(mat[i]);
            System.out.print('\n');
        }

    }

}
