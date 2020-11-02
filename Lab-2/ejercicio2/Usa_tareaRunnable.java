package p2.ejercicio2;

/**
 *  @author Nicolás Ruiz Requejo
 */


public class Usa_tareaRunnable 
{
	public static void main(String[] args) throws InterruptedException {
		
		int N = 10000000;	// iteraciones
		MiInteger recurso = new MiInteger();// recurso compartido
		Thread tInc = new Thread( new tareaRunnable(N, true, recurso));
		Thread tDec = new Thread( new tareaRunnable(N, false, recurso));
		
		tInc.start();
		tDec.start();
		
		tInc.join();
		tDec.join();
		
		System.out.println("Valor final de n = " + recurso.getInt());
	}

}
