/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Pruebas;

import Conexion.Conexion;
import Entidades.Area;
import Entidades.Menu;
import Entidades.Mesa;
import Entidades.Platillo;
import Entidades.Reservacion;
import Entidades.Restaurante;
import Entidades.Usuario;
import Repository.ReservacionRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author USER
 */
public class pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("--- INICIANDO PRUEBAS DE MONGODB ---");

        Platillo platillo1 = new Platillo("Enchiladas Suizas", 120.50, "Enchiladas de pollo con salsa verde y queso gratinado");
        Platillo platillo2 = new Platillo("Tacos al Pastor", 95.00, "Orden de 5 tacos con piña y salsa");
        Menu menuRestaurante = new Menu(Arrays.asList(platillo1, platillo2));

        Mesa mesa1 = new Mesa(1, 4);
        Mesa mesa2 = new Mesa(2, 2);
        Mesa mesa3 = new Mesa(3, 6);

        Area areaTerraza = new Area("Terraza", 20, Arrays.asList(mesa1, mesa2));
        Area areaInterior = new Area("Interior Libre", 40, Arrays.asList(mesa3));

        ObjectId restauranteId = new ObjectId();
        Restaurante miRestaurante = new Restaurante(
                restauranteId,
                "La Casa del Sabor",
                60,
                Arrays.asList(areaTerraza, areaInterior),
                menuRestaurante
        );

        ObjectId usuarioId = new ObjectId();
        Usuario miUsuario = new Usuario(
                usuarioId,
                "Juan Pérez",
                LocalDate.of(1990, 5, 15),
                "juan.perez@email.com"
        );

        System.out.println("\n--- PROBANDO RESERVACION REPOSITORY ---");
        ReservacionRepository repoReservacion = new ReservacionRepository();

        String folioPrueba = "RES-" + System.currentTimeMillis(); 
        List<Integer> mesasReservadas = Arrays.asList(1, 2); 

        Reservacion nuevaReservacion = new Reservacion(
                null, 
                5, 
                folioPrueba,
                500.00, 
                LocalDateTime.now().plusDays(2), 
                miUsuario.getId(),
                miRestaurante.getId(),
                "Terraza",
                mesasReservadas
        );

        System.out.println("Guardando reservación con folio: " + folioPrueba);
        repoReservacion.guardar(nuevaReservacion);

        if (nuevaReservacion.getId() != null) {
            System.out.println("¡Reservación guardada exitosamente con el ID: " + nuevaReservacion.getId() + "!");
        } else {
            System.out.println("Error al guardar la reservación.");
        }

        System.out.println("\nBuscando la reservación en MongoDB por el folio: " + folioPrueba);
        Reservacion reservacionRecuperada = repoReservacion.buscarPorFolio(folioPrueba);

        if (reservacionRecuperada != null) {
            System.out.println("¡Reservación encontrada!");
            System.out.println("  - ID: " + reservacionRecuperada.getId());
            System.out.println("  - Personas: " + reservacionRecuperada.getNumPersonas());
            System.out.println("  - Área: " + reservacionRecuperada.getAreaNombre());
            System.out.println("  - Mesas reservadas: " + reservacionRecuperada.getNumerosMesa());
            System.out.println("  - Fecha/Hora: " + reservacionRecuperada.getFechaHora());
        } else {
            System.out.println("No se encontró la reservación.");
        }

        System.out.println("\nCerrando conexión a la base de datos...");
        Conexion.getInstancia().cerrarConexion();
        System.out.println("Proceso finalizado.");
    }
}


