import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Encontrar numeros perfectos en un rango dado por computacion asincrona a
 * futuro
 * 
 * @author Nicolas Ruiz Requejo
 */

public class numPerfectos implements Callable<Long> {

    private long rangoIni, rangoFin;

    public numPerfectos(long ini, long fin) {
        if (ini > fin)
            throw new IllegalArgumentException("El rango final debe ser igual" + "o mayor que el inicial");
        this.rangoIni = ini;
        this.rangoFin = fin;
    }

    /**
     * Comprobar si un numero es perfecto
     * 
     * @param n numero a comprobar
     * @return true si n es perfecto, false en caso contrario
     */
    private boolean esPerfecto(long n) {
        long suma = 0;
        for (long i = 1; i < n; i++) {
            if ((n % i) == 0)
                suma += i;
        }
        return (n - suma) == 0;
    }

    @Override
    public Long call() throws Exception {
        long cuenta = 0;
        for (long i = rangoIni; i <= rangoFin; ++i) {
            if (esPerfecto(i))
                ++cuenta;
        }
        return cuenta;
    }

    private static final String USO = "Uso: numPerfectos enteromaximo [-h numhilos | -c coeficientebloqueo]";

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        double cb = 0;
        int nucleos = Runtime.getRuntime().availableProcessors();
        int numHilos = (int) (nucleos / (1 - cb));
        // maximo entero del rango a buscar
        long rangoEnteros = 0;

        // procesar linea de comandos
        if (args.length < 1) {
            System.err.println(USO);
            System.exit(-1);
        } else {
            rangoEnteros = Integer.parseInt(args[0]);
            if (args.length == 3) {
                switch (args[1]) {
                    case "-h":
                        numHilos = Integer.parseInt(args[2]);
                        break;
                    case "-c":
                        cb = Integer.parseInt(args[2]);
                        break;
                    default:
                        System.err.println(USO);
                        System.exit(-1);
                        break;
                }
            }
        }

        long tiempoIni = System.nanoTime();
        // crear pool
        ExecutorService mipool = Executors.newFixedThreadPool(numHilos);
        List<Future<Long>> resultadosParciales = new LinkedList<Future<Long>>();
        // reparto de tareas
        long numTareas = rangoEnteros / numHilos;
        long restoTareas = rangoEnteros % numHilos;
        int i;
        for (i = 1; i < (rangoEnteros - numTareas); i += numTareas) {
            System.out.println("Tareas de la " + i + " hasta " + ((i + numTareas) - 1));
            resultadosParciales.add(mipool.submit(new numPerfectos(i, (i + numTareas) - 1)));
        }
        if (restoTareas != 0) {
            System.out.println(
                    "RESTO:" + restoTareas + " Tareas de la " + i + " hasta " + ((i + numTareas + restoTareas) - 1));
            resultadosParciales.add(mipool.submit(new numPerfectos(i, (i + numTareas + restoTareas) - 1)));
        } else {
            System.out.println("EXACTO Tareas de la " + i + " hasta " + ((i + numTareas) - 1));
            resultadosParciales.add(mipool.submit(new numPerfectos(i, (i + numTareas) - 1)));
        }

        mipool.shutdown();
        // recolectar resultados parciales
        // esto puede provocar inanicion???
        long numNaturalesPerfectos = 0;
        // iterar por todos los elementos de la
        // lista de Futures para recolectar los
        // resultados parciales hasta que todos
        // hayan terminado (polling)
/*
        while (!resultadosParciales.isEmpty()) {
            int t = 0;
            Future<Long> temp = resultadosParciales.get(t); // solo si el future ha terminado se recolecta
            if (temp.isDone()) {
                numNaturalesPerfectos += temp.get();
                // si el future ha sido recolectado // lo sacamos de la lista
                resultadosParciales.remove(t);
            }
            // circular, para volver a comprobar todos
            if (!resultadosParciales.isEmpty())
                t = (t + 1) % resultadosParciales.size();
        }
*/
        // otra opcion, bloqueante
        for (Future<Long> future : resultadosParciales) {
            numNaturalesPerfectos += future.get();
        }

        double exetime = ((double) (System.nanoTime() - tiempoIni)) / 1.0e9;

        // resultados
        System.out.println("Rango buscado: " + rangoEnteros);
        System.out.println("Cantidad de numeros perfectos encontrados: " + numNaturalesPerfectos);
        System.out.println("Nucleos de la maquina: " + nucleos);
        System.out.println("Coeficiente de bloqueo: " + cb);
        System.out.println("Numero de hilos: " + numHilos);
        System.out.println("Tiempo en segundos: " + exetime);

    }

}