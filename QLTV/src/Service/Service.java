package Service;

public interface Service<T> {
    T findById(String id);
    T insert(T entity);
    void delete(String id);
    void display(T entity);
}
