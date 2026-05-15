/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import DTO.ReservacionDTO;
import DTO.RestauranteDTO;
import DTO.UsuarioDTO;
import DTO.VentaDTO;
import Facade.PagosFacade;
import Facade.ReservacionesFacade;
import Facade.RestaurantesFacade;
import Facade.UsuarioFacade;
import Interface.IPagosFacade;
import Interface.IReservacionesFacade;
import Interface.IRestaurantesFacade;
import Interface.IUsuarioFacade;

/**
 *
 * @author USER
 */
public class ControlOperaciones{
    private static ControlOperaciones instancia;

    // Dependencias a nuestros subsistemas
    private final IUsuarioFacade usuarioFacade;
    private final IReservacionesFacade reservacionesFacade;
    private final IRestaurantesFacade restaurantesFacade;
    private final IPagosFacade pagosFacade;

    /**
     * Constructor privado para el patrón Singleton.
     * Inicializa las implementaciones de las fachadas.
     */
    private ControlOperaciones() {
        this.usuarioFacade = new UsuarioFacade();
        this.reservacionesFacade = new ReservacionesFacade();
        this.restaurantesFacade = new RestaurantesFacade();
        this.pagosFacade = new PagosFacade();
    }

    /**
     * Obtiene la única instancia del controlador.
     */
    public static ControlOperaciones getInstancia() {
        if (instancia == null) {
            instancia = new ControlOperaciones();
        }
        return instancia;
    }

    // ==========================================
    // OPERACIONES DE USUARIO
    // ==========================================

    public void registrarNuevoUsuario(UsuarioDTO usuarioDTO) throws Exception {
        if (usuarioDTO.getEmail() == null || usuarioDTO.getEmail().isEmpty()) {
            throw new Exception("El correo electrónico es obligatorio.");
        }
        
        // Validamos que el correo no esté duplicado en la base de datos
        if (usuarioFacade.obtenerUsuarioPorEmail(usuarioDTO.getEmail()) != null) {
            throw new Exception("El correo electrónico ya se encuentra registrado.");
        }
        
        usuarioFacade.registrarUsuario(usuarioDTO);
    }

    public UsuarioDTO iniciarSesion(String email) throws Exception {
        if (email == null || email.isEmpty()) {
            throw new Exception("Debe ingresar su correo electrónico.");
        }
        
        // Ahora sí usamos la base de datos real para buscar al usuario
        UsuarioDTO usuario = usuarioFacade.obtenerUsuarioPorEmail(email);
        
        if (usuario == null) {
            throw new Exception("No existe una cuenta registrada con ese correo.");
        }
        
        return usuario;
    }

    // ==========================================
    // OPERACIONES DE RESTAURANTES
    // ==========================================

    public RestauranteDTO obtenerDetallesRestaurante(String restauranteId) {
        return restaurantesFacade.consultarRestaurante(restauranteId);
    }

    // ==========================================
    // OPERACIONES COMBINADAS (ORQUESTACIÓN)
    // ==========================================

    /**
     * Este es el mejor ejemplo del poder de un controlador:
     * Toma los datos, cobra en la pasarela, y si es exitoso, guarda la reserva.
     */
    public void procesarReservaConPago(ReservacionDTO reservacionDTO) throws Exception {
        try {
            // 1. Armamos el objeto Venta que exige el pago
            VentaDTO ventaDTO = new VentaDTO();
            ventaDTO.setMontoTotal(reservacionDTO.getCosto());
            ventaDTO.setUsuarioId(reservacionDTO.getUsuarioId());
            // Nota: En este punto la reserva aún no tiene ID porque no se ha guardado
            
            // 2. Procesamos el pago en la fachada (que internamente llama a PayPal)
            pagosFacade.procesarPago(ventaDTO);
            
            // 3. Si no lanzó excepción, el pago pasó. Procedemos a guardar la reservación.
            reservacionesFacade.crearReservacion(reservacionDTO);
            
            // 4. (Opcional) Si necesitas amarrar el ID de la reserva a la venta ya creada
            // actualizaríamos la venta aquí.
            
        } catch (RuntimeException ex) {
            // Si el pago falla (PayPal rechaza), capturamos y relanzamos para que la pantalla muestre el error.
            throw new Exception("No se pudo completar la transacción: " + ex.getMessage());
        }
    }
}
