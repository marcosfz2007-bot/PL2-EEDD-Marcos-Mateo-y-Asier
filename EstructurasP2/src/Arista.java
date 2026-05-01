/**
 * Clase Arista
 * ------------
 * Una Arista (también llamada "edge" en inglés) conecta dos nodos
 * y le da un significado a esa conexión mediante un "predicado".
 *
 * En RDF, una tripleta es:  (sujeto, predicado, objeto)
 * que en términos de grafo es:  origen --[predicado]--> destino
 *
 * Ejemplo de tripleta y su correspondiente arista:
 *      <persona:Einstein, nace_en, lugar:Ulm>
 *  -> origen   = Nodo("persona:Einstein")
 *  -> relacion = "nace_en"
 *  -> destino  = Nodo("lugar:Ulm")
 *
 * IMPORTANTE: en RDF las aristas son DIRIGIDAS (van de sujeto a objeto).
 * Sin embargo, para preguntas como "camino mínimo" muchas veces
 * tratamos el grafo como NO dirigido (se puede ir en los dos sentidos).
 * Eso lo decidimos en la clase Grafo, no aquí.
 */
public class Arista {

    private Nodo origen;     // El sujeto de la tripleta
    private String relacion; // El predicado: cómo se relacionan
    private Nodo destino;    // El objeto de la tripleta

    /**
     * Constructor: para crear una arista necesitamos los tres elementos
     * de la tripleta.
     */
    public Arista(Nodo origen, String relacion, Nodo destino) {
        this.origen = origen;
        this.relacion = relacion;
        this.destino = destino;
    }

    // --- Métodos getter ---

    public Nodo getOrigen() {
        return origen;
    }

    public String getRelacion() {
        return relacion;
    }

    public Nodo getDestino() {
        return destino;
    }

    /**
     * Para imprimir la arista de forma bonita y legible.
     * Ejemplo:  persona:Einstein --[nace_en]--> lugar:Ulm
     */
    @Override
    public String toString() {
        return origen + " --[" + relacion + "]--> " + destino;
    }
}
