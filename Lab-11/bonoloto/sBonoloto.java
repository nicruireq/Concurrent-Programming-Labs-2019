
/**
 * @author Nicolas Ruiz Requejo
 */

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Random;

public class sBonoloto extends UnicastRemoteObject implements IBonoloto {

    private static final int N = 6;
    private static final int MAX = 49;
    private int[] combinacionGanadora;

    public sBonoloto() throws RemoteException {
        super();
        combinacionGanadora = new int[N];
        Random gen = new Random();
        for (int i = 0; i < N; ++i)
            combinacionGanadora[i] = gen.nextInt(MAX + 1);
        System.out.println("Se ha generado la combinacion: " + Arrays.toString(combinacionGanadora));
    }

    @Override
    public boolean apostar(int[] apuesta) throws RemoteException {
        return (Arrays.equals(combinacionGanadora, apuesta)) ? true : false;
    }

    public static void main(String[] args) throws MalformedURLException, RemoteException, AlreadyBoundException {
        IBonoloto bonoloto = new sBonoloto();
        Naming.bind("BonolotoServer", bonoloto);
        System.out.println("Servidor de bonoloto activado.");
    }
}