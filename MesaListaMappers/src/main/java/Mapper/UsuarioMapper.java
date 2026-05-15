/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mapper;

import DTO.UsuarioDTO;
import Interface.IMapper;
import java.time.ZoneId;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author USER
 */
public class UsuarioMapper implements IMapper<UsuarioDTO> {

    @Override
    public Document toDocument(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }
        Document doc = new Document("nombre", dto.getNombre())
                .append("fechaNacimiento", dto.getFechaNacimiento())
                .append("email", dto.getEmail());
        if (dto.getId() != null) {
            doc.append("_id", new ObjectId(dto.getId()));
        }
        return doc;
    }

    @Override
    public UsuarioDTO toDto(Document doc) {
        if (doc == null) {
            return null;
        }
        return new UsuarioDTO(
                doc.getObjectId("_id") != null ? doc.getObjectId("_id").toHexString() : null,
                doc.getString("nombre"),
                doc.getDate("fechaNacimiento") != null ? doc.getDate("fechaNacimiento").toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null,
                doc.getString("email")
        );
    }
}
