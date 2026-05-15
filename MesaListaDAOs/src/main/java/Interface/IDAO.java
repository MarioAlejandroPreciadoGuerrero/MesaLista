/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

/**
 *
 * @author USER
 * Interfaz genérica que define el contrato estándar para las operaciones CRUD en base de datos.
 * @param <T> El tipo de entidad o documento que maneja el DAO (ej. Document).
 * @param <K> El tipo de dato del identificador principal (ej. ObjectId).
 */
public interface IDAO<T, K> {

    void insertar(T entidad);

    T buscarPorId(K id);

    void actualizar(K id, T entidad);

    void eliminar(K id);
}
