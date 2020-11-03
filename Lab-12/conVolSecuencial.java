import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * conVolSecuencial
 * 
 * @author Nicolas Ruiz Requejo
 */

public class conVolSecuencial {

    /**
     * Computes 2D convolution of a matrix with a 3 by 3 kernel
     * 
     * @param A matrix n by m
     * @param h 3 by 3 convolution kernel
     * @return convoluted matrix
     */
    public static int[][] conv2DKernel3x3(int[][] A, int[][] h) {
        if (h.length != 3 && h[0].length != 0)
            throw new IllegalArgumentException();

        int n = A.length;
        int m = A[0].length;
        int[][] C = new int[n][m];
        for (int i = 1; i < (n - 1); i++) {
            for (int j = 1; j < (m - 1); j++) {
                for (int rowoffset = -1; rowoffset <= 1; rowoffset++) {
                    for (int coloffset = -1; coloffset <= 1; coloffset++) {
                        // int temp = 0;
                        C[i][j] += A[i + rowoffset][j + coloffset] * h[1 + rowoffset][1 + coloffset];
                        // C[i][j] = temp;
                        /*
                         * if (temp > 255) C[i][j] = 255; else if (temp < 0) C[i][j] = 0; else C[i][j] =
                         * temp;
                         */
                    }
                }
            }
        }

        return C;
    }

    public static final int[][] enfoque = { { 0, -1, 0 }, { -1, 5, -1 }, { 0, -1, 0 } };
    public static final int[][] realzaBordes = { { 0, 0, 0 }, { -1, 1, 0 }, { 0, 0, 0 } };
    public static final int[][] detectaBordes = { { 0, 1, 0 }, { 1, -4, 1 }, { 0, 1, 0 } };
    public static final int[][] sobel = { { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } };
    public static final int[][] sharpen = { { 1, -2, 1 }, { -2, 5, -2 }, { 1, -2, 1 } };

    private static final int N = 1000;
    private static final int M = 1000;

    private static final int[][] prueba = { { 35, 40, 41, 45, 50 }, { 40, 40, 42, 46, 52 }, { 42, 46, 50, 55, 55 },
            { 48, 52, 56, 58, 60 }, { 56, 60, 65, 70, 75 } };
    private static final int[][] kernelprueba = { { -2, -1, 0 }, { -1, 1, 1 }, { 0, 1, 2 } };

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);

        System.out.println("1. Enfoque");
        System.out.println("2. Realzar bordes");
        System.out.println("3. Detectar bordes");
        System.out.println("4. Sobel");
        System.out.println("5. Sharpen");
        System.out.println("6. Prueba");
        System.out.println("Elija un kernel: ");
        int op = sc.nextInt();

        int[][] matriz = new int[N][M];
        rellenoAleatorio(matriz);
        //printMatrix(matriz, "original.dat");

        int[][] conv = null;
        long initime = System.nanoTime();
        switch (op) {
            case 1:
                conv = conv2DKernel3x3(matriz, enfoque);
                break;
            case 2:
                conv = conv2DKernel3x3(matriz, realzaBordes);
                break;
            case 3:
                conv = conv2DKernel3x3(matriz, detectaBordes);
                break;
            case 4:
                conv = conv2DKernel3x3(matriz, sobel);
                break;
            case 5:
                conv = conv2DKernel3x3(matriz, sharpen);
                break;
            case 6:
                conv = conv2DKernel3x3(prueba, kernelprueba);
            default:
                break;
        }
        long fintime = System.nanoTime();

        double exetime = ((double) (fintime - initime)) / 1.0e6;
        System.out.println("Tiempo de ejecucion: " + exetime + " milisegundos.");
        //printMatrix(conv, "resultado_convolucion.dat");

        sc.close();
    }

    private static void rellenoAleatorio(int[][] mat) {
        Random ran = new Random(System.nanoTime());

        for (int i = 0; i < mat.length; ++i)
            for (int j = 0; j < mat[0].length; ++j)
                mat[i][j] = ran.nextInt(255);
    }

    private static void printMatrix(int[][] mat, String name) throws IOException {
        StringBuilder build = new StringBuilder();
        int rows = mat.length;
        int cols = mat[0].length;
        build.append("[");
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                build.append(mat[i][j] + " ");
            }
            build.append('\n');
        }
        build.append("]");

        BufferedWriter writer = new BufferedWriter(new FileWriter(name + ".txt"));
        writer.write(build.toString());// save the string representation of the board
        writer.close();
    }

}