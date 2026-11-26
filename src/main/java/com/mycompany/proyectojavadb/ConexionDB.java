/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectojavadb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL =
            "jdbc:sqlserver://localhost\\SQLEXPRESS;"
            + "databaseName=Avanzada1;"
            + "encrypt=true;"
            + "trustServerCertificate=true;";

    private static final String USER = "sa";
    private static final String PASSWORD = "EbisFuen200704junio";

    private static Connection conexion = null;

    private ConexionDB() {}

    public static Connection getConexion() {

        try {

            if (conexion == null || conexion.isClosed()) {

                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                conexion = DriverManager.getConnection(URL, USER, PASSWORD);

                System.out.println("--> Conexion Exitosa!!!!!");
            }

        } catch (ClassNotFoundException e) {

            System.err.println("Error no se encontro el driver: " + e.getMessage());

        } catch (SQLException e) {

            System.err.println("Error de SQL al conectar a la base de datos: " + e.getMessage());
        }

        return conexion;
    }

    public static void cerrarConexion() {

        try {

            if (conexion != null && !conexion.isClosed()) {

                conexion.close();

                System.out.println("--> Conexion con SQL Server es Cerrada");
            }

        } catch (SQLException e) {

            System.err.println("Error al cerrar la conexion: " + e.getMessage());
        }
    }
}