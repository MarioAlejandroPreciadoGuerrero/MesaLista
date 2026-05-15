/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Facade;

import DTO.RestauranteDTO;
import Interface.IRestaurantesFacade;
import Repository.RestauranteRepository;
import java.util.List;

/**
 *
 * @author USER
 */
public class RestaurantesFacade implements IRestaurantesFacade {

    private final RestauranteRepository repository;

    public RestaurantesFacade() {
        this.repository = new RestauranteRepository();
    }

    @Override
    public void registrarRestaurante(RestauranteDTO restaurante) {
        if (restaurante != null) {
            repository.guardar(restaurante);
        }
    }

    @Override
    public RestauranteDTO consultarRestaurante(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        return repository.buscarPorId(id);
    }

    @Override
    public List<RestauranteDTO> obtenerTodosLosRestaurantes() {
        return repository.obtenerTodos();
    }

    @Override
    public void actualizarRestaurante(RestauranteDTO restaurante) {
        if (restaurante != null && restaurante.getId() != null) {
            repository.actualizar(restaurante);
        }
    }

    @Override
    public void eliminarRestaurante(String id) {
        if (id != null && !id.isEmpty()) {
            repository.eliminar(id);
        }
    }
}
