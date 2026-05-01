import java.util.List;



public class Main {

    public static void main(String[] args) throws Exception {



        System.out.println(" Grafo Conexo ");


        GrafoConocimiento grafoConexo = new GrafoConocimiento();
        CargadorDatos.cargar(grafoConexo, "datos/grafo_conexo.json");
        grafoConexo.printResumen();

        System.out.println("¿Es disjunto?  -> " + grafoConexo.esDisjunto());


        System.out.println("\nCamino mínimo persona:Ana -> persona:Carlos:");
        List<String> camino1 = grafoConexo.caminoMinimo(
                "persona:Ana", "persona:Carlos");
        imprimirCamino(camino1);


        System.out.println(" Grafo Disjunto ");


        GrafoConocimiento grafoDisjunto = new GrafoConocimiento();
        CargadorDatos.cargar(grafoDisjunto, "datos/grafo_disjunto.json");
        grafoDisjunto.printResumen();

        System.out.println("¿Es disjunto?  -> " + grafoDisjunto.esDisjunto());


        System.out.println("\nCamino mínimo persona:Ana -> persona:Carlos:");
        List<String> camino2 = grafoDisjunto.caminoMinimo(
                "persona:Ana", "persona:Carlos");
        imprimirCamino(camino2);

        System.out.println(" Grafo Ganadores Premios Nobel ");
        System.out.println("========================================");

        GrafoConocimiento grafoNobel = new GrafoConocimiento();
        CargadorDatos.cargar(grafoNobel, "datos/nobel.json");
        grafoNobel.printResumen();


        System.out.println("\n¿Qué físico nació en la misma ciudad que Einstein?");
        List<String> fisicos = grafoNobel.fisicosNacidosMismaCiudadQue(
                "persona:Einstein");
        if (fisicos.isEmpty()) {
            System.out.println("  (No se ha encontrado ningún físico)");
        } else {
            for (String f : fisicos) {
                System.out.println("  -> " + f);
            }
        }


        System.out.println("\nAñadimos la tripleta:");
        System.out.println("  <persona:Antonio, nace_en, lugar:Villarrubia de los Caballeros>");
        grafoNobel.anadirTripleta(
                "persona:Antonio", "nace_en", "lugar:Villarrubia de los Caballeros");


        System.out.println("\nLugares de nacimiento de los Premios Nobel:");
        for (String linea : grafoNobel.lugaresNacimientoPremiosNobel()) {
            System.out.println("  - " + linea);
        }

        System.out.println("\nTipos de nodos en el grafo:");
        for (String tipo : grafoNobel.tiposNodos()) {
            System.out.println("  - " + tipo);
        }


        System.out.println("\nCamino mínimo persona:Antonio -> persona:Einstein:");
        List<String> camino3 = grafoNobel.caminoMinimo(
                "persona:Antonio", "persona:Einstein");
        imprimirCamino(camino3);
    }


    private static void imprimirCamino(List<String> camino) {
        if (camino == null) {
            System.out.println("  (no hay camino entre estos nodos)");
            return;
        }
        StringBuilder sb = new StringBuilder("  ");
        for (int i = 0; i < camino.size(); i++) {
            sb.append(camino.get(i));
            if (i < camino.size() - 1) sb.append(" -> ");
        }
        System.out.println(sb.toString());
    }
}
