
public class Nodo {

    private String nombre;

    private String tipo;

    public Nodo(String nombre) {
        this.nombre = nombre;


        if (nombre.contains(":")) {
            this.tipo = nombre.split(":")[0];
        } else {

            this.tipo = "literal";
        }
    }



    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nodo)) return false;
        Nodo otro = (Nodo) o;
        return this.nombre.equals(otro.nombre);
    }


    @Override
    public int hashCode() {
        return nombre.hashCode();
    }


    @Override
    public String toString() {
        return nombre;
    }
}
