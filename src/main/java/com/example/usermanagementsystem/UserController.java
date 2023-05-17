package com.example.usermanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class UserController {

    @FXML
    private Label errorLabel;
    @FXML
    private ComboBox<String> addCompanyBox;
    @FXML
    private TextField addNameText;
    @FXML
    private TextField addScoreText;

    @FXML
    private ComboBox<String> editCompanyBox;
    @FXML
    private TextField editNameText;
    @FXML
    private TextField editScoreText;

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User,Integer> idColumn;
    @FXML
    private TableColumn<User,String> companyColumn;
    @FXML
    private TableColumn<User,String> nameColumn;
    @FXML
    private TableColumn<User,Integer> scoreColumn;

    @FXML
    private TextField searchText;
    @FXML
    private Button searchButton;

    private int editId;
    UserService userService = new UserService();
    CompanyService companyService = new CompanyService();

    @FXML
    private void initialize() {

        idColumn.setCellValueFactory(new PropertyValueFactory<User,Integer>("id"));
        companyColumn.setCellValueFactory(new PropertyValueFactory<User,String>("company"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<User,Integer>("score"));




        userTable.setItems(userService.findAllData());
        addCompanyBox.setItems(companyService.findCompaniesData());
        editCompanyBox.setItems(companyService.findCompaniesData());

    }

    @FXML
    protected void onAddButtonClick() {
        if(errorJudgeComboBox(addCompanyBox) && errorJudgeText(addNameText) && errorJudgeScore(addScoreText)) {
            errorLabel.setText("");
            var company = addCompanyBox.getValue();
            var name = addNameText.getText();
            var score = Integer.parseInt(addScoreText.getText());

            var rs = userService.insertUser(new User(company, name, score));
            userTable.setItems(userService.findAllData());

        }else{
            errorLabel.setText("再入力してください！");
        }
        addCompanyBox.setValue(null);
        addNameText.setText(null);
        addScoreText.setText(null);
    }
    @FXML
    protected void onDeleteButtonClick() {
        if(errorJudgeComboBox(editCompanyBox) && errorJudgeText(editNameText) && errorJudgeScore(editScoreText)) {
            errorLabel.setText("");

            var rs = userService.deleteUser(editId);
            userTable.setItems(userService.findAllData());


        }else{
            errorLabel.setText("再入力してください！");
        }
        editCompanyBox.setValue(null);
        editNameText.setText(null);
        editScoreText.setText(null);
    }
    @FXML
    protected void onUpdateButtonClick() {
        if(errorJudgeComboBox(editCompanyBox) && errorJudgeText(editNameText) && errorJudgeScore(editScoreText)) {
            errorLabel.setText("");
            var company = editCompanyBox.getValue();
            var name = editNameText.getText();
            var score = Integer.parseInt(editScoreText.getText());


            var rs = userService.updateUser(new User(company,name,score),editId);
            userTable.setItems(userService.findAllData());

        }else{
            errorLabel.setText("再入力してください！");
        }
        editCompanyBox.setValue(null);
        editNameText.setText(null);
        editScoreText.setText(null);
    }

    @FXML
    protected void onSearchButtonClick() {
        if(errorJudgeText(searchText) && searchButton.getText().equals("検索")) {
            errorLabel.setText("");
            var search = searchText.getText();

            var str = "%" + search + "%";
            userTable.setItems(userService.searchUser(str));
            searchButton.setText("解除");
        }else{
            searchButton.setText("検索");
            searchText.setText(null);
            userTable.setItems(userService.findAllData());
        }
    }

    @FXML
    protected void onUpdateDb() {
        userTable.setItems(userService.findAllData());
        addCompanyBox.setItems(companyService.findCompaniesData());
        editCompanyBox.setItems(companyService.findCompaniesData());
    }

    @FXML
    protected void onTableClick() {
        editId = userTable.getSelectionModel().getSelectedIndex();

        editCompanyBox.setValue(companyColumn.getCellData(editId));
        editNameText.setText(nameColumn.getCellData(editId));
        editScoreText.setText(String.valueOf(scoreColumn.getCellData(editId)));
        errorLabel.setText(String.valueOf(editId));
    }

    @FXML
    protected void onLabelClick(){
        errorLabel.setText("ユーザ管理システム");
    }

    public boolean errorJudgeComboBox(ComboBox<String> comboBox){
        if(comboBox.getValue() == null || comboBox.getValue().equals("")){
            return false;
        }
        return true;
    }

    public static boolean errorJudgeText(TextField textField){
        if(textField.getText() == null || textField.getText().equals("")){
            return false;
        }
        return true;
    }

    public boolean errorJudgeScore(TextField textField){
        if(textField.getText() == null || textField.getText().equals("")){
            return false;
        }else if(textField.getText().matches(".*[^0-9].*")){
            return false;
        }else if(!(Integer.parseInt(textField.getText()) >= 0 && Integer.parseInt(textField.getText()) <= 100)){
            return false;
        }
        return true;
    }

    public void errorShow() throws IOException {
        FXMLLoader loader = new FXMLLoader(UserApplication.class.getResource("popup.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    protected void onCompanyButtonClick() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("companyInit-view.fxml"));
        Parent root = loader.load();

        // 別ウィンドウのStageを作成
        Stage separateWindowStage = new Stage();
        separateWindowStage.setTitle("企業管理アプリ");
        separateWindowStage.setScene(new Scene(root));

        // 別ウィンドウをモーダルダイアログとして設定
        separateWindowStage.initModality(Modality.APPLICATION_MODAL);

        // 別ウィンドウを表示
        separateWindowStage.showAndWait();

        userTable.setItems(userService.findAllData());
        addCompanyBox.setItems(companyService.findCompaniesData());
        editCompanyBox.setItems(companyService.findCompaniesData());

    }
}