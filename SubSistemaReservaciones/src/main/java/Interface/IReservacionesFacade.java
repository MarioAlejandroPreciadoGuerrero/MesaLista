/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interface;

import DTO.ReservacionDTO;

/**
 *
 * @author USER
 */
public interface IReservacionesFacade {

    /**
     * Genera una nueva reservación en el sistema.
     *
     * @param reservacion DTO con los detalles de la reservación.
     */
    void crearReservacion(ReservacionDTO reservacion);

    /**
     * Busca una reservación usando su folio de confirmación.
     *
     * @param folio Folio de la reservación.
     * @return DTO de la reservación o null.
     */
    ReservacionDTO buscarPorFolio(String folio);

    /**
     * Modifica los detalles de una reservación existente.
     *
     * @param reservacion DTO modificado.
     */
    void modificarReservacion(ReservacionDTO reservacion);

    /**
     * Cancela una reservación existente en el sistema.
     *
     * @param id Identificador único de la reservación.
     */
    void cancelarReservacion(String id);

}
