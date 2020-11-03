/**
 * @author Nicolas Ruiz Requejo
 */

import java.rmi.Naming;
import java.util.Arrays;
import java.util.Scanner;

public class cBonoloto {

    public static void main(String[] args) 
        throws Exception
    {
        IBonoloto bonoloto = (IBonoloto) Naming.lookup("//localhost/BonolotoServer");

        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca su apuesta");
        System.out.println("Introduzca los numeros separados por comas:");
        String scomb = sc.nextLine();
        String[] nums = scomb.split(",");
        int[] apuesta = new int[6];
        for (int i=0; i<6; ++i)
            apuesta[i] = Integer.parseInt(nums[i]);
        System.out.println("Su apuesta es: " + Arrays.toString(apuesta));
        if ( bonoloto.apostar(apuesta) )
            System.out.println("Enhorabuena has ganado!!!");
        else
            System.out.println("Lo sentimos, otra vez sera!!!");
    }
}