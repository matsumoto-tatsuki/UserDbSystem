package com.example.usermanagementsystem;

import javafx.collections.ObservableList;

public class UserService {
    private UserDao userDao;

    public UserService() {
        var connection = DbUtil.getConnection();
        userDao = new UserDao(connection);
    }

    public ObservableList<User> findAllData(int offset){
        return userDao.findAllData(offset);
    }


    public int insertUser(User user){
        return userDao.insertUser(user);
    }

    public int deleteUser(int row, int offset){
        return userDao.deleteUser(row,offset);
    }

    public int updateUser(User user,int row,int offset){
        return userDao.updateUser(user,row,offset);
    }

    public ObservableList<User> searchUser(String str){
        return userDao.searchUser(str);
    }

    public void userServiceClose(){
        userDao.userServiceClose();
    }
}
