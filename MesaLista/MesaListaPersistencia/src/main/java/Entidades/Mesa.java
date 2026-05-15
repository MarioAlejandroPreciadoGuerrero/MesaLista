/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author USER
 */
public class Mesa {

    private Integer numero;
    private Integer capacidadMesa;

    public Mesa() {}

    public Mesa(Integer numero, Integer capacidadMesa) {
        this.numero = numero;
        this.capacidadMesa = capacidadMesa;
    }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public Integer getCapacidadMesa() { return capacidadMesa; }
    public void setCapacidadMesa(Integer capacidadMesa) { this.capacidadMesa = capacidadMesa; }
}
