package p2.ejercicio1;

/**
 *  @author Nicolás Ruiz Requejo
 */


public class Usa_hebra 
{
	public static void main(String[] args) throws InterruptedException {
		
		int N = 10000000;
		Thread tInc = new hebra(N, true);
		Thread tDec = new hebra(N, false);
		
		tInc.start();
		tDec.start();
		
		tInc.join();
		tDec.join();
		
		System.out.println("Valor final de n = " + hebra.n);
	}

}
