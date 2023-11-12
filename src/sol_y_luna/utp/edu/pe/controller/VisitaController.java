
package sol_y_luna.utp.edu.pe.controller;

import java.text.SimpleDateFormat;
import java.util.List;
import sol_y_luna.utp.edu.pe.model.dao.VisitaDao;
import sol_y_luna.utp.edu.pe.model.dto.Visita;
import sol_y_luna.utp.edu.pe.model.dto.VisitaEnCurso;
import sol_y_luna.utp.edu.pe.model.dto.VisitaReporte;


public class VisitaController {

    private final static String MENSAJE_OK = "SE GUARDÓ LA INFORMACIÓN CORRECTAMENTE";
    private final static String MENSAJE_ERROR = "OCURRIÓ UN ERROR INESPERADO, POR FAVOR CONTACTE A SOPORTE";
    private final static Integer ANIO_VIGENTE = 2023;

    public VisitaController() {
    }

    public String insertarVisita(Visita objVisita) {
        VisitaDao habitacionDao = new VisitaDao();
        boolean resultado = habitacionDao.insertarVisita(objVisita);
        String mensaje;

        if (resultado) {
            mensaje = MENSAJE_OK;
        } else {
            mensaje = MENSAJE_ERROR;
        }

        return mensaje;
    }

    public List<VisitaEnCurso> listarVisitasVigentes() {
        VisitaDao habitacionDao = new VisitaDao();
        List<VisitaEnCurso> visitaVigente = habitacionDao.listarVisitasVigentes();
        return visitaVigente;
    }

    public List<VisitaEnCurso> listarVisitasPorTerminar() {
        VisitaDao habitacionDao = new VisitaDao();
        List<VisitaEnCurso> visitaPorTerminar = habitacionDao.listarVisitasPorTerminar();
        return visitaPorTerminar;
    }

    public List<VisitaEnCurso> listarVisitasVigentesXNumHabitacion(int numVisita) {
        VisitaDao habitacionDao = new VisitaDao();
        List<VisitaEnCurso> visitaPorTerminar = habitacionDao.listarVisitasVigentesXNumHabitacion(numVisita);
        return visitaPorTerminar;
    }

    public boolean terminarVisita(int numVisita) {
        VisitaDao habitacionDao = new VisitaDao();
        boolean resultado = habitacionDao.terminarVisita(numVisita);
        return resultado;
    }

    public String obtenerReporte(int tipoReporte, int mes, Double valorTotal) {
        List<VisitaReporte> reporteResultado;
        StringBuilder resultado = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        switch (tipoReporte) {
            case 1:
                reporteResultado = obtenerHabitacionesMasUsadas();
                valorTotal = null;
                break;
            case 2:
                reporteResultado = obtenerResumenVisitasDelDia();
                valorTotal = reporteResultado.stream()
                        .mapToDouble(VisitaReporte::getPrecioHabitacion)
                        .sum();
                break;
            case 3:
                reporteResultado = obtenerResumenVisitasDeMes(ANIO_VIGENTE, mes);
                valorTotal = reporteResultado.stream()
                        .mapToDouble(VisitaReporte::getPrecioHabitacion)
                        .sum();
                break;
            default:
                throw new AssertionError();
        }

        if (tipoReporte == 1) {
            if (!reporteResultado.isEmpty()) {
                resultado.append("ID\tHabitación\tFecha de Entrada\tFecha de Salida\tRécord\n");
                for (VisitaReporte visita : reporteResultado) {
                    resultado.append(String.format("%-3d\t%-11d\t%-40s\t%-40s\t%d\n",
                            visita.getId(),
                            visita.getNroHabitacion(),
                            "-",
                            "-",
                            visita.getVecesUsada()
                    ));
                }                
            } else {
                resultado.append("No hay resultados para mostrar.\n");
            }
        }

        if (tipoReporte == 2 || tipoReporte == 3) {
            if (!reporteResultado.isEmpty()) {
                resultado.append("ID\tHabitación\tFecha de Entrada\tFecha de Salida\tImporte\n");
                for (VisitaReporte visita : reporteResultado) {
                    resultado.append(String.format("%-3d\t%-11d\t%-40s\t%-40s\t%.2f\n",
                            visita.getId(),
                            visita.getNroHabitacion(),
                            dateFormat.format(visita.getFechaEntrada()),
                            dateFormat.format(visita.getFechaSalida()),
                            visita.getPrecioHabitacion()
                    ));
                }
                resultado.append("-".repeat(144)).append("\n");
                resultado.append(" ".repeat(161)).append("TOTAL: \t").append(valorTotal);
            } else {
                resultado.append("No hay resultados para mostrar.\n");
            }
        }

        return resultado.toString();
    }

    private List<VisitaReporte> obtenerHabitacionesMasUsadas() {
        VisitaDao habitacionDao = new VisitaDao();
        List<VisitaReporte> resultado = habitacionDao.obtenerHabitacionesMasUsadas();
        return resultado;
    }

    private List<VisitaReporte> obtenerResumenVisitasDelDia() {
        VisitaDao visitaDao = new VisitaDao();
        List<VisitaReporte> resultado = visitaDao.obtenerResumenVisitasDelDia();
        return resultado;
    }

    private List<VisitaReporte> obtenerResumenVisitasDeMes(int year, int month) {
        VisitaDao visitaDao = new VisitaDao();
        List<VisitaReporte> resultado = visitaDao.obtenerResumenVisitasDeMes(year, month);
        return resultado;
    }
}
