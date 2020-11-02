
/**
 * resImagenPar.java
 * 
 * Aplicar operador de resaltado a imagen aleatoria
 * de grises con paralelismo de grano grueso
 * 
 * @author Nicolas Ruiz Requejo
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class resImagenPar implements Runnable {

    private static final int W = 10000;
    private static final int H = 10000;

    private static int[][] imagen = new int[H][W];
    private static int[][] resaltada = new int[H][W];
    private int ini; // indice fila inicial
    private int fini; // numero de filas para cada tarea

    public resImagenPar(int ini, int fini) {
        this.ini = ini;
        this.fini = fini;
    }

    public void run() {
        int temp = 0;

        for (int i = ini; i <= fini; ++i)
            for (int j = 1; j < imagen[i].length - 1; ++j) {
                temp = ((4 * imagen[i][j] - imagen[i + 1][j] - imagen[i][j + 1] - imagen[i - 1][j] - imagen[i][j - 1])
                        / 8);
                if (temp < 0) {
                    resaltada[i][j] = 0;
                } else if (temp > 255) {
                    resaltada[i][j] = 255;
                } else {
                    resaltada[i][j] = temp;
                }
            }
    }

    /**
     * Uso: resImagenPar [-h num_hilos | -c coeficiente_de_bloqueo]
     * 
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        int nucleos = Runtime.getRuntime().availableProcessors();
        double cb = 0;
        int numHilos = (int)(nucleos / (1 - cb));
        // para dar el numero de hilos o coeficiente de bloqueo por linea de comando
        if (args.length == 2) {
            if (args[0].compareTo("-h") == 0) {
                numHilos = Integer.parseInt(args[1]);
            } else if (args[0].compareTo("-c") == 0) {
                cb = Double.parseDouble(args[1]);
            }
        }
        // nTareas = cuantas filas va a calcular cada hilo
        int nTareas = (imagen.length - 2) / numHilos; // -2 para recortar fila 0 y N (primera y ultima)
        // tareasRestantes = cuando el reparto no es exacto, cuantas filas extras
        // calcula el ultimo hilo
        int tareasRestantes = (imagen.length - 2) % numHilos;
        rellenoAleatorio(imagen);
        double tiempoInicio, tiempoFinal;
        tiempoInicio = System.nanoTime();
        ExecutorService mipool = Executors.newFixedThreadPool(numHilos);

        // reparto de tareas
        int i;
        int N = imagen.length - 2 - tareasRestantes; // filas evaluadas
        // desde la primera fila en incrementos del numero de tareas por hilo
        for (i = 1; i <= (N - nTareas); i += nTareas) {
            // System.out.println("Tareas de la " + i + " hasta " + ((i + nTareas) - 1));
            mipool.execute(new resImagenPar(i, (i + nTareas) - 1));
        }
        if (tareasRestantes > 0) {
            // System.out.println("RESTO Tareas de la " + i + " hasta " + ((i + + nTareas +
            // tareasRestantes) - 1));
            mipool.execute(new resImagenPar(i, (i + nTareas + tareasRestantes) - 1));
        } else {
            mipool.execute(new resImagenPar(i, (i + nTareas) - 1));
        }
        // fin reparto
        mipool.shutdown();
        // while (!mipool.isTerminated());
        mipool.awaitTermination(1, TimeUnit.DAYS);

        tiempoFinal = System.nanoTime();
        System.out.println("Número de hilos: " + numHilos);
        System.out.println("Tiempo Total (milisegundos): " + (tiempoFinal - tiempoInicio) / 1.0e6);
        // saturar(resaltada);
        //printMatrix(imagen, "orig");
        //printMatrix(resaltada, "output");
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