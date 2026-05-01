package modelo; // Este archivo pertenece al paquete modelo.

public class Arista {

    private String origen;     // Nodo desde el que sale la arista.
    private String predicado;  // Relación que conecta origen y destino.
    private String destino;    // Nodo al que llega la arista.
    private double peso;       // Peso de la arista, útil por ejemplo para calcular caminos mínimos.

    // Constructor vacío.
    // Viene bien cuando luego queremos reconstruir objetos desde JSON.
    public Arista() {
    }

    // Constructor completo con todos los datos de la arista.
    public Arista(String origen, String predicado, String destino, double peso) {
        this.origen = origen;         // Guardamos el origen.
        this.predicado = predicado;   // Guardamos el predicado.
        this.destino = destino;       // Guardamos el destino.
        this.peso = peso;             // Guardamos el peso.
    }

    // Devuelve el nodo de origen.
    public String getOrigen() {
        return origen;
    }

    // Permite cambiar el origen.
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    // Devuelve el predicado.
    public String getPredicado() {
        return predicado;
    }

    // Permite cambiar el predicado.
    public void setPredicado(String predicado) {
        this.predicado = predicado;
    }

    // Devuelve el nodo destino.
    public String getDestino() {
        return destino;
    }

    // Permite cambiar el destino.
    public void setDestino(String destino) {
        this.destino = destino;
    }

    // Devuelve el peso de la arista.
    public double getPeso() {
        return peso;
    }

    // Permite cambiar el peso.
    public void setPeso(double peso) {
        this.peso = peso;
    }

    // Convierte la arista en texto para que se vea de forma más clara al imprimirla.
    @Override
    public String toString() {
        return origen + " --" + predicado + "--> " + destino + " (peso " + peso + ")";
    }
}
