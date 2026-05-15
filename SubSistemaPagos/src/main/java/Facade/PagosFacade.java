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

        // 1. Intentar cobrar. Ahora retorna un String (El ID real de PayPal)
        String transaccionId = pasarelaPago.procesarCobro(venta.getMontoTotal(), "Pago de Reservacion ID: " + venta.getReservacionId());

        // 2. Si falló (es null), detenemos el proceso.
        if (transaccionId == null) {
            throw new RuntimeException("El pago fue declinado por la pasarela externa.");
        }

        // 3. Si tuvo éxito, asignamos el ID de PayPal al DTO y lo guardamos en nuestra BD.
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

        // 1. Buscamos la venta en nuestra base de datos
        VentaDTO venta = repository.buscarPorId(id);
        if (venta == null || venta.getTransaccionExternaId() == null) {
            return;
        }

        // 2. Pedimos a PayPal que haga el reembolso usando el ID que habíamos guardado
        boolean reembolsoExitoso = pasarelaPago.procesarReembolso(venta.getTransaccionExternaId());

        if (!reembolsoExitoso) {
            throw new RuntimeException("No se pudo procesar el reembolso en la pasarela externa.");
        }

        // 3. Si el reembolso en PayPal tuvo éxito, eliminamos la venta de nuestra BD.
        repository.eliminar(id);
    }
}
