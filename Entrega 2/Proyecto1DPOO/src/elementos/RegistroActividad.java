package elementos;

import java.time.LocalDateTime;
import java.util.HashMap;

public class RegistroActividad {
	
	private int idActividad;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaTerminado;
	private String estado;
	private HashMap<String, String> respuestas;
	private boolean obligatoria;
	private float nota;
	
	//Constructor
	public RegistroActividad(int idActividad, String estado, HashMap<String, String> respuestas, boolean obligatoria, float nota) {
		this.idActividad = idActividad;
		this.estado = estado;
		this.respuestas = respuestas;
		this.obligatoria = obligatoria;
		this.nota = nota;
	}
	
	//Getters y setters
	public int getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaTerminado() {
		return fechaTerminado;
	}

	public void setFechaTerminado(LocalDateTime fechaTerminado) {
		this.fechaTerminado = fechaTerminado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public HashMap<String, String> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(HashMap<String, String> respuestas) {
		this.respuestas = respuestas;
	}

	public boolean isObligatoria() {
		return obligatoria;
	}

	public void setObligatoria(boolean obligatoria) {
		this.obligatoria = obligatoria;
	}
	
	public float getNota() {
		return nota;
	}

	public void setNota(float nota) {
		this.nota = nota;
	}
}
