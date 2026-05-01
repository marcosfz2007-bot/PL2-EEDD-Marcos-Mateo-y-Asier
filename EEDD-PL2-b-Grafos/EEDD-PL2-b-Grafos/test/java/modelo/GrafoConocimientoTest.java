package modelo;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GrafoConocimientoTest {

    @Test
    void defaultConstructor() {
        new GrafoConocimiento();
    }

    @Test
    void setTiposDeclarados() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        ArrayList<String> tipos = new ArrayList<String>();
        tipos.add("entidad");
        grafo.setTiposDeclarados(tipos);
    }

    @Test
    void getTiposDeclarados() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        grafo.getTiposDeclarados();
    }

    @Test
    void getNodos() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        grafo.getNodos();
    }

    @Test
    void getAristas() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        grafo.getAristas();
    }

    @Test
    void anadirNodo() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        grafo.anadirNodo("entidad:A");
    }

    @Test
    void anadirNodoRepetido() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        grafo.anadirNodo("entidad:A");
        grafo.anadirNodo("entidad:A");
    }

    @Test
    void anadirNodoNull() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        grafo.anadirNodo(null);
    }

    @Test
    void anadirTripletaConStrings() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        grafo.anadirTripleta("entidad:A", "relacion", "entidad:B");
    }

    @Test
    void anadirTripletaConObjeto() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        Tripleta tripleta = new Tripleta("entidad:A", "relacion", "entidad:B");
        grafo.anadirTripleta(tripleta);
    }

    @Test
    void anadirTripletaNull() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        grafo.anadirTripleta(null);
    }

    @Test
    void cargarTripletas() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        ArrayList<Tripleta> tripletas = new ArrayList<Tripleta>();
        tripletas.add(new Tripleta("entidad:A", "relacion", "entidad:B"));
        tripletas.add(new Tripleta("entidad:B", "relacion", "entidad:C"));
        grafo.cargarTripletas(tripletas);
    }

    @Test
    void cargarTripletasNull() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        grafo.cargarTripletas(null);
    }

    @Test
    void obtenerAristasDesde() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        grafo.anadirTripleta("entidad:A", "relacion", "entidad:B");
        grafo.obtenerAristasDesde("entidad:A");
    }

    @Test
    void obtenerAristasDesdeNoExistente() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        grafo.obtenerAristasDesde("entidad:X");
    }

    @Test
    void dijkstraDistancias() {
        GrafoConocimiento grafo = crearGrafoConectado();
        HashMap<String, Double> distancias = grafo.dijkstraDistancias("entidad:A");
        assertEquals(0.0, distancias.get("entidad:A"));
    }

    @Test
    void caminoMinimo() {
        GrafoConocimiento grafo = crearGrafoConectado();
        grafo.caminoMinimo("entidad:A", "entidad:E");
    }

    @Test
    void caminoMinimoSinCamino() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        grafo.anadirTripleta("entidad:A", "relacion", "entidad:B");
        grafo.anadirTripleta("entidad:C", "relacion", "entidad:D");
        grafo.caminoMinimo("entidad:A", "entidad:D");
    }

    @Test
    void distanciaMinima() {
        GrafoConocimiento grafo = crearGrafoConectado();
        grafo.distanciaMinima("entidad:A", "entidad:E");
    }

    @Test
    void distanciaMinimaSinCamino() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        grafo.anadirTripleta("entidad:A", "relacion", "entidad:B");
        grafo.anadirTripleta("entidad:C", "relacion", "entidad:D");
        grafo.distanciaMinima("entidad:A", "entidad:D");
    }

    @Test
    void esDisjuntoDirigidoFalse() {
        GrafoConocimiento grafo = crearGrafoConectado();
        assertFalse(grafo.esDisjuntoDirigido());
    }

    @Test
    void esDisjuntoDirigidoTrue() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        grafo.anadirTripleta("entidad:A", "relacion", "entidad:B");
        grafo.anadirTripleta("entidad:C", "relacion", "entidad:D");
        assertTrue(grafo.esDisjuntoDirigido());
    }

    @Test
    void esDisjuntoDirigidoVacio() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        grafo.esDisjuntoDirigido();
    }

    @Test
    void obtenerNodoInicial() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        grafo.anadirNodo("entidad:A");
        grafo.obtenerNodoInicial();
    }

    @Test
    void obtenerNodoInicialVacio() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        grafo.obtenerNodoInicial();
    }

    @Test
    void obtenerTiposDeNodos() {
        GrafoConocimiento grafo = crearGrafoNobel();
        grafo.obtenerTiposDeNodos();
    }

    @Test
    void obtenerTipoNodo() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        grafo.obtenerTipoNodo("persona:Albert Einstein");
    }

    @Test
    void obtenerTipoNodoLiteral() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        grafo.obtenerTipoNodo("1921");
    }

    @Test
    void obtenerTipoNodoNull() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        grafo.obtenerTipoNodo(null);
    }

    @Test
    void obtenerObjetos() {
        GrafoConocimiento grafo = crearGrafoNobel();
        grafo.obtenerObjetos("persona:Albert Einstein", "nace_en");
    }

    @Test
    void existeRelacion() {
        GrafoConocimiento grafo = crearGrafoNobel();
        assertTrue(grafo.existeRelacion("persona:Albert Einstein", "premio", "premio:Nobel"));
    }

    @Test
    void noExisteRelacion() {
        GrafoConocimiento grafo = crearGrafoNobel();
        assertFalse(grafo.existeRelacion("persona:Albert Einstein", "premio", "premio:Otro"));
    }

    @Test
    void esPremioNobel() {
        GrafoConocimiento grafo = crearGrafoNobel();
        grafo.esPremioNobel("persona:Albert Einstein");
    }

    @Test
    void esFisico() {
        GrafoConocimiento grafo = crearGrafoNobel();
        grafo.esFisico("persona:Albert Einstein");
    }

    @Test
    void buscarFisicosNacidosDondeEinstein() {
        GrafoConocimiento grafo = crearGrafoNobel();
        grafo.buscarFisicosNacidosDondeEinstein();
    }

    @Test
    void listarLugaresNacimientoPremiosNobel() {
        GrafoConocimiento grafo = crearGrafoNobel();
        grafo.anadirTripleta("persona:Antonio", "nace_en", "lugar:Villarrubia de los Caballeros");
        grafo.listarLugaresNacimientoPremiosNobel();
    }

    @Test
    void obtenerCaminosLugaresNacimientoPremiosNobel() {
        GrafoConocimiento grafo = crearGrafoNobel();
        grafo.obtenerCaminosLugaresNacimientoPremiosNobel();
    }

    @Test
    void imprimirNodos() {
        GrafoConocimiento grafo = crearGrafoConectado();
        grafo.imprimirNodos();
    }

    @Test
    void imprimirAristas() {
        GrafoConocimiento grafo = crearGrafoConectado();
        grafo.imprimirAristas();
    }

    private GrafoConocimiento crearGrafoConectado() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        grafo.anadirTripleta("entidad:A", "relacion", "entidad:B");
        grafo.anadirTripleta("entidad:B", "relacion", "entidad:C");
        grafo.anadirTripleta("entidad:C", "relacion", "entidad:D");
        grafo.anadirTripleta("entidad:D", "relacion", "entidad:E");
        grafo.anadirTripleta("entidad:A", "atajo", "entidad:C");
        return grafo;
    }

    private GrafoConocimiento crearGrafoNobel() {
        GrafoConocimiento grafo = new GrafoConocimiento();
        grafo.anadirTripleta("persona:Albert Einstein", "premio", "premio:Nobel");
        grafo.anadirTripleta("persona:Albert Einstein", "area", "area:Fisica");
        grafo.anadirTripleta("persona:Albert Einstein", "nace_en", "lugar:Ulm");
        grafo.anadirTripleta("persona:Albert Einstein", "anio_premio", "1921");
        grafo.anadirTripleta("persona:Fisico Famoso de Ulm", "premio", "premio:Nobel");
        grafo.anadirTripleta("persona:Fisico Famoso de Ulm", "area", "area:Fisica");
        grafo.anadirTripleta("persona:Fisico Famoso de Ulm", "nace_en", "lugar:Ulm");
        grafo.anadirTripleta("persona:Fisico Famoso de Ulm", "anio_premio", "1930");
        grafo.anadirTripleta("persona:Marie Curie", "premio", "premio:Nobel");
        grafo.anadirTripleta("persona:Marie Curie", "area", "area:Fisica");
        grafo.anadirTripleta("persona:Marie Curie", "nace_en", "lugar:Varsovia");
        grafo.anadirTripleta("persona:Marie Curie", "anio_premio", "1903");
        grafo.anadirTripleta("persona:Gabriel Garcia Marquez", "premio", "premio:Nobel");
        grafo.anadirTripleta("persona:Gabriel Garcia Marquez", "area", "area:Literatura");
        grafo.anadirTripleta("persona:Gabriel Garcia Marquez", "nace_en", "lugar:Aracataca");
        grafo.anadirTripleta("persona:Gabriel Garcia Marquez", "anio_premio", "1982");
        return grafo;
    }
}
