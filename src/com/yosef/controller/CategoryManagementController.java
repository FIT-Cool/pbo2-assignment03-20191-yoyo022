package com.yosef.controller;

import com.yosef.entity.Category;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryManagementController implements Initializable {
    public TextField txtID;
    public TextField txtName;
    public TableView<Category> tableCategory;
    public TableColumn<Category,String> col1;
    public TableColumn<Category,String> col2;

    Alert alert = new Alert( Alert.AlertType.ERROR );
    private MainPratikum3Controller mainPratikum3Controller;

    public void actSave(ActionEvent actionEvent) {
        Category c = new Category();
        boolean found = false;
        for (Category i : mainPratikum3Controller.getCategories()){
            if(i.getNama().equals(txtName.getText())){
                found=true;
                break;
            }
        }
        if(found){
            alert.setTitle( "ERROR" );
            alert.setContentText( "Duplicate Category Name" );
            alert.show();
        }else {
            c.setId(Integer.parseInt(txtID.getText().trim()));
            c.setNama(txtName.getText().trim());
            mainPratikum3Controller.getCategories().add(c);
        }
    }

    public void setMainPratikum3Controller(MainPratikum3Controller mainPratikum3Controller) {
        this.mainPratikum3Controller = mainPratikum3Controller;
        tableCategory.setItems(mainPratikum3Controller.getCategories());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col1.setCellValueFactory(data ->{
            Category c = data.getValue();
            return new SimpleStringProperty(String.valueOf(c.getId()));
        });
        col2.setCellValueFactory(data ->{
            Category c = data.getValue();
            return new SimpleStringProperty(c.getNama());
        });
    }

}
