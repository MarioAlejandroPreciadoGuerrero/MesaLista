/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapper;

import DTO.ReservacionDTO;
import Interface.IMapper;
import java.time.ZoneId;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author USER
 */
public class ReservacionMapper implements IMapper<ReservacionDTO> {

    @Override
    public Document toDocument(ReservacionDTO dto) {
        if (dto == null) {
            return null;
        }
        Document doc = new Document("numPersonas", dto.getNumPersonas())
                .append("folio", dto.getFolio())
                .append("costo", dto.getCosto())
                .append("fechaHora", dto.getFechaHora())
                .append("areaNombre", dto.getAreaNombre())
                .append("numerosMesa", dto.getNumerosMesa());

        if (dto.getId() != null) {
            doc.append("_id", new ObjectId(dto.getId()));
        }
        if (dto.getUsuarioId() != null) {
            doc.append("usuarioId", new ObjectId(dto.getUsuarioId()));
        }
        if (dto.getRestauranteId() != null) {
            doc.append("restauranteId", new ObjectId(dto.getRestauranteId()));
        }

        return doc;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ReservacionDTO toDto(Document doc) {
        if (doc == null) {
            return null;
        }
        return new ReservacionDTO(
                doc.getObjectId("_id") != null ? doc.getObjectId("_id").toHexString() : null,
                doc.getInteger("numPersonas"),
                doc.getString("folio"),
                doc.getDouble("costo"),
                doc.getDate("fechaHora") != null ? doc.getDate("fechaHora").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() : null,
                doc.getObjectId("usuarioId") != null ? doc.getObjectId("usuarioId").toHexString() : null,
                doc.getObjectId("restauranteId") != null ? doc.getObjectId("restauranteId").toHexString() : null,
                doc.getString("areaNombre"),
                (List<Integer>) doc.get("numerosMesa")
        );
    }
}
