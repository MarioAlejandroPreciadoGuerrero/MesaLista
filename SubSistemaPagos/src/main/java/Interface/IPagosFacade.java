/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import DTO.VentaDTO;

/**
 *
 * @author USER
 */
public interface IPagosFacade {

    /**
     * Procesa un pago registrando una nueva venta en el sistema.
     *
     * @param venta DTO con los datos del cobro.
     */
    void procesarPago(VentaDTO venta);

    /**
     * Consulta los detalles de un pago o recibo ya generado.
     *
     * @param id Identificador de la venta.
     * @return DTO de la venta o null.
     */
    VentaDTO obtenerReciboPago(String id);

    /**
     * Registra un reembolso o ajuste eliminando el registro de la venta.
     *
     * @param id Identificador de la venta a reembolsar/anular.
     */
    void anularPago(String id);
}
