package com.example.usermanagementsystem;

import javafx.collections.ObservableList;

public class UserService {
    private UserDao userDao;

    public UserService() {
        var connection = DbUtil.getConnection();
        userDao = new UserDao(connection);
    }

    public ObservableList<User> findAllData(){
        return userDao.findAllData();
    }

    public ObservableList<String> findCompaniesData(){
        return userDao.findCompaniesData();
    }

    public ObservableList<Company> findCompaniesAllData(){
        return userDao.findCompaniesAllData();
    }

    public int insertUser(User user){
        return userDao.insertUser(user);
    }

    public int deleteUser(int row){
        return userDao.deleteUser(row);
    }

    public int updateUser(User user,int row){
        return userDao.updateUser(user,row);
    }

    public ObservableList<User> searchUser(String str){
        return userDao.searchUser(str);
    }
}
