
package sol_y_luna.utp.edu.pe.model.dto;


public class VisitaEnCurso extends Visita{
    
    private String cliente;
    private String estadoHabitacion;

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEstadoHabitacion() {
        return estadoHabitacion;
    }

    public void setEstadoHabitacion(String estado) {
        this.estadoHabitacion = estado;
    }
        
}
