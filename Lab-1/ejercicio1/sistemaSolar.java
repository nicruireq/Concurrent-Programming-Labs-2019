package p1.ejercicio1;

public class sistemaSolar {
	
	public static void main(String[] args)
	{
		Estrella sol = new Estrella(1.4e+9, 6.08e+12, 1.41e+18, 1.98e+30, 1411.0, 274.0, 5778.0, -26.8, 3.8e+26, 1.36e+7, 2.0e+5);
		CuerpoPlanetario tierra = new CuerpoPlanetario(12742.0, 5.1e+8, 1.08e+12, 5.97e+24, 5.5, 10, 1.0, 0.98, 287.2, 0.367, 0.41, 0.016, sol);
		Satelite luna = new Satelite(3474.0, 38.0e+6, 2.18e+10, 7.34e+22, 3.34, 1.62, 0.2, 0, 380, 0.12, 1.54, 0.05, sol, 3.0e-10, 0.0025, tierra);
		
		System.out.println("Datos de la luna: " + luna.toString());
	}
	
}
