import java.util.Random;

/**
 * 
 * @author Nicolas Ruiz Requejo
 */

enum TipoProceso {
    PROCESO_A, PROCESO_B, PROCESO_C
}

public class UsamonitorCadena {

    public static final int N = 10000;
    public static final int M = 10000;

    public static Matriz generarAleatoria() {
        Matriz mat = new Matriz(N, M);
        Random gen = new Random();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                mat.matriz[i][j] = gen.nextInt();
            }
        }
        return mat;
    }

    public static Matriz traspuesta(Matriz mat) {
        Matriz tras = new Matriz(N, M);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                tras.matriz[i][j] = mat.matriz[j][i];
            }
        }
        return tras;
    }

    public static int productoDiagonal(Matriz mat) {
        int prod = 1;
        for (int i = 0; i < N; i++) {
            prod *= mat.matriz[i][i];
        }
        return prod;
    }

    public static void main(String[] args) throws InterruptedException {
        monitorCadena_1 bufferAB = new monitorCadena_1(100);
        monitorCadena_1 bufferBC = new monitorCadena_1(50);

        Thread procesoA = new Thread(() -> {
            for (;;) {
                Matriz aleat = generarAleatoria();
                //System.out.println(aleat.toString());
                bufferAB.insertar(aleat);
            }
        });

        Thread procesoB = new Thread(() -> {
            for (;;) {
                Matriz m = bufferAB.extraer();
                Matriz mTras = traspuesta(m);
                //System.out.println(mTras.toString());
                bufferBC.insertar(mTras);
            }
        });

        Thread procesoC = new Thread(() -> {
            for (;;) {
                Matriz m = bufferBC.extraer();
                int res = productoDiagonal(m);
                System.out.println("Resultado cadena: " + res);
            }
        });

        procesoA.setName("A");
        procesoB.setName("B");
        procesoC.setName("C");
        procesoA.start();
        procesoB.start();
        procesoC.start();

        procesoA.join();
        procesoB.join();
        procesoC.join();
    }

}