package p2.ejercicio5;

/**
 *  @author Nicolás Ruiz Requejo
 */

public class redCajeros 
{
    public static void main(String[] args) throws Exception
    {
    	cuentaCorriente cuenta1 = new cuentaCorriente(1232, 500.0);

            Thread h1 = new Thread(new cajero(cuenta1, 0, 100.0), "Cajero1");
            Thread h2 = new Thread(new cajero(cuenta1, 1, 25.0), "Cajero2");
            Thread h3 = new Thread(new cajero(cuenta1, 0, 100.0), "Cajero3");
            Thread h4 = new Thread(new cajero(cuenta1, 1, 15.0), "Cajero4");
            Thread h5 = new Thread(new cajero(cuenta1, 0, 240.0), "Cajero5");
            Thread h6 = new Thread(new cajero(cuenta1, 1, 50.0), "Cajero5");
            Thread h7 = new Thread(new cajero(cuenta1, 0, 150.0), "Cajero5");
            
            h1.start();
            h2.start();
            h3.start();
            h4.start();
            h5.start();
            h6.start();
            h7.start();
            
            h1.join();
            h2.join();
            h3.join();
            h4.join();
            h5.join();
            h6.join();
            h7.join();
            
            System.out.println("Saldo final: " + cuenta1.getSaldo());
        }

}
