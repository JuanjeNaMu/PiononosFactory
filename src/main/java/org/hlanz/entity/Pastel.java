package org.hlanz.entity;

public class Pastel {
    private Long id;
    private String nombre;
    private String sabor;
    private double precio;
    private int porciones;

    // Constructor vacío
    public Pastel() {}

    // Constructor completo
    public Pastel(Long id, String nombre, String sabor, double precio, int porciones) {
        this.id = id;
        this.nombre = nombre;
        this.sabor = sabor;
        this.precio = precio;
        this.porciones = porciones;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getSabor() { return sabor; }
    public void setSabor(String sabor) { this.sabor = sabor; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getPorciones() { return porciones; }
    public void setPorciones(int porciones) { this.porciones = porciones; }
}
