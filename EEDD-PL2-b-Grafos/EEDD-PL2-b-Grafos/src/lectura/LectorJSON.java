package lectura; // Este archivo pertenece al paquete lectura.

import com.google.gson.Gson;              // Importamos Gson para leer el JSON y convertirlo a objetos Java.
import modelo.DatosGrafo;                 // Importamos la clase que representa los datos del grafo tal y como vienen en el JSON.
import modelo.GrafoConocimiento;          // Importamos la clase del grafo ya montado.

import java.io.FileReader;                // Se usa para leer archivos.
import java.io.IOException;               // Se usa para controlar errores al trabajar con archivos.

public class LectorJSON {

    // Este método lee un archivo JSON y lo convierte en un objeto DatosGrafo.
    public DatosGrafo leerDatos(String rutaArchivo) {
        Gson gson = new Gson();          // Creamos el objeto Gson que hará la conversión.
        DatosGrafo datos = null;         // Aquí guardaremos el resultado. Empieza en null por si falla algo.

        // Abrimos el archivo en modo lectura.
        // Con try-with-resources se cierra solo al terminar.
        try (FileReader reader = new FileReader(rutaArchivo)) {
            datos = gson.fromJson(reader, DatosGrafo.class); // Leemos el JSON y lo convertimos en un objeto DatosGrafo.
        } catch (IOException e) { // Si hay algún problema leyendo el archivo, entra aquí.
            System.out.println("No se ha podido leer el archivo: " + rutaArchivo); // Mostramos un mensaje más claro.
            e.printStackTrace(); // Y además sacamos el error completo por consola.
        }

        return datos; // Devolvemos los datos leídos, o null si algo ha fallado.
    }

    // Este método va un paso más allá:
    // lee el JSON y luego construye el grafo de conocimiento a partir de esos datos.
    public GrafoConocimiento cargarGrafo(String rutaArchivo) {
        DatosGrafo datos = leerDatos(rutaArchivo);          // Primero leemos los datos del fichero.
        GrafoConocimiento grafo = new GrafoConocimiento();  // Creamos un grafo vacío.

        // Si los datos se han leído bien, cargamos la información en el grafo.
        if (datos != null) {
            grafo.setTiposDeclarados(datos.getTipos());        // Guardamos los tipos declarados.
            grafo.cargarTripletas(datos.getTripletas());       // Cargamos todas las tripletas en el grafo.
        }

        return grafo; // Devolvemos el grafo ya preparado.
    }
}
