/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author USER
 */
public class Platillo {

    private String nombrePlatillo;
    private Double precio;
    private String descripcion;

    public Platillo() {}

    public Platillo(String nombrePlatillo, Double precio, String descripcion) {
        this.nombrePlatillo = nombrePlatillo;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public String getNombrePlatillo() { return nombrePlatillo; }
    public void setNombrePlatillo(String nombrePlatillo) { this.nombrePlatillo = nombrePlatillo; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
