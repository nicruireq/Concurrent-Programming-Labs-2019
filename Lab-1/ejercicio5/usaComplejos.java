package p1.ejercicio5;

import java.util.Scanner;

public class usaComplejos
{
    public static void main(String[] args)
    {
        menu();
    }

    private static void menu()
    {
        Integer op = 0;
        Scanner sc = new Scanner(System.in);

        while (op != 6)
        {
            System.out.println("\nSeleccione operacion:");
            System.out.println("1) Suma");
            System.out.println("2) Resta");
            System.out.println("3) Producto");
            System.out.println("4) Cociente");
            System.out.println("5) Modulo");
            System.out.println("6) Salir");
            System.out.println("\n\tSleccione opcion: ");
            op = sc.nextInt();

            switch(op)
            {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    slectOp(op);
                    break;
                case 6: 
                    break;
                default:
                    System.out.println("ERROR: Opcion invalida");
            }
        }    
    }

    private static void slectOp(int op)
    {
        Scanner sc = new Scanner(System.in);
        String sc1, sc2;
        Complejos c1, c2; 

        System.out.println("\nIntroduzca un complejo formato(x,y): ");
        sc1 = sc.next();
        System.out.println("\nIntroduzca un complejo formato(x,y): ");
        sc2 = sc.next();

        try
        {
            c1 = new Complejos(sc1);
            c2 = new Complejos(sc2);
        
            System.out.println("\nRESULTADO: ");
            switch (op)
            {
                case 1:
                    System.out.println(c1.toString() + " + " + c2.toString() + " = " + c1.suma(c2).toString() );
                    break;
                case 2:
                    System.out.println(c1.toString() + " - " + c2.toString() + " = " + c1.resta(c2).toString() );
                    break;
                case 3:
                    System.out.println(c1.toString() + " * " + c2.toString() + " = " + c1.producto(c2).toString() );
                    break;
                case 4:
                    System.out.println(c1.toString() + " / " + c2.toString() + " = " + c1.cociente(c2).toString() );
                    break;
                case 5:
                    System.out.println("modulo(" + c1.toString() + ") = " + c1.modulo() + "\n" + 
                                       "modulo(" + c2.toString() + ") = " + c2.modulo() );
                    break;
            }

        } catch (IllegalArgumentException e)
        {
            e.getMessage();
        }
    }
}
