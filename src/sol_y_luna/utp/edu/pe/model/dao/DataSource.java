package sol_y_luna.utp.edu.pe.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {

    public Connection getConexion() {

        String jdbcUrl = "jdbc:mysql://localhost:3306/hotel_sol_y_luna";
        String usuario = "root";
        String contraseña = "Rzo22ca+-#.";

        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contraseña);

            return conexion;
        } catch (ClassNotFoundException | SQLException e) {
            
            System.out.println(e.getMessage());
            return null;
            
        }
    }
}
