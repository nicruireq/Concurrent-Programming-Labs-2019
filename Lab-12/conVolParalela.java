import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * conVolParalela
 * 
 * @author Nicolas Ruiz Requejo
 */

public class conVolParalela implements Runnable {

    private static final int N = 1000;
    private static final int M = 1000;

    public static final int[][] enfoque = { { 0, -1, 0 }, { -1, 5, -1 }, { 0, -1, 0 } };
    public static final int[][] realzaBordes = { { 0, 0, 0 }, { -1, 1, 0 }, { 0, 0, 0 } };
    public static final int[][] detectaBordes = { { 0, 1, 0 }, { 1, -4, 1 }, { 0, 1, 0 } };
    public static final int[][] sobel = { { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } };
    public static final int[][] sharpen = { { 1, -2, 1 }, { -2, 5, -2 }, { 1, -2, 1 } };

    public static final int[][] prueba = { { 35, 40, 41, 45, 50 }, { 40, 40, 42, 46, 52 }, { 42, 46, 50, 55, 55 },
            { 48, 52, 56, 58, 60 }, { 56, 60, 65, 70, 75 } };
    public static final int[][] kernelprueba = { { -2, -1, 0 }, { -1, 1, 1 }, { 0, 1, 2 } };

    public static int[][] A = new int[N][M];
    public static int[][] C = new int[N][M];

    private int ini, fini;
    private int[][] mat, h;

    public conVolParalela(int ini, int fini, int[][] mat, int[][] kernel) {
        this.ini = ini;
        this.fini = fini;
        this.mat = mat;
        h = kernel;
    }

    @Override
    public void run() {
        for (int i = ini; i <= fini; i++) {
            for (int j = 1; j < (M - 1); j++) {
                for (int rowoffset = -1; rowoffset <= 1; rowoffset++) {
                    for (int coloffset = -1; coloffset <= 1; coloffset++) {
                        C[i][j] += mat[i + rowoffset][j + coloffset] * h[1 + rowoffset][1 + coloffset];
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Numero de tareas: ");
        int numHilos = sc.nextInt();

        System.out.println("1. Enfoque");
        System.out.println("2. Realzar bordes");
        System.out.println("3. Detectar bordes");
        System.out.println("4. Sobel");
        System.out.println("5. Sharpen");
        System.out.println("6. Prueba");
        System.out.println("Elija un kernel: ");
        int op = sc.nextInt();
        int[][] kernel = null;
        switch (op) {
            case 1:
                kernel = conVolParalela.enfoque;
                break;
            case 2:
                kernel = conVolParalela.realzaBordes;
                break;
            case 3:
                kernel = conVolParalela.detectaBordes;
                break;
            case 4:
                kernel = conVolParalela.sobel;
                break;
            case 5:
                kernel = conVolParalela.sharpen;
                break;
            case 6:
                kernel = conVolParalela.kernelprueba;
            default:
                break;
        }

        rellenoAleatorio(A);

        long initime = System.nanoTime();
        // reparto
        List<Runnable> tareas = new LinkedList<Runnable>();
        int numTareas = (N - 2) / numHilos;
        int restoTareas = (N - 2) % numHilos;
        int i;
        int n = N - 2 - restoTareas; // filas evaluadas
        //System.out.println("numTareas = " + numTareas);
        //System.out.println("resto = " + restoTareas);
        //System.out.println("n = " + n);
        for (i = 1; i <= (n - numTareas); i += numTareas) {
            //System.out.println("ini= " + i + " fin= " + ((i + numTareas) - 1));
            tareas.add(new conVolParalela(i, (i + numTareas) - 1, A, kernel));
        }
        if (restoTareas != 0) {
            //System.out.println("RESTO ini= " + i + " fin= " + ((i + numTareas + restoTareas) - 1));
            tareas.add(new conVolParalela(i, (i + numTareas + restoTareas) - 1, A, kernel));
        } else {
            //System.out.println("EXACTO ini= " + i + " fin= " + ((i + numTareas) - 1));
            tareas.add(new conVolParalela(i, (i + numTareas) - 1, A, kernel));
        }

        ExecutorService pool = Executors.newFixedThreadPool(numHilos);
        for (Runnable runnable : tareas) {
            pool.execute(runnable);
        }

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.DAYS);

        long endtime = System.nanoTime();
        double exetime = ((double) endtime - initime) / 1.0e6;

        System.out.println("Tiempo de ejecucion: " + exetime + " milisegundos.");
        //printMatrix(prueba, "orig");
        //printMatrix(C, "resultado");
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