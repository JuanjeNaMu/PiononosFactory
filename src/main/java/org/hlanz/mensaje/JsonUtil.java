package org.hlanz.mensaje;

import org.hlanz.entity.Pastel;

import java.util.List;


// Convierte objetos Pastel a texto JSON y viceversa
public class JsonUtil {

    // MÉTODO 1: Convierte UN pastel a texto JSON
    public static String pastelToJson(Pastel p) {
        return String.format(
                "{\"id\":%d,\"nombre\":\"%s\",\"sabor\":\"%s\",\"precio\":%.2f,\"porciones\":%d}",
                p.getId(), p.getNombre(), p.getSabor(), p.getPrecio(), p.getPorciones()
        );
    }

    // MÉTODO 2: Convierte una LISTA de pasteles a texto JSON
    public static String pastelesListToJson(List<Pastel> pasteles) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < pasteles.size(); i++) {
            json.append(pastelToJson(pasteles.get(i)));  // Añade cada pastel
            if (i < pasteles.size() - 1) {
                json.append(",");  // Pone coma entre pasteles (pero no al final)
            }
        }
        json.append("]");  // Cierra el array
        return json.toString();
    }

    // MÉTODO 3: Convierte texto JSON a un objeto Pastel
    public static Pastel jsonToPastel(String json) {
        Pastel pastel = new Pastel();  // Creamos pastel vacío

        // Extraemos cada valor del JSON
        pastel.setNombre(extraerValor(json, "nombre"));   // Extrae el nombre
        pastel.setSabor(extraerValor(json, "sabor"));     // Extrae el sabor

        String precioStr = extraerValor(json, "precio");
        if (precioStr != null) {
            pastel.setPrecio(Double.parseDouble(precioStr));  // Convierte texto a número
        }

        String porcionesStr = extraerValor(json, "porciones");
        if (porcionesStr != null) {
            pastel.setPorciones(Integer.parseInt(porcionesStr));  // Convierte texto a número
        }

        return pastel;  // Devuelve el pastel completo
    }

    // MÉTODO 4: Buscar valores dentro del JSON
    private static String extraerValor(String json, String clave) {
        String patron = "\"" + clave + "\"";  // Busca "clave"
        int inicio = json.indexOf(patron);
        if (inicio == -1) return null;  // Si no encuentra la clave, devuelve null

        inicio = json.indexOf(":", inicio) + 1;  // Avanza hasta después de los dos puntos
        int fin;

        json = json.substring(inicio).trim();  // Corta el string desde ahí

        // Si el valor está entre comillas (es texto)
        if (json.startsWith("\"")) {
            inicio = 1;  // Salta la comilla inicial
            fin = json.indexOf("\"", inicio);  // Busca la comilla final
            return json.substring(inicio, fin);  // Extrae el texto
        } else {
            // Si el valor NO está entre comillas (es número)
            fin = json.indexOf(",");
            if (fin == -1) fin = json.indexOf("}");  // Busca el final
            return json.substring(0, fin).trim();  // Extrae el número
        }
    }
}