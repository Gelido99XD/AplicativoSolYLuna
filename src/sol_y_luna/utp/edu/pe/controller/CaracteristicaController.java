
package sol_y_luna.utp.edu.pe.controller;

import java.util.List;
import sol_y_luna.utp.edu.pe.model.dao.CaracteristicaDao;
import sol_y_luna.utp.edu.pe.model.dto.CaracteristicaServicio;


public class CaracteristicaController {

    public CaracteristicaController() {
    }

    public List<CaracteristicaServicio> buscarHabitacion(int nroHabitacion) {
        
        CaracteristicaDao caracteristicaDao = new CaracteristicaDao();
        List<CaracteristicaServicio> servicios = caracteristicaDao.buscarServicios(nroHabitacion);

        return servicios;
    }

}
