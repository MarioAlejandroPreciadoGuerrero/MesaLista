/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapper;

import DTO.VentaDTO;
import Interface.IMapper;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author USER
 */
public class VentaMapper implements IMapper<VentaDTO> {

    @Override
    public Document toDocument(VentaDTO dto) {
        if (dto == null) {
            return null;
        }
        Document doc = new Document("montoTotal", dto.getMontoTotal())
                .append("transaccionExternaId", dto.getTransaccionExternaId()); // Mapeamos el nuevo campo
        if (dto.getId() != null) {
            doc.append("_id", new ObjectId(dto.getId()));
        }
        if (dto.getReservacionId() != null) {
            doc.append("reservacionId", new ObjectId(dto.getReservacionId()));
        }
        if (dto.getUsuarioId() != null) {
            doc.append("usuarioId", new ObjectId(dto.getUsuarioId()));
        }
        return doc;
    }

    @Override
    public VentaDTO toDto(Document doc) {
        if (doc == null) {
            return null;
        }
        return new VentaDTO(
                doc.getObjectId("_id") != null ? doc.getObjectId("_id").toHexString() : null,
                doc.getDouble("montoTotal"),
                doc.getObjectId("reservacionId") != null ? doc.getObjectId("reservacionId").toHexString() : null,
                doc.getObjectId("usuarioId") != null ? doc.getObjectId("usuarioId").toHexString() : null,
                doc.getString("transaccionExternaId") // Recuperamos el nuevo campo
        );
    }
}
