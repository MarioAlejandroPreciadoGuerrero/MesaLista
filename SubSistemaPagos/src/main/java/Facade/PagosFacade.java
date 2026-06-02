/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Facade;

import Adapter.PayPalAdapter;
import DTO.VentaDTO;
import Interface.IPagosFacade;
import Interface.IPasarelaPago;
import Repository.VentaRepository;

/**
 *
 * @author USER
 */
public class PagosFacade implements IPagosFacade {

    private final VentaRepository repository;
    private final IPasarelaPago pasarelaPago;

    public PagosFacade() {
        this.repository = new VentaRepository();
        this.pasarelaPago = new PayPalAdapter();
    }

    @Override
    public void procesarPago(VentaDTO venta) {
        if (venta == null) {
            return;
        }

        
        String transaccionId = pasarelaPago.procesarCobro(venta.getMontoTotal(), "Pago de Reservacion ID: " + venta.getReservacionId());

        
        if (transaccionId == null) {
            throw new RuntimeException("El pago fue declinado por la pasarela externa.");
        }

        
        venta.setTransaccionExternaId(transaccionId);
        repository.guardar(venta);
    }

    @Override
    public VentaDTO obtenerReciboPago(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        return repository.buscarPorId(id);
    }

    @Override
    public void anularPago(String id) {
        if (id == null || id.isEmpty()) {
            return;
        }

       
        VentaDTO venta = repository.buscarPorId(id);
        if (venta == null || venta.getTransaccionExternaId() == null) {
            return;
        }

        
        boolean reembolsoExitoso = pasarelaPago.procesarReembolso(venta.getTransaccionExternaId());

        if (!reembolsoExitoso) {
            throw new RuntimeException("No se pudo procesar el reembolso en la pasarela externa.");
        }

        
        repository.eliminar(id);
    }
}
