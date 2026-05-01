import java.io.IOException;
import java.util.List;

/**
 * Clase CargadorDatos
 * --------------------
 * Esta clase se encarga de COGER las Tripletas que ha leído el LectorJSON
 * y AÑADIRLAS al grafo. Es decir, sirve de "puente" entre el lector
 * de archivos y la estructura del grafo.
 *
 * Responsabilidades:
 *   - LectorJSON       -> lee el archivo y devuelve una List<Tripleta>.
 *   - CargadorDatos    -> usa LectorJSON y mete las Tripletas en el grafo.
 *   - Grafo            -> guarda los nodos/aristas y permite consultas.
 *
 * Así, si el día de mañana quisiéramos cambiar el formato de los
 * archivos (por ejemplo, leer XML o CSV) solo tendríamos que cambiar
 * el LectorJSON; ni el Grafo ni el CargadorDatos necesitarían tocarse
 * apenas.
 *
 * NOTA: el formato de archivo esperado es una lista JSON con objetos
 * que tienen los campos "s", "p" y "o", por ejemplo:
 *      [
 *        {"s": "persona:Einstein", "p": "nace_en", "o": "lugar:Ulm"}
 *      ]
 */
public class CargadorDatos {

    /**
     * Carga todas las tripletas del archivo indicado en el grafo dado.
     *
     * @param grafo        el grafo en el que se añadirán las tripletas
     * @param rutaArchivo  ruta del archivo JSON con los datos
     * @throws IOException si hay un error leyendo el archivo
     */
    public static void cargar(GrafoConocimiento grafo, String rutaArchivo) throws IOException {

        // 1) Pedimos al LectorJSON que nos dé la lista de Tripletas.
        List<Tripleta> tripletas = LectorJSON.leer(rutaArchivo);

        // 2) Recorremos cada Tripleta y la añadimos al grafo.
        int contador = 0;
        for (Tripleta t : tripletas) {
            grafo.anadirTripleta(t);
            contador++;
        }

        System.out.println("Cargadas " + contador + " tripletas desde " + rutaArchivo);
    }
}