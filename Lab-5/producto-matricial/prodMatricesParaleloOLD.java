import java.lang.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.text.*;

public class prodMatricesParaleloOLD implements Runnable
{
    public static final int nfilas = 1000; 
    public static final int ncols = 1000;
    public static double[][] matA = new double[nfilas][ncols];
    public static double[][] matB = new double[nfilas][ncols];;
    public static double[][] matProd = new double[nfilas][ncols];

    private int iini, jini;
    private double result;
    public prodMatricesParaleloOLD(int i, int j)
    {
        this.iini = i;
        this.jini = j;
        this.result = 0.0f;
    }

    @Override
    public void run() 
    {
        for (int r=0; r<nfilas;++r)
        {
            this.result += matA[iini][r] * matB[r][jini];
        }
        matProd[iini][jini] = this.result;
    }

    public static void main(String[] args)
    {
        // rellenar matrices
        rellenoAleatorio(matA);
        rellenoAleatorio(matB);
        // calcular hilos del pool
        int Cb = 0;
        int nucleos = Runtime.getRuntime().availableProcessors();
        int numHilos = nucleos / (1 - Cb);
        double tiempoInicio, tiempoFinal;
		tiempoInicio = System.nanoTime();
        // crear pool
        ThreadPoolExecutor mipool = new ThreadPoolExecutor(
            numHilos, numHilos, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()
        );
        mipool.prestartAllCoreThreads();
        // crear tareas
        for (int i=0; i<nfilas; ++i)
            for (int j=0; j<ncols; ++j)
                mipool.execute( new Thread(new prodMatricesParaleloOLD(i, j)) );
        // shutdown impide que se añadan mas tareas a la pool pero
        // terminan las que ya estan submited
        mipool.shutdown();
        // bucle de espera ocupada
        while ( !mipool.isTerminated() ) {}
        // tomar tiempo final
        tiempoFinal = System.nanoTime();
        System.out.println("Numero de Nucleos: "+nucleos);
        System.out.println("Coficiente de Bloqueo: "+Cb);
        System.out.println("Tamano del Pool: "+ numHilos);
        System.out.println("Tiempo Total (segundos): "+(tiempoFinal-tiempoInicio)/1.0e9); 
        // resultado
        System.out.println("Matriz A: \n");
        //printMatrix(matA);
        System.out.println("Matriz B: \n");
        //printMatrix(matB);
        System.out.println("Matriz Resultado: \n");
        //printMatrix(matProd);
    }

    private static void rellenoAleatorio(double[][] mat)
    {
        Random ran = new Random(System.nanoTime());

        for (int i=0; i<mat.length; ++i)
            for (int j=0; j<mat[0].length; ++j)
                mat[i][j] = ran.nextDouble()*10;
    }

    //  Imprime un vector de double
    private static void printVector(double[] v)
    {
        System.out.print("[ ");
        for (int i=0; i<v.length; ++i)
        {
            if (i == v.length-1)
                System.out.print( String.format(Locale.ROOT, "%.2f", v[i]) + " ]");
            else 
                System.out.print(" " + String.format(Locale.ROOT, "%.2f", v[i]) + ", ");
        }
    }

    //  imprime matriz de double
    private static void printMatrix(double[][] mat)
    {
        int rows = mat.length;
        for (int i=0; i<rows; ++i)
        {
            printVector(mat[i]);
            System.out.print('\n');
        }
        
    }

    

}