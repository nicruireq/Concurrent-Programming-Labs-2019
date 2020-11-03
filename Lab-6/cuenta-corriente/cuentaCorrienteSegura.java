/**
 * cuentaCorrienteSegura.java
 * 
 * @author Nicolas Ruiz Requejo
 */
public class cuentaCorrienteSegura {

    private double saldo;

    public cuentaCorrienteSegura(double cantidadInicial) {
        saldo = cantidadInicial;
    }

    public synchronized void ingreso(double deposito) {
		if (deposito > 0) saldo += deposito;
	}

    public synchronized boolean reintegro(double cantidad) {
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