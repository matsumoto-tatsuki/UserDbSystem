package com.example.usermanagementsystem;

public class User {
    private int id;
    private String company;
    private String name;
    private int score;

    public User(String company,String name,int score){
        this.company = company;
        this.name = name;
        this.score = score;
    }
    public User(int id,String company,String name,int score){
        this.id = id;
        this.company = company;
        this.name = name;
        this.score = score;
    }

    public int getId(){
        return this.id;
    }
    public String getCompany() {
        return company;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }


    public void setCompany(String company) {
        this.company = company;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
