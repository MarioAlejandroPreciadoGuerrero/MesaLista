/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import DTO.AreaDTO;
import DTO.MenuDTO;
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
import java.util.List;

/**
 *
 * @author USER
 */
public class ControlOperaciones {

    private static ControlOperaciones instancia;

    // Dependencias a nuestros subsistemas
    private final IUsuarioFacade usuarioFacade;
    private final IReservacionesFacade reservacionesFacade;
    private final IRestaurantesFacade restaurantesFacade;
    private final IPagosFacade pagosFacade;

    /**
     * Constructor privado para el patrón Singleton. Inicializa las
     * implementaciones de las fachadas.
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

    public void registrarNuevoUsuario(UsuarioDTO usuarioDTO) throws Exception {
        if (usuarioDTO.getEmail() == null || usuarioDTO.getEmail().isEmpty()) {
            throw new Exception("El correo electrónico es obligatorio.");
        }

        if (usuarioFacade.obtenerUsuarioPorEmail(usuarioDTO.getEmail()) != null) {
            throw new Exception("El correo electrónico ya se encuentra registrado.");
        }

        usuarioFacade.registrarUsuario(usuarioDTO);
    }

    public UsuarioDTO iniciarSesion(String email, String contrasena) throws Exception {
        if (email == null || email.isEmpty()) {
            throw new Exception("Debe ingresar su correo electrónico.");
        }
        if (contrasena == null || contrasena.isEmpty()) {
            throw new Exception("Debe ingresar su contraseña.");
        }
        UsuarioDTO usuario = usuarioFacade.obtenerUsuarioPorEmail(email);
        if (usuario == null) {
            throw new Exception("No existe una cuenta registrada con ese correo.");
        }
        if (!contrasena.equals(usuario.getContrasena())) {
            throw new Exception("Contraseña incorrecta.");
        }
        return usuario;
    }

    public RestauranteDTO obtenerDetallesRestaurante(String restauranteId) {
        return restaurantesFacade.consultarRestaurante(restauranteId);
    }

    public List<RestauranteDTO> obtenerTodosLosRestaurantes() {
        return restaurantesFacade.obtenerTodosLosRestaurantes();
    }

    public void actualizarAreasRestaurante(String restauranteId, List<AreaDTO> areas) throws Exception {
        RestauranteDTO restaurante = restaurantesFacade.consultarRestaurante(restauranteId);
        if (restaurante == null) throw new Exception("No se encontró el restaurante.");
        restaurante.setAreas(areas);
        restaurantesFacade.actualizarRestaurante(restaurante);
    }

    public void actualizarMenuRestaurante(String restauranteId, MenuDTO menu) throws Exception {
        RestauranteDTO restaurante = restaurantesFacade.consultarRestaurante(restauranteId);
        if (restaurante == null) throw new Exception("No se encontró el restaurante.");
        restaurante.setMenu(menu);
        restaurantesFacade.actualizarRestaurante(restaurante);
    }
    
    public List<ReservacionDTO> obtenerHistorialReservaciones(String usuarioId) {
        return reservacionesFacade.obtenerHistorialUsuario(usuarioId);
    }

    /**
     * Este es el mejor ejemplo del poder de un controlador: Toma los datos,
     * cobra en la pasarela, y si es exitoso, guarda la reserva.
     */
    public void procesarReservaConPago(ReservacionDTO reservacionDTO) throws Exception {
        try {
            VentaDTO ventaDTO = new VentaDTO();
            ventaDTO.setMontoTotal(reservacionDTO.getCosto());
            ventaDTO.setUsuarioId(reservacionDTO.getUsuarioId());

            pagosFacade.procesarPago(ventaDTO);

            reservacionesFacade.crearReservacion(reservacionDTO);

        } catch (RuntimeException ex) {
            throw new Exception("No se pudo completar la transacción: " + ex.getMessage());
        }
    }
}
