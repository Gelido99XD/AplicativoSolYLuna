package sol_y_luna.utp.edu.pe.model.dao;

import sol_y_luna.utp.edu.pe.model.dto.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao extends DataSource {

    public boolean registrarCliente(Cliente cliente) {
        Connection con = getConexion();
        String sql = "INSERT INTO cliente (tipoDocumento, nroDocumento, nombre, apellido, telefono, correo) VALUES (?, ?, ?, ?, ?, ?)";
        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, cliente.getTipoDocumento());
            statement.setString(2, cliente.getNroDocumento());
            statement.setString(3, cliente.getNombre());
            statement.setString(4, cliente.getApellido());
            statement.setString(5, cliente.getTelefono());
            statement.setString(6, cliente.getCorreo());
            statement.executeUpdate();
            return true; 
        } catch (SQLException e) { 
            System.err.println("Error al registrar el cliente: " + e.getMessage());
            return false;
        }
        
    }

    public int actualizarClientePorID(Cliente cliente) {
        Connection con = getConexion();
        String sql = "UPDATE Cliente SET tipoDocumento=?, nroDocumento=?, nombre=?, apellido=?, telefono=?, correo=? WHERE id=?";
        int resultado;
        
        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setInt(1, cliente.getTipoDocumento());
            statement.setString(2, cliente.getNroDocumento());
            statement.setString(3, cliente.getNombre());
            statement.setString(4, cliente.getApellido());
            statement.setString(5, cliente.getTelefono());
            statement.setString(6, cliente.getCorreo());
            statement.setInt(7, cliente.getId());

            int filasActualizadas = statement.executeUpdate();
            if (filasActualizadas > 0) {
                resultado = 0;
            } else {
                resultado = -1;
            }
        } catch (SQLException e) {
            
            resultado = -999;
            System.err.println("Error al registrar el cliente: " + e.getMessage());
        }
        
        return resultado;
    }

    public List<Cliente> listarClientes() {
        
        Connection con = getConexion();
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setTipoDocumento(resultSet.getInt("tipoDocumento"));
                cliente.setNroDocumento(resultSet.getString("nroDocumento"));
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setApellido(resultSet.getString("apellido"));
                cliente.setTelefono(resultSet.getString("telefono"));
                cliente.setCorreo(resultSet.getString("correo"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Error al registrar el cliente: " + e.getMessage());
        }
        return clientes;
    }

    public Cliente buscarCliente(String nroDocumento) {
        Connection con = getConexion();
        String sql = "SELECT * FROM Cliente WHERE nroDocumento = ?";
        try ( PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, nroDocumento);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setTipoDocumento(resultSet.getInt("tipoDocumento"));
                cliente.setNroDocumento(resultSet.getString("nroDocumento"));
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setApellido(resultSet.getString("apellido"));
                cliente.setTelefono(resultSet.getString("telefono"));
                cliente.setCorreo(resultSet.getString("correo"));
                return cliente;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Error al registrar el cliente: " + e.getMessage());
            return null;
        }
    }
}
