package service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostgreSQLDAO {

    private Connection connection;

    public PostgreSQLDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Person> getPersons(String name, String surname, Integer age, String sex, String position) {
        List<Person> persons = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from persons p " +
                    "where (" + enquote(name) + " is NULL or " + enquote(name) + " =p.name) AND " +
                    "(" + enquote(surname) + " is NULL or " + enquote(surname) + " =p.surname) AND " +
                    "(" + age + " is NULL or " + age + " =p.age) AND " +
                    "(" + enquote(sex) + " is NULL or " + enquote(sex) + " =p.sex) AND " +
                    "(" + enquote(position) + " is NULL or " + enquote(position) + " =p.position)");

            while (rs.next()) {
                String resName = rs.getString("name");
                String resSurname = rs.getString("surname");
                int resAge = rs.getInt("age");
                String resSex = rs.getString("sex");
                String resPosition = rs.getString("position");

                Person person = new Person(resName, resSurname, resAge, resSex, resPosition);
                persons.add(person);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return persons;
    }

    private String enquote(String o) {
        return o == null ? o : ("'" + o + "'");
    }
}
