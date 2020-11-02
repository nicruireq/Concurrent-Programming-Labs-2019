package p2.ejercicio4;

import java.util.Random;

/**
 *  @author Nicolás Ruiz Requejo
 */

public class escalaVPar implements Runnable
{
    static final int N = 100000000;
    public static int[] vect = new int[N];
    private int ini, fin; 
    //  factor de escalado
    private int k;

    public escalaVPar(int ini, int fin, int k)
    {
        this.ini = ini;
        this.fin = fin;
        this.k = k;
    }

    public void run()
    {
        for (int i=ini; i<fin; ++i)
            vect[i] *= k;
    }

    public static void main(String[] args)
        throws Exception
    {
        int nCores = 8;
        Random ran = new Random();
        int tamVect = escalaVPar.vect.length;
        int longitud = tamVect / nCores;
        escalaVPar[] evps = new escalaVPar[nCores];
        Thread[] hilos = new Thread[nCores];
        int ini = 0, fini = longitud-1;

        //  rellena vector
        //System.out.println("\nANTES ESCALAR:\n");
        for (int i=0; i<tamVect; ++i)
        {
            vect[i] = ran.nextInt();
            //System.out.println("pos["+i+"] = " + vect[i]);
        }

        for (int i=0; i<nCores; ++i)
        {
            evps[i] = new escalaVPar(ini, fini, 1);
            hilos[i] = new Thread(evps[i]);
            ini = fini + 1;
            fini += longitud;
        }
        
        // co-rutina
        for (int i=0; i<nCores; ++i)
            hilos[i].start();

        for (int i=0; i<nCores; ++i)
            hilos[i].join();

        //fin
        /*
        System.out.println("\nRESULTADO:\n");
        for (int i=0; i<tamVect; ++i)
            System.out.println("pos["+i+"] = " + vect[i]);
        */
    }

}
