/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import Conexion.Conexion;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import Entidades.Reservacion;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.time.ZoneId;
import java.util.List;

/**
 * @author USER
 * Implementación del patrón Repository exclusivamente para la clase Reservacion.
 * Abstrae la lógica de acceso a la base de datos haciendo uso de nuestra clase Singleton de Conexion.
 */
public class ReservacionRepository {

    private final MongoCollection<Document> collection;

    /**
     * Inicializa el repositorio conectándose a la colección "reservaciones"
     * usando la clase Conexion provista anteriormente.
     */
    public ReservacionRepository() {
        this.collection = Conexion.getInstancia().getDatabase().getCollection("reservaciones");
    }

    /**
     * Guarda una nueva reservación en la base de datos de MongoDB.
     * * @param reservacion Objeto Reservacion a persistir.
     */
    public void guardar(Reservacion reservacion) {
        if (reservacion == null) return;
        
        Document doc = new Document("numPersonas", reservacion.getNumPersonas())
                .append("folio", reservacion.getFolio())
                .append("costo", reservacion.getCosto())
                .append("fechaHora", reservacion.getFechaHora())
                .append("usuarioId", reservacion.getUsuarioId())
                .append("restauranteId", reservacion.getRestauranteId())
                .append("areaNombre", reservacion.getAreaNombre())
                .append("numerosMesa", reservacion.getNumerosMesa());

        collection.insertOne(doc);
        reservacion.setId(doc.getObjectId("_id"));
    }

    /**
     * Busca una reservación mediante su identificador único Folio.
     * * @param folio El folio generado en formato String.
     * @return El objeto Reservacion si se encuentra, de lo contrario null.
     */
    @SuppressWarnings("unchecked") // Agregamos esto para quitar la advertencia del cast
    public Reservacion buscarPorFolio(String folio) {
        if (folio == null || folio.isEmpty()) return null;
        
        Document doc = collection.find(Filters.eq("folio", folio)).first();
        if (doc == null) return null;

        List<Integer> numerosMesaList = (List<Integer>) doc.get("numerosMesa");

        return new Reservacion(
                doc.getObjectId("_id"),
                doc.getInteger("numPersonas"),
                doc.getString("folio"),
                doc.getDouble("costo"),
                doc.getDate("fechaHora").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                doc.getObjectId("usuarioId"),
                doc.getObjectId("restauranteId"),
                doc.getString("areaNombre"),
                numerosMesaList // Pasamos la lista casteada
        );
    }
    
    /**
     * Elimina una reservacion basada en su ID de Mongo.
     * * @param id ObjectId de la reservacion.
     */
    public void eliminar(ObjectId id) {
        if (id != null) collection.deleteOne(Filters.eq("_id", id));
    }
}
