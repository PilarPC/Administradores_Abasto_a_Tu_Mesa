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

public class ProveedoresController {
    public int id_proveedor=0;
    @FXML
    private TextField Tf_idOrden;

    @FXML
    private VBox VbOrden;
    @FXML
    private TextField estado;
    @FXML
    void Ck_actualizarPantalla(ActionEvent event) {
        VbOrden.getChildren().clear();
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String Query="SELECT P.id_producto, P.producto, PV.nombre  nombre_proveedor ,D.cantidad, D.precio, O.estado, O.fecha_de_pedido, O.total, O.id_orden, PV.id_proveedor\n" +
                "FROM (((abasto_db.proveedores PV\n" +
                "INNER JOIN abasto_db.productos P ON PV.id_proveedor = P.id_proveedor)\n" +
                "INNER JOIN abasto_db.detalles_orden D ON P.id_producto = D.id_producto)\n" +
                "INNER JOIN abasto_db.ordenes O ON D.id_orden = O.id_orden)"+
                "Where PV.id_proveedor = "+id_proveedor+";";

        try{
            Statement statement=connectDB.createStatement();
            ResultSet q= statement.executeQuery(Query);
            String cadena="";
            TableView<Data> TV_orden = new TableView<>();
            final ObservableList<Data> data = FXCollections.observableArrayList();
            while(q.next()){
                //cadena=cadena+"\n"+"ID orden: "+q.getString("O.id_orden")+" Producto: "+q.getString("P.producto")+" Cantidad: "+q.getString("D.cantidad")+" Estado de la orden: "+q.getString("O.estado");
                data.add(new Data(q.getString("O.id_orden"), q.getString("P.producto"), q.getString("D.cantidad"), q.getString("O.estado")));
            }
            TableColumn TVO_id = new TableColumn("ID_orden");
            TVO_id.setCellValueFactory(new PropertyValueFactory<>("ID_orden"));
            TableColumn TVO_prod = new TableColumn("Producto");
            TVO_prod.setCellValueFactory(new PropertyValueFactory<>("Producto"));
            TableColumn TVO_cant = new TableColumn("Cantidad");
            TVO_cant.setCellValueFactory(new PropertyValueFactory<>("Cantidad"));
            TableColumn TVO_est = new TableColumn("Estado");
            TVO_est.setCellValueFactory(new PropertyValueFactory<>("Estado"));
            TV_orden.setItems(data);
            TV_orden.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            TV_orden.getColumns().addAll(TVO_id, TVO_prod, TVO_cant, TVO_est);
            VbOrden.getChildren().add(TV_orden);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    void Ck_actualizarPedido(ActionEvent event) {
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String est=estado.getText();
        String id=Tf_idOrden.getText();
        String Query="update abasto_db.ordenes\n" +
                "set estado = '"+est+"'\n" +
                "where id_orden = '"+id+"';";
        try{
            Statement statement=connectDB.createStatement();
            statement.executeUpdate(Query);
        }
        catch(Exception e){
            e.printStackTrace();
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
        SimpleStringProperty ID_orden;
        SimpleStringProperty Producto;
        SimpleStringProperty Cantidad;
        SimpleStringProperty Estado;
        Data(String ID_orden, String Producto, String Cantidad, String Estado){
            this.ID_orden = new SimpleStringProperty(ID_orden);
            this.Producto = new SimpleStringProperty(Producto);
            this.Cantidad = new SimpleStringProperty(Cantidad);
            this.Estado = new SimpleStringProperty(Estado);
        }
        public String getID_orden(){
            return ID_orden.get();
        }
        public void setID_orden(String id_orden){
            ID_orden.set(id_orden);
        }
        public String getProducto(){
            return Producto.get();
        }
        public void setProducto(String producto){
            Producto.set(producto);
        }
        public String getCantidad(){
            return Cantidad.get();
        }
        public void setCantidad(String cantidad){
            Cantidad.set(cantidad);
        }
        public String getEstado(){return Estado.get();}
        public void setEstado(String estado){Estado.set(estado);
        }
    }

}
/*

 @FXML
    void Ck_actualizarPantalla(ActionEvent event) {
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String Query="SELECT P.id_producto, P.producto, PV.nombre  nombre_proveedor ,D.cantidad, D.precio, O.estado, O.fecha_de_pedido, O.total, O.id_orden, PV.id_proveedor\n" +
                "FROM (((abasto_db.proveedores PV\n" +
                "INNER JOIN abasto_db.productos P ON PV.id_proveedor = P.id_proveedor)\n" +
                "INNER JOIN abasto_db.detalles_orden D ON P.id_producto = D.id_producto)\n" +
                "INNER JOIN abasto_db.ordenes O ON D.id_orden = O.id_orden)\n" +
                "Where PV.id_proveedor = "+id_proveedor+";";

        try{
            Statement statement=connectDB.createStatement();
            ResultSet q= statement.executeQuery(Query);
            VbOrden.getChildren().clear();
            while(q.next()){
                Label l=new Label("ID orden: "+q.getString("O.id_orden")+" Producto: "+q.getString("P.producto")+" Cantidad: "+q.getString("D.cantidad")+" Estado de la orden: "+q.getString("O.estado"));
                VbOrden.getChildren().add(l);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
 */