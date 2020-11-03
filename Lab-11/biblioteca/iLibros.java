
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface iLibros extends Remote {
    public void insertar(String titulo, String autor, String categoria) 
        throws RemoteException;
    public Libro consultar(String nombre) throws RemoteException;
    public Libro[] consultarCategoria(String categoria) throws RemoteException;
    public Libro[] consultarAutor(String autor) throws RemoteException;
    public boolean eliminar(String nombre) throws RemoteException;
}