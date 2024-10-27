package procesamiento;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import elementos.Actividad;
import elementos.Opcion;
import elementos.Pregunta;
import elementos.PreguntaAbierta;
import elementos.PreguntaMultiple;
import elementos.RegistroActividad;
import elementos.RegistroLearningPath;

public class ProcesamientoRegistros {

	HashMap<Integer, ArrayList<RegistroLearningPath>> registrosLP;
	
	public ProcesamientoRegistros() {
		this.registrosLP = new HashMap<>();
	}
	
	public void InscribirEstudiante(int idLP, String loginActual, ProcesamientoActividad AC, ProcesamientoLearningPath LPC) {
		LocalDateTime fecha = LocalDateTime.now();
		RegistroLearningPath rlp = new RegistroLearningPath(loginActual, fecha, fecha, CrearRegistros(idLP, AC, LPC));
		if (!(registrosLP.containsKey(idLP))) {
			ArrayList<RegistroLearningPath> registros = new ArrayList<>();
			registros.add(rlp);
			registrosLP.put(idLP, registros);
		} else {
			ArrayList<RegistroLearningPath> registros = registrosLP.get(idLP);
			registros.add(rlp);
			registrosLP.put(idLP, registros);
		}
	}
	
	public float TiempoDedicadoPorActividad(int idLP) {
		float terminados = 0;
		float tiempo = 0;
		
		ArrayList<RegistroLearningPath> registros = registrosLP.get(idLP);
		for (RegistroLearningPath rlp : registros) {
			List<RegistroActividad> registrosA = rlp.getRegistrosA();
			for (RegistroActividad ra : registrosA) {
				if (ra.getEstado().equals("Completada")) {
					terminados++;
					tiempo += ra.getFechaInicio().until(ra.getFechaTerminado(), ChronoUnit.MINUTES);
				}
			}
		}
		if (terminados != 0) {
			return tiempo / terminados;
		}
		return 0;
	}
	
	public float PorcentajeCompletado(int idLP) {
		ArrayList<RegistroLearningPath> registros = registrosLP.get(idLP);
		float completados = 0;
		for (RegistroLearningPath rlp : registros) {
			if (RevisarEstadoRLP(rlp)) {
				completados++;
			}
		}
		if (registros.size() != 0) {
			return (completados / registros.size() * 100);
		}
		return 0;
	}
	
	public boolean RevisarEstadoRLP(RegistroLearningPath rlp) {
		if (rlp.getEstado().equals("Completada")) {
			return true;
		} else {
			return false;
		}
	}
	
	public List<RegistroActividad> CrearRegistros(int idLP, ProcesamientoActividad AC, ProcesamientoLearningPath LPC) {
		HashMap<Actividad, Boolean> mapaActividades = LPC.learningPaths.get(idLP).getActividades();
		ArrayList<RegistroActividad> registros = new ArrayList<>();
		Set<Actividad> actividades = mapaActividades.keySet();
		for (Actividad actividad : actividades) {
			HashMap<String, String> respuestas = new HashMap<>();
			RegistroActividad RA;
			List<PreguntaAbierta> preguntas;
			List<PreguntaMultiple> preguntasMultiples;
			switch (actividad.getTipo()) {
				case "Tarea":
					RA = new RegistroActividad(actividad.getId(), "No enviado", respuestas, mapaActividades.get(actividad), 0);
					registros.add(RA);
					break;
				case "RecursoEducativo":
					RA = new RegistroActividad(actividad.getId(), "No enviado", respuestas, mapaActividades.get(actividad), 0);
					registros.add(RA);
					break;
				case "Encuesta":
					preguntas = AC.GetPreguntasAbiertas(actividad);
					for (Pregunta pregunta : preguntas) {
						respuestas.put(pregunta.getTextoPregunta(), null);
					}
					RA = new RegistroActividad(actividad.getId(), "No enviado", respuestas, mapaActividades.get(actividad), 0);
					registros.add(RA);
					break;
				case "Quiz":
					preguntasMultiples = AC.GetPreguntasMultiples(actividad);
					for (Pregunta pregunta : preguntasMultiples) {
						respuestas.put(pregunta.getTextoPregunta(), null);
					}
					RA = new RegistroActividad(actividad.getId(), "No enviado", respuestas, mapaActividades.get(actividad), 0);
					registros.add(RA);
					break;
				case "Examen":
					preguntas = AC.GetPreguntasAbiertas(actividad);
					for (Pregunta pregunta : preguntas) {
						respuestas.put(pregunta.getTextoPregunta(), null);
					}
					RA = new RegistroActividad(actividad.getId(), "No enviado", respuestas, mapaActividades.get(actividad), 0);
					registros.add(RA);
					break;
			}
		}
		return registros;
	}

	public void DesarrollarActividad(int idLP, int idA, String tipo, String loginActual, Scanner input, ProcesamientoActividad AC) {
		List<RegistroActividad> lra = null;
		RegistroActividad registro = null;
		ArrayList<RegistroLearningPath> lrlp = registrosLP.get(idLP);
		for (RegistroLearningPath rlp : lrlp) {
			if (rlp.getLoginEstudiante().equals(loginActual)) {
				lra = rlp.getRegistrosA();
			}
		}
		for (RegistroActividad ra : lra) {
			if (ra.getIdActividad() == idA) {
				registro = ra;
			}
		}
		Actividad actividad = AC.GetActividad(idA);
		HashMap<String,String> respuestas;
		Set<String> preguntas;
		String respuesta;
		switch (tipo) {
		case "Tarea":
			System.out.println("Lea la descripcion de la tarea, cuando ya la haya enviado al profesor, ingrese Y");
			do {
				respuesta = input.nextLine();
			} while (respuesta.equals("Y"));
			registro.setEstado("Enviada");
			System.out.println("Ha completado la actividad con exito");
			break;
		case "RecursoEducativo":
			System.out.println("Lea la descripcion de la tarea, cuando ya la haya enviado al profesor, ingrese Y");
			do {
				respuesta = input.nextLine();
			} while (!(respuesta.equals("Y")));
			registro.setEstado("Completada");
			registro.setFechaTerminado(LocalDateTime.now());
			System.out.println("Ha completado la actividad con exito");
			break;
		case "Encuesta":
			respuestas = registro.getRespuestas();
			preguntas = respuestas.keySet();
			for (String pregunta : preguntas) {
				System.out.println(pregunta);
				System.out.println("Ingrese su respuesta: ");
				respuesta = input.nextLine();
				respuestas.put(pregunta, respuesta);
			}
			registro.setEstado("Completada");
			registro.setFechaTerminado(LocalDateTime.now());
			System.out.println("Ha completado la actividad con exito");
			break;
		case "Quiz":
			String textoA = null, textoB = null, textoC = null, textoD = null;
			respuestas = registro.getRespuestas();
			preguntas = respuestas.keySet();
			List<PreguntaMultiple> opciones = actividad.getPreguntasMultiples();
			for (PreguntaMultiple pregunta : opciones) {
				System.out.println(pregunta.getTextoPregunta());
				List<Opcion> variantes = pregunta.getOpciones();
				textoA = variantes.get(0).getTextoOpcion();
				System.out.printf("A. %s\n", textoA);
				
				textoB = variantes.get(1).getTextoOpcion();
				System.out.printf("B. %s\n", textoB);
				
				textoC = variantes.get(2).getTextoOpcion();
				System.out.printf("C. %s\n", textoC);
				
				textoD = variantes.get(3).getTextoOpcion();
				System.out.printf("D. %s\n", textoD);
				
				System.out.println("Ingrese la letra de la opcion que quiere elegir: ");
				do {
				respuesta = input.nextLine();
				} while (!(respuesta.equals("A") || respuesta.equals("B") || respuesta.equals("C") || respuesta.equals("D")));
				switch (respuesta) {
					case "A":
					respuestas.put(pregunta.getTextoPregunta(), textoA);
						break;
					case "B":
						respuestas.put(pregunta.getTextoPregunta(), textoB);
						break;
					case "C":
						respuestas.put(pregunta.getTextoPregunta(), textoC);
						break;
					case "D":
						respuestas.put(pregunta.getTextoPregunta(), textoD);
						break;
				}
			}
			registro.setEstado("Completada");
			registro.setFechaTerminado(LocalDateTime.now());
			System.out.println("Ha completado la actividad con exito");
			break;
		case "Examen":
			respuestas = registro.getRespuestas();
			preguntas = respuestas.keySet();
			for (String pregunta : preguntas) {
				System.out.println(pregunta);
				System.out.println("Ingrese su respuesta: ");
				respuesta = input.nextLine();
				respuestas.put(pregunta, respuesta);
			}
			registro.setEstado("Enviada");
			System.out.println("Ha enviado la actividad con exito");
			break;
		}
	}
 	
	public void MostrarProgreso (int idLP, String login) {
		RegistroLearningPath rlp = null;
		if (registrosLP.containsKey(idLP)) {
			ArrayList<RegistroLearningPath> registros = registrosLP.get(idLP);
			for (RegistroLearningPath registro : registros) {
				if (registro.getLoginEstudiante().equals(login)) {
					rlp = registro;
				}
			}
			if (rlp != null) {
				System.out.println("Esta es la informacion para el estudiante: ");
				System.out.printf("Estado: %s\n", rlp.getEstado());
				System.out.printf("Fecha de inscripcion: %s\n", rlp.getFechaInscrito());
				System.out.printf("Login: %s\n", rlp.getLoginEstudiante());
				for (RegistroActividad ra : rlp.getRegistrosA()) {
					System.out.printf("Actividad: %s\n", ra.getIdActividad());
					System.out.printf("Estado: %s\n", ra.getEstado());
					if (ra.getRespuestas() != null) {
						System.out.println("Respuestas:");
						HashMap<String, String> respuestas = ra.getRespuestas();
						Set<String> preguntas = respuestas.keySet();
						if (!(ra.getEstado().equals("No enviado"))) {
							for (String pregunta : preguntas) {
								System.out.printf("\nPregunta: %s\n", pregunta);
								System.out.printf("Respuesta: %s\n", respuestas.get(pregunta));
							}
						} else {
							System.out.println("No hay preguntas respondidas");
						}
					}
				}
			} else {
				System.out.println("El estudiante no esta inscrito en el learning path");
			}
		} else {
			System.out.println("El learning path no existe o no tiene estudiantes registrados");
		}
	}
}
