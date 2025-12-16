package org.hlanz.entity;

// Define la clase pastel
public class Pastel {
    private Long id;
    private String nombre;
    private String sabor;
    private double precio;
    private int porciones;

    // Constructor vacío - Para crear un pastel "en blanco"
    public Pastel() {}

    // Constructor completo - Para crear un pastel con todos sus datos de una vez
    public Pastel(Long id, String nombre, String sabor, double precio, int porciones) {
        this.id = id;
        this.nombre = nombre;
        this.sabor = sabor;
        this.precio = precio;
        this.porciones = porciones;
    }

    // GETTERS

    public Long getId() { return id; }

    public String getNombre() { return nombre; }

    public String getSabor() { return sabor; }

    public double getPrecio() { return precio; }

    public int getPorciones() { return porciones; }

    // SETTERS

    public void setId(Long id) { this.id = id; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public void setSabor(String sabor) { this.sabor = sabor; }

    public void setPrecio(double precio) { this.precio = precio; }

    public void setPorciones(int porciones) { this.porciones = porciones; }
}