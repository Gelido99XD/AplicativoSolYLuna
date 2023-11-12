package sol_y_luna.utp.edu.pe.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sol_y_luna.utp.edu.pe.model.dto.Usuario;

public class UsuarioDao extends DataSource {

    public Usuario validarUsuario(Usuario usuario) {
        
        Connection con = getConexion();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Usuario usuarioValidado = null;

        
        try {

            String sql = "SELECT * FROM Usuario WHERE usuario = ? AND password = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getPassword());

            rs = stmt.executeQuery();

            if (rs.next()) {

                usuarioValidado = new Usuario();
                usuarioValidado.setUsuario(rs.getString("usuario"));
                usuarioValidado.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarioValidado;
    }
}
