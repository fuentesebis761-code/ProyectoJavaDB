/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectojavadb;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

/**
 *
 * @author HP1
 */
public class ProyectoJavaDB {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection cn = ConexionDB.getConexion();

        if (cn == null) {
            System.out.println("No se pudo conectar a la base de datos");
            return;
        }

        int opcion = 0;
        while (opcion != 5) {
            System.out.println("\n=== MENU CRUD ALUMNOS ===");
            System.out.println("1. Insertar Alumno");
            System.out.println("2. Consultar Alumnos");
            System.out.println("3. Actualizar Alumno");
            System.out.println("4. Eliminar Alumno");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    insertarAlumno(cn, sc);
                    break;
                case 2:
                    consultarAlumnos(cn);
                    break;
                case 3:
                    actualizarAlumno(cn, sc);
                    break;
                case 4:
                    eliminarAlumno(cn, sc);
                    break;
                case 5:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        }
        ConexionDB.cerrarConexion();
        sc.close();
    }

    
    public static void insertarAlumno(Connection cn, Scanner sc) {
        System.out.print("Ingrese nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese correo: ");
        String correo = sc.nextLine();

        String sql = "INSERT INTO Alumnos(nombre, correo) VALUES(?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, correo);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Alumno Ingresado Exitosamente");
             
                System.out.println("\n--- Alumno Ingresado ---");
                consultarAlumnos(cn);
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar: " + e.getMessage());
        }
    }

    public static void consultarAlumnos(Connection cn) {
        String sql = "SELECT id, nombre, correo FROM Alumnos";
        try (PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n==============================================");
            System.out.println("ID       |    Nombre       |    Correo");
            System.out.println("==============================================");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                System.out.printf("%-3d   | %-20s | %s\n", id, nombre, correo);
            }
            System.out.println("==============================================");
        } catch (SQLException e) {
            System.err.println("Error al consultar: " + e.getMessage());
        }
    }

    public static void actualizarAlumno(Connection cn, Scanner sc) {
        consultarAlumnos(cn);
        System.out.print("Ingrese el ID del alumno a actualizar: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Ingrese nuevo nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Ingrese nuevo correo: ");
        String correo = sc.nextLine();

        String sql = "UPDATE Alumnos SET nombre = ?, correo = ? WHERE id = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setInt(3, id);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Alumno Actualizado Exitosamente");
                consultarAlumnos(cn);
            } else {
                System.out.println("No se encontro alumno con ese ID");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
        }
    }

    public static void eliminarAlumno(Connection cn, Scanner sc) {
        consultarAlumnos(cn);
        System.out.print("Ingrese el ID del alumno a eliminar: ");
        int id = sc.nextInt();
        sc.nextLine();

        String sql = "DELETE FROM Alumnos WHERE id = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Alumno Eliminado Exitosamente");
                consultarAlumnos(cn);
            } else {
                System.out.println("No se encontro alumno con ese ID");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar: " + e.getMessage());
        }
    }
}