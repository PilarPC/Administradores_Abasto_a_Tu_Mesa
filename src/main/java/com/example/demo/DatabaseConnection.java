package com.example.demo;
import java.sql.Connection;
import java.sql.DriverManager;
public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection(){
        /*String databaseName="abasto_db";
        String databaseUser="root";
        String databasePassword="";
        String url="jdbc:mysql://localhost:3306/"+databaseName;
        try {
           Class.forName("com.mysql.cj.jdbc.Driver");
           databaseLink=DriverManager.getConnection(url,databaseUser,databasePassword);
        }catch(Exception e){

        }*/

        String databaseName="abasto_db";
        String databaseUser="abasto_root@abasto";
        String databasePassword="MXPanamericana22";
        String url="jdbc:mysql://abasto.mysql.database.azure.com:3306/"+databaseName;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink=DriverManager.getConnection(url,databaseUser,databasePassword);
        }catch(Exception e){
            e.printStackTrace();
        }
        return databaseLink;
    }
}
