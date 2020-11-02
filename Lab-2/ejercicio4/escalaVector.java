package p2.ejercicio4;

import java.util.Random;

/**
 *  @author Nicolás Ruiz Requejo
 */

public class escalaVector 
{ 

    public static void escalarVector(int[] vect, int k)
    {
        for (int i=0; i<vect.length; ++i)
            vect[i] *= k;
    }

    public static void main(String[] args)
    {
    	int N = 100000000;	// 10e+8 elmentos
        int[] v = new int[N];
        Random ran = new Random();

        for (int i=0; i<v.length; ++i)
            v[i] = ran.nextInt() % 10;

        escalarVector(v, 5);

        // imprimir 20 primeros
        for (int i=0; i<20 ; ++i)
            System.out.println("pos["+i+"] = " + v[i]);
    }

}