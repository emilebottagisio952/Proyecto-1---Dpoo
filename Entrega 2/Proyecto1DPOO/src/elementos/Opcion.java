package elementos;

public class Opcion {

	private String explicacion;
	private Boolean correcta;
	private String textoOpcion;
	
	//Constructor
	public Opcion(String textoOpcion, String explicacion, Boolean correcta) {
		this.textoOpcion = textoOpcion;
		this.explicacion = explicacion;
		this.correcta = correcta;
	}
	
	//Getters y Setters
	public String getTextoOpcion() {
		return textoOpcion;
	}
	public void setTextoOpcion(String textoOpcion) {
		this.textoOpcion = textoOpcion;
	}
	public String getExplicacion() {
		return explicacion;
	}
	public void setExplicacion(String explicacion) {
		this.explicacion = explicacion;
	}
	public Boolean getCorrecta() {
		return correcta;
	}
	public void setCorrecta(Boolean correcta) {
		this.correcta = correcta;
	}
	
}
