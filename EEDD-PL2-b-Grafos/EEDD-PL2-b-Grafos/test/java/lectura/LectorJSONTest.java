package lectura;

import modelo.DatosGrafo;
import modelo.GrafoConocimiento;
import org.junit.jupiter.api.Test;

class LectorJSONTest {

    @Test
    void defaultConstructor() {
        new LectorJSON();
    }

    @Test
    void leerDatos() {
        LectorJSON lector = new LectorJSON();
        DatosGrafo datos = lector.leerDatos("datos/grafo_conectado.json");
        datos.getTipos();
        datos.getTripletas();
    }

    @Test
    void leerDatosArchivoNoExistente() {
        LectorJSON lector = new LectorJSON();
        lector.leerDatos("datos/no_existe.json");
    }

    @Test
    void cargarGrafo() {
        LectorJSON lector = new LectorJSON();
        GrafoConocimiento grafo = lector.cargarGrafo("datos/grafo_conectado.json");
        grafo.getNodos();
        grafo.getAristas();
    }

    @Test
    void cargarGrafoArchivoNoExistente() {
        LectorJSON lector = new LectorJSON();
        lector.cargarGrafo("datos/no_existe.json");
    }
}
