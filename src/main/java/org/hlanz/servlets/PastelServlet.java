package org.hlanz.servlets;

import org.hlanz.entity.Pastel;
import org.hlanz.mensaje.JsonUtil;
import org.hlanz.repository.PastelRepository;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

// API REST para pasteles en /api/pasteles
@WebServlet("/api/pasteles/*")
public class PastelServlet extends HttpServlet {
    private PastelRepository repository = PastelRepository.getInstance();

    // MÉTODO 1: GET /api/pasteles → lista todos o GET /api/pasteles/5 → obtiene pastel con ID 5
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String pathInfo = req.getPathInfo(); // Obtiene /5 de /api/pasteles/5

        try {
            // Si hay ID en la URL (/api/pasteles/5)
            if (pathInfo != null && !pathInfo.equals("/")) {
                Long id = Long.parseLong(pathInfo.substring(1)); // Quita / inicial
                Pastel pastel = repository.obtenerPorId(id);

                if (pastel != null) {
                    resp.getWriter().write(JsonUtil.pastelToJson(pastel));
                } else {
                    resp.setStatus(404);
                    resp.getWriter().write("{\"error\":\"Pastel no encontrado\"}");
                }
            } else {
                // Sin ID, devuelve todos
                List<Pastel> pasteles = repository.obtenerTodos();
                resp.getWriter().write(JsonUtil.pastelesListToJson(pasteles));
            }
        } catch (NumberFormatException e) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"ID inválido\"}");
        }
    }

    //MÉTODO 2:  POST /api/pasteles → crea pastel nuevo
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String json = leerBody(req); // Lee JSON del body
            Pastel nuevoPastel = JsonUtil.jsonToPastel(json);
            Pastel pastelCreado = repository.crear(nuevoPastel);

            resp.setStatus(201); // Created
            resp.getWriter().write(JsonUtil.pastelToJson(pastelCreado));
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"Datos inválidos\"}");
        }
    }

    // MÉTODO 3: PUT /api/pasteles/5 → actualiza pastel con ID 5
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"ID requerido\"}");
            return;
        }

        try {
            Long id = Long.parseLong(pathInfo.substring(1));
            String json = leerBody(req);
            Pastel pastel = JsonUtil.jsonToPastel(json);
            Pastel actualizado = repository.actualizar(id, pastel);

            if (actualizado != null) {
                resp.getWriter().write(JsonUtil.pastelToJson(actualizado));
            } else {
                resp.setStatus(404);
                resp.getWriter().write("{\"error\":\"Pastel no encontrado\"}");
            }
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"Datos inválidos\"}");
        }
    }

    // MÉTODO 4: DELETE /api/pasteles/5 → elimina pastel con ID 5
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"ID requerido\"}");
            return;
        }

        try {
            Long id = Long.parseLong(pathInfo.substring(1));

            if (repository.eliminar(id)) {
                resp.setStatus(204); // No Content (éxito sin cuerpo)
            } else {
                resp.setStatus(404);
                resp.getWriter().write("{\"error\":\"Pastel no encontrado\"}");
            }
        } catch (NumberFormatException e) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"ID inválido\"}");
        }
    }

    // Lee el cuerpo de la petición HTTP (el JSON que envía el cliente)
    private String leerBody(HttpServletRequest req) throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }
}