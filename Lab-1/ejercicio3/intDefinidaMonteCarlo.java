package p1.ejercicio3;

import java.util.Scanner;
import java.util.function.*;

public class intDefinidaMonteCarlo 
{
	
	private static double areaRectangulo(double b0, double b1, DoubleFunction<Double> fun)
    {
		return (b1-b0) * Math.max(Math.abs(fun.apply(b0)-b0), Math.abs(fun.apply(b1)-b0));
		
        //return (f(1) * (1-0) ); // area del rectangulo que encuadra la funcion
                                //  A = b * h; b = x_final-x_inicial; h = f(x_final)-0
    }
	
	public static double monteCarlo(int np, DoubleFunction<Double> fun)
    {
        double xAleat, yAleat;
        double cuenta = 0.0;    //acumula puntos por debajo de la curva
        double result;
        double area = areaRectangulo(0,1,fun);	// rectangulo con base 1

        System.out.println("AREA = " + area);
        	
        for (int i=0; i<np; ++i)
        {
            xAleat = Math.random(); // genera x aleatorio
            yAleat = area * Math.random(); // genera y del par (xAleat, yAleat)

            if (yAleat <= fun.apply(xAleat))    // comprueba que el punto aleatorio esta por debajo de la funcion
                ++cuenta;               // cuenta el punto 
        } 
        result = (cuenta / np) * area;    // calcula proporcion del area del rectangulo
                                                // que es el area bajo la funcion (solucion aproximada)
        return result;
    }
	
		public static void main(String args[])
		{
			int puntos;
	        Scanner sc = new Scanner(System.in);
	        System.out.println("Introduzca numero de puntos para la aproximacion: ");
	        puntos = sc.nextInt();
	        double rfun1 = monteCarlo(puntos, x->{return Math.sin(x);});
	        double rfun2 = monteCarlo(puntos, x->{return x;});
	        		
	        System.out.println("Solucion aproximada de [f(x)=sin(x)]: " + rfun1);
	        System.out.println("Solucion aproximada de [f(x)=x]: " + rfun2);
	        
	        
	        sc.close();
	    }
}
