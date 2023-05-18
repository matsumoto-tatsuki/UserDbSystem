package com.example.usermanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class UserDao {
    private Connection connection;

    private String searchStr;
    private boolean selectFlag = true;

    ResultSet searchResult;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public ObservableList<User> findAllData(int offset){
        final var SQL = "SELECT u.id user_id, c.name  company_name, u.name user_name, u.score FROM users u JOIN companies c ON u.company_id = c.id ORDER BY user_id LIMIT 50 OFFSET ?";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setInt(1,offset);
            ResultSet rs = stmt.executeQuery();
            ObservableList<User> list = FXCollections.observableArrayList();
            while (rs.next()) {
                var user = new User(rs.getInt("user_id"), rs.getString("company_name"),rs.getString("user_name"), rs.getInt("score"));
                list.add(user);
            }
            selectFlag = true;
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

    public int deleteUser(int row,int offset){
        final String SQL;
        if(selectFlag){
            SQL = "DELETE FROM users WHERE id = (SELECT id FROM users ORDER BY id LIMIT 1 OFFSET ?)";
        }else{
            SQL = "DELETE FROM user WHERE id = (SELECT id FROM ("+ searchStr +") s ORDER BY id LIMIT 1 OFFSET ?)";
        }

        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setInt(1, (row + offset));
            var rs = stmt.executeUpdate();

            return rs;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public int updateUser(User user,int row,int offset){
        final String SQL;
        if(selectFlag){
            SQL = "UPDATE users SET name = ?,company_id = (SELECT id FROM companies WHERE name = ?),score = ? WHERE id = (SELECT id FROM users ORDER BY id LIMIT 1 OFFSET ?)";
        }else{
            SQL = "UPDATE users SET name = ?,company_id = (SELECT id FROM companies WHERE name = ?),score = ? WHERE id = (SELECT user_id FROM ("+ searchStr + ") s ORDER BY user_id LIMIT 1 OFFSET ?)";
        }
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getCompany());
            stmt.setInt(3, user.getScore());
            stmt.setInt(4, (row + offset));
            var rs = stmt.executeUpdate();

            return rs;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<User> searchUser(String str){
        final var SQL
                = "SELECT u.id user_id, c.name  company_name, u.name user_name, u.score FROM users u JOIN companies c ON u.company_id = c.id WHERE u.name LIKE ? ORDER BY u.id";

        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {

            stmt.setString(1, str);
            searchStr = String.valueOf(stmt);

            ResultSet rs = stmt.executeQuery();


            ObservableList<User> list = FXCollections.observableArrayList();
            while (rs.next()) {
                var user = new User(rs.getInt("user_id"), rs.getString("company_name"),rs.getString("user_name"), rs.getInt("score"));
                list.add(user);
            }
            selectFlag = false;
            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void userServiceClose(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
