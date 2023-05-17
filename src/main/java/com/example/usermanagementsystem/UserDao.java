package com.example.usermanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public ObservableList<User> findAllData(){
        final var SQL = "SELECT u.id user_id, c.name  company_name, u.name user_name, u.score FROM users u JOIN companies c ON u.company_id = c.id";

        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            ResultSet rs = stmt.executeQuery();
            ObservableList<User> list = FXCollections.observableArrayList();
            while (rs.next()) {
                var user = new User(rs.getInt("user_id"), rs.getString("company_name"),rs.getString("user_name"), rs.getInt("score"));
                list.add(user);
            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<String> findCompaniesData(){
        final var SQL = "SELECT name FROM companies";

        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            ResultSet rs = stmt.executeQuery();
            ObservableList<String> list = FXCollections.observableArrayList();
            while (rs.next()) {
                list.add(rs.getString("name"));
            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ObservableList<Company> findCompaniesAllData(){
        final var SQL = "SELECT id, name FROM companies";

        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            ResultSet rs = stmt.executeQuery();
            ObservableList<Company> list = FXCollections.observableArrayList();
            while (rs.next()) {
                list.add(new Company(rs.getInt("id"),rs.getString("name")));
            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insertUser(User user){
        final var SQL
                = "INSERT INTO users(name,company_id,score) VALUES(?,(SELECT id FROM companies WHERE name = ?),?)";

        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getCompany());
            stmt.setInt(3, user.getScore());
            var rs = stmt.executeUpdate();

            return rs;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteUser(int row){
        final var SQL
                = "DELETE FROM users WHERE id = (SELECT id FROM users LIMIT 1 OFFSET ?)";

        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setInt(1, row);
            var rs = stmt.executeUpdate();

            return rs;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateUser(User user,int row){
        final var SQL
                = "UPDATE users SET name = ?,company_id = (SELECT id FROM companies WHERE name = ?),score = ? WHERE id = (SELECT id FROM users LIMIT 1 OFFSET ?)";

        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getCompany());
            stmt.setInt(3, user.getScore());
            stmt.setInt(4, row);
            var rs = stmt.executeUpdate();

            return rs;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<User> searchUser(String str){
        final var SQL
                = "SELECT u.id user_id, c.name  company_name, u.name user_name, u.score FROM users u JOIN companies c ON u.company_id = c.id WHERE u.name LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {

            stmt.setString(1, str);
            ResultSet rs = stmt.executeQuery();

            ObservableList<User> list = FXCollections.observableArrayList();
            while (rs.next()) {
                var user = new User(rs.getInt("user_id"), rs.getString("company_name"),rs.getString("user_name"), rs.getInt("score"));
                list.add(user);
            }
            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
