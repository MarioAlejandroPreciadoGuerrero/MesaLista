/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import DTO.UsuarioDTO;

/**
 *
 * @author USER
 */
public interface IUsuarioFacade {

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param usuario DTO con la información del usuario.
     */
    void registrarUsuario(UsuarioDTO usuario);

    /**
     * Obtiene la información de un usuario específico.
     *
     * @param id Identificador del usuario en formato String.
     * @return DTO del usuario encontrado o null.
     */
    UsuarioDTO obtenerUsuario(String id);

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param usuario DTO con los datos actualizados.
     */
    void actualizarPerfil(UsuarioDTO usuario);

    /**
     * Da de baja a un usuario del sistema.
     *
     * @param id Identificador del usuario.
     */
    void eliminarUsuario(String id);
}
