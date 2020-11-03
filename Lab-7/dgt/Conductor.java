import java.util.LinkedList;
import java.util.List;

/**
 * @author Nicolas Ruiz Requejo
 */

enum TipoPermiso {
    AM, A1, A2, A, B, C
}

public class Conductor {
    private final String dni, nombre, apellidos;
    private List<TipoPermiso> permisos;

    public Conductor(String dni, String nom, String apel) {
        this.dni = dni;
        nombre = nom;
        apellidos = apel;
        permisos = new LinkedList<TipoPermiso>();
    }

    public void addPermiso(TipoPermiso tp) {
        permisos.add(tp);
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public List<TipoPermiso> getPermisos() {
        return new LinkedList<TipoPermiso>(permisos);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Conductor) {
            Conductor in = (Conductor) obj;
            if (in.dni.equals(dni)) {
                if (in.nombre.equals(nombre)) {
                    if (in.apellidos.equals(apellidos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String s1 = "[" + dni + ", " + nombre + " " + apellidos + ", Permisos: \n(";
        for (TipoPermiso p : permisos) {
            s1 = s1 + p.toString() + " ";
        }
        s1 = s1 + ") ]";
        return s1;
    }

}