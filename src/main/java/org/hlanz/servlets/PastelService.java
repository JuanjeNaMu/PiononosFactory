package org.hlanz.servlets;

import org.hlanz.entity.Pastel;
import org.hlanz.mensaje.JsonUtil;
import org.hlanz.repository.PastelRepository;

import java.util.List;

// Esta clase maneja la lógica de negocio. Es intermediario entre el servlet (que recibe peticiones HTTP) y el repository (que maneja los datos)
public class PastelService {
    private PastelRepository repository = PastelRepository.getInstance();

    // MÉTODO 1: GET - Obtener todos los pasteles
    public String obtenerTodos() {
        List<Pastel> pasteles = repository.obtenerTodos();  // Pide datos al repository
        return JsonUtil.pastelesListToJson(pasteles);  // Convierte a JSON y devuelve
    }

    // MÉTODO 2: GET - Obtener un pastel por ID
    public String obtenerPorId(Long id) {
        Pastel pastel = repository.obtenerPorId(id);  // Busca el pastel

        if (pastel != null) {
            return JsonUtil.pastelToJson(pastel);  // Si existe, lo convierte a JSON
        } else {
            // Si no existe, devuelve error en JSON
            return "{\"error\":\"Pastel no encontrado\",\"status\":404}";
        }
    }

    // MÉTODO 3: POST - Crear un nuevo pastel
    public String crear(String jsonPastel) {
        try {
            // Convierte JSON a objeto Pastel
            Pastel nuevoPastel = JsonUtil.jsonToPastel(jsonPastel);
            // Guarda en el repository
            Pastel pastelCreado = repository.crear(nuevoPastel);
            // Devuelve el pastel creado como JSON
            return JsonUtil.pastelToJson(pastelCreado);
        } catch (Exception e) {
            // Si hay error, devuelve mensaje de error
            return "{\"error\":\"Datos inválidos\",\"status\":400}";
        }
    }

    // MÉTODO 4: PUT - Actualizar un pastel existente
    public String actualizar(Long id, String jsonPastel) {
        try {
            Pastel pastel = JsonUtil.jsonToPastel(jsonPastel);  // JSON → Objeto
            Pastel actualizado = repository.actualizar(id, pastel);  // Actualiza

            if (actualizado != null) {
                return JsonUtil.pastelToJson(actualizado);  // Éxito
            } else {
                return "{\"error\":\"Pastel no encontrado\",\"status\":404}";  // No existe
            }
        } catch (Exception e) {
            return "{\"error\":\"Datos inválidos\",\"status\":400}";  // Error en datos
        }
    }

    // MÉTODO 5: DELETE - Eliminar un pastel
    public String eliminar(Long id) {
        boolean eliminado = repository.eliminar(id);  // Intenta eliminar

        if (eliminado) {
            return "{\"mensaje\":\"Pastel eliminado correctamente\",\"status\":204}";
        } else {
            return "{\"error\":\"Pastel no encontrado\",\"status\":404}";
        }
    }
}
