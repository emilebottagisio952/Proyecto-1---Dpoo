package elementos;

import java.util.HashMap;
import java.time.LocalDateTime;

public class LearningPath {

	private String titulo;
	private String descripcionGeneral;
	private String nivelDificultad;
	private int duracion;
	private LocalDateTime fechaCreacion;
	private LocalDateTime fechaModificacion;
	private int version;
	private HashMap<Actividad, Boolean> actividades;
	private String loginCreador;
	
	//Constructor
	public LearningPath(String titulo, String descripcionGeneral, String nivelDificultad, int duracion,
			LocalDateTime fechaCreacion, LocalDateTime fechaModificacion, int version, HashMap<Actividad, Boolean> actividades, String loginCreador) {
		super();
		this.titulo = titulo;
		this.descripcionGeneral = descripcionGeneral;
		this.nivelDificultad = nivelDificultad;
		this.duracion = duracion;
		this.fechaCreacion = fechaCreacion;
		this.fechaModificacion = fechaModificacion;
		this.version = version;
		this.actividades = actividades;
		this.loginCreador = loginCreador;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcionGeneral() {
		return descripcionGeneral;
	}

	public void setDescripcionGeneral(String descripcionGeneral) {
		this.descripcionGeneral = descripcionGeneral;
	}

	public String getNivelDificultad() {
		return nivelDificultad;
	}

	public void setNivelDificultad(String nivelDificultad) {
		this.nivelDificultad = nivelDificultad;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(LocalDateTime fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public HashMap<Actividad, Boolean> getActividades() {
		return actividades;
	}

	public void setActividades(HashMap<Actividad, Boolean> actividades) {
		this.actividades = actividades;
	}

	public String getLoginCreador() {
		return loginCreador;
	}

	public void setLoginCreador(String loginCreador) {
		this.loginCreador = loginCreador;
	}	
}
