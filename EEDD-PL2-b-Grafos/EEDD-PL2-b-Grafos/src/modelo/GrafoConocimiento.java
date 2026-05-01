package modelo; // Este archivo pertenece al paquete modelo.

import java.util.ArrayList; // Importamos ArrayList para manejar listas.
import java.util.HashMap;   // Importamos HashMap para guardar las adyacencias y otras estructuras auxiliares.

public class GrafoConocimiento {

    private ArrayList<String> tiposDeclarados;                    // Lista con los tipos que vienen declarados en los datos.
    private ArrayList<String> nodos;                             // Lista de nodos del grafo.
    private ArrayList<Arista> aristas;                           // Lista de aristas del grafo.
    private HashMap<String, ArrayList<Arista>> adyacencias;      // Mapa que relaciona cada nodo con sus aristas salientes.

    // Constructor vacío.
    // Aquí dejamos todas las estructuras creadas desde el principio.
    public GrafoConocimiento() {
        tiposDeclarados = new ArrayList<String>();                       // Lista vacía para los tipos.
        nodos = new ArrayList<String>();                                 // Lista vacía para los nodos.
        aristas = new ArrayList<Arista>();                               // Lista vacía para las aristas.
        adyacencias = new HashMap<String, ArrayList<Arista>>();          // Mapa vacío para las adyacencias.
    }

    // Guarda los tipos declarados que vienen de fuera.
    public void setTiposDeclarados(ArrayList<String> tiposDeclarados) {
        this.tiposDeclarados = tiposDeclarados;
    }

    // Devuelve los tipos declarados.
    public ArrayList<String> getTiposDeclarados() {
        return tiposDeclarados;
    }

    // Devuelve la lista de nodos.
    public ArrayList<String> getNodos() {
        return nodos;
    }

    // Devuelve la lista de aristas.
    public ArrayList<Arista> getAristas() {
        return aristas;
    }

    // Añade un nodo al grafo.
    public void anadirNodo(String nodo) {
        if (nodo != null) { // Primero comprobamos que el nodo no sea null.
            if (!nodos.contains(nodo)) { // Solo lo añadimos si todavía no estaba.
                nodos.add(nodo); // Lo metemos en la lista de nodos.
                adyacencias.put(nodo, new ArrayList<Arista>()); // Y le creamos su lista de adyacencias vacía.
            }
        }
    }

    // Añade una tripleta al grafo a partir de sus tres partes.
    public void anadirTripleta(String sujeto, String predicado, String objeto) {
        anadirNodo(sujeto); // Nos aseguramos de que el sujeto exista como nodo.
        anadirNodo(objeto); // Nos aseguramos de que el objeto exista como nodo.

        Arista arista = new Arista(sujeto, predicado, objeto, 1.0); // Creamos la arista con peso 1 por defecto.
        aristas.add(arista); // La añadimos a la lista general de aristas.
        adyacencias.get(sujeto).add(arista); // Y también a las aristas que salen del sujeto.
    }

    // Añade una tripleta ya creada como objeto.
    public void anadirTripleta(Tripleta tripleta) {
        if (tripleta != null) { // Comprobamos que la tripleta exista.
            anadirTripleta(tripleta.getS(), tripleta.getP(), tripleta.getO()); // Reutilizamos el otro método.
        }
    }

    // Carga muchas tripletas de golpe.
    public void cargarTripletas(ArrayList<Tripleta> tripletas) {
        if (tripletas != null) { // Solo seguimos si la lista no es null.
            for (int i = 0; i < tripletas.size(); i++) { // Recorremos todas las tripletas.
                anadirTripleta(tripletas.get(i)); // Añadimos cada una al grafo.
            }
        }
    }

    // Devuelve todas las aristas que salen de un nodo.
    public ArrayList<Arista> obtenerAristasDesde(String nodo) {
        ArrayList<Arista> resultado = new ArrayList<Arista>(); // Creamos una lista vacía por si no se encuentra nada.

        if (adyacencias.containsKey(nodo)) { // Si el nodo existe en el mapa de adyacencias...
            resultado = adyacencias.get(nodo); // ...devolvemos sus aristas salientes.
        }

        return resultado;
    }

    // Calcula la distancia mínima desde un nodo origen al resto usando Dijkstra.
    public HashMap<String, Double> dijkstraDistancias(String origen) {
        HashMap<String, Double> distancias = new HashMap<String, Double>();   // Aquí se guardan las distancias.
        HashMap<String, String> predecesores = new HashMap<String, String>(); // Aquí se guarda el nodo anterior de cada camino.

        ejecutarDijkstra(origen, distancias, predecesores); // Ejecutamos el algoritmo.

        return distancias; // Devolvemos las distancias calculadas.
    }

    // Devuelve el camino mínimo entre dos nodos.
    public ArrayList<String> caminoMinimo(String origen, String destino) {
        HashMap<String, Double> distancias = new HashMap<String, Double>();   // Distancias mínimas calculadas.
        HashMap<String, String> predecesores = new HashMap<String, String>(); // Para reconstruir el camino.
        ArrayList<String> camino = new ArrayList<String>();                   // Aquí iremos guardando el camino final.

        ejecutarDijkstra(origen, distancias, predecesores); // Ejecutamos Dijkstra.

        // Solo seguimos si el destino existe y es alcanzable.
        if (distancias.containsKey(destino)) {
            if (distancias.get(destino) != Double.POSITIVE_INFINITY) {
                String actual = destino; // Empezamos a reconstruir el camino desde el destino.
                boolean seguir = true;   // Variable de control para el bucle.

                while (seguir) {
                    camino.add(0, actual); // Vamos metiendo cada nodo al principio para que el orden quede bien.

                    if (actual.equals(origen)) { // Si hemos llegado al origen, terminamos.
                        seguir = false;
                    } else {
                        actual = predecesores.get(actual); // Retrocedemos al nodo anterior.

                        if (actual == null) { // Si algo falla y no hay predecesor, vaciamos el camino.
                            seguir = false;
                            camino.clear();
                        }
                    }
                }
            }
        }

        return camino; // Devolvemos el camino calculado.
    }

    // Devuelve solo la distancia mínima entre dos nodos.
    public double distanciaMinima(String origen, String destino) {
        HashMap<String, Double> distancias = dijkstraDistancias(origen); // Calculamos todas las distancias desde el origen.
        double distancia = Double.POSITIVE_INFINITY; // Por defecto asumimos que no hay camino.

        if (distancias.containsKey(destino)) { // Si el destino existe...
            distancia = distancias.get(destino); // ...guardamos su distancia real.
        }

        return distancia;
    }

    // Método privado que implementa el algoritmo de Dijkstra.
    private void ejecutarDijkstra(String origen, HashMap<String, Double> distancias, HashMap<String, String> predecesores) {
        ArrayList<String> noVisitados = new ArrayList<String>(); // Lista con los nodos pendientes de visitar.

        // Inicializamos todas las distancias a infinito y sin predecesor.
        for (int i = 0; i < nodos.size(); i++) {
            String nodo = nodos.get(i);
            distancias.put(nodo, Double.POSITIVE_INFINITY); // Aún no conocemos la distancia a ese nodo.
            predecesores.put(nodo, null);                   // Aún no tiene nodo anterior.
            noVisitados.add(nodo);                          // Todos empiezan como no visitados.
        }

        // Si el origen existe en el grafo, su distancia a sí mismo es 0.
        if (distancias.containsKey(origen)) {
            distancias.put(origen, 0.0);
        }

        // Mientras queden nodos sin visitar...
        while (noVisitados.size() > 0) {
            String u = obtenerNodoConMenorDistancia(noVisitados, distancias); // Buscamos el nodo con menor distancia provisional.

            if (u != null) {
                noVisitados.remove(u); // Marcamos ese nodo como visitado.

                ArrayList<Arista> aristasNodo = obtenerAristasDesde(u); // Sacamos sus aristas salientes.
                for (int i = 0; i < aristasNodo.size(); i++) { // Recorremos esas aristas.
                    Arista arista = aristasNodo.get(i);
                    String v = arista.getDestino(); // Nodo al que llega la arista.

                    if (noVisitados.contains(v)) { // Solo actualizamos si ese nodo aún no ha sido visitado.
                        double nuevaDistancia = distancias.get(u) + arista.getPeso(); // Calculamos una posible nueva distancia.

                        if (nuevaDistancia < distancias.get(v)) { // Si mejora la anterior...
                            distancias.put(v, nuevaDistancia);    // ...actualizamos la distancia.
                            predecesores.put(v, u);               // ...y guardamos de dónde venimos.
                        }
                    }
                }
            }
        }
    }

    // Este método busca, entre los nodos no visitados, el que tenga la distancia más pequeña.
    private String obtenerNodoConMenorDistancia(ArrayList<String> noVisitados, HashMap<String, Double> distancias) {
        String nodoMenor = null;                           // Aquí guardaremos el mejor candidato.
        double distanciaMenor = Double.POSITIVE_INFINITY; // Empezamos suponiendo una distancia enorme.

        for (int i = 0; i < noVisitados.size(); i++) { // Recorremos los nodos pendientes.
            String nodo = noVisitados.get(i);
            double distancia = distancias.get(nodo);

            if (nodoMenor == null) { // Si es el primero, se convierte en el menor provisional.
                nodoMenor = nodo;
                distanciaMenor = distancia;
            } else {
                if (distancia < distanciaMenor) { // Si encontramos uno mejor, lo actualizamos.
                    nodoMenor = nodo;
                    distanciaMenor = distancia;
                }
            }
        }

        return nodoMenor;
    }

    // Comprueba si el grafo dirigido está disjunto, es decir, si hay nodos a los que no se llega desde el nodo inicial.
    public boolean esDisjuntoDirigido() {
        boolean esDisjunto = false; // En principio asumimos que no lo es.

        if (nodos.size() > 0) { // Solo tiene sentido comprobarlo si hay nodos.
            String nodoInicial = nodos.get(0); // Tomamos el primer nodo como punto de partida.
            HashMap<String, Double> distancias = dijkstraDistancias(nodoInicial); // Calculamos las distancias desde él.

            for (int i = 0; i < nodos.size(); i++) { // Recorremos todos los nodos.
                String nodo = nodos.get(i);
                if (distancias.get(nodo) == Double.POSITIVE_INFINITY) { // Si alguno sigue a distancia infinita...
                    esDisjunto = true; // ...significa que no es alcanzable.
                }
            }
        }

        return esDisjunto;
    }

    // Devuelve el nodo inicial del grafo, que aquí se toma como el primero de la lista.
    public String obtenerNodoInicial() {
        String nodoInicial = null; // Por defecto no hay nodo inicial.

        if (nodos.size() > 0) { // Si hay nodos...
            nodoInicial = nodos.get(0); // ...devolvemos el primero.
        }

        return nodoInicial;
    }

    // Devuelve una lista con los tipos de nodos que existen realmente en el grafo.
    public ArrayList<String> obtenerTiposDeNodos() {
        ArrayList<String> tipos = new ArrayList<String>(); // Lista donde iremos guardando los tipos sin repetir.

        for (int i = 0; i < nodos.size(); i++) { // Recorremos todos los nodos.
            String nodo = nodos.get(i);
            String tipo = obtenerTipoNodo(nodo); // Sacamos su tipo.

            if (!tipos.contains(tipo)) { // Si todavía no estaba en la lista...
                tipos.add(tipo); // ...lo añadimos.
            }
        }

        return tipos;
    }

    // Obtiene el tipo de un nodo mirando la parte anterior a los dos puntos.
    // Por ejemplo, "persona:Albert Einstein" tendría tipo "persona".
    public String obtenerTipoNodo(String nodo) {
        String tipo = "literal"; // Si no hay dos puntos, lo tratamos como literal.

        if (nodo != null) {
            int posicion = nodo.indexOf(":"); // Buscamos la posición de los dos puntos.
            if (posicion > 0) { // Si existe y no está al principio...
                tipo = nodo.substring(0, posicion); // ...el tipo es lo que va antes.
            }
        }

        return tipo;
    }

    // Devuelve todos los objetos relacionados con un sujeto mediante un predicado concreto.
    public ArrayList<String> obtenerObjetos(String sujeto, String predicado) {
        ArrayList<String> objetos = new ArrayList<String>();      // Aquí guardaremos los resultados.
        ArrayList<Arista> aristasNodo = obtenerAristasDesde(sujeto); // Sacamos las aristas que salen del sujeto.

        for (int i = 0; i < aristasNodo.size(); i++) { // Recorremos esas aristas.
            Arista arista = aristasNodo.get(i);

            if (arista.getPredicado().equals(predicado)) { // Si el predicado coincide...
                objetos.add(arista.getDestino()); // ...añadimos el destino a la lista de objetos.
            }
        }

        return objetos;
    }

    // Comprueba si existe una relación exacta entre sujeto, predicado y objeto.
    public boolean existeRelacion(String sujeto, String predicado, String objeto) {
        boolean existe = false; // En principio asumimos que no existe.
        ArrayList<Arista> aristasNodo = obtenerAristasDesde(sujeto); // Sacamos las aristas del sujeto.

        for (int i = 0; i < aristasNodo.size(); i++) { // Recorremos todas.
            Arista arista = aristasNodo.get(i);

            if (arista.getPredicado().equals(predicado)) { // Primero comprobamos el predicado.
                if (arista.getDestino().equals(objeto)) {  // Luego comprobamos el objeto.
                    existe = true; // Si ambas cosas coinciden, la relación existe.
                }
            }
        }

        return existe;
    }

    // Comprueba si una persona tiene relación con el premio Nobel.
    public boolean esPremioNobel(String persona) {
        return existeRelacion(persona, "premio", "premio:Nobel");
    }

    // Comprueba si una persona pertenece al área de Física.
    public boolean esFisico(String persona) {
        return existeRelacion(persona, "area", "area:Fisica");
    }

    // Busca físicos con Nobel que hayan nacido en el mismo lugar que Einstein.
    public ArrayList<String> buscarFisicosNacidosDondeEinstein() {
        ArrayList<String> resultado = new ArrayList<String>(); // Aquí guardaremos los nombres encontrados.
        ArrayList<String> lugaresEinstein = obtenerObjetos("persona:Albert Einstein", "nace_en"); // Sacamos los lugares de nacimiento de Einstein.

        for (int i = 0; i < nodos.size(); i++) { // Recorremos todos los nodos del grafo.
            String nodo = nodos.get(i);

            if (obtenerTipoNodo(nodo).equals("persona")) { // Solo nos interesan nodos de tipo persona.
                if (!nodo.equals("persona:Albert Einstein")) { // Excluimos al propio Einstein.
                    if (esFisico(nodo)) { // Debe ser físico.
                        if (esPremioNobel(nodo)) { // Y además premio Nobel.
                            ArrayList<String> lugaresPersona = obtenerObjetos(nodo, "nace_en"); // Sacamos sus lugares de nacimiento.
                            for (int j = 0; j < lugaresPersona.size(); j++) { // Recorremos esos lugares.
                                String lugar = lugaresPersona.get(j);
                                if (lugaresEinstein.contains(lugar)) { // Si coincide con alguno de Einstein...
                                    if (!resultado.contains(nodo)) {   // ...y todavía no estaba añadido...
                                        resultado.add(nodo); // ...lo metemos en el resultado.
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return resultado;
    }

    // Devuelve los lugares de nacimiento de todas las personas que tienen premio Nobel.
    public ArrayList<String> listarLugaresNacimientoPremiosNobel() {
        ArrayList<String> lugares = new ArrayList<String>(); // Lista de lugares sin repetir.

        for (int i = 0; i < nodos.size(); i++) { // Recorremos todos los nodos.
            String nodo = nodos.get(i);

            if (obtenerTipoNodo(nodo).equals("persona")) { // Solo trabajamos con personas.
                if (esPremioNobel(nodo)) { // Y solo si tienen Nobel.
                    ArrayList<String> lugaresPersona = obtenerObjetos(nodo, "nace_en"); // Sacamos sus lugares de nacimiento.
                    for (int j = 0; j < lugaresPersona.size(); j++) {
                        String lugar = lugaresPersona.get(j);
                        if (!lugares.contains(lugar)) { // Evitamos meter repetidos.
                            lugares.add(lugar);
                        }
                    }
                }
            }
        }

        return lugares;
    }

    // Devuelve una lista de cadenas que representan los caminos relacionados con Nobel y lugar de nacimiento.
    public ArrayList<String> obtenerCaminosLugaresNacimientoPremiosNobel() {
        ArrayList<String> caminos = new ArrayList<String>(); // Aquí guardaremos las descripciones de cada camino.

        for (int i = 0; i < nodos.size(); i++) { // Recorremos todos los nodos.
            String nodo = nodos.get(i);

            if (obtenerTipoNodo(nodo).equals("persona")) { // Solo personas.
                if (esPremioNobel(nodo)) { // Solo las que tienen Nobel.
                    ArrayList<String> lugaresPersona = obtenerObjetos(nodo, "nace_en"); // Sacamos sus lugares de nacimiento.
                    for (int j = 0; j < lugaresPersona.size(); j++) {
                        String lugar = lugaresPersona.get(j);
                        String camino = nodo + " --premio--> premio:Nobel y " + nodo + " --nace_en--> " + lugar; // Construimos una descripción del camino.
                        caminos.add(camino); // La añadimos a la lista.
                    }
                }
            }
        }

        return caminos;
    }

    // Imprime todos los nodos del grafo por pantalla.
    public void imprimirNodos() {
        for (int i = 0; i < nodos.size(); i++) {
            System.out.println(nodos.get(i));
        }
    }

    // Imprime todas las aristas del grafo por pantalla.
    public void imprimirAristas() {
        for (int i = 0; i < aristas.size(); i++) {
            System.out.println(aristas.get(i).toString());
        }
    }
}
