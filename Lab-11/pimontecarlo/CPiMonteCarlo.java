/**
 * @author Nicolas Ruiz Requejo
 */

import java.rmi.Naming;
import java.util.Scanner;

public class CPiMonteCarlo {

    public static void main(String[] args) throws Exception {
        iPiMonteCarlo server = (iPiMonteCarlo) Naming.lookup("//localhost:2020/montecarlo");
        Scanner sc = new Scanner(System.in);
        for (;;) {
            System.out.println("1. Peticion de puntos\n" + "2. Ver aproximacion actual\n" + "3. Resetear aproximacion\n"
                    + "4. Salir");
            int opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    System.out.println("Introduzca el numero de puntos: ");
                    int puntos = sc.nextInt();
                    server.masPuntos(puntos);
                    break;
                case 2:
                    System.out.println("El valor aproximado de la integral es: " + 
                            server.iValue() );
                    break;
                case 3:
                    server.reset();
                    System.out.println("Reseteado con exito!!!");
                    break;

                default:
                    System.exit(0);
                    break;
            }
        }
    }
}