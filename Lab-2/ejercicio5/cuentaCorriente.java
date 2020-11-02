package p2.ejercicio5;

/**
 *  @author Nicolás Ruiz Requejo
 */


public class cuentaCorriente 
{
	private int codigo;
	private double saldo;
	
	public cuentaCorriente() {
		
	}
	
	public cuentaCorriente(int cod, double sal) {
		codigo = cod;
		saldo = sal;
	}
	
	public void ingreso(double deposito) {
		if (saldo > 0) saldo += deposito;
	}
	
	public boolean reintegro(double cantidad) {
		if (cantidad > 0 && (saldo-cantidad) >= 0) {
			saldo -= cantidad;
			return true;
		} else {
			return false;
		}
	}
	
	public double getSaldo() {
		return saldo;
	}
	
}
