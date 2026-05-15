/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import DTO.RestauranteDTO;
import java.util.List;

/**
 *
 * @author USER
 */
public interface IRestaurantesFacade {

    /**
     * Da de alta un nuevo restaurante con todas sus áreas y menú.
     *
     * @param restaurante DTO con la información completa.
     */
    void registrarRestaurante(RestauranteDTO restaurante);

    /**
     * Consulta la información completa de un restaurante.
     *
     * @param id Identificador del restaurante.
     * @return DTO del restaurante o null.
     */
    RestauranteDTO consultarRestaurante(String id);

    List<RestauranteDTO> obtenerTodosLosRestaurantes();

    /**
     * Actualiza la información general, menú o áreas de un restaurante.
     *
     * @param restaurante DTO con los cambios.
     */
    void actualizarRestaurante(RestauranteDTO restaurante);

    /**
     * Elimina un restaurante del sistema.
     *
     * @param id Identificador del restaurante.
     */
    void eliminarRestaurante(String id);

}
