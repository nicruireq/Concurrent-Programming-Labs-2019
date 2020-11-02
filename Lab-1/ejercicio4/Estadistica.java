package p1.ejercicio4;

import java.util.*;

public class Estadistica
{
    private Map<Double, Integer> datos;
    int tam;

    public Estadistica(Collection<Double> entrada)
    {
        datos = new HashMap<Double, Integer>();
        tam = entrada.size();

        for (Double e:entrada)
        {
            if (!datos.containsKey(e))  // si la clave no esta en el map
                datos.put(e, 1);
            else
                datos.put(e, datos.get(e)+1);   // si ya estaba, actualizo el valor
                                                // se incrementa en 1
        }

    }

    public Estadistica(Double[] entrada)
    {
        datos = new HashMap<Double, Integer>();
        tam = entrada.length;

        for (Double e:entrada)
        {
            if (!datos.containsKey(e))  // si la clave no esta en el map
                datos.put(e, 1);
            else
                datos.put(e, datos.get(e)+1);   // si ya estaba, actualizo el valor
                                                // se incrementa en 1
        }
    }

    public String toString()
    {
		StringBuffer format = new StringBuffer("(dato, frecuencia)");
        Set<Map.Entry<Double, Integer>> pares = datos.entrySet();
        // para mostrar ordenados:
        SortedSet<Map.Entry<Double, Integer>> ordenados = new TreeSet<Map.Entry<Double, Integer>>(Map.Entry.comparingByKey());
        ordenados.addAll(pares);

		for (Map.Entry<Double, Integer> p : ordenados)
		{
			format.append("\n(" + p.getKey() + ", " + p.getValue() + ")\n");
		}
		
		return format.toString();
    }

    public Double getMedia()
    {
        Double count = 0.0;
        Set<Map.Entry<Double, Integer>> pares = datos.entrySet();

        for(Map.Entry<Double, Integer> par : pares)
        {
            count += ( par.getKey() * ((double)par.getValue()) );
        }
       
        return (count / this.tam);
    }

    public Double getVarianza()
    {
        Double media = getMedia();
        Double count = 0.0;
        Set<Map.Entry<Double, Integer>> pares = datos.entrySet();

        for(Map.Entry<Double, Integer> par : pares)
        {
            count += (par.getKey() * par.getKey() * par.getValue());
        }

        return (count / this.tam) - (media * media);
    }

    public Double getDesviacion()
    {
        Double varianza = getVarianza();

        return Math.sqrt(varianza);
    }

    public Double getModa()
    {
        Integer max = 0;    //menor numero posible (frecuencias > 0)
        Double moda = 0.0;
        Set<Map.Entry<Double, Integer>> pares = datos.entrySet();
        
        for(Map.Entry<Double, Integer> par : pares)
        {
            if (par.getValue() > max)
            {
                max = par.getValue();
                moda = par.getKey();
            }
        }

        return moda;
        //return Collections.max(pares, Map.Entry.comparingByValue()).getKey(); 
    }
    

    /**
     * 
     * @param args: arg[0] es el numero de datos 
     */
    public static void main(String[] args)
    {
        Integer param;	// numero de datos a introducir por teclado para el estudio
        List<Double> entrada = new ArrayList<Double>();
        Scanner sc = new Scanner(System.in);

        if (args.length != 1)
            throw new IllegalArgumentException("ERROR: Falta un argumento");
        
        if (( param = Integer.parseInt(args[0]) ) < 1)
            throw new IllegalArgumentException("ERROR: Argumento invalido");
        
        //  lectura
        for (Integer i=0; i<param; ++i)
        {
            System.out.print("\nIntroduzca dato: ");
            entrada.add(sc.nextDouble());
        }

        Estadistica estat = new Estadistica(entrada);
        menu(estat);
        
        sc.close();
    }

    private static void menu(Estadistica es)
    {
        Integer op = 0;
        Scanner sc = new Scanner(System.in);

        while (op != -1)
        {
            System.out.println("Seleccione operacion:");
            System.out.println("1) media");
            System.out.println("2) moda");
            System.out.println("3) varianza");
            System.out.println("4) Desviacion tipica");
            System.out.println("5) Salir");
            System.out.println("\n\tSlecciones opcion: ");
            op = sc.nextInt();

            switch(op)
            {
                case 1:
                    System.out.println("\n\tMedia = " + es.getMedia());
                    break;
                case 2:
                    System.out.println("\n\tModa = " + es.getModa());
                    break;
                case 3:
                    System.out.println("\n\tVarianza = " + es.getVarianza());
                    break;
                case 4:
                    System.out.println("\n\tDesviacion = " + es.getDesviacion());
                    break;
                case 5:
                    return;
                default:
                    System.out.println("ERROR: Opcion invalida");
            }
        }    
        
        sc.close();
    }

}