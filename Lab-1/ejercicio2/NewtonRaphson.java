package p1.ejercicio2;

import java.lang.Math;
import java.util.Scanner;
import java.util.function.*;

public class NewtonRaphson
{
	
	public static double newton(double inicial, int iteraciones, 
			DoubleFunction<Double> funcion, DoubleFunction<Double> derivada)
	{
		double xn = inicial;
		
		for (int i=0; i<iteraciones; ++i) {
			xn = xn - (funcion.apply(xn) / derivada.apply(xn));
		}
		
		return xn;
	}
	
	public static void main(String[] args)
	{
		double x0, raiz;
		int it;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Para la funcion [cos x - x^3]:");
		
		System.out.println("Introduzca punto inicial: ");
		x0 = sc.nextDouble();
		System.out.println("Introduzca numero de iteraciones: ");
		it = sc.nextInt();
		raiz = newton(x0, it, x->{return Math.cos(x)-Math.pow(x,3);}, 
				x->{return (-1.0)*Math.sin(x)-3.0*Math.pow(x,2);});
		System.out.println("Aproximacion de la raiz = "+ raiz);
		
		System.out.println("Para la funcion [x^2 - 5]:");
		System.out.println("Introduzca punto inicial: ");
		x0 = sc.nextDouble();
		System.out.println("Introduzca numero de iteraciones: ");
		it = sc.nextInt();
		raiz = newton(x0, it, x->{return Math.pow(x,2)-5;}, 
				x->{return 2*x;});
		System.out.println("Aproximacion de la raiz = "+ raiz);
		
		sc.close();
	}
}
