/**
 * @author  Nicolás Ruiz Requejo
 */

import mpi.*;

public class primosMPJ {

    public static boolean esPrimo(long n) {
        if (n <= 1)
            return (false);
        for (long i = 2; i <= Math.sqrt(n); i++)
            if (n % i == 0)
                return (false);
        return (true);
    }

    public static long contarPrimos(long linf, long lsup) {
        long micuenta = 0;
        for (long i = linf; i <= lsup; i++)
            if (esPrimo(i))
                ++micuenta;
        return micuenta;
    }

    public static void main(String[] args) throws Exception {
        // Uso: primosMPJ enteroinferior enterosuperior
        /*if (args.length != 2) {
            System.err.println("Uso: primosMPJ enteroinferior enterosuperior");
            System.exit(-1);
        }*/

        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int emisor = 0;
        int tag = 100;

        // entero inferior del rango de busqueda
        long rangeInf = 0; //Integer.parseInt(args[0]);
        // entero superior del rango de busqueda
        long rangeSup = 100000000; //Integer.parseInt(args[1]);
        // reparto de tareas
        long tVentana = (rangeSup - rangeInf) / size;
        // Para almacenar limite inicial y final de cada particion
        long[] linferiores = new long[size];
        long[] lsuperiores = new long[size];

        
        // paerticionar trabajo
        long inicioComputacion = 0;
        if (rank == emisor) {
            inicioComputacion = System.nanoTime();
            linferiores[0] = rangeInf;
            lsuperiores[0] = rangeInf + tVentana;
            for (int i = 1; i < size; ++i) {
                linferiores[i] = lsuperiores[i-1] + 1;
                lsuperiores[i] = lsuperiores[i-1] + tVentana;
            }
        }
        long[] myLimInf = new long[1]; // mi limite inferior
        long[] myLimSup = new long[1];   // mi limite superior
        // enviar a cada proceso su limite inferior
        MPI.COMM_WORLD.Scatter(linferiores, 0, 1, MPI.LONG, myLimInf, 0, 1, MPI.LONG, emisor);
        // enviar a cada proceso su limite superior
        MPI.COMM_WORLD.Scatter(lsuperiores, 0, 1, MPI.LONG, myLimSup, 0, 1, MPI.LONG, emisor);

        // para almacenar la cuenta de primos encontrados por cada proceso
        long[] primosPorProceso = new long[1];
        // ejecutar tarea buscar primos
        primosPorProceso[0] = contarPrimos(myLimInf[0], myLimSup[0]);

        // reduccion para recolectar los resultados parciales y calcular el final
        long[] primosTotales = new long[1];
        MPI.COMM_WORLD.Reduce(primosPorProceso, 0, primosTotales, 0, 1, MPI.LONG, MPI.SUM, emisor);

        long tiempoTotal = 0;
        if (rank == emisor) {
            tiempoTotal = (System.nanoTime() - inicioComputacion) / (long) 1.0e9;
            System.out.println("Primos hallados: " + primosTotales[0]);
            System.out.println("Calculo finalizado en " + tiempoTotal + " segundos");
        }

        MPI.Finalize();
    }
}