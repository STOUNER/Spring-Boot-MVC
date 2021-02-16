package application.dao;

public interface UserCustomCrudRepository<T> {
    public T getUserByName(String login);
}
