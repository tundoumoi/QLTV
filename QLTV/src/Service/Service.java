/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

/**
 *
 * @author NHAT NAM
 */
public interface Service<T> {

    T findById(String id);

    T insert();

    void delete(String id);
    
    void display(T entity);

}
