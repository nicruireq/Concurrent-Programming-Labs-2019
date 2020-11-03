import java.io.Serializable;

public class Libro implements Serializable {
    private String titulo, autor, categoria;

    public Libro(String tit, String aut, String cat) {
        this.titulo = tit;
        this.autor = aut;
        this.categoria = cat;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public boolean equals(Object obj) {
        Libro other = (Libro) obj;
        if (other instanceof Libro) {
            if (other.getTitulo().equals(titulo) && other.getAutor().equals(autor)
                    && other.getCategoria().equals(categoria))
                return true;
            else
                return false;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Titulo: " + titulo + "\nAutor: " + autor + "\nCategoria: " + categoria;
    }

}