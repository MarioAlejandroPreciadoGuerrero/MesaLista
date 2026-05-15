/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

/**
 *
 * @author USER
 */
public interface IPasarelaPago {

    /**
     * Procesa un cobro en la plataforma externa.
     *
     * @param monto Cantidad a cobrar.
     * @param concepto Descripción del cobro.
     * @return true si el pago fue aprobado, false si fue rechazado.
     */
    String procesarCobro(Double monto, String concepto);

    /**
     * Genera un reembolso en la plataforma externa.
     *
     * @param transaccionId ID de la transacción original devuelta por la
     * pasarela.
     * @return true si el reembolso fue exitoso.
     */
    boolean procesarReembolso(String transaccionId);

}
