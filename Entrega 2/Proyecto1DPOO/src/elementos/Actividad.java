package elementos;

import java.util.List;

public class Actividad {
    private String loginCreador;
    private int id;
    private String tipo;
    private String descripcion;
    private String[] objetivos;
    private String nivelDificultad;
    private int duracion;
    private List<Actividad> actividadesPrevias;
    private String fechaLimite;
    private List<Actividad> actividadesSeguimiento;
    private String url;
    private List<PreguntaMultiple> preguntasMultiples;
    private List<PreguntaAbierta> preguntasAbiertas;
    private float notaMinima;

    // Constructor ajustado
    public Actividad(String loginCreador, int id, String tipo, String descripcion, String[] objetivos,
                     String nivelDificultad, int duracion, List<Actividad> actividadesPrevias, String fechaLimite,
                     List<Actividad> actividadesSeguimiento, String url, List<PreguntaMultiple> preguntasMultiples,
                     List<PreguntaAbierta> preguntasAbiertas, float notaMinima) {
        this.loginCreador = loginCreador;
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.objetivos = objetivos;
        this.nivelDificultad = nivelDificultad;
        this.duracion = duracion;
        this.actividadesPrevias = actividadesPrevias;
        this.fechaLimite = fechaLimite;
        this.actividadesSeguimiento = actividadesSeguimiento;
        this.url = url;
        this.preguntasMultiples = preguntasMultiples;
        this.preguntasAbiertas = preguntasAbiertas;
        this.notaMinima = notaMinima;
    }
	
	//Getters y Setters
	public String getLoginCreador() {
		return loginCreador;
	}

	public void setLoginCreador(String loginCreador) {
		this.loginCreador = loginCreador;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String[] getObjetivos() {
		return objetivos;
	}

	public void setObjetivos(String[] objetivos) {
		this.objetivos = objetivos;
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

	public List<Actividad> getActividadesPrevias() {
		return actividadesPrevias;
	}

	public void setActividadesPrevias(List<Actividad> actividadesPrevias) {
		this.actividadesPrevias = actividadesPrevias;
	}

	public String getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(String fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	public List<Actividad> getActividadesSeguimiento() {
		return actividadesSeguimiento;
	}

	public void setActividadesSeguimiento(List<Actividad> actividadesSeguimiento) {
		this.actividadesSeguimiento = actividadesSeguimiento;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<PreguntaMultiple> getPreguntasMultiples() {
		return preguntasMultiples;
	}

	public void setPreguntasMultiples(List<PreguntaMultiple> preguntasMultiples) {
		this.preguntasMultiples = preguntasMultiples;
	}

	public List<PreguntaAbierta> getPreguntasAbiertas() {
		return preguntasAbiertas;
	}

	public void setPreguntasAbiertas(List<PreguntaAbierta> preguntasAbiertas) {
		this.preguntasAbiertas = preguntasAbiertas;
	}
	
	public float getNotaMinima() {
		return notaMinima;
	}
	
	public void setNotaMinima(float notaMinima) {
		this.notaMinima = notaMinima;
	}
}
