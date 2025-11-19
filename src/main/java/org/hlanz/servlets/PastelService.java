package org.hlanz.servlets;

import org.hlanz.entity.Pastel;
import org.hlanz.mensaje.JsonUtil;
import org.hlanz.repository.PastelRepository;

import java.util.List;

public class PastelService {
    private PastelRepository repository = PastelRepository.getInstance();

    // GET - Obtener todos los pasteles
    public String obtenerTodos() {
        List<Pastel> pasteles = repository.obtenerTodos();
        return JsonUtil.pastelesListToJson(pasteles);
    }

    // GET - Obtener un pastel por ID
    public String obtenerPorId(Long id) {
        Pastel pastel = repository.obtenerPorId(id);

        if (pastel != null) {
            return JsonUtil.pastelToJson(pastel);
        } else {
            return "{\"error\":\"Pastel no encontrado\",\"status\":404}";
        }
    }

    // POST - Crear un nuevo pastel
    public String crear(String jsonPastel) {
        try {
            Pastel nuevoPastel = JsonUtil.jsonToPastel(jsonPastel);
            Pastel pastelCreado = repository.crear(nuevoPastel);
            return JsonUtil.pastelToJson(pastelCreado);
        } catch (Exception e) {
            return "{\"error\":\"Datos inválidos\",\"status\":400}";
        }
    }

    // PUT - Actualizar un pastel existente
    public String actualizar(Long id, String jsonPastel) {
        try {
            Pastel pastel = JsonUtil.jsonToPastel(jsonPastel);
            Pastel actualizado = repository.actualizar(id, pastel);

            if (actualizado != null) {
                return JsonUtil.pastelToJson(actualizado);
            } else {
                return "{\"error\":\"Pastel no encontrado\",\"status\":404}";
            }
        } catch (Exception e) {
            return "{\"error\":\"Datos inválidos\",\"status\":400}";
        }
    }

    // DELETE - Eliminar un pastel
    public String eliminar(Long id) {
        boolean eliminado = repository.eliminar(id);

        if (eliminado) {
            return "{\"mensaje\":\"Pastel eliminado correctamente\",\"status\":204}";
        } else {
            return "{\"error\":\"Pastel no encontrado\",\"status\":404}";
        }
    }
}
