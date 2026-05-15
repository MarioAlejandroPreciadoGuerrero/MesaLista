/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import DAOs.ReservacionDAO;
import DTO.ReservacionDTO;
import Mapper.ReservacionMapper;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author USER
 */
public class ReservacionRepository {
    
    private final ReservacionDAO dao;
    private final ReservacionMapper mapper;

    public ReservacionRepository() {
        this.dao = new ReservacionDAO();
        this.mapper = new ReservacionMapper();
    }

    public void guardar(ReservacionDTO dto) {
        if (dto == null) return;
        Document doc = mapper.toDocument(dto);
        dao.insertar(doc);
        dto.setId(doc.getObjectId("_id").toHexString());
    }

    public ReservacionDTO buscarPorId(String id) {
        if (id == null || id.isEmpty()) return null;
        return mapper.toDto(dao.buscarPorId(new ObjectId(id)));
    }

    public java.util.List<ReservacionDTO> buscarPorUsuario(String usuarioId) {
        java.util.List<ReservacionDTO> dtos = new java.util.ArrayList<>();
        if (usuarioId == null || usuarioId.isEmpty()) return dtos;
        
        java.util.List<org.bson.Document> docs = dao.buscarPorUsuario(new org.bson.types.ObjectId(usuarioId));
        if (docs != null) {
            for (org.bson.Document doc : docs) {
                dtos.add(mapper.toDto(doc));
            }
        }
        return dtos;
    }
    
    public ReservacionDTO buscarPorFolio(String folio) {
        if (folio == null || folio.isEmpty()) return null;
        return mapper.toDto(dao.buscarPorFolio(folio));
    }

    public void actualizar(ReservacionDTO dto) {
        if (dto == null || dto.getId() == null) return;
        dao.actualizar(new ObjectId(dto.getId()), mapper.toDocument(dto));
    }

    public void eliminar(String id) {
        if (id != null && !id.isEmpty()) dao.eliminar(new ObjectId(id));
    }
    
}
