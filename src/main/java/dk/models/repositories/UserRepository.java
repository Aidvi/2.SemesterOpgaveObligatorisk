package dk.models.repositories;

import dk.models.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import sun.swing.StringUIClientPropertyKey;
import java.util.ArrayList;

@Repository
public class UserRepository implements IUserRepository {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public void create(User user) {
        String query = "INSERT INTO user(name, email, password) VALUES('" + user.getName() + "', '" + user.getEmail() + "', '" + user.getPassword() + "') ";

        jdbc.update(query);
    }
    @Override
    public User login(User user) {
        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM user WHERE name ='" + user.getName() + "' AND password =  '"+ user.getPassword() + "'");
        User currentUser = new User();
         if(sqlRowSet.next()) {
             String name = sqlRowSet.getString("name");
             String password = sqlRowSet.getString("password");
             String email = sqlRowSet.getString("email");
             int userId = sqlRowSet.getInt("user_id");


             currentUser.setName(name);
             currentUser.setPassword(password);
             currentUser.setEmail(email);
             currentUser.setUserId(userId);

         }
        return currentUser;
    }
    @Override
    public void update(User user) {

        jdbc.update("Update user SET password = '" + user.getPassword() + "' WHERE user_id = '" + user.getUserId() + "'");
    }

    @Override
    public void delete(int id) {
        jdbc.update("DELETE FROM user WHERE user_id = " + id);
    }
}











