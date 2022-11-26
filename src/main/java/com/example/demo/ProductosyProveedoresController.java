package com.example.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProductosyProveedoresController {

    @FXML
    private Label titulo;

    @FXML
    private VBox VBPyP;

    public String tabla="";

    public void ctexto(String txt){
        titulo.setText(txt);
        tabla=txt;

        switch (tabla) {
            case "Proveedores" -> {
                DatabaseConnection connectNow = new DatabaseConnection();
                Connection connectDB = connectNow.getConnection();
                String Query = "SELECT * FROM abasto_db.proveedores;";
                try {
                    Statement statement = connectDB.createStatement();
                    ResultSet q = statement.executeQuery(Query);
                    String cadena = "";

                        //cadena = cadena + "\n" + "ID proveedor: " + q.getString("id_proveedor") + " Proveedor: " + q.getString("proveedor") + " Nombre: " + q.getString("nombre");
                        TableView<Data_Proveedores> TV_prov = new TableView<>();
                        final ObservableList<Data_Proveedores> data_proveedores = FXCollections.observableArrayList();
                        while(q.next()){
                        data_proveedores.add(new Data_Proveedores(q.getString("id_proveedor"), q.getString("proveedor"), q.getString("nombre")));
                        }
                        TableColumn TVPr_idprov = new TableColumn("ID_proveedor");
                        TVPr_idprov.setCellValueFactory(new PropertyValueFactory<>("ID_proveedor"));
                        TableColumn TVPr_prov = new TableColumn("Proveedor");
                        TVPr_prov.setCellValueFactory(new PropertyValueFactory<>("Proveedor"));
                        TableColumn TVPr_nom = new TableColumn("Nombre");
                        TVPr_nom.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
                        TV_prov.setItems(data_proveedores);
                        TV_prov.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                        TV_prov.getColumns().addAll(TVPr_idprov, TVPr_prov, TVPr_nom);
                        VBPyP.getChildren().add(TV_prov);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case "Productos" -> {
                DatabaseConnection connectNow = new DatabaseConnection();
                Connection connectDB = connectNow.getConnection();
                String Query = "SELECT * FROM abasto_db.productos;";
                try {
                    Statement statement = connectDB.createStatement();
                    ResultSet q = statement.executeQuery(Query);
                        TableView<Data_Productos> TV_prod = new TableView<>();
                        final ObservableList<Data_Productos> data_productos = FXCollections.observableArrayList(

                        );
                    while(q.next()){
                        data_productos.add(new Data_Productos(q.getString("id_producto"), q.getString("producto"), q.getString("id_categoria"), q.getString("precio_por_kg"), q.getString("kg_en_existencia"), q.getString("archivo_imagen"), q.getString("id_proveedor")) );
                        }
                        TableColumn TVP_idprod = new TableColumn("ID_PRODUCTO");
                        TVP_idprod.setCellValueFactory(new PropertyValueFactory<>("ID_PRODUCTO"));
                        TableColumn TVP_prod = new TableColumn("PRODUCTO");
                        TVP_prod.setCellValueFactory(new PropertyValueFactory<>("PRODUCTO"));
                        TableColumn TVP_idc = new TableColumn("ID_CATEGORIA");
                        TVP_idc.setCellValueFactory(new PropertyValueFactory<>("ID_CATEGORIA"));
                        TableColumn TVP_pkg = new TableColumn("PRECIO_POR_KG");
                        TVP_pkg.setCellValueFactory(new PropertyValueFactory<>("PRECIO_POR_KG"));
                        TableColumn TVP_kge = new TableColumn("KG_EN_EXISTENCIA");
                        TVP_kge.setCellValueFactory(new PropertyValueFactory<>("KG_EN_EXISTENCIA"));
                        TableColumn TVP_img = new TableColumn("ARCHIVO_IMAGEN");
                        TVP_img.setCellValueFactory(new PropertyValueFactory<>("ARCHIVO_IMAGEN"));
                        TableColumn TVP_idprov = new TableColumn("ID_PROVEEDOR");
                        TVP_idprov.setCellValueFactory(new PropertyValueFactory<>("ID_PROVEEDOR"));
                        TV_prod.setItems(data_productos);
                        TV_prod.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                        TV_prod.getColumns().addAll(TVP_idprod, TVP_prod, TVP_idc, TVP_pkg, TVP_kge, TVP_img, TVP_idprov);
                        VBPyP.getChildren().add(TV_prod);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            case "Categorias" -> {
                DatabaseConnection connectNow = new DatabaseConnection();
                Connection connectDB = connectNow.getConnection();
                String Query = "SELECT * FROM abasto_db.categorias;";
                try {
                    Statement statement = connectDB.createStatement();
                    ResultSet queryOutput = statement.executeQuery(Query);
                    String cadena = "";

                        //cadena = cadena + "\n" + queryOutput.getString("id_categoria") + queryOutput.getString("categoria");
                        TableView<Data_Categoria> TV_cat = new TableView<>();
                        final ObservableList<Data_Categoria> data_categorias = FXCollections.observableArrayList(

                        );
                        while(queryOutput.next()){
                            data_categorias.add( new Data_Categoria(queryOutput.getString("id_categoria"), queryOutput.getString("categoria")));
                        }
                        TableColumn TVC_idcat = new TableColumn("ID_categoria");
                        TVC_idcat.setCellValueFactory(new PropertyValueFactory<>("ID_categoria"));
                        TableColumn TVC_cat = new TableColumn("Categoria");
                        TVC_cat.setCellValueFactory(new PropertyValueFactory<>("Categoria"));
                        TV_cat.setItems(data_categorias);
                        TV_cat.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                        TV_cat.getColumns().addAll(TVC_idcat, TVC_cat);
                        VBPyP.getChildren().add(TV_cat);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static class Data_Proveedores{
        SimpleStringProperty ID_proveedor;
        SimpleStringProperty Proveedor;
        SimpleStringProperty Nombre;
        Data_Proveedores(String ID_proveedor, String Proveedor, String Nombre){
            this.ID_proveedor = new SimpleStringProperty(ID_proveedor);
            this.Proveedor = new SimpleStringProperty(Proveedor);
            this.Nombre = new SimpleStringProperty(Nombre);
        }
        public String getID_proveedor(){
            return ID_proveedor.get();
        }
        public void setID_proveedor(String id_proveedor){
            ID_proveedor.set(id_proveedor);
        }
        public String getProveedor(){
            return Proveedor.get();
        }
        public void setProveedor(String proveedor){
            Proveedor.set(proveedor);
        }
        public String getNombre(){
            return Nombre.get();
        }
        public void setNombre(String nombre){
            Nombre.set(nombre);
        }
    }
    public static class Data_Productos{
        SimpleStringProperty ID_PRODUCTO;
        SimpleStringProperty PRODUCTO;
        SimpleStringProperty ID_CATEGORIA;
        SimpleStringProperty PRECIO_POR_KG;
        SimpleStringProperty KG_EN_EXISTENCIA;
        SimpleStringProperty ARCHIVO_IMAGEN;
        SimpleStringProperty ID_PROVEEDOR;
        Data_Productos(String ID_PRODUCTO, String PRODUCTO, String ID_CATEGORIA, String PRECIO_POR_KG, String KG_EN_EXISTENCIA, String ARCHIVO_IMAGEN, String ID_PROVEEDOR){
            this.ID_PRODUCTO = new SimpleStringProperty(ID_PRODUCTO);
            this.PRODUCTO = new SimpleStringProperty(PRODUCTO);
            this.ID_CATEGORIA = new SimpleStringProperty(ID_CATEGORIA);
            this.PRECIO_POR_KG = new SimpleStringProperty(PRECIO_POR_KG);
            this.KG_EN_EXISTENCIA = new SimpleStringProperty(KG_EN_EXISTENCIA);
            this.ARCHIVO_IMAGEN = new SimpleStringProperty(ARCHIVO_IMAGEN);
            this.ID_PROVEEDOR = new SimpleStringProperty(ID_PROVEEDOR);
        }
        public String getID_PRODUCTO(){
            return ID_PRODUCTO.get();
        }
        public void setID_PRODUCTO(String id_producto){
            ID_PRODUCTO.set(id_producto);
        }
        public String getPRODUCTO(){
            return PRODUCTO.get();
        }
        public void setPRODUCTO(String producto){
            PRODUCTO.set(producto);
        }
        public String getID_CATEGORIA(){
            return ID_CATEGORIA.get();
        }
        public void setID_CATEGORIA(String id_categoria){
            ID_CATEGORIA.set(id_categoria);
        }
        public String getPRECIO_POR_KG(){
            return PRECIO_POR_KG.get();
        }
        public void setPRECIO_POR_KG(String precio_por_kg){
            PRECIO_POR_KG.set(precio_por_kg);
        }
        public String getKG_EN_EXISTENCIA(){
            return KG_EN_EXISTENCIA.get();
        }
        public void setKG_EN_EXISTENCIA(String kg_en_existencia){
            KG_EN_EXISTENCIA.set(kg_en_existencia);
        }
        public String getARCHIVO_IMAGEN(){
            return ARCHIVO_IMAGEN.get();
        }
        public void setARCHIVO_IMAGEN(String archivo_imagen){
            ARCHIVO_IMAGEN.set(archivo_imagen);
        }
        public String getID_PROVEEDOR(){
            return ID_PROVEEDOR.get();
        }
        public void setID_PROVEEDOR(String id_proveedor){
            ID_PROVEEDOR.set(id_proveedor);
        }
    }
    public static class Data_Categoria{
        SimpleStringProperty ID_categoria;
        SimpleStringProperty Categoria;
        Data_Categoria(String ID_categoria, String Categoria){
            this.ID_categoria = new SimpleStringProperty(ID_categoria);
            this.Categoria = new SimpleStringProperty(Categoria);
        }
        public String getID_categoria(){
            return ID_categoria.get();
        }
        public void setID_categoria(String id_categoria){
            ID_categoria.set(id_categoria);
        }
        public String getCategoria(){
            return Categoria.get();
        }
        public void setCategoria(String categoria){
            Categoria.set(categoria);
        }
    }
}
