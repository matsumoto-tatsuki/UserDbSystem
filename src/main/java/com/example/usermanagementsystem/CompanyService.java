package com.example.usermanagementsystem;

import javafx.collections.ObservableList;

public class CompanyService {
    private CompanyDao companyDao;

    public CompanyService() {
        var connection = DbUtil.getConnection();
        companyDao = new CompanyDao(connection);
    }

    public ObservableList<String> findCompaniesData(){
        return companyDao.findCompaniesData();
    }

    public ObservableList<Company> findCompaniesAllData(){
        return companyDao.findCompaniesAllData();
    }
    public int insertCompany(Company company){
        return companyDao.insertCompany(company);
    }

    public int deleteCompany(int row){
        return companyDao.deleteCompany(row);
    }

    public int updateCompany(Company company,int row){
        return companyDao.updateCompany(company,row);
    }

    public void companyServiceClose(){
        companyDao.companyServiceClose();
    }
}
