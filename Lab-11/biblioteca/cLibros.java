
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class cLibros {

    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        iLibros biblioteca = (iLibros) Naming.lookup("//localhost/ServerBiblioteca");
        Scanner sc = new Scanner(System.in);
        for (;;) {
            System.out.println(
                    "Pulse 1 para nuevo libro\n" + "2 para buscar por titulo\n" + "3 para buscar por categoria\n"
                            + "4 para buscar por autor\n" + "5 para eliminar libro\n" + "6 para salir\n");
            int opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    nuevo(biblioteca);
                    break;
                case 2:
                    getByTit(biblioteca);
                    break;
                case 3:
                    getByCat(biblioteca);
                    break;
                case 4:
                    getByAut(biblioteca);
                    break;
                case 5:
                    eliminar(biblioteca);
                    break;
                default:
                    System.exit(0);
                    break;
            }
            sc.reset();
        }
    }

    public static void nuevo(iLibros biblioteca) throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca titulo: ");
        String tit = sc.nextLine();
        System.out.println("Introduzca autor: ");
        String aut = sc.nextLine();
        System.out.println("Introduzca categoria: ");
        String cat = sc.nextLine();
        biblioteca.insertar(tit, aut, cat);
        sc.close();
    }

    public static void eliminar(iLibros biblioteca) throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca titulo: ");
        String tit = sc.nextLine();
        if (biblioteca.eliminar(tit))
            System.out.println("Se ha eliminado con exito.");
        else
            System.out.println("No se encuentra el libro");
        sc.close();
    }

    public static void getByTit(iLibros biblioteca) throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca titulo: ");
        String tit = sc.nextLine();

        Libro lib = biblioteca.consultar(tit);
        if (lib != null) {
            System.out.println("Se ha encontrado: \n" + lib.toString());
        } else {
            System.out.println("No se ha encontrado.");
        }
        sc.close();
    }

    public static void getByCat(iLibros biblioteca) throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca categoria: ");
        String tit = sc.nextLine();

        Libro[] libs = biblioteca.consultarCategoria(tit);
        if (libs.length > 0) {
            System.out.println("Se ha encontrado: \n");
            for (Libro lib : libs) {
                System.out.println(lib.toString());
                System.out.println("----------------------------------");
            }
        } else {
            System.out.println("No se ha encontrado.");
        }
        sc.close();
    }

    public static void getByAut(iLibros biblioteca) throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca autor: ");
        String tit = sc.nextLine();

        Libro[] libs = biblioteca.consultarAutor(tit);
        if (libs.length > 0) {
            System.out.println("Se ha encontrado: \n");
            for (Libro lib : libs) {
                System.out.println(lib.toString());
                System.out.println("----------------------------------");
            }
        } else {
            System.out.println("No se ha encontrado.");
        }
        sc.close();
    }

}