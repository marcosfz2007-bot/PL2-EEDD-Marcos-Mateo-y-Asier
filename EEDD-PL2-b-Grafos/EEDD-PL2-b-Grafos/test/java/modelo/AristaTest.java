package modelo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AristaTest {

    @Test
    void defaultConstructor() {
        new Arista();
    }

    @Test
    void paramConstructor() {
        new Arista("entidad:A", "relacion", "entidad:B", 1.0);
    }

    @Test
    void getOrigen() {
        Arista arista = new Arista("entidad:A", "relacion", "entidad:B", 1.0);
        arista.getOrigen();
    }

    @Test
    void setOrigen() {
        Arista arista = new Arista("entidad:A", "relacion", "entidad:B", 1.0);
        arista.setOrigen("entidad:C");
    }

    @Test
    void getPredicado() {
        Arista arista = new Arista("entidad:A", "relacion", "entidad:B", 1.0);
        arista.getPredicado();
    }

    @Test
    void setPredicado() {
        Arista arista = new Arista("entidad:A", "relacion", "entidad:B", 1.0);
        arista.setPredicado("atajo");
    }

    @Test
    void getDestino() {
        Arista arista = new Arista("entidad:A", "relacion", "entidad:B", 1.0);
        arista.getDestino();
    }

    @Test
    void setDestino() {
        Arista arista = new Arista("entidad:A", "relacion", "entidad:B", 1.0);
        arista.setDestino("entidad:C");
    }

    @Test
    void getPeso() {
        Arista arista = new Arista("entidad:A", "relacion", "entidad:B", 1.0);
        arista.getPeso();
    }

    @Test
    void setPeso() {
        Arista arista = new Arista("entidad:A", "relacion", "entidad:B", 1.0);
        arista.setPeso(2.0);
    }

    @Test
    void testToString() {
        Arista arista = new Arista("entidad:A", "relacion", "entidad:B", 1.0);
        assertEquals("entidad:A --relacion--> entidad:B (peso 1.0)", arista.toString());
    }
}
