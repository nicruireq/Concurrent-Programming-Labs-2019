import java.util.LinkedList;
import java.util.List;

/**
 * @author Nicolas Ruiz Requejo
 */

public class Conductores {
    private List<Conductor> conductorBD;

    public Conductores() {
        conductorBD = new LinkedList<Conductor>();
    }

    public synchronized void addConductor(Conductor con) {
        conductorBD.add(con);
    }

    public synchronized Conductor getConductorByDni(String dni) {
        for (Conductor con : conductorBD) {
            if (con.getDni().equals(dni)) {
                return con;
            }
        }
        return null;
    }

    public synchronized void deleteConductorByDni(String dni) {
        for (Conductor con : conductorBD) {
            if (con.getDni().equals(dni)) {
                conductorBD.remove(con);
                break;
            }
        }
    }
}