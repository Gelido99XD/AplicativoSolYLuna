package sol_y_luna.utp.edu.pe.model.dao;

import sol_y_luna.utp.edu.pe.model.dto.Habitacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HabitacionDao extends DataSource {

    public boolean crearHabitacion(Habitacion habitacion) {
        Connection con = getConexion();
        String sql = "INSERT INTO Habitacion (nroHabitacion, nroCamas, piso, precio, estado) VALUES (?, ?, ?, ?, ?)";
        
        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, habitacion.getNroHabitacion());
            statement.setInt(2, habitacion.getNroCamas());
            statement.setInt(3, habitacion.getPiso());
            statement.setDouble(4, habitacion.getPrecio());
            statement.setString(5, habitacion.getEstado());

            int filasInsertadas = statement.executeUpdate();
            return filasInsertadas > 0;
        } catch (SQLException e) {
            
            e.printStackTrace();
            return false;
            
        }
    }

    public List<Habitacion> listarHabitaciones() {
        List<Habitacion> habitaciones = new ArrayList<>();
        Connection con = getConexion();
        String sql = "SELECT * FROM Habitacion";
        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Habitacion habitacion = new Habitacion();
                habitacion.setNroHabitacion(resultSet.getInt("nroHabitacion"));
                habitacion.setNroCamas(resultSet.getInt("nroCamas"));
                habitacion.setPiso(resultSet.getInt("piso"));
                habitacion.setPrecio(resultSet.getDouble("precio"));
                habitacion.setEstado(resultSet.getString("estado"));
                habitaciones.add(habitacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return habitaciones;
    }

    public List<Habitacion> buscarHabitacionesPorEstado(String estado) {
        List<Habitacion> habitaciones = new ArrayList<>();
        Connection con = getConexion();
        String sql = "SELECT * FROM Habitacion WHERE estado=?";
        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, estado);
            ResultSet resultSet = statement.executeQuery();
          
            while (resultSet.next()) {
                
                Habitacion habitacion = new Habitacion();
                habitacion.setNroHabitacion(resultSet.getInt("nroHabitacion"));
                habitacion.setNroCamas(resultSet.getInt("nroCamas"));
                habitacion.setPiso(resultSet.getInt("piso"));
                habitacion.setPrecio(resultSet.getDouble("precio"));
                habitacion.setEstado(resultSet.getString("estado"));
                habitaciones.add(habitacion);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return habitaciones;
    }

    public Habitacion buscarHabitacionesPorNro(int nroHabitacion) {
        Habitacion habitacion = null;
        Connection con = getConexion();
        String sql = "SELECT * FROM Habitacion WHERE nroHabitacion=?";
        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, nroHabitacion);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                habitacion = new Habitacion();
                habitacion.setNroHabitacion(resultSet.getInt("nroHabitacion"));
                habitacion.setNroCamas(resultSet.getInt("nroCamas"));
                habitacion.setPiso(resultSet.getInt("piso"));
                habitacion.setPrecio(resultSet.getDouble("precio"));
                habitacion.setEstado(resultSet.getString("estado"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            habitacion = null;
        }
        return habitacion;
    }

    public boolean actualizarHabitacion(Habitacion habitacion) {
        Connection con = getConexion();
        String sql = "UPDATE Habitacion SET nroCamas=?, piso=?, precio=?, estado=? WHERE nroHabitacion=?";
        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, habitacion.getNroCamas());
            statement.setInt(2, habitacion.getPiso());
            statement.setDouble(3, habitacion.getPrecio());
            statement.setString(4, habitacion.getEstado());
            statement.setInt(5, habitacion.getNroHabitacion());

            int filasActualizadas = statement.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean usarHabitacion(int nroHabitacion) {
        Connection con = getConexion();
        String sql = "UPDATE Habitacion SET estado='EN USO' WHERE nroHabitacion=? AND estado = 'DISPONIBLE'";
        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, nroHabitacion);

            int filasActualizadas = statement.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean limpiarHabitacion(int nroHabitacion) {
        Connection con = getConexion();
        String sql = "UPDATE Habitacion SET estado='LIMPIEZA' WHERE nroHabitacion=? AND estado = 'EN USO'";
        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, nroHabitacion);

            int filasActualizadas = statement.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean disponibilizarHabitacion(int nroHabitacion) {
        Connection con = getConexion();
        String sql = "UPDATE Habitacion SET estado='DISPONIBLE' WHERE nroHabitacion=? AND estado = 'LIMPIEZA'";
        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, nroHabitacion);

            int filasActualizadas = statement.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
