package org.hlanz.repository;

import org.hlanz.entity.Pastel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

// Esta clase simula una base de datos en memoria. Usa un HashMap que desaparecen al apagar el programa
public class PastelRepository {
    private static PastelRepository instance;  // Única instancia (Singleton)
    private Map<Long, Pastel> pasteles = new HashMap<>();
    private AtomicLong idGenerator = new AtomicLong(1);  // Generador automático de IDs

    // Constructor PRIVADO - Parte del patrón Singleton
    private PastelRepository() {
        // Creamos 4 pasteles de ejemplo para empezar
        crear(new Pastel(null, "Tres Leches", "Vainilla", 25.50, 12));
        crear(new Pastel(null, "Selva Negra", "Chocolate", 30.00, 10));
        crear(new Pastel(null, "Red Velvet", "Chocolate Rojo", 28.75, 14));
        crear(new Pastel(null, "Zanahoria", "Especias", 22.00, 8));
    }

    // MÉTODO 1: Obtener la única instancia (Singleton)
    // Asegura que solo haya UN PastelRepository en todo el programa
    public static PastelRepository getInstance() {
        if (instance == null) {
            instance = new PastelRepository();  // Se crea solo la primera vez
        }
        return instance;
    }

    // MÉTODO 2: Devuelve TODOS los pasteles como una lista
    public List<Pastel> obtenerTodos() {
        return new ArrayList<>(pasteles.values());  // Convierte el mapa a lista
    }

    // MÉTODO 3: Busca un pastel por su ID
    public Pastel obtenerPorId(Long id) {
        return pasteles.get(id);  // Devuelve null si no existe
    }

    // MÉTODO 4: Crea un nuevo pastel (le asigna ID automático)
    public Pastel crear(Pastel pastel) {
        Long id = idGenerator.getAndIncrement();
        pastel.setId(id);  // Asigna el ID nuevo (autoincrement) al pastel
        pasteles.put(id, pastel);  // Guarda en el mapa
        return pastel;  // Devuelve el pastel con su ID
    }

    // MÉTODO 4: Actualiza un pastel existente
    public Pastel actualizar(Long id, Pastel pastel) {
        if (pasteles.containsKey(id)) {
            pastel.setId(id);  // Asegura que tenga el ID correcto
            pasteles.put(id, pastel);  // Reemplaza el viejo con el nuevo
            return pastel;  // Devuelve el pastel actualizado
        }
        return null;  // Si no existe, devuelve null
    }

    // MÉTODO 5: Elimina un pastel por ID
    public boolean eliminar(Long id) {
        return pasteles.remove(id) != null;  // True si lo eliminó, False si no existía
    }
}