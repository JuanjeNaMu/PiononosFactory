package org.hlanz.servlets;

import org.hlanz.entity.Pastel;
import org.hlanz.mensaje.JsonUtil;
import org.hlanz.repository.PastelRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

// Esta clase ES el servidor web
// Extiende un HttpServlet, un programa que responde a peticiones HTTP
public class PastelServlet extends HttpServlet {
    private PastelRepository repository = PastelRepository.getInstance();

    // MNÉTODO 1: GET - Obtener todos o uno por ID
    // Se ejecuta cuando alguien hace: GET http://tu-servidor/api/pasteles
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // La respuesta será JSON
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        // Miramos qué URL pidieron
        String pathInfo = req.getPathInfo();

        try {
            // Si piden /api/pasteles (sin ID)
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /api/pasteles - Obtener todos
                List<Pastel> pasteles = repository.obtenerTodos();
                String json = JsonUtil.pastelesListToJson(pasteles);
                resp.setStatus(HttpServletResponse.SC_OK);  // Código 200 = OK
                resp.getWriter().write(json);  // Envía el JSON

            } else {
                // GET /api/pasteles/{id} - Obtener uno por ID
                Long id = Long.parseLong(pathInfo.substring(1));
                Pastel pastel = repository.obtenerPorId(id);

                if (pastel != null) {
                    String json = JsonUtil.pastelToJson(pastel);
                    resp.setStatus(HttpServletResponse.SC_OK);  // 200 = OK
                    resp.getWriter().write(json);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);  // 404 = No encontrado
                    resp.getWriter().write("{\"error\":\"Pastel no encontrado\"}");
                }
            }
        } catch (NumberFormatException e) {
            // Si el ID no es un número da error 400
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"ID inválido\"}");
        }
    }

    // MÉTODO 2: POST - Crear nuevo pastel
    // Se ejecuta cuando alguien hace: POST http://tu-servidor/api/pasteles
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            // Leer el cuerpo de la petición, QUE ES UN JSON
            String json = leerBody(req);

            // Convertir JSON a objeto Pastel
            Pastel nuevoPastel = JsonUtil.jsonToPastel(json);

            // Guardar en el repositorio
            Pastel pastelCreado = repository.crear(nuevoPastel);

            // Responder con el pastel creado
            resp.setStatus(HttpServletResponse.SC_CREATED);  // Si todo va bien, 201 = Creado
            resp.getWriter().write(JsonUtil.pastelToJson(pastelCreado));

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 = Error en datos
            resp.getWriter().write("{\"error\":\"Datos inválidos\"}");
        }
    }

    // MÉTODO 3: PUT - Actualizar pastel existente
    // Se ejecuta cuando alguien hace: PUT http://tu-servidor/api/pasteles/1
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();

        // Verificar que nos dieron un ID
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 = Falta ID
            resp.getWriter().write("{\"error\":\"ID requerido\"}");
            return;
        }

        try {
            Long id = Long.parseLong(pathInfo.substring(1));  // Extrae ID de la URL
            String json = leerBody(req);  // Lee el JSON del body
            Pastel pastel = JsonUtil.jsonToPastel(json);  // Convierte

            Pastel actualizado = repository.actualizar(id, pastel);  // Actualiza

            if (actualizado != null) {
                resp.setStatus(HttpServletResponse.SC_OK);  // 200 = OK
                resp.getWriter().write(JsonUtil.pastelToJson(actualizado));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);  // 404 = No existe
                resp.getWriter().write("{\"error\":\"Pastel no encontrado\"}");
            }

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 = Error
            resp.getWriter().write("{\"error\":\"Datos inválidos\"}");
        }
    }

    // MÉTODO 4: DELETE - Eliminar pastel
    // Se ejecuta cuando alguien hace: DELETE http://tu-servidor/api/pasteles/3
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 = Falta ID
            resp.getWriter().write("{\"error\":\"ID requerido\"}");
            return;
        }

        try {
            Long id = Long.parseLong(pathInfo.substring(1));  // Extrae ID
            boolean eliminado = repository.eliminar(id);  // Elimina

            if (eliminado) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);  // 204 = Eliminado (sin contenido)
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);  // 404 = No existe
                resp.getWriter().write("{\"error\":\"Pastel no encontrado\"}");
            }

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 = ID inválido
            resp.getWriter().write("{\"error\":\"ID inválido\"}");
        }
    }

    // MÉTODO 5: Método auxiliar para leer el body de la petición
    // (El JSON que nos envían en POST/PUT)
    private String leerBody(HttpServletRequest req) throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);  // Lee línea por línea
        }
        return buffer.toString();  // Devuelve todo como un solo string
    }
}
