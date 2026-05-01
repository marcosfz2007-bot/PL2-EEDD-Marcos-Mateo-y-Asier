package modelo;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class DatosGrafoTest {

    @Test
    void defaultConstructor() {
        new DatosGrafo();
    }

    @Test
    void paramConstructor() {
        ArrayList<String> tipos = new ArrayList<String>();
        ArrayList<Tripleta> tripletas = new ArrayList<Tripleta>();
        new DatosGrafo(tipos, tripletas);
    }

    @Test
    void getTipos() {
        DatosGrafo datos = new DatosGrafo();
        datos.getTipos();
    }

    @Test
    void setTipos() {
        DatosGrafo datos = new DatosGrafo();
        ArrayList<String> tipos = new ArrayList<String>();
        tipos.add("entidad");
        datos.setTipos(tipos);
    }

    @Test
    void getTripletas() {
        DatosGrafo datos = new DatosGrafo();
        datos.getTripletas();
    }

    @Test
    void setTripletas() {
        DatosGrafo datos = new DatosGrafo();
        ArrayList<Tripleta> tripletas = new ArrayList<Tripleta>();
        tripletas.add(new Tripleta("entidad:A", "relacion", "entidad:B"));
        datos.setTripletas(tripletas);
    }
}
