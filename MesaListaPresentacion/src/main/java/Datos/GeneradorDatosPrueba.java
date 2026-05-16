/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import DTO.AreaDTO;
import DTO.MenuDTO;
import DTO.MesaDTO;
import DTO.PlatilloDTO;
import DTO.RestauranteDTO;
import DTO.UsuarioDTO;
import Facade.RestaurantesFacade;
import Facade.UsuarioFacade;
import Interface.IRestaurantesFacade;
import Interface.IUsuarioFacade;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class GeneradorDatosPrueba {

    public static void inicializarDatos() {
        IUsuarioFacade usuarioFacade = new UsuarioFacade();
        if (usuarioFacade.obtenerUsuarioPorEmail("admin@mesalista.com") == null) {
            UsuarioDTO admin = new UsuarioDTO();
            admin.setNombre("Admin");
            admin.setEmail("admin@mesalista.com");
            admin.setContrasena("12345");
            admin.setFechaNacimiento(LocalDate.of(2000, 1, 1));
            usuarioFacade.registrarUsuario(admin);
        }

        IRestaurantesFacade fachada = new RestaurantesFacade();

        List<RestauranteDTO> existentes = fachada.obtenerTodosLosRestaurantes();
        if (existentes != null && !existentes.isEmpty()) {
            return;
        }

        RestauranteDTO rest1 = new RestauranteDTO();
        rest1.setNombre("Rincon el Asador");
        rest1.setCapacidadTotal(80);

        List<AreaDTO> areas1 = new ArrayList<>();
        List<MesaDTO> mesasTerraza1 = new ArrayList<>();
        mesasTerraza1.add(new MesaDTO(1, 4));
        mesasTerraza1.add(new MesaDTO(2, 4));
        mesasTerraza1.add(new MesaDTO(3, 6));
        areas1.add(new AreaDTO("Terraza", 14, mesasTerraza1));

        List<MesaDTO> mesasInterior1 = new ArrayList<>();
        mesasInterior1.add(new MesaDTO(4, 2));
        mesasInterior1.add(new MesaDTO(5, 2));
        mesasInterior1.add(new MesaDTO(6, 8));
        areas1.add(new AreaDTO("Interior", 12, mesasInterior1));

        rest1.setAreas(areas1);

        List<PlatilloDTO> platillos1 = new ArrayList<>();
        platillos1.add(new PlatilloDTO("Corte Ribeye 400g", 350.0, "Corte calidad Angus con guarnicion"));
        platillos1.add(new PlatilloDTO("Papas al Horno", 90.0, "Papas con crema, tocino y cebollin"));
        platillos1.add(new PlatilloDTO("Vino Tinto Casa Madero", 450.0, "Botella de 750ml"));
        rest1.setMenu(new MenuDTO(platillos1));

        fachada.registrarRestaurante(rest1);

        RestauranteDTO rest2 = new RestauranteDTO();
        rest2.setNombre("El Deshuesadero");
        rest2.setCapacidadTotal(100);

        List<AreaDTO> areas2 = new ArrayList<>();
        List<MesaDTO> mesasGral2 = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            mesasGral2.add(new MesaDTO(i, 4));
        }
        areas2.add(new AreaDTO("General", 40, mesasGral2));
        rest2.setAreas(areas2);

        List<PlatilloDTO> platillos2 = new ArrayList<>();
        platillos2.add(new PlatilloDTO("Doble Smash Burger", 120.0, "Doble carne, queso cheddar, tocino"));
        platillos2.add(new PlatilloDTO("Clasica BBQ", 110.0, "Carne de res con salsa BBQ artesanal"));
        platillos2.add(new PlatilloDTO("Pollo Crispy", 95.0, "Pechuga de pollo empanizada crujiente"));
        rest2.setMenu(new MenuDTO(platillos2));

        fachada.registrarRestaurante(rest2);

        RestauranteDTO rest3 = new RestauranteDTO();
        rest3.setNombre("Mariscos el Rey");
        rest3.setCapacidadTotal(120);

        List<AreaDTO> areas3 = new ArrayList<>();
        List<MesaDTO> mesasPalapa3 = new ArrayList<>();
        mesasPalapa3.add(new MesaDTO(1, 10));
        mesasPalapa3.add(new MesaDTO(2, 6));
        areas3.add(new AreaDTO("Palapa", 16, mesasPalapa3));
        rest3.setAreas(areas3);

        List<PlatilloDTO> platillos3 = new ArrayList<>();
        platillos3.add(new PlatilloDTO("Tostada de Ceviche", 60.0, "Ceviche de pescado fresco"));
        platillos3.add(new PlatilloDTO("Aguachile Verde", 180.0, "Camaron curtido en limon y chile serrano"));
        platillos3.add(new PlatilloDTO("Tacos Gobernador", 150.0, "3 tacos de camaron con costra de queso"));
        rest3.setMenu(new MenuDTO(platillos3));

        fachada.registrarRestaurante(rest3);
    }

}
