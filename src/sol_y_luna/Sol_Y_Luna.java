
package sol_y_luna;

import java.util.List;
import sol_y_luna.utp.edu.pe.model.dto.Cliente;
import sol_y_luna.utp.edu.pe.model.dao.ClienteDao;
import sol_y_luna.utp.edu.pe.model.dao.DataSource;


public class Sol_Y_Luna {

   
    public static void main(String[] args) {
        // TODO code application logic here
        //Cliente cliente = new Cliente(1,"76572863","Jerry","Aguilar","982521527","jerry.cueto@gmail.com");
        
        ClienteDao daoCliente = new ClienteDao();
        //daoCliente.registrarCliente(cliente);
        
        Cliente clienteRes = daoCliente.buscarCliente("76572863");
        System.out.println(clienteRes.getApellido());
        
    }
    
}
