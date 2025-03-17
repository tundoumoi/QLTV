package DAO;

public interface GenericDAO<T, C> {
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
