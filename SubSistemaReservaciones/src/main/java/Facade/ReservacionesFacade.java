/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Facade;

import DTO.ReservacionDTO;
import Interface.IReservacionesFacade;
import Repository.ReservacionRepository;




/**
 *
 * @author USER
 */
public class ReservacionesFacade implements IReservacionesFacade {

    private final ReservacionRepository repository;

    public ReservacionesFacade() {
        this.repository = new ReservacionRepository();
    }

    @Override
    public void crearReservacion(ReservacionDTO reservacion) {
        if (reservacion != null) repository.guardar(reservacion);
    }

    @Override
    public ReservacionDTO buscarPorFolio(String folio) {
        if (folio == null || folio.isEmpty()) return null;
        return repository.buscarPorFolio(folio);
    }

    @Override
    public void modificarReservacion(ReservacionDTO reservacion) {
        if (reservacion != null && reservacion.getId() != null) repository.actualizar(reservacion);
    }

    @Override
    public void cancelarReservacion(String id) {
        if (id != null && !id.isEmpty()) repository.eliminar(id);
    }
}
