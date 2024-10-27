package elementos;

import java.util.List;

public class PreguntaMultiple extends Pregunta {

	private List<Opcion> opciones;
	
	//Constructor
	public PreguntaMultiple(String textoPregunta, List<Opcion> opciones) {
		super(textoPregunta);
		this.opciones = opciones;
	}

	//Getters y Setters
	public List<Opcion> getOpciones() {
		return opciones;
	}

	public void setOpciones(List<Opcion> opciones) {
		this.opciones = opciones;
	}
	
}
