
/**
 * clienteMultiple.java
 * 
 * @author Nicolas Ruiz Requejo
 */

import java.net.*;
import java.io.*;

public class clienteContador {

    private static int _random() {
        return (int) (Math.random() * 10);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Uso: clienteMultiple num_peticiones");
            System.exit(-1);
        }
        int peticiones = Integer.parseInt(args[0]);
        int puerto = 2001;
        for (int j = 0; j < peticiones; j++) {
            try {

                System.out.println("Realizando conexion " + j + " ...");
                Socket cable = new Socket("localhost", puerto);
                System.out.println("Realizada conexion a " + cable);
                PrintWriter salida = new PrintWriter(
                        new BufferedWriter(new OutputStreamWriter(cable.getOutputStream())));
                salida.println(_random());
                salida.flush();
                System.out.println("Cerrando conexion" + j + "...");
                cable.close();

            } // try
            catch (Exception e) {
                System.out.println("Error en sockets...");
            }
        }
    }// main
}// Cliente_Hilos
