package Pruebas;

import Entidades.*;
import Pantallas.Navegador;
import Pantallas.PantallaListaRestaurantes;
import org.bson.types.ObjectId;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Punto de entrada de la aplicación.
 * Construye datos de ejemplo y lanza PantallaListaRestaurantes.
 * En producción los datos vendrían de MongoDB vía repositorios.
 */
public class pruebas {

    public static void main(String[] args) {

        // Usuario de ejemplo
        Usuario usuario = new Usuario(
            new ObjectId(),
            "Carlos G",
            LocalDate.of(1995, 5, 20),
            "carlos@email.com"
        );

        // Platillos
        Platillo tacos    = new Platillo("Tacos de Asada",    89.0,  "Tacos con carne asada");
        Platillo burger   = new Platillo("Smash Burger",      129.0, "Hamburguesa doble con queso");
        Platillo caldo    = new Platillo("Caldo de Res",       95.0, "Caldo tradicional");
        Platillo mariscos = new Platillo("Ceviche Tostada",   110.0, "Ceviche fresco");
        Platillo aguachile= new Platillo("Aguachile Verde",   115.0, "Camarones en salsa verde");
        Platillo pozole   = new Platillo("Pozole Rojo",        99.0, "Pozole estilo Sinaloa");
        Platillo enchil   = new Platillo("Enchiladas Suizas",  85.0, "Con crema y queso");

        // Areas
        Area terraza  = new Area("Terraza",  20, Arrays.asList(new Mesa(1,4), new Mesa(2,6)));
        Area interior = new Area("Interior", 40, Arrays.asList(new Mesa(3,4), new Mesa(4,8)));
        Area vip      = new Area("VIP",      10, Arrays.asList(new Mesa(5,10)));

        // Restaurantes
        Restaurante r1 = new Restaurante(new ObjectId(), "Rincon el Asador", 80,
            Arrays.asList(terraza, interior, vip),
            new Menu(Arrays.asList(tacos, burger, caldo, enchil)));

        Restaurante r2 = new Restaurante(new ObjectId(), "El Deshuesadero", 60,
            Arrays.asList(interior, terraza),
            new Menu(Arrays.asList(mariscos, aguachile, caldo)));

        Restaurante r3 = new Restaurante(new ObjectId(), "Mariscos el Rey", 8,
            Arrays.asList(vip),
            new Menu(Arrays.asList(pozole, caldo, mariscos)));

        List<Restaurante> restaurantes = Arrays.asList(r1, r2, r3);

        // Lanzar interfaz
        SwingUtilities.invokeLater(() ->
            Navegador.ir(new PantallaListaRestaurantes(usuario, restaurantes))
        );
    }
}
