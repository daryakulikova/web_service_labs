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

    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from persons");

            persons = rsToList(rs);
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return persons;
    }

    public List<Person> getPersons(String name, String surname, Integer age, String sex, String position) {
        List<Person> persons = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from persons p " +
                    "where (" + enquote(name) + " is NULL or " + enquote(name) + " =p.name) AND " +
                    "(" + enquote(surname) + " is NULL or " + enquote(surname) + " =p.surname) AND " +
                    "(" + age + " is NULL or " + age + " =p.age) AND " +
                    "(" + enquote(sex) + " is NULL or " + enquote(sex) + " =p.sex) AND " +
                    "(" + enquote(position) + " is NULL or " + enquote(position) + " =p.position)");

            persons = rsToList(rs);
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return persons;
    }

    public long addPerson(String name, String surname, Integer age, String sex, String position) {
        long id = 0;
        try (Connection connection = ConnectionUtil.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "INSERT INTO persons (name, surname, age, phone, gender) " +
                            "VALUES (" + enquote(name) + ", " + enquote(surname) + ", " + age + ", " +
                            enquote(sex) + ", " + enquote(position) + ") RETURNING id");
            if (rs.next()) {
                id = rs.getLong("id");
            }

        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id;
    }

    public int updatePerson(Long id, String name, String surname, Integer age, String sex, String position) {
        int status = 500;
        try (Connection connection = ConnectionUtil.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "UPDATE persons SET name = " + enquote(name) + ", surname = " + enquote(surname) +
                            ", age = " + age + ", phone = " + enquote(sex) + ", gender = " + enquote(position) +
                            " WHERE id = " + id + " RETURNING id");
            if (rs.next()) {
                Long returnId = rs.getLong("id");
                if (returnId.equals(id))
                    status = 200;
            }

        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return status;
    }

    public int deletePerson(Long id) {
        int status = 500;
        try (Connection connection = ConnectionUtil.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("DELETE FROM persons WHERE id = " + id + " RETURNING id");
            if (rs.next()) {
                Long returnId = rs.getLong("id");
                if (returnId.equals(id))
                    status = 200;
            }

        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQLDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return status;
    }

    private List<Person> rsToList(ResultSet rs) throws SQLException {
        List<Person> persons = new ArrayList<>();
        while (rs.next()) {
            String resName = rs.getString("name");
            String resSurname = rs.getString("surname");
            int resAge = rs.getInt("age");
            String resSex = rs.getString("sex");
            String resCity = rs.getString("position");

            Person person = new Person(resName, resSurname, resAge, resSex, resCity);
            persons.add(person);
        }
        return persons;
    }

    private String enquote(String o) {
        return o == null ? o : ("'" + o + "'");
    }
}
