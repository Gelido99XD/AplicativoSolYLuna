
package sol_y_luna.utp.edu.pe.model.dao;

import sol_y_luna.utp.edu.pe.model.dto.Visita;
import sol_y_luna.utp.edu.pe.model.dto.VisitaEnCurso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import sol_y_luna.utp.edu.pe.model.dto.VisitaReporte;


public class VisitaDao extends DataSource {

    public boolean insertarVisita(Visita objVisita) {
        
        Connection con = getConexion();
        String sql = "INSERT INTO Visita (idCliente, nroHabitacion, fechaEntrada, fechaSalida) VALUES (?, ?, ?, ?)";
        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            // Obtener la fecha y hora actual
            Date fechaEntrada = new Date();

            // Calcular la fecha y hora de salida (8 horas después)
            long tiempoEntrada = fechaEntrada.getTime();
            long tiempoSalida = tiempoEntrada + (8 * 60 * 60 * 1000); // 8 horas en milisegundos
            Date fechaSalida = new Date(tiempoSalida);

            // Convertir las fechas a objetos Timestamp para la base de datos
            Timestamp entradaTimestamp = new Timestamp(fechaEntrada.getTime());
            Timestamp salidaTimestamp = new Timestamp(fechaSalida.getTime());

            statement.setInt(1, objVisita.getIdCliente());
            statement.setInt(2, objVisita.getNroHabitacion());
            statement.setTimestamp(3, entradaTimestamp);
            statement.setTimestamp(4, salidaTimestamp);

            int filasInsertadas = statement.executeUpdate();
            return filasInsertadas > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean terminarVisita(int numVisita) {
        Connection con = getConexion();
        String sql = "UPDATE Visita SET estado=0 WHERE id=?";
        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, numVisita);

            int filasActualizadas = statement.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<VisitaEnCurso> listarVisitasVigentes() {
        List<VisitaEnCurso> visitasVigentes = new ArrayList<>();
        Connection con = getConexion();
        String sql = "SELECT v.id, v.nroHabitacion, CONCAT(c.nombre, ' ', c.apellido) AS cliente, v.fechaEntrada, v.fechaSalida "
                + "FROM Visita v INNER JOIN Cliente c ON v.idCliente = c.id "
                + "WHERE v.fechaSalida >= NOW()"
                + "AND v.estado = 1";
        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                VisitaEnCurso visita = new VisitaEnCurso();
                visita.setId(resultSet.getInt("id"));
                visita.setNroHabitacion(resultSet.getInt("nroHabitacion"));
                visita.setCliente(resultSet.getString("cliente"));
                visita.setFechaEntrada(resultSet.getTimestamp("fechaEntrada"));
                visita.setFechaSalida(resultSet.getTimestamp("fechaSalida"));
                visitasVigentes.add(visita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visitasVigentes;
    }

    public List<VisitaEnCurso> listarVisitasPorTerminar() {
        List<VisitaEnCurso> visitasVigentes = new ArrayList<>();
        Connection con = getConexion();
        String sql = "SELECT v.id, v.nroHabitacion, h.estado ,CONCAT(c.nombre, ' ', c.apellido) AS cliente, v.fechaEntrada, v.fechaSalida "
                + "FROM Visita v "
                + "INNER JOIN Cliente c ON v.idCliente = c.id "
                + "INNER JOIN Habitacion h on v.nroHabitacion = h.nroHabitacion "
                + "WHERE v.fechaSalida >= NOW() "
                + "AND v.fechaSalida <= DATE_ADD(NOW(), INTERVAL 15 MINUTE);";
        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                VisitaEnCurso visita = new VisitaEnCurso();
                visita.setId(resultSet.getInt("id"));
                visita.setNroHabitacion(resultSet.getInt("nroHabitacion"));
                visita.setEstadoHabitacion(resultSet.getString("estado"));
                visita.setCliente(resultSet.getString("cliente"));
                visita.setFechaEntrada(resultSet.getTimestamp("fechaEntrada"));
                visita.setFechaSalida(resultSet.getTimestamp("fechaSalida"));
                visitasVigentes.add(visita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visitasVigentes;
    }

    public List<VisitaEnCurso> listarVisitasVigentesXNumHabitacion(Integer numVisita) {
        List<VisitaEnCurso> visitasVigentes = new ArrayList<>();
        Connection con = getConexion();
        String sql = "SELECT v.id, v.nroHabitacion, h.estado ,CONCAT(c.nombre, ' ', c.apellido) AS cliente, v.fechaEntrada, v.fechaSalida "
                + "FROM Visita v "
                + "INNER JOIN Cliente c ON v.idCliente = c.id "
                + "INNER JOIN Habitacion h on v.nroHabitacion = h.nroHabitacion "
                + "WHERE v.fechaSalida >= NOW() "
                + "AND v.nroHabitacion = ? ";

        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, numVisita);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                VisitaEnCurso visita = new VisitaEnCurso();
                visita.setId(resultSet.getInt("id"));
                visita.setNroHabitacion(resultSet.getInt("nroHabitacion"));
                visita.setEstadoHabitacion(resultSet.getString("estado"));
                visita.setCliente(resultSet.getString("cliente"));
                visita.setFechaEntrada(resultSet.getTimestamp("fechaEntrada"));
                visita.setFechaSalida(resultSet.getTimestamp("fechaSalida"));
                visitasVigentes.add(visita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visitasVigentes;
    }

    public List<VisitaReporte> obtenerHabitacionesMasUsadas() {
        List<VisitaReporte> habitacionesMasUsadas = new ArrayList<>();
        Connection con = getConexion();
        String sql = "SELECT ROW_NUMBER() OVER (ORDER BY COUNT(*) DESC) AS id_correlativo, v.nroHabitacion, h.precio, MIN(v.fechaEntrada) AS fecha_entrada, MAX(v.fechaSalida) AS fecha_salida, COUNT(*) AS vecesUsada "
                + "FROM Visita v "
                + "INNER JOIN Habitacion h ON v.nroHabitacion = h.nroHabitacion "
                + "GROUP BY v.nroHabitacion, h.precio "
                + "ORDER BY vecesUsada DESC;";

        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                VisitaReporte visitaReporte = new VisitaReporte();
                visitaReporte.setId(resultSet.getInt("id_correlativo"));
                visitaReporte.setNroHabitacion(resultSet.getInt("nroHabitacion"));
                visitaReporte.setPrecioHabitacion(resultSet.getDouble("precio"));
                visitaReporte.setFechaEntrada(resultSet.getTimestamp("fecha_entrada"));
                visitaReporte.setFechaSalida(resultSet.getTimestamp("fecha_salida"));
                visitaReporte.setVecesUsada(resultSet.getInt("vecesUsada"));
                habitacionesMasUsadas.add(visitaReporte);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return habitacionesMasUsadas;
    }

    public List<VisitaReporte> obtenerResumenVisitasDelDia() {
        List<VisitaReporte> resumenVisitasDelDia = new ArrayList<>();
        Connection con = getConexion();
        String sql = "SELECT v.id, h.nroHabitacion, h.precio, v.fechaEntrada, v.fechaSalida "
                + "FROM Visita v "
                + "INNER JOIN Habitacion h ON v.nroHabitacion = h.nroHabitacion "
                + "WHERE DATE(v.fechaEntrada) = CURDATE()";

        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                VisitaReporte visitaReporte = new VisitaReporte();
                visitaReporte.setId(resultSet.getInt("id"));
                visitaReporte.setNroHabitacion(resultSet.getInt("nroHabitacion"));
                visitaReporte.setPrecioHabitacion(resultSet.getDouble("precio"));
                visitaReporte.setFechaEntrada(resultSet.getTimestamp("fechaEntrada"));
                visitaReporte.setFechaSalida(resultSet.getTimestamp("fechaSalida"));
                resumenVisitasDelDia.add(visitaReporte);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resumenVisitasDelDia;
    }

    public List<VisitaReporte> obtenerResumenVisitasDeMes(int year, int month) {
        List<VisitaReporte> resumenVisitasDeMes = new ArrayList<>();
        Connection con = getConexion();
        String sql = "SELECT v.id, h.nroHabitacion, h.precio, v.fechaEntrada, v.fechaSalida "
                + "FROM Visita v "
                + "INNER JOIN Habitacion h ON v.nroHabitacion = h.nroHabitacion "
                + "WHERE YEAR(v.fechaEntrada) = ? AND MONTH(v.fechaEntrada) = ?";

        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, year);
            statement.setInt(2, month);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                VisitaReporte visitaReporte = new VisitaReporte();
                visitaReporte.setId(resultSet.getInt("id"));                
                visitaReporte.setNroHabitacion(resultSet.getInt("nroHabitacion"));
                visitaReporte.setPrecioHabitacion(resultSet.getDouble("precio"));
                visitaReporte.setFechaEntrada(resultSet.getTimestamp("fechaEntrada"));
                visitaReporte.setFechaSalida(resultSet.getTimestamp("fechaSalida"));
                resumenVisitasDeMes.add(visitaReporte);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resumenVisitasDeMes;
    }

}
