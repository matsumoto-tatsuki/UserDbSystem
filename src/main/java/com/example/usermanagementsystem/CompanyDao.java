package com.example.usermanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyDao {
    private Connection connection;

    public CompanyDao(Connection connection) {
        this.connection = connection;
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

    public int insertCompany(Company company){
        final var SQL
                = "INSERT INTO companies(name) VALUES(?)";
        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, company.getName());
            var rs = stmt.executeUpdate();

            return rs;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteCompany(int row){
        final var SQL
                = "DELETE FROM companies WHERE id = (SELECT id FROM companies LIMIT 1 OFFSET ?)";

        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setInt(1, row);
            var rs = stmt.executeUpdate();

            return rs;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int updateCompany(Company company,int row){
        final var SQL
                = "UPDATE companies SET name = ? WHERE id = (SELECT id FROM companies LIMIT 1 OFFSET ?)";

        try (PreparedStatement stmt = connection.prepareStatement(SQL)) {
            stmt.setString(1, company.getName());
            stmt.setInt(2, row);
            var rs = stmt.executeUpdate();

            return rs;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
