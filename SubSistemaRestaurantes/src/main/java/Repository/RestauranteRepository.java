/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import DAOs.RestauranteDAO;
import DTO.RestauranteDTO;
import Mapper.RestauranteMapper;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author USER
 */
public class RestauranteRepository {

    private final RestauranteDAO dao;
    private final RestauranteMapper mapper;

    public RestauranteRepository() {
        this.dao = new RestauranteDAO();
        this.mapper = new RestauranteMapper();
    }

    public void guardar(RestauranteDTO dto) {
        if (dto == null) {
            return;
        }
        Document doc = mapper.toDocument(dto);
        dao.insertar(doc);
        dto.setId(doc.getObjectId("_id").toHexString());
    }

    public RestauranteDTO buscarPorId(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        return mapper.toDto(dao.buscarPorId(new ObjectId(id)));
    }

    public List<RestauranteDTO> obtenerTodos() {
        List<Document> documentos = dao.obtenerTodos();
        List<RestauranteDTO> dtos = new ArrayList<>();
        if (documentos != null) {
            for (Document doc : documentos) {
                dtos.add(mapper.toDto(doc));
            }
        }
        return dtos;
    }

    public void actualizar(RestauranteDTO dto) {
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
