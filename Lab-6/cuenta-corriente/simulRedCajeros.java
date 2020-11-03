/**
 * simulRedCajeros.java
 * 
 * @author Nicolas Ruiz Requejo
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class simulRedCajeros {

    public static void main(String[] args) throws InterruptedException {
        double cantidadInicial = 500.0;
        cuentaCorrienteSegura cuenta1 = new cuentaCorrienteSegura(cantidadInicial);

        System.out.println("Saldo inicial: " + cantidadInicial);

        ExecutorService pool = Executors.newCachedThreadPool();
        pool.execute(()->{cuenta1.ingreso(300.0);cuenta1.reintegro(150.0);});
        pool.execute(() -> {cuenta1.reintegro(75.0);cuenta1.reintegro(50.0);});
        pool.execute(()->{cuenta1.reintegro(200.0);cuenta1.ingreso(5.0);cuenta1.reintegro(300.0);});
        
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.DAYS);
        

        System.out.println("Al final tiene que quedar 30.0 en la cuenta... Y queda: " + cuenta1.getSaldo());
    }

}