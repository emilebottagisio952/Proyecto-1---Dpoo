package elementos;
import java.time.LocalDateTime;
import java.util.List;

public class RegistroLearningPath {

	private String loginEstudiante;
	private LocalDateTime fechaInscrito;
	private LocalDateTime fechaTerminado;
	private List<RegistroActividad> RegistrosA;
	private String estado;
	
	//Constructor
	public RegistroLearningPath(String loginEstudiante, LocalDateTime fechaInscrito, LocalDateTime fechaTerminado,
			List<RegistroActividad> registrosA) {
		this.loginEstudiante = loginEstudiante;
		this.fechaInscrito = fechaInscrito;
		this.fechaTerminado = fechaTerminado;
		this.RegistrosA = registrosA;
		this.estado = "Iniciado";
	}

	//Getters y setters
	public String getLoginEstudiante() {
		return loginEstudiante;
	}

	public void setLoginEstudiante(String loginEstudiante) {
		this.loginEstudiante = loginEstudiante;
	}

	public LocalDateTime getFechaInscrito() {
		return fechaInscrito;
	}

	public void setFechaInscrito(LocalDateTime fechaInscrito) {
		this.fechaInscrito = fechaInscrito;
	}

	public LocalDateTime getFechaTerminado() {
		return fechaTerminado;
	}

	public void setFechaTerminado(LocalDateTime fechaTerminado) {
		this.fechaTerminado = fechaTerminado;
	}

	public List<RegistroActividad> getRegistrosA() {
		return RegistrosA;
	}

	public void setRegistrosA(List<RegistroActividad> registrosA) {
		RegistrosA = registrosA;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
