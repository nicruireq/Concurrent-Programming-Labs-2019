package p2.ejercicio5;

/**
 *  @author Nicolás Ruiz Requejo
 */

public class cajero implements Runnable
{
	private cuentaCorriente cuenta = null;
    //  1 es Desposito, 0 es Reintegro
    private int operacion;
    private double cantidad;

    public cajero(cuentaCorriente cuenta, int operacion, double cantidad)
    {
        this.cuenta = cuenta;
        this.operacion = operacion;
        this.cantidad = cantidad;
    }

    public void run()
    {
        switch (operacion)
        {
            case (0):
                cuenta.reintegro(cantidad);
                break;
            case (1):
                cuenta.ingreso(cantidad);
                break;
        }
    }

}
