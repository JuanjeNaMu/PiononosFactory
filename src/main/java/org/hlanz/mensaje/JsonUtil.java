package org.hlanz.mensaje;

import org.hlanz.entity.Pastel;
import java.util.List;

// Convierte objetos Pastel a JSON y al revés
public class JsonUtil {

    // Convierte pastel a JSON
    public static String pastelToJson(Pastel p) {
        return String.format(
                "{\"id\":%d,\"nombre\":\"%s\",\"sabor\":\"%s\",\"precio\":%.2f,\"porciones\":%d}",
                p.getId(), p.getNombre(), p.getSabor(), p.getPrecio(), p.getPorciones()
        );
    }

    // Convierte lista de pasteles a JSON array
    public static String pastelesListToJson(List<Pastel> pasteles) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < pasteles.size(); i++) {
            json.append(pastelToJson(pasteles.get(i)));
            if (i < pasteles.size() - 1) json.append(",");
        }
        json.append("]");
        return json.toString();
    }

    // Convierte JSON a objeto Pastel
    public static Pastel jsonToPastel(String json) {
        Pastel pastel = new Pastel();
        pastel.setNombre(extraerValor(json, "nombre"));
        pastel.setSabor(extraerValor(json, "sabor"));
        pastel.setPrecio(Double.parseDouble(extraerValor(json, "precio")));
        pastel.setPorciones(Integer.parseInt(extraerValor(json, "porciones")));
        return pastel;
    }

    // Coge un valor del string JSON (busca la clave y devuelve su valor)
    private static String extraerValor(String json, String clave) {
        int inicio = json.indexOf("\"" + clave + "\"");
        inicio = json.indexOf(":", inicio) + 1;
        json = json.substring(inicio).trim();

        // Si es string (empieza con "), extrae hasta la siguiente "
        if (json.startsWith("\"")) {
            return json.substring(1, json.indexOf("\"", 1));
        }
        // Si es número, extrae hasta la coma o }
        int fin = json.indexOf(",");
        if (fin == -1) fin = json.indexOf("}");
        return json.substring(0, fin).trim();
    }
}