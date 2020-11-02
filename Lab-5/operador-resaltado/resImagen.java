
/**
 * resImagen.java
 * 
 * Aplicar operador de resaltado a imagen aleatoria
 * de grises, version secuencial
 * 
 * @author Nicolas Ruiz Requejo
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class resImagen {

    private static final int W = 10000;
    private static final int H = 10000;
    private int[][] imagen;

    public resImagen(int[][] img) {
        this.imagen = img;
    }

    public int[][] resaltado() {
        int[][] res = new int[imagen.length][imagen[0].length];
        int temp = 0;

        for (int i = 1; i < imagen.length - 1; ++i)
            for (int j = 1; j < imagen[i].length - 1; ++j) {
                temp = ((4 * imagen[i][j] - imagen[i + 1][j] - imagen[i][j + 1] - imagen[i - 1][j]
                        - imagen[i][j - 1]) / 8);
                if (temp < 0) {
                    res[i][j] = 0;
                } else if (temp > 255) {
                    res[i][j] = 255;
                } else {
                    res[i][j] = temp;
                }
            }

        return res;
    }

    public static void main(String[] args) throws Exception {
        int[][] mat = new int[H][W];
        rellenoAleatorio(mat);
        double tiempoInicio, tiempoFinal;
        tiempoInicio = System.nanoTime();
        resImagen res = new resImagen(mat);
        int[][] resaltada = res.resaltado();
        tiempoFinal = System.nanoTime();
        System.out.println("Tiempo Total (milisegundos): " + (tiempoFinal - tiempoInicio) / 1.0e6);
        // saturar(resaltada);
        printMatrix(mat, "orig");
        printMatrix(resaltada, "output");
    }

    private static void rellenoAleatorio(int[][] mat) {
        Random ran = new Random(System.nanoTime());

        for (int i = 0; i < mat.length; ++i)
            for (int j = 0; j < mat[0].length; ++j)
                mat[i][j] = ran.nextInt(255);
    }

    private static void saturar(int[][] mat) {
        for (int i = 0; i < mat.length; ++i)
            for (int j = 0; j < mat[0].length; ++j)
                if (mat[i][j] < 0) {
                    mat[i][j] = 0;
                } else if (mat[i][j] > 255) {
                    mat[i][j] = 255;
                }
    }

    // imprime matriz de double
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