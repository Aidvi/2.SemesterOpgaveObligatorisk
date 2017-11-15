package dk.models.repositories;

import dk.models.entities.User;

import java.util.ArrayList;

public interface IUserRepository {

    public void create(User st);

    public void update(User user);

    public void delete(int id);

    public User login(User user);
}
