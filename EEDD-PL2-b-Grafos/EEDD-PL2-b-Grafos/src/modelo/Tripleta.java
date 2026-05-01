package modelo; // Este archivo pertenece al paquete modelo.

public class Tripleta {

    private String s; // Sujeto de la tripleta.
    private String p; // Predicado o relación.
    private String o; // Objeto de la tripleta.

    // Constructor vacío.
    // Viene bien para trabajar con JSON y reconstruir objetos.
    public Tripleta() {
    }

    // Constructor completo con sujeto, predicado y objeto.
    public Tripleta(String s, String p, String o) {
        this.s = s; // Guardamos el sujeto.
        this.p = p; // Guardamos el predicado.
        this.o = o; // Guardamos el objeto.
    }

    // Devuelve el sujeto.
    public String getS() {
        return s;
    }

    // Permite cambiar el sujeto.
    public void setS(String s) {
        this.s = s;
    }

    // Devuelve el predicado.
    public String getP() {
        return p;
    }

    // Permite cambiar el predicado.
    public void setP(String p) {
        this.p = p;
    }

    // Devuelve el objeto.
    public String getO() {
        return o;
    }

    // Permite cambiar el objeto.
    public void setO(String o) {
        this.o = o;
    }

    // Devuelve la tripleta en formato texto para verla más clara.
    @Override
    public String toString() {
        return "<\"" + s + "\", \"" + p + "\", \"" + o + "\">";
    }
}