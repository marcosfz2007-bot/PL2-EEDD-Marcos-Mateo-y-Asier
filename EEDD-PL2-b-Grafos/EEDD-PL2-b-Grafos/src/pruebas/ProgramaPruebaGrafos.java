package pruebas; // Este archivo pertenece al paquete pruebas.

import lectura.LectorJSON;          // Importamos la clase que se encarga de leer los archivos JSON.
import modelo.GrafoConocimiento;    // Importamos la clase principal del grafo.

import java.util.ArrayList;         // Importamos ArrayList para guardar listas de resultados.

public class ProgramaPruebaGrafos {

    public static void main(String[] args) {
        LectorJSON lector = new LectorJSON(); // Creamos el lector que usaremos para cargar los grafos desde archivos JSON.

        probarGrafoConectado(lector); // Ejecutamos la prueba del grafo conectado.
        probarGrafoDisjunto(lector);  // Ejecutamos la prueba del grafo disjunto.
        probarGrafoNobel(lector);     // Ejecutamos la prueba del grafo de premios Nobel.
    }

    // Este método prueba un grafo conectado dirigido.
    private static void probarGrafoConectado(LectorJSON lector) {
        System.out.println("============================================"); // Línea decorativa.
        System.out.println("PRUEBA 1: GRAFO CONECTADO DIRIGIDO"); // Título de la prueba.
        System.out.println("============================================"); // Línea decorativa.

        GrafoConocimiento grafo = lector.cargarGrafo("datos/grafo_conectado.json"); // Cargamos el grafo desde su archivo JSON.

        System.out.println("Nodo inicial usado para comprobar si es disjunto: " + grafo.obtenerNodoInicial()); // Mostramos el nodo inicial.
        System.out.println("¿El grafo es disjunto siguiendo la direccion de las aristas?: " + grafo.esDisjuntoDirigido()); // Comprobamos si el grafo está disjunto.

        System.out.println(); // Dejamos una línea en blanco para que la salida quede más clara.
        System.out.println("Camino minimo dirigido entre entidad:A y entidad:E usando Dijkstra:"); // Explicamos qué camino vamos a calcular.

        ArrayList<String> camino = grafo.caminoMinimo("entidad:A", "entidad:E"); // Calculamos el camino mínimo entre A y E.
        imprimirCamino(camino); // Mostramos ese camino por pantalla.
        System.out.println("Distancia: " + grafo.distanciaMinima("entidad:A", "entidad:E")); // Mostramos también la distancia total.
        System.out.println(); // Otra línea en blanco para separar bloques.
    }

    // Este método prueba un grafo disjunto dirigido.
    private static void probarGrafoDisjunto(LectorJSON lector) {
        System.out.println("============================================"); // Línea decorativa.
        System.out.println("PRUEBA 2: GRAFO DISJUNTO DIRIGIDO"); // Título de la segunda prueba.
        System.out.println("============================================"); // Línea decorativa.

        GrafoConocimiento grafo = lector.cargarGrafo("datos/grafo_disjunto.json"); // Cargamos el segundo grafo desde su JSON.

        System.out.println("Nodo inicial usado para comprobar si es disjunto: " + grafo.obtenerNodoInicial()); // Mostramos el nodo inicial.
        System.out.println("¿El grafo es disjunto siguiendo la direccion de las aristas?: " + grafo.esDisjuntoDirigido()); // Comprobamos si está disjunto.

        System.out.println(); // Línea en blanco.
        System.out.println("Camino minimo dirigido entre entidad:A y entidad:D:"); // Indicamos qué camino vamos a buscar.

        ArrayList<String> camino = grafo.caminoMinimo("entidad:A", "entidad:D"); // Calculamos el camino mínimo entre A y D.
        imprimirCamino(camino); // Mostramos el camino.
        System.out.println("Distancia: " + grafo.distanciaMinima("entidad:A", "entidad:D")); // Mostramos la distancia calculada.
        System.out.println(); // Línea en blanco para separar.
    }

    // Este método prueba el grafo de conocimiento relacionado con premios Nobel.
    private static void probarGrafoNobel(LectorJSON lector) {
        System.out.println("============================================"); // Línea decorativa.
        System.out.println("PRUEBA 3: GRAFO DE CONOCIMIENTO DE PREMIOS NOBEL"); // Título de la tercera prueba.
        System.out.println("============================================"); // Línea decorativa.

        GrafoConocimiento grafo = lector.cargarGrafo("datos/nobel_fisicos.json"); // Cargamos el grafo de premios Nobel.

        System.out.println("Fisicos premio Nobel nacidos en la misma ciudad que Einstein:"); // Explicamos qué consulta vamos a hacer.
        ArrayList<String> fisicos = grafo.buscarFisicosNacidosDondeEinstein(); // Buscamos los físicos que cumplen esa condición.
        imprimirLista(fisicos); // Mostramos la lista de resultados.

        System.out.println(); // Línea en blanco.
        System.out.println("Se añade la tripleta pedida en el enunciado:"); // Avisamos de que ahora se va a añadir una tripleta nueva.
        System.out.println("<\"persona:Antonio\", \"nace_en\", \"lugar:Villarrubia de los Caballeros\">"); // Mostramos la tripleta tal como aparece.
        grafo.anadirTripleta("persona:Antonio", "nace_en", "lugar:Villarrubia de los Caballeros"); // Añadimos esa tripleta al grafo.

        System.out.println(); // Línea en blanco.
        System.out.println("Lugares de nacimiento de los premios Nobel:"); // Indicamos la siguiente consulta.
        ArrayList<String> lugares = grafo.listarLugaresNacimientoPremiosNobel(); // Obtenemos los lugares de nacimiento.
        imprimirLista(lugares); // Mostramos los resultados.

        System.out.println(); // Línea en blanco.
        System.out.println("Caminos recorridos para que la respuesta sea correcta:"); // Explicamos la siguiente salida.
        ArrayList<String> caminos = grafo.obtenerCaminosLugaresNacimientoPremiosNobel(); // Sacamos los caminos relacionados con esa consulta.
        imprimirLista(caminos); // Mostramos esos caminos.

        System.out.println(); // Línea en blanco.
        System.out.println("Tipos de nodos que aparecen en el grafo:"); // Indicamos que ahora veremos los tipos de nodos.
        ArrayList<String> tipos = grafo.obtenerTiposDeNodos(); // Obtenemos los tipos de nodos existentes.
        imprimirLista(tipos); // Mostramos la lista.

        System.out.println(); // Última línea en blanco para dejar la salida limpia.
    }

    // Este método imprime un camino de nodos de forma clara.
    private static void imprimirCamino(ArrayList<String> camino) {
        if (camino.size() == 0) { // Si el camino está vacío...
            System.out.println("No hay camino dirigido entre esas dos entidades."); // ...avisamos de que no existe camino.
        } else {
            for (int i = 0; i < camino.size(); i++) { // Recorremos todos los nodos del camino.
                if (i == camino.size() - 1) { // Si estamos en el último nodo...
                    System.out.print(camino.get(i)); // ...lo imprimimos sin flecha al final.
                } else {
                    System.out.print(camino.get(i) + " -> "); // Si no es el último, añadimos una flecha.
                }
            }
            System.out.println(); // Al final hacemos salto de línea.
        }
    }

    // Este método imprime una lista de resultados, uno por línea.
    private static void imprimirLista(ArrayList<String> lista) {
        if (lista.size() == 0) { // Si no hay elementos en la lista...
            System.out.println("No se han encontrado resultados."); // ...mostramos ese mensaje.
        } else {
            for (int i = 0; i < lista.size(); i++) { // Recorremos todos los elementos.
                System.out.println("- " + lista.get(i)); // Los mostramos con un guion delante para que quede más ordenado.
            }
        }
    }
}