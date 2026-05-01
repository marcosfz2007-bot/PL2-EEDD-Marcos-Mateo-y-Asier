import java.io.*;
import java.util.*;


public class LectorJSON {


    public static List<Tripleta> leer(String rutaArchivo) throws IOException {

        // ---- 1) Leer todo el archivo a una sola cadena de texto ----
        String texto = leerArchivoCompleto(rutaArchivo);

        List<Tripleta> tripletas = new ArrayList<>();
        int i = 0;
        while (i < texto.length()) {
            int inicio = texto.indexOf("{", i);
            if (inicio == -1) break;          // no quedan más bloques
            int fin = texto.indexOf("}", inicio);
            if (fin == -1) break;             // bloque mal cerrado

            // Contenido entre las llaves, por ejemplo:
            //   "s": "persona:Einstein", "p": "nace_en", "o": "lugar:Ulm"
            String bloque = texto.substring(inicio + 1, fin);

            String s = extraerValor(bloque, "s");
            String p = extraerValor(bloque, "p");
            String o = extraerValor(bloque, "o");

            // Creamos la tripleta y la añadimos solo si es válida.
            Tripleta t = new Tripleta(s, p, o);
            if (t.esValida()) {
                tripletas.add(t);
            }

            i = fin + 1;
        }

        return tripletas;
    }

    private static String leerArchivoCompleto(String rutaArchivo) throws IOException {
        StringBuilder contenido = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo));
        try {
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea).append(" ");
            }
        } finally {
            reader.close();
        }
        return contenido.toString();
    }

    private static String extraerValor(String bloque, String clave) {
        String marcador = "\"" + clave + "\"";
        int idx = bloque.indexOf(marcador);
        if (idx == -1) return null;


        int comillaInicio = bloque.indexOf("\"", idx + marcador.length());
        if (comillaInicio == -1) return null;


        int comillaFin = bloque.indexOf("\"", comillaInicio + 1);
        if (comillaFin == -1) return null;

        return bloque.substring(comillaInicio + 1, comillaFin);
    }
}