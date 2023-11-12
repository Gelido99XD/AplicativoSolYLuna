
package sol_y_luna.utp.edu.pe.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sol_y_luna.utp.edu.pe.model.dto.CaracteristicaServicio;


public class CaracteristicaDao extends DataSource {

    public List<CaracteristicaServicio> buscarServicios(Integer numHabitacion) {
        List<CaracteristicaServicio> servicios = new ArrayList<>();
        Connection con = getConexion();
        String sql = "SELECT c.nroHabitacion, s.nombreServicio "
                + "FROM Caracteristica c INNER JOIN Servicio s ON c.idServicio = s.id "
                + "WHERE c.nroHabitacion = ?";
        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, numHabitacion);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CaracteristicaServicio servicio = new CaracteristicaServicio();
                servicio.setNroHabitacion(resultSet.getInt("nroHabitacion"));
                servicio.setNombreServicio(resultSet.getString("nombreServicio"));
                servicios.add(servicio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servicios;
    }

}
