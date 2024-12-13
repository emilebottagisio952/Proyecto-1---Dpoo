package procesamiento;


import elementos.Actividad;
import elementos.Opcion;
import elementos.PreguntaAbierta;
import elementos.PreguntaMultiple;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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

		
	
	private List<Integer> parseIds(String idsString) {
		List<Integer> ids = new ArrayList<>();
		if (!idsString.equals("null")) {
			String[] idsArray = idsString.split(",");
			for (String id : idsArray) {
				ids.add(Integer.parseInt(id.trim()));
			}
		}
		return ids;
	}

	
public void ImprimirActividadesVarias(ArrayList<Integer> ids) {
    System.out.println("\nActividades seleccionadas:");
    boolean hayActividades = false;
    for (int id : ids) {
        Actividad actividad = actividades.get(id);
        if (actividad != null) {
            System.out.printf("ID: %d\n", actividad.getId());
            System.out.printf(" Descripcion: %s\n", actividad.getDescripcion());
            System.out.printf(" Creador: %s\n", actividad.getLoginCreador());
            System.out.printf(" Tipo: %s\n", actividad.getTipo());
            System.out.printf(" Nivel de dificultad: %s\n", actividad.getNivelDificultad());
            System.out.printf(" Duracion en minutos: %d\n\n", actividad.getDuracion());
            hayActividades = true;
        } else {
            System.out.printf("La actividad con ID %d no existe.\n", id);
        }
    }
    if (!hayActividades) {
        System.out.println("No se encontraron actividades para los IDs proporcionados.");
    }
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
		

		if (!directorio.exists()) {
			System.err.println("El directorio no existe. Se creará al guardar.");
			directorio.mkdirs();
		}

		File archivo = new File(directorio, nombreArchivo); 
	
		try (PrintWriter writer = new PrintWriter(new FileWriter(archivo, true))) { 

			for (Actividad actividad : actividades.values()) {
				writer.println(actividad.getDescripcion() + ";" + actividad.getDuracion() + ";" + actividad.getLoginCreador() + ";" + actividad.getNivelDificultad() + ";" + actividad.getTipo() + ";" + actividad.getFechaLimite() + ";" +  actividad.getNotaMinima());
			}
			System.out.println("Datos guardados exitosamente en " + archivo.getAbsolutePath());
		} catch (IOException e) {
			System.err.println("Error al guardar los datos: " + e.getMessage());
			throw e;
		}
	}
	 public void cargarACDesdeArchivo(String nombreArchivo) throws IOException {
        if (actividades == null) {
        	actividades = new HashMap<>();
        }

        String directorioRelativo = "Persistencia"; 
        File archivo = new File(directorioRelativo, nombreArchivo);

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parte = line.split(";"); 
                if (parte.length >= 7) {
                    String descripcion = parte[0];
                    Integer duracion = Integer.parseInt(parte[1]);
					String logInCreador = parte[2];
					String dificultad = parte[3]; 
					String tipo = parte[4];
					String fecha = parte[5];
					float notaMinima = Float.parseFloat(parte[6]);
					String objetivos = "a,b,c,d";
					List<Integer> idActividadesPrevias = new ArrayList<>();
					List<Integer> idActividadesSeguimiento = new ArrayList<>();
					List<String> preguntas = new ArrayList<>();
					HashMap<String, HashMap<String, String>> preguntasMultiples = new HashMap<>();
					List<Integer> correctas = new ArrayList<>();
					switch (tipo) {
						case "Tarea":
							CrearActividadTarea(logInCreador,tipo,descripcion,objetivos,dificultad,duracion,idActividadesPrevias,fecha,idActividadesSeguimiento);
							break;
						case "RecursoEducativo":
							CrearActividadRecurso(logInCreador,tipo,descripcion,objetivos,dificultad,duracion,idActividadesPrevias,fecha,idActividadesSeguimiento,"");
							break;
						case "Encuesta":
							CrearActividadEncuesta(logInCreador,tipo,descripcion,objetivos,dificultad,duracion,idActividadesPrevias,fecha,idActividadesSeguimiento,preguntas);
							break;
						case "Quiz":
							CrearActividadQuiz(logInCreador,tipo,descripcion,objetivos,dificultad,duracion,idActividadesPrevias,fecha,idActividadesSeguimiento,preguntasMultiples,correctas,notaMinima);
							break;
						case "Examen":
							CrearActividadExamen(logInCreador,tipo,descripcion,objetivos,dificultad,duracion,idActividadesPrevias,fecha,idActividadesSeguimiento,preguntas,notaMinima);
							break;
					}
                    
                }
            }
            System.out.println("Datos cargados exitosamente desde " + archivo.getAbsolutePath() + ". Total de estudiantes: " + actividades.size());
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe. Se creará al guardar.");
        } catch (IOException e) {
            System.err.println("Error al cargar los datos: " + e.getMessage());
            throw e;
        }
    }
}
