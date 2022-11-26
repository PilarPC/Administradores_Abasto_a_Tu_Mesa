package com.example.demo;

import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

public class SistemasController {

    @FXML
    private TextField ADMINISTRADOR;

    @FXML
    private TextField CONTRAA;

    @FXML
    private TextField CONTRAP;

    @FXML
    private TextField CORREOA;

    @FXML
    private TextField CORREOP;

    @FXML
    private TextField IDA;

    @FXML
    private TextField IDP;

    @FXML
    private TextField NOMBREA;

    @FXML
    private TextField NOMBREP;

    @FXML
    private TextField PROVEEDOR;

    @FXML
    private VBox VBproveedores;

    @FXML
    private VBox VBventas;

    @FXML
    void ActualizarAdministradores(ActionEvent event) {
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String Query="SELECT * FROM abasto_db.administradores;";

        try{
            Statement statement=connectDB.createStatement();
            ResultSet queryOutput= statement.executeQuery(Query);
            VBventas.getChildren().clear();
            
                TableView<Data> TV_admin = new TableView<>();
                final ObservableList<Data> data_a = FXCollections.observableArrayList(
                        //new Data(queryOutput.getString("id_admin"), queryOutput.getString("correo"), queryOutput.getString("contra"), queryOutput.getString("tipoAdmin"))
                );
                while(queryOutput.next()){
                    data_a.add(new Data(queryOutput.getString("id_admin"), queryOutput.getString("correo"), queryOutput.getString("contra"), queryOutput.getString("tipoAdmin"), queryOutput.getString("nombre")));
                }

                TableColumn TVA_id = new TableColumn("ID");
                TVA_id.setCellValueFactory(new PropertyValueFactory<>("ID"));
                TableColumn TVA_correo = new TableColumn("Correo");
                TVA_correo.setCellValueFactory(new PropertyValueFactory<>("Correo"));
                TableColumn TVA_contra = new TableColumn("Contra");
                TVA_contra.setCellValueFactory(new PropertyValueFactory<>("Contra"));
                TableColumn TVA_tipo = new TableColumn("Tipo Admin");
                TVA_tipo.setCellValueFactory(new PropertyValueFactory<>("Tipo"));
                TableColumn TVA_nombre = new TableColumn("Nombre");
                TVA_nombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
                TV_admin.setItems(data_a);
                TV_admin.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                TV_admin.getColumns().addAll(TVA_id, TVA_correo, TVA_contra, TVA_tipo,TVA_nombre);
                VBventas.getChildren().add(TV_admin);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void ActualizarProveedores(ActionEvent event) {
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String Query="SELECT * FROM abasto_db.proveedores";
        int cont=0;
        try{
            Statement statement=connectDB.createStatement();
            ResultSet queryOutput= statement.executeQuery(Query);
            VBproveedores.getChildren().clear();
                TableView<Data> TV_prov = new TableView<>();
                final ObservableList<Data> data_p = FXCollections.observableArrayList();
                while(queryOutput.next()){
                     data_p.add( new Data(queryOutput.getString("id_proveedor"), queryOutput.getString("correo"), queryOutput.getString("contra"), queryOutput.getString("proveedor"), queryOutput.getString("nombre")));
                }
                TableColumn TVP_id = new TableColumn("ID");
                TVP_id.setCellValueFactory(new PropertyValueFactory<>("ID"));
                TableColumn TVP_correo = new TableColumn("Correo");
                TVP_correo.setCellValueFactory(new PropertyValueFactory<>("Correo"));
                TableColumn TVP_contra = new TableColumn("Contra");
                TVP_contra.setCellValueFactory(new PropertyValueFactory<>("Contra"));
                TableColumn TVP_tipo = new TableColumn("Tipo proveedor");
                TVP_tipo.setCellValueFactory(new PropertyValueFactory<>("Tipo"));
                TableColumn TVP_nombre = new TableColumn("Nombre");
                TVP_nombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
                TV_prov.setItems(data_p);
                TV_prov.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                TV_prov.getColumns().addAll(TVP_id, TVP_correo, TVP_contra, TVP_tipo,TVP_nombre);
                VBproveedores.getChildren().add(TV_prov);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void AgregarProveedor(ActionEvent event) {
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String id_proveedor=IDP.getText();
        String correo=CORREOP.getText();
        String nombre=NOMBREP.getText();
        String contra=CONTRAP.getText();
        String proveedor=PROVEEDOR.getText();
        boolean datosCorretos=true;
        if(correo.equals("") | nombre.equals("") | contra.equals("") | proveedor.equals("")){
            datosCorretos=false;
            alerta("Faltan datos","Uno o varios casillas se encuentras vacias");
        }
        if(buscarProveedor(correo)){
            datosCorretos=false;
            alerta("Dato repetido","Este correo ya ha sido dado de alta");
        }
        if(correo.endsWith("@gmail.com") | correo.endsWith("@gmail.com.mx") | correo.endsWith("@yahoo.com")|correo.endsWith("@yahoo.com.mx")|correo.endsWith("@outlook.com.mx")|correo.endsWith("@outlook.com")){

        }else{
            datosCorretos=false;
            alerta("Dato incorrecto","El dominio del correo no es correcto");
        }
        if(datosCorretos){
            String Query="INSERT INTO `abasto_db`.`proveedores` ( `proveedor`, `correo`, `nombre`, `contra`) VALUES ('"+proveedor+"', '"+correo+"', '"+nombre+"', '"+contra+"');";
            try{
                Statement statement=connectDB.createStatement();
                statement.executeUpdate(Query);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        //String Query="INSERT INTO `abasto_db`.`proveedores` (`id_proveedor`, `proveedor`, `correo`, `nombre`, `contra`) VALUES ('6', 'proveedor', 'correo', 'nombre', 'contra');";

    }



    @FXML
    void AgrgarAdmin(ActionEvent event) {
        boolean datosCorretos=true;
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String id_admin=IDA.getText();
        String correo=CORREOA.getText();
        String nombre=NOMBREA.getText();
        String contra=CONTRAA.getText();
        String tipoAdmin=ADMINISTRADOR.getText();
        tipoAdmin=tipoAdmin.toLowerCase();
        if(tipoAdmin.equals("ventas")){
            tipoAdmin="Ventas";
        }
        else if(tipoAdmin.equals("sistemas")){
            tipoAdmin="Sistemas";
        }
        else{
            datosCorretos=false;
        }
        if(correo.equals("") | nombre.equals("") | contra.equals("") | tipoAdmin.equals("")){
            datosCorretos=false;
            alerta("Faltan datos","Uno o varios casillas se encuentras vacias");
        }
        if(buscarAdministrador(correo)){
                alerta("Dato repetido","Este correo ya ha sido dado de alta");
                datosCorretos=false;
        }
        if(correo.endsWith("@gmail.com") | correo.endsWith("@gmail.com.mx") | correo.endsWith("@yahoo.com")|correo.endsWith("@yahoo.com.mx")|correo.endsWith("@outlook.com.mx")|correo.endsWith("@outlook.com")){

        }else{
            datosCorretos=false;
            alerta("Dato incorrecto","El dominio del correo no es correcto");
        }
        if(datosCorretos==true){
            String Query="INSERT INTO `abasto_db`.`administradores` ( `correo`, `nombre`, `contra`, `tipoAdmin`) VALUES ('"+correo+"', '"+nombre+"', '"+contra+"', '"+tipoAdmin+"'); ";
            try{
                Statement statement=connectDB.createStatement();
                statement.executeUpdate(Query);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            alerta("Datos incorrectos","verifique lo ingresado en tipo administrador");
        }




    }

    @FXML
    void EliminarAdmin(ActionEvent event) {

        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String id_admin=IDA.getText();
        String Query="DELETE FROM `abasto_db`.`administradores` WHERE (`id_admin` = '"+id_admin+"');";
        boolean confir=confirmacion("eliminar el administrador cuyo id es "+id_admin);
        if(confir) {
            try {
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(Query);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void EliminarProveedor(ActionEvent event) {
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String id_admin=IDP.getText();
        String Query="DELETE FROM `abasto_db`.`proveedores` WHERE (`id_proveedor` = '"+id_admin+"');";
        boolean confir=confirmacion("eliminar el proveedor cuyo id es "+id_admin);
        if(confir) {
            try {
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(Query);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
    @FXML
    void salir(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root=fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public static class Data{
        SimpleStringProperty ID;
        SimpleStringProperty Correo;
        SimpleStringProperty Contra;
        SimpleStringProperty Tipo;
        SimpleStringProperty Nombre;



        public void setNombre(String nombre) {
            this.Nombre.set(nombre);
        }

        Data(String ID, String Correo, String Contra, String Tipo,String Nombre){
            this.ID = new SimpleStringProperty(ID);
            this.Correo = new SimpleStringProperty(Correo);
            this.Contra = new SimpleStringProperty(Contra);
            this.Tipo = new SimpleStringProperty(Tipo);
            this.Nombre = new SimpleStringProperty(Nombre);
        }
        public String getID(){
            return ID.get();
        }
        public void setID(String id){
            ID.set(id);
        }
        public String getCorreo(){
            return Correo.get();
        }
        public void setCorreo(String correo){
            Correo.set(correo);
        }
        public String getContra(){
            return Contra.get();
        }
        public void setContra(String contra){
            Contra.set(contra);
        }
        public String getTipo(){
            return Tipo.get();
        }
        public void setTipo(String tipo){
            Tipo.set(tipo);
        }
        public String getNombre() {return Nombre.get();}
        public SimpleStringProperty nombreProperty() {return Nombre;}
    }
    public void alerta(String titulo,String mensaje){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    private boolean buscarAdministrador(String correoNuevo) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String Query="SELECT * FROM abasto_db.administradores;";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(Query);
            while(queryOutput.next()){
                if(correoNuevo.equals(queryOutput.getString("correo"))){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean buscarProveedor(String correo) {

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String Query="SELECT * FROM abasto_db.proveedores";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(Query);
            while(queryOutput.next()){
                if(correo.equals(queryOutput.getString("correo"))){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    boolean confirmacion(String estas_seguro){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText("¿Estas seguro "+estas_seguro+" ?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }
}
