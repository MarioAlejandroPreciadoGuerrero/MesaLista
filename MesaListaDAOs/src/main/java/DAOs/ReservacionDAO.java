/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Conexion.Conexion;
import Interface.IDAO;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author USER
 */
public class ReservacionDAO implements IDAO<Document, ObjectId> {

    private final MongoCollection<Document> collection;

    public ReservacionDAO() {
        this.collection = Conexion.getInstancia().getDatabase().getCollection("reservaciones");
    }

    @Override
    public void insertar(Document documento) {
        if (documento != null) {
            collection.insertOne(documento);
        }
    }

    @Override
    public Document buscarPorId(ObjectId id) {
        if (id == null) {
            return null;
        }
        return collection.find(Filters.eq("_id", id)).first();
    }

    public Document buscarPorFolio(String folio) {
        if (folio == null || folio.isEmpty()) {
            return null;
        }
        return collection.find(Filters.eq("folio", folio)).first();
    }

    @Override
    public void actualizar(ObjectId id, Document documento) {
        if (id != null && documento != null) {
            collection.updateOne(Filters.eq("_id", id), new Document("$set", documento));
        }
    }

    @Override
    public void eliminar(ObjectId id) {
        if (id != null) {
            collection.deleteOne(Filters.eq("_id", id));
        }
    }
}
