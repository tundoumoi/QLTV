package Service;

public interface Service<T> {
    T findById(String id);
    void insert(T entity);
    void delete(String id);
    void display(T entity);
}
