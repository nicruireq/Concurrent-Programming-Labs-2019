/**
 * @author Nicolas Ruiz Requejo
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface iPiMonteCarlo
  extends Remote
{
  public void reset() throws RemoteException;
  public void masPuntos(int npoints)  throws RemoteException;
  public double iValue() throws RemoteException;
}