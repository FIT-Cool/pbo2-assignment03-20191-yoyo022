package com.yosef.controller;

import com.yosef.Main;
import com.yosef.entity.Category;
import com.yosef.entity.Toko;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPratikum3Controller implements Initializable{

    @FXML
    private TextField txtID;
    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<Category> comboCategory;
    @FXML
    private DatePicker datepickExpiredDate;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnReset;
    @FXML
    private Button btnUpdate;
    @FXML
    private TableView<Toko> tableToko;
    @FXML
    private TableColumn<Toko,String> col1;
    @FXML
    private TableColumn<Toko,String> col2;
    @FXML
    private TableColumn<Toko,String> col3;
    @FXML
    private TableColumn<Toko,String> col4;

    Alert alert = new Alert( Alert.AlertType.ERROR );
    private ObservableList<Category> categories;
    private ObservableList<Toko>tokoes;
    private Toko item;
    @FXML
    private void actShowCategoryManagement(ActionEvent actionEvent) {
        try {
            //Parent root = FXMLLoader.load(Main.class.getResource("view/FacultyForm.fxml"));

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CategoryManagement.fxml"));
            AnchorPane root = loader.load();
            CategoryManagementController controller = loader.getController();
            controller.setMainPratikum3Controller(this);

            Stage mainStage = new Stage();
            mainStage.setTitle("Category Management");
            mainStage.setScene(new Scene(root));
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void actSave(ActionEvent actionEvent) {
        Toko t = new Toko();
        int found = 0;
        if(txtID.getText().isEmpty()||txtName.getText().isEmpty()||comboCategory.getSelectionModel().isEmpty()||datepickExpiredDate.getEditor().getText().isEmpty()){
            alert.setTitle( "ERROR" );
            alert.setContentText( "Please fill id/name/category/expired date" );
            alert.show();
        }else{
            for(Toko i: tokoes){
                if(i.getNama().equals(txtName.getText())){
                    found ++;
                    break;
                }
            }
            if(found>=1){
                alert.setTitle( "ERROR" );
                alert.setContentText( "Duplicate name" );
                alert.show();
            }else{
                t.setId(Integer.parseInt(txtID.getText().trim()));
                t.setNama(txtName.getText().trim());
                t.setCategory(comboCategory.getSelectionModel().getSelectedItem());
                t.setDate(datepickExpiredDate.getValue());
                tokoes.add(t);
            }
        }
    }

    public void actReset(ActionEvent actionEvent) {
        txtID.clear();
        txtName.clear();
        comboCategory.setValue(null);
        datepickExpiredDate.setValue(null);
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);

    }

    public void actUpdate(ActionEvent actionEvent) {
        btnUpdate.setDisable(true);
        item.setId(Integer.parseInt(txtID.getText()));
        item.setNama(txtName.getText().trim());
        item.setCategory(comboCategory.getSelectionModel().getSelectedItem());
        item.setDate(datepickExpiredDate.getValue());
        tableToko.refresh();
        btnSave.setDisable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        comboCategory.setItems(getCategories());
        tableToko.setItems(getTokoes());
        col1.setCellValueFactory(data ->{
            Toko t = data.getValue();
            return new SimpleStringProperty(String.valueOf(t.getId()));
        });
        col2.setCellValueFactory(data ->{
            Toko t = data.getValue();
            return new SimpleStringProperty(t.getNama());
        });col3.setCellValueFactory(data ->{
            Toko t = data.getValue();
            return new SimpleStringProperty(t.getCategory().toString());
        });col4.setCellValueFactory(data ->{
            Toko t = data.getValue();
            return new SimpleStringProperty(t.getDate().toString());
        });
    }
    public ObservableList<Category>getCategories(){
        if (categories == null){
            categories = FXCollections.observableArrayList();
        }
        return categories;
    }public ObservableList<Toko>getTokoes(){
        if (tokoes == null){
            tokoes = FXCollections.observableArrayList();
        }
        return tokoes ;
    }

    @FXML
    private void mouseClicked(MouseEvent mouseEvent) {
        btnUpdate.setDisable(false);
        item = tableToko.getSelectionModel().getSelectedItem();
        txtID.setText(String.valueOf(item.getId()));
        txtName.setText(item.getNama());
        comboCategory.setValue(item.getCategory());
        datepickExpiredDate.setValue(item.getDate());
        btnSave.setDisable(true);
    }
}
