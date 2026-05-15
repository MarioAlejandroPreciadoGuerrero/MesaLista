/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Facade;

import DTO.UsuarioDTO;
import Interface.IUsuarioFacade;
import Repository.UsuarioRepository;

/**
 *
 * @author USER
 */
public class UsuarioFacade implements IUsuarioFacade {

    private final UsuarioRepository repository;

    public UsuarioFacade() {
        this.repository = new UsuarioRepository();
    }

    @Override
    public void registrarUsuario(UsuarioDTO usuario) {
        if (usuario != null) {
            repository.guardar(usuario);
        }
    }

    @Override
    public UsuarioDTO obtenerUsuario(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        return repository.buscarPorId(id);
    }

    @Override
    public void actualizarPerfil(UsuarioDTO usuario) {
        if (usuario != null && usuario.getId() != null) {
            repository.actualizar(usuario);
        }
    }

    @Override
    public void eliminarUsuario(String id) {
        if (id != null && !id.isEmpty()) {
            repository.eliminar(id);
        }
    }
}
