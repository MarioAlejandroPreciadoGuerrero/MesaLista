/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import org.bson.Document;

/**
 *
 * @author USER
 *
 * Interfaz genérica para todos los mappers de la aplicación.
 * @param <D> El tipo de Data Transfer Object (DTO)
 */
public interface IMapper<D> {

    /**
     * Convierte un DTO a un Documento BSON de MongoDB.
     * @param entity
     * @return 
     */
    Document toDocument(D entity);

    /**
     * Convierte un Documento BSON de MongoDB a un DTO.
     * @param doc
     * @return 
     */
    D toDto(Document doc);
}
