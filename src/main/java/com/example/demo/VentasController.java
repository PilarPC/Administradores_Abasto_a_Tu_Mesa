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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VentasController {

    Map<String, String> Id_proveedores = new HashMap<>();
    Map<String, String> Id_categoria = new HashMap<>();

    @FXML
    private TextField CATEGORIAC;

    @FXML
    private TextField IDC;

    @FXML
    private TextField IDCATEGORIA;

    @FXML
    private TextField IDPRODUCTO;

    @FXML
    private TextField IDPROVEEDOR;

    @FXML
    private TextField KGEISTENTE;

    @FXML
    private TextField PRECIOKG;

    @FXML
    private TextField PRODUCTO;

    @FXML
    private TextField RUTAI;

    @FXML
    private VBox cuadroReportes;

    @FXML
    private Label tituloReporte;

    public static class Data_cate{
        SimpleStringProperty Categoria;
        SimpleStringProperty Ordenes_de_ventas_realizadas;
        Data_cate(String Categoria, String Ordenes_de_ventas_realizadas){
            this.Categoria = new SimpleStringProperty(Categoria);
            this.Ordenes_de_ventas_realizadas = new SimpleStringProperty(Ordenes_de_ventas_realizadas);
        }
        public String getCategoria() {return Categoria.get();}
        public SimpleStringProperty categoriaProperty() {return Categoria;}
        public void setCategoria(String categoria) {this.Categoria.set(categoria);}
        public String getOrdenes_de_ventas_realizadas() {return Ordenes_de_ventas_realizadas.get();}
        public SimpleStringProperty ordenes_de_ventas_realizadasProperty() {return Ordenes_de_ventas_realizadas;}
        public void setOrdenes_de_ventas_realizadas(String ordenes_de_ventas_realizadas) {this.Ordenes_de_ventas_realizadas.set(ordenes_de_ventas_realizadas);}
    }

    public static class Data_uni{
        SimpleStringProperty Producto;
        SimpleStringProperty Veces_Compradas;
        Data_uni(String Producto, String Veces_Compradas){
            this.Producto=new SimpleStringProperty(Producto);
            this.Veces_Compradas=new SimpleStringProperty(Veces_Compradas);
        }
        public String getProducto() {return Producto.get();}
        public SimpleStringProperty ProductoProperty() {return Producto;}
        public void setProducto(String Producto) {this.Producto.set(Producto);}
        public String getVeces_Compradas() {return Veces_Compradas.get();}
        public SimpleStringProperty Veces_CompradasProperty() {return Veces_Compradas;}
        public void setVeces_Compradas(String Veces_Compradas) {this.Veces_Compradas.set(Veces_Compradas);}

    }

    public static class Data_kg{
        SimpleStringProperty Producto;
        SimpleStringProperty Unidades_Vendidas;
        Data_kg(String Producto, String Unidades_Vendidas){
            this.Producto=new SimpleStringProperty(Producto);
            this.Unidades_Vendidas=new SimpleStringProperty(Unidades_Vendidas);
        }
        public String getProducto() {return Producto.get();}
        public SimpleStringProperty ProductoProperty() {return Producto;}
        public void setProducto(String Producto) {this.Producto.set(Producto);}
        public String getUnidades_Vendidas() {return Unidades_Vendidas.get();}
        public SimpleStringProperty Unidades_VendidasProperty() {return Unidades_Vendidas;}
        public void setUnidades_Vendidas(String Unidades_Vendidas) {this.Unidades_Vendidas.set(Unidades_Vendidas);}

    }

    public static class Data_prov{
        SimpleStringProperty Proveedor;
        SimpleStringProperty Unidades_Vendidas;
        Data_prov(String Proveedor, String Unidades_Vendidas){
            this.Proveedor=new SimpleStringProperty(Proveedor);
            this.Unidades_Vendidas=new SimpleStringProperty(Unidades_Vendidas);
        }
        public String getProveedor() {return Proveedor.get();}
        public SimpleStringProperty ProveedorProperty() {return Proveedor;}
        public void setProveedor(String Proveedor) {this.Proveedor.set(Proveedor);}
        public String getUnidades_Vendidas() {return Unidades_Vendidas.get();}
        public SimpleStringProperty Unidades_VendidasProperty() {return Unidades_Vendidas;}
        public void setUnidades_Vendidas(String Unidades_Vendidas) {this.Unidades_Vendidas.set(Unidades_Vendidas);}

    }

    @FXML
    void categoriaMas(ActionEvent event) { //esta esta bien
        cuadroReportes.getChildren().clear();
        tituloReporte.setText("Reporte de categorias que más venden");
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String Query="select  C.categoria, sum(D.cantidad) unidades_vendidas, sum(D.precio) ganancia, count(C.categoria) catigorias_mas_vendidas \n" +
                "from (((abasto_db.detalles_orden D\n" +
                "inner join abasto_db.productos P ON D.id_producto = P.id_producto)\n" +
                "inner join abasto_db.proveedores PV ON P.id_proveedor = PV.id_proveedor)\n" +
                "inner join abasto_db.categorias C ON P.id_categoria = C.id_categoria)\n" +
                "group by C.categoria\n" +
                "order by count(C.categoria) desc\n";
        try{
            Statement statement=connectDB.createStatement();
            ResultSet q= statement.executeQuery(Query);

            TableView<Data_cate> TV_prov = new TableView<>();
            final ObservableList<Data_cate> data_cates = FXCollections.observableArrayList();
            while(q.next()){
                data_cates.add(new Data_cate(q.getString("categoria"),q.getString("catigorias_mas_vendidas")));
            }
            TableColumn TVPr_idprov = new TableColumn("Categoria");
            TVPr_idprov.setCellValueFactory(new PropertyValueFactory<>("Categoria"));
            TableColumn TVPr_prov = new TableColumn("Ordenes_de_ventas_realizadas");
            TVPr_prov.setCellValueFactory(new PropertyValueFactory<>("Ordenes_de_ventas_realizadas"));

            TV_prov.setItems(data_cates);
            TV_prov.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            TV_prov.getColumns().addAll(TVPr_idprov, TVPr_prov);
            cuadroReportes.getChildren().add(TV_prov);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void categoriaMenos(ActionEvent event) { //esta esta bien
        cuadroReportes.getChildren().clear();
        tituloReporte.setText("Reporte de categorias que menos venden");
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String Query="select  C.categoria, sum(D.cantidad) unidades_vendidas, sum(D.precio) ganancia, count(C.categoria) catigorias_mas_vendidas \n" +
                "from (((abasto_db.detalles_orden D\n" +
                "inner join abasto_db.productos P ON D.id_producto = P.id_producto)\n" +
                "inner join abasto_db.proveedores PV ON P.id_proveedor = PV.id_proveedor)\n" +
                "inner join abasto_db.categorias C ON P.id_categoria = C.id_categoria)\n" +
                "group by C.categoria\n" +
                "order by count(C.categoria) asc\n";
        try{
            Statement statement=connectDB.createStatement();
            ResultSet q= statement.executeQuery(Query);

            TableView<Data_cate> TV_prov = new TableView<>();
            final ObservableList<Data_cate> data_cates = FXCollections.observableArrayList();
            while(q.next()){
                data_cates.add(new Data_cate(q.getString("categoria"),q.getString("catigorias_mas_vendidas")));
            }
            TableColumn TVPr_idprov = new TableColumn("Categoria");
            TVPr_idprov.setCellValueFactory(new PropertyValueFactory<>("Categoria"));
            TableColumn TVPr_prov = new TableColumn("Ordenes_de_ventas_realizadas");
            TVPr_prov.setCellValueFactory(new PropertyValueFactory<>("Ordenes_de_ventas_realizadas"));

            TV_prov.setItems(data_cates);
            TV_prov.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            TV_prov.getColumns().addAll(TVPr_idprov, TVPr_prov);
            cuadroReportes.getChildren().add(TV_prov);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    @FXML
    void masUnidad(ActionEvent event) {
        cuadroReportes.getChildren().clear();
        tituloReporte.setText("Reporte de productos con más unidades vendidas");
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String Query="select P.producto, count(D.id_producto) veces_compradas, D.id_producto, sum(D.cantidad) unidades_vendidas, P.id_proveedor, PV.nombre nombre_proveedor\n" +
                "from ((abasto_db.detalles_orden D\n" +
                "inner join abasto_db.productos P ON D.id_producto = P.id_producto)\n" +
                "inner join abasto_db.proveedores PV ON P.id_proveedor = PV.id_proveedor)\n" +
                "group by id_producto\n" +
                "order by count(D.id_producto) desc\n" +
                "limit 5\n";
        try{
            Statement statement=connectDB.createStatement();
            ResultSet q= statement.executeQuery(Query);

            TableView<Data_uni> TV_prov = new TableView<>();
            final ObservableList<Data_uni> data_unies = FXCollections.observableArrayList();
            while(q.next()){
                data_unies.add(new Data_uni(q.getString("producto"),q.getString("veces_compradas")));
            }
            TableColumn TVPr_idprov = new TableColumn("Producto");
            TVPr_idprov.setCellValueFactory(new PropertyValueFactory<>("Producto"));
            TableColumn TVPr_prov = new TableColumn("Veces_Compradas");
            TVPr_prov.setCellValueFactory(new PropertyValueFactory<>("Veces_Compradas"));

            TV_prov.setItems(data_unies);
            TV_prov.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            TV_prov.getColumns().addAll(TVPr_idprov, TVPr_prov);
            cuadroReportes.getChildren().add(TV_prov);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void menosUnidad(ActionEvent event) {
        cuadroReportes.getChildren().clear();
        tituloReporte.setText("Reporte de productos con menos unidades vendidas");
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String Query="select P.producto, count(D.id_producto) veces_compradas, D.id_producto, sum(D.cantidad) unidades_vendidas, P.id_proveedor, PV.nombre nombre_proveedor\n" +
                "from ((abasto_db.detalles_orden D\n" +
                "inner join abasto_db.productos P ON D.id_producto = P.id_producto)\n" +
                "inner join abasto_db.proveedores PV ON P.id_proveedor = PV.id_proveedor)\n" +
                "group by id_producto\n" +
                "order by count(D.id_producto) asc\n" +
                "limit 5\n";
        try{
            Statement statement=connectDB.createStatement();
            ResultSet q= statement.executeQuery(Query);

            TableView<Data_uni> TV_prov = new TableView<>();
            final ObservableList<Data_uni> data_unies = FXCollections.observableArrayList();
            while(q.next()){
                data_unies.add(new Data_uni(q.getString("producto"),q.getString("veces_compradas")));
            }
            TableColumn TVPr_idprov = new TableColumn("Producto");
            TVPr_idprov.setCellValueFactory(new PropertyValueFactory<>("Producto"));
            TableColumn TVPr_prov = new TableColumn("Veces_Compradas");
            TVPr_prov.setCellValueFactory(new PropertyValueFactory<>("Veces_Compradas"));

            TV_prov.setItems(data_unies);
            TV_prov.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            TV_prov.getColumns().addAll(TVPr_idprov, TVPr_prov);
            cuadroReportes.getChildren().add(TV_prov);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    @FXML
    void productoMas(ActionEvent event) {//UNIDADES VENDIDAS más kilos
        cuadroReportes.getChildren().clear();
        tituloReporte.setText("Reporte de productos más vendidos por kg");
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String Query="select P.producto, count(D.id_producto) veces_compradas, D.id_producto, sum(D.cantidad) unidades_vendidas, P.id_proveedor, PV.nombre nombre_proveedor\n" +
                "from ((abasto_db.detalles_orden D\n" +
                "inner join abasto_db.productos P ON D.id_producto = P.id_producto)\n" +
                "inner join abasto_db.proveedores PV ON P.id_proveedor = PV.id_proveedor)\n" +
                "group by id_producto\n" +
                "order by sum(D.cantidad) desc\n" +
                "limit 5\n";
        try{
            Statement statement=connectDB.createStatement();
            ResultSet q= statement.executeQuery(Query);

            TableView<Data_kg> TV_prov = new TableView<>();
            final ObservableList<Data_kg> data_kges = FXCollections.observableArrayList();
            while(q.next()){
                data_kges.add(new Data_kg(q.getString("producto"),q.getString("unidades_vendidas")));
            }
            TableColumn TVPr_idprov = new TableColumn("Producto");
            TVPr_idprov.setCellValueFactory(new PropertyValueFactory<>("Producto"));
            TableColumn TVPr_prov = new TableColumn("Kilos_vendidos");
            TVPr_prov.setCellValueFactory(new PropertyValueFactory<>("Unidades_Vendidas"));

            TV_prov.setItems(data_kges);
            TV_prov.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            TV_prov.getColumns().addAll(TVPr_idprov, TVPr_prov);
            cuadroReportes.getChildren().add(TV_prov);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    @FXML
    void productosMenos(ActionEvent event) {
        cuadroReportes.getChildren().clear();
        tituloReporte.setText("Reporte de productos menos vendidos por kg");
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String Query="select P.producto, count(D.id_producto) veces_compradas, D.id_producto, sum(D.cantidad) unidades_vendidas, P.id_proveedor, PV.nombre nombre_proveedor\n" +
                "from ((abasto_db.detalles_orden D\n" +
                "inner join abasto_db.productos P ON D.id_producto = P.id_producto)\n" +
                "inner join abasto_db.proveedores PV ON P.id_proveedor = PV.id_proveedor)\n" +
                "group by id_producto\n" +
                "order by sum(D.cantidad) asc\n" +
                "limit 5\n";
        try{
            Statement statement=connectDB.createStatement();
            ResultSet q= statement.executeQuery(Query);

            TableView<Data_kg> TV_prov = new TableView<>();
            final ObservableList<Data_kg> data_kges = FXCollections.observableArrayList();
            while(q.next()){
                data_kges.add(new Data_kg(q.getString("producto"),q.getString("veces_compradas")));
            }
            TableColumn TVPr_idprov = new TableColumn("Producto");
            TVPr_idprov.setCellValueFactory(new PropertyValueFactory<>("Producto"));
            TableColumn TVPr_prov = new TableColumn("Unidades_Vendidas");
            TVPr_prov.setCellValueFactory(new PropertyValueFactory<>("Unidades_Vendidas"));

            TV_prov.setItems(data_kges);
            TV_prov.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            TV_prov.getColumns().addAll(TVPr_idprov, TVPr_prov);
            cuadroReportes.getChildren().add(TV_prov);
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }





    @FXML
    void proveedorMas(ActionEvent event) {
        cuadroReportes.getChildren().clear();
        tituloReporte.setText("Reporte de proveedores que han vendido más");
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String Query="select count(D.id_producto) veces_compradas, D.id_producto, sum(D.cantidad) unidades_vendidas, P.id_proveedor, PV.nombre nombre_proveedor, sum(D.precio) ganancia\n" +
                "from ((abasto_db.detalles_orden D\n" +
                "inner join abasto_db.productos P ON D.id_producto = P.id_producto)\n" +
                "inner join abasto_db.proveedores PV ON P.id_proveedor = PV.id_proveedor)\n" +
                "group by PV.nombre\n" +
                "order by sum(D.precio) desc\n" +
                "limit 5\n";
        try{
            Statement statement=connectDB.createStatement();
            ResultSet q= statement.executeQuery(Query);

            TableView<Data_prov> TV_prov = new TableView<>();
            final ObservableList<Data_prov> data_proves = FXCollections.observableArrayList();
            while(q.next()){
                data_proves.add(new Data_prov(q.getString("nombre_proveedor"),"$"+q.getString("ganancia")));
            }
            TableColumn TVPr_idprov = new TableColumn("Proveedor");
            TVPr_idprov.setCellValueFactory(new PropertyValueFactory<>("Proveedor"));
            TableColumn TVPr_prov = new TableColumn("Ventas");
            TVPr_prov.setCellValueFactory(new PropertyValueFactory<>("Unidades_Vendidas"));

            TV_prov.setItems(data_proves);
            TV_prov.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            TV_prov.getColumns().addAll(TVPr_idprov, TVPr_prov);
            cuadroReportes.getChildren().add(TV_prov);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    void proveedorMenos(ActionEvent event) {
        cuadroReportes.getChildren().clear();
        tituloReporte.setText("Reporte de proveedores que han vendido menos");
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String Query="select count(D.id_producto) veces_compradas, D.id_producto, sum(D.cantidad) unidades_vendidas, P.id_proveedor, PV.nombre nombre_proveedor, sum(D.precio) ganancia\n" +
                "from ((abasto_db.detalles_orden D\n" +
                "inner join abasto_db.productos P ON D.id_producto = P.id_producto)\n" +
                "inner join abasto_db.proveedores PV ON P.id_proveedor = PV.id_proveedor)\n" +
                "group by PV.nombre\n" +
                "order by sum(D.precio) asc\n" +
                "limit 5\n";
        try{
            Statement statement=connectDB.createStatement();
            ResultSet q= statement.executeQuery(Query);

            TableView<Data_prov> TV_prov = new TableView<>();
            final ObservableList<Data_prov> data_proves = FXCollections.observableArrayList();
            while(q.next()){
                data_proves.add(new Data_prov(q.getString("nombre_proveedor"),"$"+q.getString("ganancia")));
            }
            TableColumn TVPr_idprov = new TableColumn("Proveedor");
            TVPr_idprov.setCellValueFactory(new PropertyValueFactory<>("Proveedor"));
            TableColumn TVPr_prov = new TableColumn("Ventas");
            TVPr_prov.setCellValueFactory(new PropertyValueFactory<>("Unidades_Vendidas"));

            TV_prov.setItems(data_proves);
            TV_prov.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            TV_prov.getColumns().addAll(TVPr_idprov, TVPr_prov);
            cuadroReportes.getChildren().add(TV_prov);
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }




















    @FXML
    void actualizarC(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ProductosyProveedores.fxml"));
        Parent root =fxmlLoader.load();
        Scene scene=new Scene(root);
        Stage stage1=new Stage();
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setScene(scene);
        ProductosyProveedoresController controller=fxmlLoader.getController();
        controller.ctexto("Categorias");
        stage1.showAndWait();


    }

    @FXML
    void agregarC(ActionEvent event) {
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String id=IDC.getText();
        String categoria=CATEGORIAC.getText();
        boolean ban=false;
        ban=buscarCategorias(categoria);
        if(categoria.equals("")){
            alerta("Falta dato","No se puede dejar vacia la casilla de categoria");
        }
        else if(ban){
            alerta("Dato incorrecto","No se puede agregar una categoría existente");
        }
        else {
            String Query="INSERT INTO `abasto_db`.`categorias` (`categoria`) VALUES ( '"+categoria+"');";
            try{
                Statement statement=connectDB.createStatement();
                statement.executeUpdate(Query);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

    }

    private boolean buscarCategorias(String categoriaNueva) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String Query = "SELECT * FROM abasto_db.categorias;";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(Query);
            while(queryOutput.next()){
                if(categoriaNueva.equals(queryOutput.getString("categoria"))){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    void agregarP(ActionEvent event) {

        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String id_producto=IDPRODUCTO.getText();
        String producto=PRODUCTO.getText();
        String id_categoria=IDCATEGORIA.getText();
        String precio_por_kg=PRECIOKG.getText();
        String kg_en_existencia=KGEISTENTE.getText();
        String archivo_imagen=RUTAI.getText();
        String id_proveedor=IDPROVEEDOR.getText();
        llenarIDs();
        String mensaje="";
        String m2="";
        boolean ban=true;
        try {
            Float.parseFloat(id_categoria);
            Float.parseFloat(precio_por_kg);
            Float.parseFloat(kg_en_existencia);
            Float.parseFloat(id_proveedor);

        }
        catch (NumberFormatException ex){
            ban=false;
            mensaje="Ingreso mal alguno de las siguientes campos:\nid_categoria, precio_por_kilo, kg_en_existencia, id_proveedor";
        }
        if(!Id_categoria.containsKey(id_categoria)){
           m2="No existe el ID categoria que usted ingreso\n";
           ban=false;
        }
        if(!Id_proveedores.containsKey(id_proveedor)){
            m2=m2+"No existe el ID proveedor que usted ingreso\n";
            ban=false;
        }
        if(!m2.equals("")){
            alerta("Error",m2);
        }
       if(ban){
           String Query="INSERT INTO `abasto_db`.`productos` (`producto`, `id_categoria`, `precio_por_kg`, `kg_en_existencia`, `archivo_imagen`, `id_proveedor`) VALUES ('"+producto+"', '"+id_categoria+"', '"+precio_por_kg+"', '"+kg_en_existencia+"', '"+archivo_imagen+"', '"+id_proveedor+"');";
           try{
               Statement statement=connectDB.createStatement();
               statement.executeUpdate(Query);
           }
           catch(Exception e){
               e.printStackTrace();
           }
       }
       else{
           if(!mensaje.equals(""))
               alerta("Dato incorrecot",mensaje);
       }

    }

    @FXML
    void eliminarC(ActionEvent event) {
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String id=IDC.getText();
        boolean confir=confirmacion("eliminar la categoria cuyo id es "+id);
        if(confir){
            String Query="DELETE FROM `abasto_db`.`categorias` WHERE (`id_categoria` = '"+id+"');";
            try{
                Statement statement=connectDB.createStatement();
                //statement.executeQuery(Query);
                statement.executeUpdate(Query);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    void eliminarP(ActionEvent event) {

        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String id=IDPRODUCTO.getText();
        String Query="DELETE FROM `abasto_db`.`productos` WHERE (`id_producto` = '"+id+"');";
        boolean confir=confirmacion("eliminar el producto cuyo id es "+id);
        if(confir){
            try{
                Statement statement=connectDB.createStatement();
                //statement.executeQuery(Query);
                statement.executeUpdate(Query);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }


    }

    @FXML
    void modifiarC(ActionEvent event) {

        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String id=IDC.getText();
        String nuevaC=CATEGORIAC.getText();
        String Query="UPDATE `abasto_db`.`categorias` SET `categoria` = '"+nuevaC+"' WHERE (`id_categoria` = '"+id+"');";
        boolean confir=confirmacion("modificar el catalogo cuyo id es "+id);
        if(confir) {
            try {
                Statement statement = connectDB.createStatement();
                statement.executeUpdate(Query);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    String agregarQuery(String set,String string,String variable){
        if(!variable.equals("")){
            if(set.equals("")){
                set="`"+string+"` = '"+variable+"'";
            }
            else{
                set=set+","+"`"+string+"` = '"+variable+"'";
            }
        }
        return set;
    }
    @FXML
    void modificarP(ActionEvent event) {
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String sett="";
        String id_producto=IDPRODUCTO.getText();
        String producto=PRODUCTO.getText();
        if(!producto.equals("")){
           sett="`producto` = '"+producto+"'";
        }
        String id_categoria=IDCATEGORIA.getText();
        sett=agregarQuery(sett,"id_categoria",id_categoria);
        String precio_por_kg=PRECIOKG.getText();
        sett=agregarQuery(sett,"precio_por_kg",precio_por_kg);
        String kg_en_existencia=KGEISTENTE.getText();
        sett=agregarQuery(sett,"kg_en_existencia",kg_en_existencia);
        String archivo_imagen=RUTAI.getText();
        sett=agregarQuery(sett,"archivo_imagen",archivo_imagen);
        String id_proveedor=IDPROVEEDOR.getText();
        sett=agregarQuery(sett,"id_proveedor",id_proveedor);
        llenarIDs();
        String mensaje="";
        String m2="";
        boolean ban=true;
        /*try {
            Float.parseFloat(id_categoria);
            Float.parseFloat(precio_por_kg);
            Float.parseFloat(kg_en_existencia);
            Float.parseFloat(id_proveedor);

        }
        catch (NumberFormatException ex){
            ban=false;
            mensaje="Ingreso letras en lugar de numeros en algún campo como:\nid_categoria, precio_por_kilo, kg_en_existencia, id_proveedor";
        }*/
        if(!Id_categoria.containsKey(id_categoria)){
            m2="No existe el ID categoria que usted ingreso\n";
            ban=false;
            if(id_categoria.equals("")){
                ban=true;
                m2="";
            }

        }
        if(!Id_proveedores.containsKey(id_proveedor)){
            m2=m2+"No existe el ID proveedor que usted ingreso\n";
            ban=false;
            if(id_proveedor.equals("")){
                ban=true;
                m2="";
            }
        }
        if(!m2.equals("")){
            alerta("Error",m2);
        }
        if(id_producto.equals("")) {
            alerta("Error","No ingreso ningún ID de producto");
            ban=false;

        }
        if(ban) {
            boolean confir = confirmacion("modificar el producto cuyo id es " + id_producto);
            String Query = "UPDATE `abasto_db`.`productos` SET  " + sett + " WHERE (`id_producto` = '" + id_producto + "');";
            if (confir) {
                try {
                    Statement statement = connectDB.createStatement();
                    statement.executeUpdate(Query);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }

    @FXML
    void verProductos(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ProductosyProveedores.fxml"));
        Parent root =fxmlLoader.load();
        Scene scene=new Scene(root);
        Stage stage1=new Stage();
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setScene(scene);
        ProductosyProveedoresController controller=fxmlLoader.getController();
        controller.ctexto("Productos");
        stage1.showAndWait();

    }

    @FXML
    void verProveedores(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ProductosyProveedores.fxml"));
        Parent root =fxmlLoader.load();
        Scene scene=new Scene(root);
        Stage stage1=new Stage();
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.setScene(scene);
        ProductosyProveedoresController controller=fxmlLoader.getController();
        controller.ctexto("Proveedores");
        stage1.showAndWait();

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
    public void alerta(String titulo,String mensaje){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    void llenarIDs(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String Query = "SELECT * FROM abasto_db.proveedores;";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet q = statement.executeQuery(Query);

            while(q.next()){
                Id_proveedores.put(q.getString("id_proveedor"),q.getString("id_proveedor"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        Query = "SELECT * FROM abasto_db.categorias;";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(Query);
            String cadena = "";

            //cadena = cadena + "\n" + queryOutput.getString("id_categoria") + queryOutput.getString("categoria");
            TableView<ProductosyProveedoresController.Data_Categoria> TV_cat = new TableView<>();
            final ObservableList<ProductosyProveedoresController.Data_Categoria> data_categorias = FXCollections.observableArrayList(

            );
            while(queryOutput.next()){
                Id_categoria.put(queryOutput.getString("id_categoria"),queryOutput.getString("id_categoria"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    boolean confirmacion(String estas_seguro){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText("¿Estas seguro de "+estas_seguro+" ?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }
}
