/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

/**
 *
 * @author LENOVO Ideapad 3
 */
public interface GenericDAO2<T, C> {
    // Lấy tất cả các bản ghi từ bảng
    C getAll();

    // Tìm một bản ghi theo ID
    T getById(String id);

    // Thêm một bản ghi mới
    void insert(T entity);

    // Cập nhật một bản ghi
    void update(T entity);

    // Xóa một bản ghi theo ID
    void delete(String id);
}
