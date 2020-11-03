/**
 * @author Nicolas Ruiz Requejo
 * 
 * Aproximacion de PI por el metodo de monte carlo 
 * en RMI
 */

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.lang.Math;

public class SPiMonteCarlo extends UnicastRemoteObject implements iPiMonteCarlo {

    private int intentos;
    private int contados;
    private double aproximacion;
    ReentrantLock mutex;

    public SPiMonteCarlo() throws RemoteException {
        super();
        intentos = 0;
        contados = 0;
        aproximacion = 0;
        mutex = new ReentrantLock();
    }

    private double funcion(double x) {
        return Math.sqrt(1 - x * x);
    }

    /**
     * Cada vez que un cliente llama, un hilo llama a este metodo
     * se ejecuta de forma paralela implicita, tienes
     * que controlar el acceso en exclusion mutua a recursos compartidos
     * @param npoints
     * @throws RemoteException
     */
    @Override
    public void masPuntos(int npoints) throws RemoteException {
        Random generador = new Random();
        int contadorLocal = 0;

        for (int i = 0; i < npoints; i++) {
            double cx = generador.nextDouble();
            double cy = generador.nextDouble();
            if ( funcion(cx) > cy ) ++contadorLocal;
        }
        System.out.println("npoints=" + npoints);
        System.out.println("contadorLocal=" + contadorLocal);

        mutex.lock();
        try {
            intentos += npoints;
            contados += contadorLocal;
            aproximacion = 4.0 * ( (double)contados / (double)intentos ); // el area es 1, no multiplico por el area entonces
        } finally {mutex.unlock();}
        System.out.println("intentos=" + intentos);
        System.out.println("contados=" + contados);
        System.out.println("aproximacion=" + aproximacion);
        
    }

    @Override
    public double iValue() throws RemoteException {
        return aproximacion;
    }

    public void reset() throws RemoteException {
        mutex.lock();
        try {
            intentos = 0;
            contados = 0;
            aproximacion = 0;
        } finally {mutex.unlock();}
    }

    public static void main(String[] args) 
        throws Exception
    {
      iPiMonteCarlo montecarlo = new SPiMonteCarlo();
        Naming.bind("//localhost:2020/montecarlo", montecarlo);
        System.out.println("Servidor montecarlo activo.");
    }

}