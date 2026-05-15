/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import DAOs.UsuarioDAO;
import DTO.UsuarioDTO;
import Mapper.UsuarioMapper;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author USER
 */
public class UsuarioRepository {

    private final UsuarioDAO dao;
    private final UsuarioMapper mapper;

    public UsuarioRepository() {
        this.dao = new UsuarioDAO();
        this.mapper = new UsuarioMapper();
    }

    public void guardar(UsuarioDTO dto) {
        if (dto == null) {
            return;
        }
        Document doc = mapper.toDocument(dto);
        dao.insertar(doc);
        dto.setId(doc.getObjectId("_id").toHexString());
    }

    public UsuarioDTO buscarPorId(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        return mapper.toDto(dao.buscarPorId(new ObjectId(id)));
    }

    public UsuarioDTO buscarPorEmail(String email) {
        if (email == null || email.isEmpty()) {
            return null;
        }
        return mapper.toDto(dao.buscarPorEmail(email));
    }

    public void actualizar(UsuarioDTO dto) {
        if (dto == null || dto.getId() == null) {
            return;
        }
        dao.actualizar(new ObjectId(dto.getId()), mapper.toDocument(dto));
    }

    public void eliminar(String id) {
        if (id != null && !id.isEmpty()) {
            dao.eliminar(new ObjectId(id));
        }
    }
}
