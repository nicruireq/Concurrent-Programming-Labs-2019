/**
 * @author Nicolas Ruiz Requejo
 */

public class mutex {

    private int s, bloqueados;

    public mutex(int valorInicial) {
        s = valorInicial;
        bloqueados = 0;
    }

    public synchronized void waitDijkstra() {
        while (s == 0) {
            try {
                System.out.println("Seccion critica ocupada, me bloqueo");
                bloqueados++;
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        s--;
    }

    public synchronized void signal() {
        // por que este codigo esta mal:
        // como s sigue en 0 por habia bloqueados
        // y no se ejecuto s++, todos se quedan
        // bloqueado en un livelock
        //if (bloqueados > 0) {
        //    System.out.println("Habia procesos bloaqueados = " + bloqueados);
        //    notifyAll();
        //} else {
        //    System.out.println("No entra nadie nunca");
        //    s++;
        //}
        // y este si es correcto:
        // notifyAll despertara a todos los que haya bloqueados
        // si los hay si lo hubiese se desbloqueara pero s no 
        // fuese >0 se bloquearia de nuevo por siempre
        // tenemos que asegurar que cuando se despierten
        // como el que ejecuta el signal va a salir siempre
        // deje preaparado s, s>0 para que los demas puedan
        // entrar a la seccion critica. Por lo tanto:
            
        s++; 
        notifyAll();  
    }
}