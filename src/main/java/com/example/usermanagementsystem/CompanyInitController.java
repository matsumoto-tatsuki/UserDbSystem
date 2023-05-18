package com.example.usermanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

import static com.example.usermanagementsystem.UserController.errorJudgeText;

public class CompanyInitController {
    private UserController userController;
    @FXML
    private TextField companyText;

    @FXML
    private TableView<Company> companyTable;
    @FXML
    private TableColumn<Company,Integer> idColumn;
    @FXML
    private TableColumn<Company,String> nameColumn;

    private int editId;

    CompanyService companyService = new CompanyService();

    public void setParent(UserController userController){
        this.userController = userController;
    }

    public void updateParent(){
        userController.onNextButtonClick();
    }
    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        companyTable.setItems(companyService.findCompaniesAllData());
       //updateParent();
    }

    public void onAddButtonClick(ActionEvent event) {
        updateParent();
//        if(errorJudgeText(companyText)) {
//            var name = companyText.getText();
//            var rs = companyService.insertCompany(new Company(name));
//            companyTable.setItems(companyService.findCompaniesAllData());
//
//        }
//        companyText.setText(null);
    }

    public void onDeleteButtonClick(ActionEvent event) {
        if(errorJudgeText(companyText)) {
            var rs = companyService.deleteCompany(editId);
            companyTable.setItems(companyService.findCompaniesAllData());
        }
        companyText.setText(null);
    }

    public void onUpdateButtonClick(ActionEvent event) {
        if(errorJudgeText(companyText)) {
            var name = companyText.getText();
            var rs = companyService.updateCompany(new Company(name),editId);
            companyTable.setItems(companyService.findCompaniesAllData());
        }
        companyText.setText(null);
    }

    @FXML
    protected void onTableClick() {
        editId = companyTable.getSelectionModel().getSelectedIndex();

        companyText.setText(nameColumn.getCellData(editId));
    }
}
