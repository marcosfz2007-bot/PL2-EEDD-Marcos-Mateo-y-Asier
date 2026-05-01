package modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TripletaTest {

    @Test
    void defaultConstructor() {
        new Tripleta();
    }

    @Test
    void paramConstructor() {
        new Tripleta("entidad:A", "relacion", "entidad:B");
    }

    @Test
    void getS() {
        Tripleta tripleta = new Tripleta("entidad:A", "relacion", "entidad:B");
        tripleta.getS();
    }

    @Test
    void setS() {
        Tripleta tripleta = new Tripleta("entidad:A", "relacion", "entidad:B");
        tripleta.setS("entidad:C");
    }

    @Test
    void getP() {
        Tripleta tripleta = new Tripleta("entidad:A", "relacion", "entidad:B");
        tripleta.getP();
    }

    @Test
    void setP() {
        Tripleta tripleta = new Tripleta("entidad:A", "relacion", "entidad:B");
        tripleta.setP("atajo");
    }

    @Test
    void getO() {
        Tripleta tripleta = new Tripleta("entidad:A", "relacion", "entidad:B");
        tripleta.getO();
    }

    @Test
    void setO() {
        Tripleta tripleta = new Tripleta("entidad:A", "relacion", "entidad:B");
        tripleta.setO("entidad:C");
    }

    @Test
    void testToString() {
        Tripleta tripleta = new Tripleta("entidad:A", "relacion", "entidad:B");
        assertEquals("<\"entidad:A\", \"relacion\", \"entidad:B\">", tripleta.toString());
    }
}
