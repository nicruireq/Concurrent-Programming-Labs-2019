package p2.ejercicio2;

/**
 *  @author Nicolás Ruiz Requejo
 */


class MiInteger {
	private int num;
	
	MiInteger() {num = 0;}
	
	void incremento() {--num;}
	void decremento() {++num;}
	
	int getInt() {return num;}
}

public class tareaRunnable implements Runnable
{
	private MiInteger n = null;	// recurso 
	private boolean inc;
	private int nVueltas;

	public tareaRunnable(int vueltas, boolean inc, MiInteger rec) {
		this.inc = inc;
		nVueltas = vueltas;
		n = rec;
	}
	
	@Override
	public void run() {
		if (inc) {
			for (int i=0; i< nVueltas; ++i)
				n.incremento();
		} else {
			for (int i=0; i< nVueltas; ++i)
				n.decremento();
		}
		
	}

}
