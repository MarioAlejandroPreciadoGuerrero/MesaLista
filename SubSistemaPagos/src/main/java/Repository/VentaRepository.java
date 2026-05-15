/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;


import DAOs.VentaDAO;
import DTO.VentaDTO;
import Mapper.VentaMapper;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author USER
 */
public class VentaRepository {

    private final VentaDAO dao;
    private final VentaMapper mapper;

    public VentaRepository() {
        this.dao = new VentaDAO();
        this.mapper = new VentaMapper();
    }

    public void guardar(VentaDTO dto) {
        if (dto == null) {
            return;
        }
        Document doc = mapper.toDocument(dto);
        dao.insertar(doc);
        dto.setId(doc.getObjectId("_id").toHexString());
    }

    public VentaDTO buscarPorId(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        return mapper.toDto(dao.buscarPorId(new ObjectId(id)));
    }

    public void actualizar(VentaDTO dto) {
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
