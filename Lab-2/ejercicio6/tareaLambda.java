package p2.ejercicio6;

/**
 *  @author Nicolás Ruiz Requejo
 */


public class tareaLambda 
{
	static int n = 0;
	
	public static void main(String[] args) throws InterruptedException {
		
		int N = 1000000;	// iteraciones
		Runnable task1 = () -> {for(int i=0; i<N; i++) ++n;};
		Runnable task2 = () -> {for(int i=0; i<N; i++) --n;};
		Thread tInc = new Thread( task1);
		Thread tDec = new Thread( task2);
		
		tInc.start();
		tDec.start();
		
		tInc.join();
		tDec.join();
		
		System.out.println("Valor final de n = " + n);
	}

}
