
package sol_y_luna.utp.edu.pe.model.dto;


public class Habitacion {
    
    private Integer nroHabitacion;
    private Integer nroCamas;
    private Integer piso;
    private Double precio;
    private String estado;

    public Habitacion() {
    }

    public Habitacion(Integer nroHabitacion, Integer nroCamas, Integer piso, Double precio, String estado) {
        this.nroHabitacion = nroHabitacion;
        this.nroCamas = nroCamas;
        this.piso = piso;
        this.precio = precio;
        this.estado = estado;
    }    

    public Integer getNroHabitacion() {
        return nroHabitacion;
    }

    public void setNroHabitacion(Integer nroHabitacion) {
        this.nroHabitacion = nroHabitacion;
    }

    public Integer getNroCamas() {
        return nroCamas;
    }

    public void setNroCamas(Integer nroCamas) {
        this.nroCamas = nroCamas;
    }

    public Integer getPiso() {
        return piso;
    }

    public void setPiso(Integer piso) {
        this.piso = piso;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}
