package org.hlanz.repository;

import org.hlanz.entity.Pastel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Gestiona la BD de pasteles (patrón Singleton)
public class PastelRepository {
    private static PastelRepository instance;
    private Connection conn;

    // Constructor privado (solo se ejecuta 1 vez)
    private PastelRepository(){
        try {
            // Conecta a PostgreSQL
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/Pastel",
                    "postgres",
                    "adminadmin123"
            );

            // Crea tabla si no existe
            String sql = "CREATE TABLE IF NOT EXISTS pastel (" +
                    "id SERIAL PRIMARY KEY, " +
                    "nombre VARCHAR(100) NOT NULL, " +
                    "sabor VARCHAR(100) NOT NULL, " +
                    "precio DECIMAL(10,2) NOT NULL, " +
                    "porciones INTEGER NOT NULL)";
            conn.createStatement().execute(sql);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    // Devuelve la única instancia (Singleton)
    public static PastelRepository getInstance() {
        if (instance == null) instance = new PastelRepository();
        return instance;
    }

    // MÉTODO 1: INSERT - Guarda pastel nuevo y devuelve con ID generado
    public Pastel crear(Pastel p){
        String sql = "INSERT INTO pastel (nombre, sabor, precio, porciones) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getSabor());
            stmt.setDouble(3, p.getPrecio());
            stmt.setInt(4, p.getPorciones());
            stmt.executeUpdate();

            // Obtiene el ID generado por la BD
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) p.setId(rs.getLong(1));
            return p;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // MÉTODO 2: SELECT * - Devuelve todos los pasteles
    public List<Pastel> obtenerTodos(){
        List<Pastel> pasteles = new ArrayList<>();
        String sql = "SELECT * FROM pastel ORDER BY id";
        try {
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while(rs.next()){
                Pastel p = new Pastel();
                p.setId(rs.getLong("id"));
                p.setNombre(rs.getString("nombre"));
                p.setSabor(rs.getString("sabor"));
                p.setPrecio(rs.getDouble("precio"));
                p.setPorciones(rs.getInt("porciones"));
                pasteles.add(p);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return pasteles;
    }

    // MÉTODO 3: SELECT WHERE id - Busca 1 pastel por ID
    public Pastel obtenerPorId(Long id) {
        String sql = "SELECT * FROM pastel WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Pastel p = new Pastel();
                p.setId(rs.getLong("id"));
                p.setNombre(rs.getString("nombre"));
                p.setSabor(rs.getString("sabor"));
                p.setPrecio(rs.getDouble("precio"));
                p.setPorciones(rs.getInt("porciones"));
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // MÉTODO 4: UPDATE - Modifica un pastel existente
    public Pastel actualizar(Long id, Pastel pastel) {
        String sql = "UPDATE pastel SET nombre=?, sabor=?, precio=?, porciones=? WHERE id=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, pastel.getNombre());
            stmt.setString(2, pastel.getSabor());
            stmt.setDouble(3, pastel.getPrecio());
            stmt.setInt(4, pastel.getPorciones());
            stmt.setLong(5, id);

            if (stmt.executeUpdate() > 0) {
                pastel.setId(id);
                return pastel;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // MÉTODO 5: DELETE - Elimina un pastel
    public boolean eliminar(Long id) {
        String sql = "DELETE FROM pastel WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}