import java.util.Random;

/**
 * Producto escalar de vectores de reales
 * 
 * @author Nicolas Ruiz Requejo
 */

public class prodEscalar {

    private static final int N = 1000000;

    /**
     * @param vectorA vector de double
     * @param vectorB vector de double
     * @return producto escalar de vectorA y vectorB
     * @throws Exception si las dimensiones de vectorA y vectorB difieren
     */
    public static double productoEscalar(double[] vectorA, double[] vectorB) throws Exception {
        double resultado = 0.0;

        if (vectorA.length != vectorB.length)
            throw new Exception("Los vectores deben tener la misma dimension");

        for (int i = 0; i < vectorB.length; i++)
            resultado += vectorA[i] * vectorB[i];
        return resultado;
    }

    public static void main(String[] args) throws Exception {
        double[] v1 = new double[N];
        double[] v2 = new double[N];

        //fillRandomVector(v1);
        //fillRandomVector(v2);
        fillSequentialVector(v1);
        fillSequentialVector(v2);

        long tini = System.nanoTime();
        double prod = productoEscalar(v1, v2);
        double exeTime = ((double) (System.nanoTime() - tini)) / 1.0e12;

        System.out.println("El resultado del producto es: " + prod);
        System.out.println("El tiempo de ejecución es: " + exeTime + " segundos");
    }

    /**
     * Imprime un vector de double
     * 
     * @param v vector de double
     **/
    private static void printVector(double[] v) {
        System.out.print("[ ");
        for (int i = 0; i < v.length; ++i) {
            if (i == v.length - 1)
                System.out.print(v[i] + " ]");
            else
                System.out.print(" " + v[i] + ", ");
        }
    }

    /**
     * Rellena un vector de double con datos aleatorios
     * 
     * @param v vector de double
     */
    private static void fillRandomVector(double[] v) {
        Random generador = new Random(System.nanoTime());
        for (int i = 0; i < v.length; i++)
            v[i] = generador.nextDouble();
    }

    /**
     * Rellena en vector de double con los n primeros enteros positivos
     * 
     * @param v vector de double
     */
    private static void fillSequentialVector(double[] v) {
        for (int i = 0; i < v.length; i++) {
            v[i] = i + 1;
        }
    }
}