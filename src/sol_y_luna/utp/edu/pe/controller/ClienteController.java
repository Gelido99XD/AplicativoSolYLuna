
package sol_y_luna.utp.edu.pe.controller;

import java.util.List;
import sol_y_luna.utp.edu.pe.model.dto.Cliente;
import sol_y_luna.utp.edu.pe.model.dao.ClienteDao;
//comentario de Usuario2 cliente

public class ClienteController {

    private final static String MENSAJE_OK = "SE GUARDÓ LA INFORMACIÓN CORRECTAMENTE";
    private final static String MENSAJE_ERROR_UPDATE = "NO SE ENCONTRO EL CLIENTE";
    private final static String MENSAJE_ERROR = "OCURRIÓ UN ERROR INESPERADO, POR FAVOR CONTACTE A SOPORTE";

    public ClienteController() {
    }

    public String registrarCliente(Cliente objCliente) {
        
        ClienteDao clienteDao = new ClienteDao();
        String mensaje;
        boolean resultado;

        if (objCliente.getTipoDocumento() == 2) {
            String numeroDocumento = String.format("%011d", Integer.valueOf(objCliente.getNroDocumento()));
            objCliente.setNroDocumento(numeroDocumento);
        }

        resultado = clienteDao.registrarCliente(objCliente);

        if (resultado) {
            mensaje = MENSAJE_OK;
        } else {
            mensaje = MENSAJE_ERROR;
        }

        return mensaje;
    }

    public String actualizarCliente(Cliente objCliente) {
        
        ClienteDao clienteDao = new ClienteDao();
        String mensaje;
        int resultado;

        if (objCliente.getTipoDocumento() == 2) {
            String numeroDocumento = String.format("%011d", Integer.valueOf(objCliente.getNroDocumento()));
            objCliente.setNroDocumento(numeroDocumento);
        }

        resultado = clienteDao.actualizarClientePorID(objCliente);

        switch (resultado) {
            case 0:
                mensaje = MENSAJE_OK;
                break;
            case 1:
                mensaje = MENSAJE_ERROR_UPDATE;
                break;
            default:
                mensaje = MENSAJE_ERROR;
                break;
        }

        return mensaje;
    }

    public List<Cliente> listarClientes() {
        ClienteDao clienteDao = new ClienteDao();
        List<Cliente> clientes = clienteDao.listarClientes();

        return clientes;
    }

    public Cliente buscarCliente(String nroDocumento) {
        ClienteDao clienteDao = new ClienteDao();
        Cliente objCliente = clienteDao.buscarCliente(nroDocumento);

        return objCliente;
    }

}
