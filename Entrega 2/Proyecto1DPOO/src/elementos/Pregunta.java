package elementos;

public abstract class Pregunta {

	private String textoPregunta;
	
	public Pregunta(String textoPregunta) {
		super();
		this.textoPregunta = textoPregunta;
	}

	//Getters y Setters
	public String getTextoPregunta() {
		return textoPregunta;
	}

	public void setTextoPregunta(String textoPregunta) {
		this.textoPregunta = textoPregunta;
	}
	
}
