
public class Tripleta {


    private String sujeto;
    private String predicado;
    private String objeto;


    public Tripleta(String sujeto, String predicado, String objeto) {
        this.sujeto = sujeto;
        this.predicado = predicado;
        this.objeto = objeto;
    }



    public String getSujeto() {
        return sujeto;
    }

    public String getPredicado() {
        return predicado;
    }

    public String getObjeto() {
        return objeto;
    }


    public boolean esValida() {
        return sujeto != null && !sujeto.isEmpty()
                && predicado != null && !predicado.isEmpty()
                && objeto != null && !objeto.isEmpty();
    }


    @Override
    public String toString() {
        return "< " + sujeto + " , " + predicado + " , " + objeto + " >";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tripleta)) return false;
        Tripleta otra = (Tripleta) o;
        return this.sujeto.equals(otra.sujeto)
                && this.predicado.equals(otra.predicado)
                && this.objeto.equals(otra.objeto);
    }

    @Override
    public int hashCode() {
        return (sujeto + "|" + predicado + "|" + objeto).hashCode();
    }
}