package procesamiento;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import elementos.Actividad;
import elementos.Estudiante;
import elementos.Opcion;
import elementos.PreguntaAbierta;
import elementos.PreguntaMultiple;

public class ProcesamientoActividad {
	
	HashMap<Integer, Actividad> actividades;
	
	public ProcesamientoActividad() {
	    this.actividades = new HashMap<>();
	}
	
	public void ImprimirActividades() {
		Set<Integer> ids = actividades.keySet();
		System.out.println("\nEstas son las actividades disponibles (El numero a su lado corresponde a su id).");
		for (int id : ids) {
			System.out.printf("%d.", id);
			System.out.printf("\n Descripcion: %s.", actividades.get(id).getDescripcion());
			System.out.printf("\n Creador: %s.", actividades.get(id).getLoginCreador());
			System.out.printf("\n Tipo: %s.", actividades.get(id).getTipo());
			System.out.printf("\n Nivel de dificultad: %s.", actividades.get(id).getNivelDificultad());
			System.out.printf("\n Duracion en minutos: %s.\n", actividades.get(id).getDuracion());
		}
	}
	
	public void ImprimirActividades(ArrayList<Integer> ids) {
		System.out.println("\nEstas son las actividades disponibles en el learning path (El numero a su lado corresponde a su id).");
		for (int id : ids) {
			System.out.printf("%d.", id);
			System.out.printf("\n Descripcion: %s.", actividades.get(id).getDescripcion());
			System.out.printf("\n Creador: %s.", actividades.get(id).getLoginCreador());
			System.out.printf("\n Tipo: %s.", actividades.get(id).getTipo());
			System.out.printf("\n Nivel de dificultad: %s.", actividades.get(id).getNivelDificultad());
			System.out.printf("\n Duracion en minutos: %s.\n", actividades.get(id).getDuracion());
		}
	}
	
	public Actividad GetActividad(int id) {
		return actividades.get(id);
	}
	
	public List<PreguntaAbierta> GetPreguntasAbiertas(Actividad a) {
		return a.getPreguntasAbiertas();
	}
	
	public List<PreguntaMultiple> GetPreguntasMultiples(Actividad a) {
		return a.getPreguntasMultiples();
	}
	
	public boolean VerificarCreador(int id, String loginActual) {
		if (actividades.get(id).getLoginCreador().equals(loginActual)) {
			return true;
		}
		return false;
	}
	
	public List<Actividad> GetActividades(List<Integer> ids) {
		ArrayList<Actividad> actividadesLista = new ArrayList<>();
		for (int id : ids) {
			actividadesLista.add(GetActividad(id));
		}
		return actividadesLista;
	}
	
	//Creacion Actividades
	public void CrearActividadExamen(String loginCreador, String tipo, String descripcion, String stringObjetivos,
			String nivelDificultad, int duracion, List<Integer> idActividadesPrevias, String fechaLimite,
			List<Integer> idActividadesSeguimiento, List<String> preguntas, float notaMinima) {
		int id = actividades.size() + 1;
		String[] objetivos = stringObjetivos.split(",");
		List<Actividad> actividadesPrevias = GetActividades(idActividadesPrevias);
		List<Actividad> actividadesSeguimiento = GetActividades(idActividadesSeguimiento);
		List<PreguntaAbierta> preguntasAbiertas = CrearPreguntas(preguntas);
		Actividad actividad = new Actividad(loginCreador, id, tipo, descripcion, objetivos, nivelDificultad, duracion, 
				actividadesPrevias, fechaLimite, actividadesSeguimiento, null, null, preguntasAbiertas, notaMinima);
		actividades.put(id, actividad);
	}
	private List<PreguntaAbierta> CrearPreguntas(List<String> preguntas) {
		ArrayList<PreguntaAbierta> preguntasAbiertas = new ArrayList<>();
		for (String pregunta : preguntas) {
			PreguntaAbierta preguntaAbierta = new PreguntaAbierta(pregunta);
			preguntasAbiertas.add(preguntaAbierta);
		}
		return preguntasAbiertas;
	}

	public void CrearActividadQuiz(String loginCreador, String tipo, String descripcion, String stringObjetivos,
			String nivelDificultad, int duracion, List<Integer> idActividadesPrevias, String fechaLimite,
			List<Integer> idActividadesSeguimiento, HashMap<String, HashMap<String, String>> preguntas, 
			List<Integer> correctas, float notaMinima) {
		int id = actividades.size() + 1;
		String[] objetivos = stringObjetivos.split(",");
		List<Actividad> actividadesPrevias = GetActividades(idActividadesPrevias);
		List<Actividad> actividadesSeguimiento = GetActividades(idActividadesSeguimiento);
		List<PreguntaMultiple> preguntasMultiples = CrearPreguntasMultiples(preguntas, correctas);
		Actividad actividad = new Actividad(loginCreador, id, tipo, descripcion, objetivos, nivelDificultad, duracion,
				actividadesPrevias, fechaLimite, actividadesSeguimiento, null, preguntasMultiples, null, notaMinima);
		actividades.put(id, actividad);
	}
	private List<PreguntaMultiple> CrearPreguntasMultiples(HashMap<String, HashMap<String, String>> preguntas, List<Integer> correctas) {
		ArrayList<PreguntaMultiple> preguntasMultiples = new ArrayList<>();
		Set<String> stringPreguntas = preguntas.keySet();
		for (String pregunta : stringPreguntas) {
			Set<String> stringOpciones = preguntas.get(pregunta).keySet();
			ArrayList<Opcion> opciones = new ArrayList<>();
			for (String opcion : stringOpciones) {
				Opcion opc;
				if (correctas.get(preguntasMultiples.size()) == opciones.size() + 1) {
					opc = new Opcion(opcion, preguntas.get(pregunta).get(opcion), true);
				} else {
					opc = new Opcion(opcion, preguntas.get(pregunta).get(opcion), false);
				}
				opciones.add(opc);
			}
			PreguntaMultiple preguntaMultiple = new PreguntaMultiple(pregunta, opciones);
			preguntasMultiples.add(preguntaMultiple);
		}
		return preguntasMultiples;
	}

	public void CrearActividadEncuesta(String loginCreador, String tipo, String descripcion, String stringObjetivos,
			String nivelDificultad, int duracion, List<Integer> idActividadesPrevias, String fechaLimite,
			List<Integer> idActividadesSeguimiento, List<String> preguntas) {
		int id = actividades.size() + 1;
		String[] objetivos = stringObjetivos.split(",");
		List<Actividad> actividadesPrevias = GetActividades(idActividadesPrevias);
		List<Actividad> actividadesSeguimiento = GetActividades(idActividadesSeguimiento);
		List<PreguntaAbierta> preguntasAbiertas = CrearPreguntas(preguntas);
		Actividad actividad = new Actividad(loginCreador, id, tipo, descripcion, objetivos, nivelDificultad, duracion, 
				actividadesPrevias, fechaLimite, actividadesSeguimiento, null, null, preguntasAbiertas, 0);
		actividades.put(id, actividad);
	}
	
	public void CrearActividadRecurso(String loginCreador, String tipo, String descripcion, String stringObjetivos,
			String nivelDificultad, int duracion, List<Integer> idActividadesPrevias, String fechaLimite,
			List<Integer> idActividadesSeguimiento, String url) {
		
		int id = actividades.size() + 1;
		String[] objetivos = stringObjetivos.split(",");
		List<Actividad> actividadesPrevias = GetActividades(idActividadesPrevias);
		List<Actividad> actividadesSeguimiento = GetActividades(idActividadesSeguimiento);
		Actividad actividad = new Actividad(loginCreador, id, tipo, descripcion, objetivos, nivelDificultad, duracion, 
				actividadesPrevias, fechaLimite, actividadesSeguimiento, url, null, null, 0);
		actividades.put(id, actividad);
	}
	
	public void CrearActividadTarea(String loginCreador, String tipo, String descripcion, String stringObjetivos,
			String nivelDificultad, int duracion, List<Integer> idActividadesPrevias, String fechaLimite,
			List<Integer> idActividadesSeguimiento) {
		int id = actividades.size() + 1;
		String[] objetivos = stringObjetivos.split(",");
		List<Actividad> actividadesPrevias = GetActividades(idActividadesPrevias);
		List<Actividad> actividadesSeguimiento = GetActividades(idActividadesSeguimiento);
		Actividad actividad = new Actividad(loginCreador, id, tipo, descripcion, objetivos, nivelDificultad, duracion, 
				actividadesPrevias, fechaLimite, actividadesSeguimiento, null, null, null, 0);
		actividades.put(id, actividad);
	}
	
	//Edicion Actividades
	public void CrearActividadExamen(String loginCreador, String tipo, String descripcion, String stringObjetivos,
			String nivelDificultad, int duracion, List<Integer> idActividadesPrevias, String fechaLimite,
			List<Integer> idActividadesSeguimiento, List<String> preguntas, float notaMinima, int idActividad) {
		int id = idActividad;
		String[] objetivos = stringObjetivos.split(",");
		List<Actividad> actividadesPrevias = GetActividades(idActividadesPrevias);
		List<Actividad> actividadesSeguimiento = GetActividades(idActividadesSeguimiento);
		List<PreguntaAbierta> preguntasAbiertas = CrearPreguntas(preguntas);
		Actividad actividad = new Actividad(loginCreador, id, tipo, descripcion, objetivos, nivelDificultad, duracion, 
				actividadesPrevias, fechaLimite, actividadesSeguimiento, null, null, preguntasAbiertas, notaMinima);
		actividades.put(id, actividad);
	}

	public void CrearActividadQuiz(String loginCreador, String tipo, String descripcion, String stringObjetivos,
			String nivelDificultad, int duracion, List<Integer> idActividadesPrevias, String fechaLimite,
			List<Integer> idActividadesSeguimiento, HashMap<String, HashMap<String, String>> preguntas,
			List<Integer> correctas, float notaMinima, int idActividad) {
		int id = idActividad;
		String[] objetivos = stringObjetivos.split(",");
		List<Actividad> actividadesPrevias = GetActividades(idActividadesPrevias);
		List<Actividad> actividadesSeguimiento = GetActividades(idActividadesSeguimiento);
		List<PreguntaMultiple> preguntasMultiples = CrearPreguntasMultiples(preguntas, correctas);
		Actividad actividad = new Actividad(loginCreador, id, tipo, descripcion, objetivos, nivelDificultad, duracion,
				actividadesPrevias, fechaLimite, actividadesSeguimiento, null, preguntasMultiples, null, notaMinima);
		actividades.put(id, actividad);
	}

	public void CrearActividadEncuesta(String loginCreador, String tipo, String descripcion, String stringObjetivos,
			String nivelDificultad, int duracion, List<Integer> idActividadesPrevias, String fechaLimite,
			List<Integer> idActividadesSeguimiento, List<String> preguntas, int idActividad) {
		int id = idActividad;
		String[] objetivos = stringObjetivos.split(",");
		List<Actividad> actividadesPrevias = GetActividades(idActividadesPrevias);
		List<Actividad> actividadesSeguimiento = GetActividades(idActividadesSeguimiento);
		List<PreguntaAbierta> preguntasAbiertas = CrearPreguntas(preguntas);
		Actividad actividad = new Actividad(loginCreador, id, tipo, descripcion, objetivos, nivelDificultad, duracion, 
				actividadesPrevias, fechaLimite, actividadesSeguimiento, null, null, preguntasAbiertas, 0);
		actividades.put(id, actividad);
	}
	
	public void CrearActividadRecurso(String loginCreador, String tipo, String descripcion, String stringObjetivos,
			String nivelDificultad, int duracion, List<Integer> idActividadesPrevias, String fechaLimite,
			List<Integer> idActividadesSeguimiento, String url, int idActividad) {
		
		int id = idActividad;
		String[] objetivos = stringObjetivos.split(",");
		List<Actividad> actividadesPrevias = GetActividades(idActividadesPrevias);
		List<Actividad> actividadesSeguimiento = GetActividades(idActividadesSeguimiento);
		Actividad actividad = new Actividad(loginCreador, id, tipo, descripcion, objetivos, nivelDificultad, duracion, 
				actividadesPrevias, fechaLimite, actividadesSeguimiento, url, null, null, 0);
		actividades.put(id, actividad);
	}
	
	public void CrearActividadTarea(String loginCreador, String tipo, String descripcion, String stringObjetivos,
			String nivelDificultad, int duracion, List<Integer> idActividadesPrevias, String fechaLimite,
			List<Integer> idActividadesSeguimiento, int idActividad) {
		int id = idActividad;
		String[] objetivos = stringObjetivos.split(",");
		List<Actividad> actividadesPrevias = GetActividades(idActividadesPrevias);
		List<Actividad> actividadesSeguimiento = GetActividades(idActividadesSeguimiento);
		Actividad actividad = new Actividad(loginCreador, id, tipo, descripcion, objetivos, nivelDificultad, duracion, 
				actividadesPrevias, fechaLimite, actividadesSeguimiento, null, null, null, 0);
		actividades.put(id, actividad);
	}
	   public void guardarActividadesEnArchivo(String nombreArchivo) throws IOException {
        String directorioRelativo = "Persistencia"; 
        File directorio = new File(directorioRelativo);
        
        // Asegúrate de que el directorio existe
        if (!directorio.exists()) {
            System.err.println("El directorio no existe. Se creará al guardar.");
            directorio.mkdirs(); // Crea el directorio si no existe
        }

        // Crea el archivo en la ruta deseada con el nombre proporcionado
        File archivo = new File(directorio, nombreArchivo); // Ahora usa el nombre del archivo proporcionado

        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo, true))) { // Modo apéndice
            // Guarda todos los estudiantes en el archivo
			for (Actividad actividad : actividades.values()) {
				writer.println(actividad.getDescripcion() + "," + actividad.getDuracion() + "," + actividad.getLoginCreador()+ "," + actividad.getNivelDificultad() + "," + actividad.getTipo()); // Guarda como login,password
				}
            System.out.println("Datos guardados exitosamente en " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
            throw e;
        }
    }
}
