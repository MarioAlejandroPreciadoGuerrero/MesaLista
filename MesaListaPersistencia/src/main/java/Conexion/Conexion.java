/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class Conexion {

    private static Conexion instancia;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private static final String DATABASE_NAME = "mesaLista";
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";

    /**
     * Constructor privado para evitar la instanciación directa.
     * Inicializa la conexión a MongoDB y selecciona la base de datos.
     */
    private Conexion() {
        try {
            Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
            mongoLogger.setLevel(Level.SEVERE);
            
            this.mongoClient = MongoClients.create(CONNECTION_STRING);
            this.database = mongoClient.getDatabase(DATABASE_NAME);
            System.out.println("Conexión exitosa a la base de datos: " + DATABASE_NAME);
        } catch (Exception e) {
            System.err.println("Error al conectar con MongoDB: " + e.getMessage());
        }
    }

    /**
     * Obtiene la única instancia de la clase Conexion (Singleton).
     * 
     * @return La instancia única de Conexion.
     */
    public static Conexion getInstancia() {
        if (instancia == null) instancia = new Conexion();
        return instancia;
    }

    /**
     * Retorna la base de datos a la que se ha conectado.
     * 
     * @return Objeto MongoDatabase correspondiente a "mesaLista".
     */
    public MongoDatabase getDatabase() {
        return this.database;
    }

    /**
     * Cierra la conexión con el servidor de MongoDB.
     */
    public void cerrarConexion() {
        if (this.mongoClient != null) this.mongoClient.close();
    }
}
