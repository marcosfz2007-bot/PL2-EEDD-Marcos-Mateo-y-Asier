package modelo; // Este archivo también está dentro del paquete modelo.

import java.util.ArrayList; // Importamos ArrayList para guardar listas dinámicas.

public class DatosGrafo {

    private ArrayList<String> tipos;         // Lista con los tipos declarados en el grafo.
    private ArrayList<Tripleta> tripletas;   // Lista con las tripletas sujeto-predicado-objeto.

    // Constructor vacío.
    // Aquí aprovechamos para dejar creadas las listas desde el principio.
    public DatosGrafo() {
        tipos = new ArrayList<String>();          // Creamos la lista de tipos vacía.
        tripletas = new ArrayList<Tripleta>();    // Creamos la lista de tripletas vacía.
    }

    // Constructor completo.
    // Sirve para crear el objeto ya con sus tipos y tripletas.
    public DatosGrafo(ArrayList<String> tipos, ArrayList<Tripleta> tripletas) {
        this.tipos = tipos;               // Guardamos la lista de tipos.
        this.tripletas = tripletas;       // Guardamos la lista de tripletas.
    }

    // Devuelve los tipos declarados.
    public ArrayList<String> getTipos() {
        return tipos;
    }

    // Permite cambiar la lista de tipos.
    public void setTipos(ArrayList<String> tipos) {
        this.tipos = tipos;
    }

    // Devuelve las tripletas.
    public ArrayList<Tripleta> getTripletas() {
        return tripletas;
    }

    // Permite cambiar la lista de tripletas.
    public void setTripletas(ArrayList<Tripleta> tripletas) {
        this.tripletas = tripletas;
    }
}