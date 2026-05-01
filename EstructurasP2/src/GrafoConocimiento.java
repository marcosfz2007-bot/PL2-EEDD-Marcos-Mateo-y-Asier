import java.util.*;


public class GrafoConocimiento {


    public Map<String, Nodo> nodos = new HashMap<>();


    public List<Arista> aristas = new ArrayList<>();


    public void anadirTripleta(String sujeto, String predicado, String objeto) {
        Nodo nodoOrigen = obtenerOCrearNodo(sujeto);
        Nodo nodoDestino = obtenerOCrearNodo(objeto);
        Arista arista = new Arista(nodoOrigen, predicado, nodoDestino);
        aristas.add(arista);
    }


    public void anadirTripleta(Tripleta t) {
        if (t == null || !t.esValida()) {
            return; // ignoramos tripletas vacías o mal formadas
        }
        anadirTripleta(t.getSujeto(), t.getPredicado(), t.getObjeto());
    }


    public Nodo obtenerOCrearNodo(String nombre) {
        if (!nodos.containsKey(nombre)) {
            nodos.put(nombre, new Nodo(nombre));
        }
        return nodos.get(nombre);
    }



    public List<String> vecinos(String nombreNodo) {
        List<String> resultado = new ArrayList<>();
        for (Arista a : aristas) {
            if (a.getOrigen().getNombre().equals(nombreNodo)) {
                resultado.add(a.getDestino().getNombre());
            } else if (a.getDestino().getNombre().equals(nombreNodo)) {
                resultado.add(a.getOrigen().getNombre());
            }
        }
        return resultado;
    }

    public List<String> caminoMinimo(String origen, String destino) {

        // Si alguno de los nodos no existe, no hay camino posible.
        if (!nodos.containsKey(origen) || !nodos.containsKey(destino)) {
            System.out.println("  (Aviso: uno de los nodos no existe en el grafo)");
            return null;
        }

        Queue<String> cola = new LinkedList<>();

        Map<String, String> previo = new HashMap<>();

        Set<String> visitados = new HashSet<>();

        cola.add(origen);
        visitados.add(origen);

        while (!cola.isEmpty()) {
            String actual = cola.poll();


            if (actual.equals(destino)) {

                List<String> camino = new ArrayList<>();
                String paso = destino;
                while (paso != null) {
                    camino.add(0, paso);
                    paso = previo.get(paso);
                }
                return camino;
            }


            for (String vecino : vecinos(actual)) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    previo.put(vecino, actual);
                    cola.add(vecino);
                }
            }
        }


        return null;
    }


    public boolean esDisjunto() {
        if (nodos.isEmpty()) {
            return false;
        }


        String inicio = nodos.keySet().iterator().next();

        Set<String> visitados = new HashSet<>();
        Queue<String> cola = new LinkedList<>();
        cola.add(inicio);
        visitados.add(inicio);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            for (String vecino : vecinos(actual)) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.add(vecino);
                }
            }
        }


        return visitados.size() < nodos.size();
    }


    public List<String> fisicosNacidosMismaCiudadQue(String persona) {
        List<String> resultado = new ArrayList<>();


        String ciudad = null;
        for (Arista a : aristas) {
            if (a.getOrigen().getNombre().equals(persona)
                    && a.getRelacion().equals("nace_en")) {
                ciudad = a.getDestino().getNombre();
                break;
            }
        }

        if (ciudad == null) {
            return resultado;
        }


        Set<String> personasMismaCiudad = new HashSet<>();
        for (Arista a : aristas) {
            if (a.getRelacion().equals("nace_en")
                    && a.getDestino().getNombre().equals(ciudad)
                    && !a.getOrigen().getNombre().equals(persona)) {
                personasMismaCiudad.add(a.getOrigen().getNombre());
            }
        }


        for (String p : personasMismaCiudad) {
            for (Arista a : aristas) {
                if (a.getOrigen().getNombre().equals(p)
                        && a.getRelacion().equals("es_un")
                        && a.getDestino().getNombre().equals("fisico")) {
                    resultado.add(p);
                    break;
                }
            }
        }

        return resultado;
    }


    public List<String> lugaresNacimientoPremiosNobel() {
        List<String> resultado = new ArrayList<>();


        Set<String> ganadoresNobel = new HashSet<>();
        for (Arista a : aristas) {
            if (a.getRelacion().equals("gano_premio")
                    && a.getDestino().getNombre().startsWith("premio:Nobel")) {
                ganadoresNobel.add(a.getOrigen().getNombre());
            }
        }


        for (Arista a : aristas) {
            if (a.getRelacion().equals("nace_en")
                    && ganadoresNobel.contains(a.getOrigen().getNombre())) {
                resultado.add(a.getOrigen().getNombre()
                        + " nació en " + a.getDestino().getNombre());
            }
        }

        return resultado;
    }


    public Set<String> tiposNodos() {
        Set<String> tipos = new TreeSet<>(); // TreeSet para tenerlos ordenados
        for (Nodo n : nodos.values()) {
            tipos.add(n.getTipo());
        }
        return tipos;
    }



    public Map<String, Nodo> getNodos() {
        return nodos;
    }

    public List<Arista> getAristas() {
        return aristas;
    }


    public void imprimirResumen() {
        System.out.println("Grafo con " + nodos.size() + " nodos y "
                + aristas.size() + " aristas.");
    }

    public void printResumen() {
    }
}
