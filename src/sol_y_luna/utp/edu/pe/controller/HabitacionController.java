
package sol_y_luna.utp.edu.pe.controller;

import java.util.List;
import sol_y_luna.utp.edu.pe.model.dao.HabitacionDao;
import sol_y_luna.utp.edu.pe.model.dto.Habitacion;


public class HabitacionController {
    private final static String DISPONIBLE = "DISPONIBLE";
    private final static String EN_USO = "EN USO";
    private final static String LIMPIEZA = "LIMPIEZA";
    private final static String MENSAJE_OK = "SE GUARDÓ LA INFORMACIÓN CORRECTAMENTE";
    private final static String MENSAJE_ERROR = "OCURRIÓ UN ERROR INESPERADO, POR FAVOR CONTACTE A SOPORTE";

    public String registrarHabitacion(Habitacion objHabitacion) {
        HabitacionDao habitacionDao = new HabitacionDao();
        String mensaje;
        boolean resultado;

        resultado = habitacionDao.crearHabitacion(objHabitacion);

        if (resultado) {
            mensaje = MENSAJE_OK;
        } else {
            mensaje = MENSAJE_ERROR;
        }

        return mensaje;
    }

    public String actualizarHabitacion(Habitacion objHabitacion) {
        
        HabitacionDao habitacionDao = new HabitacionDao();
        String mensaje;
        boolean resultado;

        resultado = habitacionDao.actualizarHabitacion(objHabitacion);

        if (resultado) {
            mensaje = MENSAJE_OK;
        } else {
            mensaje = MENSAJE_ERROR;
        }

        return mensaje;
    }

    public List<Habitacion> listarHabitaciones(int estado) {
        HabitacionDao habitacionDao = new HabitacionDao();
        List<Habitacion> habitaciones = null;

        switch (estado) {
            case 0:
                habitaciones = habitacionDao.listarHabitaciones();
                break;
            case 1:
                habitaciones = habitacionDao.buscarHabitacionesPorEstado(DISPONIBLE);
                break;
            case 2:
                habitaciones = habitacionDao.buscarHabitacionesPorEstado(EN_USO);
                break;
            case 3:
                habitaciones = habitacionDao.buscarHabitacionesPorEstado(LIMPIEZA);
                break;
        }

        return habitaciones;
    }

    public Habitacion buscarHabitacion(int nroHabitacion) {
        HabitacionDao habitacionDao = new HabitacionDao();
        Habitacion habitacion = habitacionDao.buscarHabitacionesPorNro(nroHabitacion);

        return habitacion;
    }

    public boolean usarHabitacion(int nroHabitacion) {
        
        HabitacionDao habitacionDao = new HabitacionDao();
        boolean resultado = habitacionDao.usarHabitacion(nroHabitacion);

        return resultado;
    }

    public boolean limpiarHabitacion(int nroHabitacion) {
        
        HabitacionDao habitacionDao = new HabitacionDao();
        boolean resultado = habitacionDao.limpiarHabitacion(nroHabitacion);

        return resultado;
    }

    public boolean disponibilizarHabitacion(int nroHabitacion) {
        
        HabitacionDao habitacionDao = new HabitacionDao();
        boolean resultado = habitacionDao.disponibilizarHabitacion(nroHabitacion);

        return resultado;
    }
}
