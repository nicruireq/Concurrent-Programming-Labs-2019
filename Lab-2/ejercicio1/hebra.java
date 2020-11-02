package p2.ejercicio1;

/**
 *  @author Nicolás Ruiz Requejo
 */


public class hebra extends Thread
{
	static int n = 0;
	
	private int _iVueltas;
	private boolean _bTipoHilo;
	
	public hebra(int iIncrs, boolean bTipo) {
		_iVueltas = iIncrs;
		_bTipoHilo = bTipo;
	}
	
	public void run() {
		if (_bTipoHilo) {
			for (int i=0; i< _iVueltas; ++i)
				++n;
		} else {
			for (int i=0; i< _iVueltas; ++i)
				--n;
		}
	}
	
}
