
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

public class sLibros extends UnicastRemoteObject implements iLibros {

    private static final int MAX = 1000;
    private int last;
    private Libro[] catalogo;

    public sLibros() throws RemoteException {
        super();
        catalogo = new Libro[MAX];
        last = -1;
    }

    @Override
    public void insertar(String titulo, String autor, String categoria) throws RemoteException {
        catalogo[++last] = new Libro(titulo, autor, categoria);
    }

    @Override
    public Libro consultar(String nombre) throws RemoteException {

        for (Libro lib : catalogo) {
            if (lib != null && lib.getTitulo().equals(nombre)) {
                return lib;
            }
        }
        return null;
    }

    @Override
    public Libro[] consultarCategoria(String categoria) throws RemoteException {
        List<Libro> encontrados = new LinkedList<Libro>();
        for (Libro lib : catalogo) {
            if (lib != null && lib.getCategoria().equals(categoria)) {
                encontrados.add(lib);
            }
        }
        return encontrados.toArray(new Libro[0]);
    }

    @Override
    public Libro[] consultarAutor(String autor) throws RemoteException {
        List<Libro> encontrados = new LinkedList<Libro>();
        for (Libro lib : catalogo) {
            if (lib != null && lib.getAutor().equals(autor)) {
                encontrados.add(lib);
            }
        }
        return encontrados.toArray(new Libro[0]);
    }

    @Override
    public boolean eliminar(String nombre) throws RemoteException {
        for (int i = 0; i < catalogo.length; ++i) {
            if ((catalogo[i] != null) && catalogo[i].getTitulo().equals(nombre)) {
                catalogo[i] = null;
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException {
        sLibros biblioteca = new sLibros();
        Naming.bind("ServerBiblioteca", biblioteca);
        System.out.println("Servidor de biblioteca activo.");
    }

}