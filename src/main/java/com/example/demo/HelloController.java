package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HelloController {

    @FXML
    private PasswordField CONTRAid;

    @FXML
    private TextField CORREROid;

    private Stage stage;
    private Scene scene;
    private String tipo;


    @FXML
    void ingresarAdmin(ActionEvent event) throws IOException {
        String tipoAdmin;
        String correoIngresado=CORREROid.getText();
        String contraIngresada=CONTRAid.getText();
        boolean correoEncontrado=false;
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String Query="SELECT * FROM abasto_db.administradores;";
        try{
            Statement statement=connectDB.createStatement();
            ResultSet queryOutput= statement.executeQuery(Query);
            while(queryOutput.next()){

                if(correoIngresado.equals(queryOutput.getString("correo"))){
                    correoEncontrado=true;
                    if(contraIngresada.equals(queryOutput.getString("contra"))){
                        tipoAdmin=queryOutput.getString("tipoAdmin");
                        tipo = tipoAdmin;
                        if(tipoAdmin.equals("Ventas")){
                            cambioDeAdmin("Ventas",event);
                        }
                        else if(tipoAdmin.equals("Sistemas")){
                            cambioDeAdmin("Sistemas",event);
                        }
                    }else{
                       alerta("Datos incorrectos","El correro o contraseña es incorrecto");
                    }
                }
            }
             if(correoEncontrado==false){
                alerta("Datos incorrectos","No existe el correo ingresado");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    void ingresarProveedor(ActionEvent event) {
        String correoIngresado=CORREROid.getText();
        String contraIngresada=CONTRAid.getText();
        boolean correoEncontrado=false;
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String Query="SELECT * FROM abasto_db.proveedores;";
        try{
            Statement statement=connectDB.createStatement();
            ResultSet queryOutput= statement.executeQuery(Query);
            while(queryOutput.next()){

                if(correoIngresado.equals(queryOutput.getString("correo"))){
                    correoEncontrado=true;
                    if(contraIngresada.equals(queryOutput.getString("contra"))){
                       cambioProveedor(event,queryOutput.getString("id_proveedor"));
                    }else{
                        alerta("Datos incorrectos","El correro o contraseña es incorrecto");
                    }
                }
            }
            if(correoEncontrado==false){
                alerta("Datos incorrectos","No existe el correo ingresado");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    private void cambioProveedor(ActionEvent event, String id_proveedor) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Proveedores.fxml"));
        Parent root=fxmlLoader.load();
        ProveedoresController proveedoresController=fxmlLoader.getController();
        int id=Integer.parseInt(id_proveedor);
        proveedoresController.id_proveedor=id;
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setTitle("Proveedores");
        stage.setScene(scene);
        stage.show();
    }

    public void cambioDeAdmin(String TipoAdmin,ActionEvent event) throws IOException {
       FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(TipoAdmin+".fxml"));
       Parent root=fxmlLoader.load();
       //SistemasController sistemasController=fxmlLoader.getController();
       stage=(Stage)((Node)event.getSource()).getScene().getWindow();
       scene=new Scene(root);
       stage.setTitle("Admin" + tipo);
       stage.setScene(scene);
       stage.show();

   }
    public void alerta(String titulo,String mensaje){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
