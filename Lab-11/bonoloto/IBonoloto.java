/**
 * @author Nicolas Ruiz Requejo
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * IBonoloto
 */
public interface IBonoloto extends Remote {

    public boolean apostar(int[] apuesta) throws RemoteException;
}