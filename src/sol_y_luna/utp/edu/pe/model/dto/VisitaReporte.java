
package sol_y_luna.utp.edu.pe.model.dto;


public class VisitaReporte extends Visita{
    
    private Integer vecesUsada;
    private Double precioHabitacion;

    public Integer getVecesUsada() {
        return vecesUsada;
    }

    public void setVecesUsada(Integer vecesUsada) {
        this.vecesUsada = vecesUsada;
    }

    public Double getPrecioHabitacion() {
        return precioHabitacion;
    }

    public void setPrecioHabitacion(Double precioHabitacion) {
        this.precioHabitacion = precioHabitacion;
    }
    
}
