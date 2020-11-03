import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Nicolas Ruiz Requejo
 */

public class usaConductores {
    public static void main(String[] args) throws InterruptedException {
        Conductores bd = new Conductores();
        Conductor c1 = new Conductor("31874565c", "Jose Maria", "Perez Galvez");
        c1.addPermiso(TipoPermiso.A);
        c1.addPermiso(TipoPermiso.B);
        Conductor c2 = new Conductor("32458547k", "Maria Josefa", "Martinez Martinez");
        c2.addPermiso(TipoPermiso.B);
        Conductor c3 = new Conductor("31745898z", "Daniel", "Hinojosa Ruiz");
        c3.addPermiso(TipoPermiso.B);
        c3.addPermiso(TipoPermiso.C);
        Conductor c4 = new Conductor("32458547k", "Cristina", "Gonzalez Bosque");
        c4.addPermiso(TipoPermiso.AM);

        bd.addConductor(c1);
        bd.addConductor(c2);
        bd.addConductor(c3);

        ExecutorService pool = Executors.newCachedThreadPool();

        Runnable t1 = () -> {
            System.out.println("Tarea 1 busca conductor 1: " + bd.getConductorByDni("31874565c").toString());
        };

        Runnable t2 = () -> {
            System.out.println("Tarea 2 aniade perimso A2 a conductor2");
            bd.getConductorByDni("32458547k").addPermiso(TipoPermiso.A2);
            System.out.println("Tarea 2 conductor 2 con permiso A2: " + bd.getConductorByDni("32458547k").toString());
        };

        Runnable t3 = () -> {
            System.out.println("Tarea 2 aniade perimso C a conductor2");
            bd.getConductorByDni("32458547k").addPermiso(TipoPermiso.C);
            System.out.println("Tarea 2 conductor 2 con permiso C: " + bd.getConductorByDni("32458547k").toString());
        };

        Runnable t4 = () -> {
            System.out.println("Tarea 4 eliminando conductor1");
            bd.deleteConductorByDni("31874565c");
        };

        Runnable t5 = () -> {
            System.out.println("Tarea 5 aniade conductor4");
            bd.addConductor(c4);
        };

        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);
        pool.execute(t5);

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.DAYS);

    }
}