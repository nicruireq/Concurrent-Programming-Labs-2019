package p1.ejercicio5;

import java.lang.*;
import java.util.*;

public final class Complejos
{
    private final double[] compx = new double[2];

    public Complejos()
    {
        compx[0] = 0.0;
        compx[1] = 0.0;
    }

    public Complejos(double real, double img)
    {
        compx[0] = real;
        compx[1] = img;
    }

    public Complejos(String format)
    {
        Double r, i;
        String sr, si;
        String[] tokens;

        tokens = format.split(","); //parte la cadena
        if (tokens.length == 2) // comprobar formato "(x,y)"
        {
            if (tokens[0].length() > 1 
                && tokens[1].length() > 1)    // evitar: (,)
            {
                if (tokens[0].startsWith("(")
                     && tokens[1].endsWith(")"))
                {
                    sr = tokens[0].substring(1);    //extraer parte real en cadena "(x"
                    si = tokens[1].substring(0, tokens[1].length()-1);    //extraer parte imaginaria en cadena "y)"

                    if (isDouble(sr) && isDouble(si))
                    {
                        r = Double.parseDouble(sr.trim());
                        i = Double.parseDouble(si.trim());
                        // instanciar objeto:
                        compx[0] = (double)r;
                        compx[1] = (double)i;
                    }
                    else    // no cumple formato => lanza excepcion
                        throw new IllegalArgumentException("ERROR: No son numeros: (x,y)");
                }
                else    // no cumple formato => lanza excepcion
                    throw new IllegalArgumentException("ERROR: Formato invalido, debe ser: (x,y)");
            }
            else    // no cumple formato => lanza excepcion
                throw new IllegalArgumentException("ERROR: Cadena vacia: (x,y)");
        }
        else    // no cumple formato => lanza excepcion
            throw new IllegalArgumentException("ERROR: Demasiados elementos: (x,y)");
    }

    private static boolean isDouble(String cadena)
	{
		try {
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}

    public Complejos conjugado()
    {
        return new Complejos(compx[0], -compx[1]);
    }

    public Complejos suma(Complejos c)
    {
        return new Complejos(compx[0] + c.getReal(), 
                             compx[1] + c.getImg()  );
    }

    public Complejos resta(Complejos c)
    {
        return new Complejos(compx[0] - c.getReal(), 
                             compx[1] - c.getImg()  );
    }

    public double modulo()
    {
        return (Math.sqrt(compx[0]*compx[0] 
                          + compx[1]*compx[1]) );
    }

    public Complejos producto(Complejos c)
    {
        return new Complejos(compx[0]*c.getReal() - compx[1]*c.getImg(),
                             compx[0]*c.getImg() + compx[1]*c.getReal() );
    }

    public Complejos cociente(Complejos dem)
    {
        double c = dem.getReal();
        double d = dem.getImg();
        double prod = c*c + d*d;   //producto denominador * conj(denonminador)
        return new Complejos( (compx[0]*dem.getReal() + compx[1]*dem.getImg()) / prod,
                                (compx[1]*dem.getReal() - compx[0]*dem.getImg()) / prod );
    }

    public double getReal()
    {
        return this.compx[0];
    }

    public double getImg()
    {
        return this.compx[1];
    }

    public boolean equals(Object o)
    {
        boolean val = false;

        if (o instanceof Complejos)
        {
            val = ( compx[0]==((Complejos)o).getReal() )
                    && ( compx[1]==((Complejos)o).getImg() ) ;
        }

        return val;
    }

    public int hashCode()
    {
        return compx.hashCode();
    }

    public String toString()
    {
        return "(" + compx[0] + ", " + compx[1] + "i)";
    }

}
