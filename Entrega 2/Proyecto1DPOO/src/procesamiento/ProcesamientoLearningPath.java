package procesamiento;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import elementos.Actividad;
import elementos.LearningPath;

import java.time.LocalDateTime;

public class ProcesamientoLearningPath {

	HashMap<Integer, LearningPath> learningPaths;
	
	public ProcesamientoLearningPath() {
	    this.learningPaths = new HashMap<>();
	}
	
	public void ImprimirLearningPaths() {
		Set<Integer> ids = learningPaths.keySet();
		System.out.println("\nEstos son los learning paths disponibles (El numero a su lado corresponde a su id).");
		for (int id : ids) {
			System.out.printf("%d.", id);
			System.out.printf("\n Titulo: %s.", learningPaths.get(id).getTitulo());
			System.out.printf("\n Descripcion: %s.", learningPaths.get(id).getDescripcionGeneral());
			System.out.printf("\n Creador: %s.", learningPaths.get(id).getLoginCreador());
			System.out.printf("\n FechaCreacion: %s.", learningPaths.get(id).getFechaCreacion());
			System.out.printf("\n FechaModificacion: %s.", learningPaths.get(id).getFechaModificacion());
			System.out.printf("\n Nivel de dificultad: %s.", learningPaths.get(id).getNivelDificultad());
			System.out.printf("\n Duracion en minutos: %s.\n", learningPaths.get(id).getDuracion());
			System.out.printf("\n Version: %s.", learningPaths.get(id).getVersion());
		}
	}
	
	public void CrearLearningPath(String titulo, String descripcion, String nivelDificultad, int duracion, HashMap<Integer, Boolean> idActividades, ProcesamientoActividad AC, String loginActual) {
		LocalDateTime fechaCreacion = LocalDateTime.now();
		LocalDateTime fechaModificacion = LocalDateTime.now();
		Set<Integer> setIds = idActividades.keySet();
		HashMap<Actividad, Boolean> actividades = new HashMap<>();
		for (int id : setIds) {
			actividades.put(AC.GetActividad(id), idActividades.get(id));
		}
		
		LearningPath lp = new LearningPath(titulo, descripcion, nivelDificultad, duracion, fechaCreacion, fechaModificacion, 0, actividades, loginActual);
		learningPaths.put(learningPaths.size() + 1, lp);
	}
	
	public void CrearLearningPath(String titulo, String descripcion, String nivelDificultad, int duracion, HashMap<Integer, Boolean> idActividades, ProcesamientoActividad AC, String loginActual, int idLP) {
		LearningPath original = learningPaths.get(idLP);
		if (!(learningPaths.containsKey(idLP))) {
			System.out.println("El learning path con la id pedida no existe");
		} else {
			if (!(loginActual.equals(original.getLoginCreador()))) {
				System.out.println("Usted no es el creador de este learning path, no tiene derecho a modificarlo");
			} else { 
				LocalDateTime fechaCreacion = original.getFechaCreacion();
				LocalDateTime fechaModificacion = LocalDateTime.now();
				Set<Integer> setIds = idActividades.keySet();
				HashMap<Actividad, Boolean> actividades = new HashMap<>();
				for (int id : setIds) {
					actividades.put(AC.GetActividad(id), idActividades.get(id));
				}
				LearningPath lp = new LearningPath(titulo, descripcion, nivelDificultad, duracion, fechaCreacion, fechaModificacion, original.getVersion() + 1, actividades, loginActual);
				learningPaths.put(learningPaths.size() + 1, lp);
			}
		}
	}
	
	public ArrayList<Integer> GetActividadesLP(int idLP) {
		ArrayList<Integer> ids = new ArrayList<>();
		LearningPath lp = learningPaths.get(idLP);
		Set<Actividad> actividades = lp.getActividades().keySet();
		for (Actividad a : actividades) {
			ids.add(a.getId());
		}
		return ids;
	}
	
	public void guardarLPEnArchivo(String nombreArchivo) throws IOException {
        String directorioRelativo = "Persistencia"; 
        File directorio = new File(directorioRelativo);
        
        if (!directorio.exists()) {
            directorio.mkdirs(); 
        }

        File archivo = new File(directorio, nombreArchivo); 

        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo, true))) { 
            for (LearningPath learningPath : learningPaths.values()) {
                writer.println(learningPath.getTitulo() + "," + learningPath.getDescripcionGeneral() + "," + learningPath.getNivelDificultad()+ "," + 
                				learningPath.getDuracion()+ "," + learningPath.getFechaCreacion()+ "," + learningPath.getFechaModificacion()+ "," + 
                				learningPath.getVersion() + "," + learningPath.getActividades() + "," + learningPath.getLoginCreador() ); // Guarda como login,password
            }
            System.out.println("Datos guardados exitosamente en " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
            throw e;
        }
    }
    public void cargarLPDesdeArchivo(String nombreArchivo) throws IOException {
        if (learningPaths == null) {
        	learningPaths = new HashMap<>();
        }

        String directorioRelativo = "Persistencia"; 
        File archivo = new File(directorioRelativo, nombreArchivo);

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parte = line.split(","); 
                if (parte.length == 2) {
                    String titulo = parte[0];
                    String descripcion = parte[1];
                    String dificultad = parte[2];
                    Integer duracion = Integer.parseInt(parte[3]);
                    LocalDateTime fechaCreacion = LocalDateTime.parse(parte[4]);
                    LocalDateTime fechaModificacion = LocalDateTime.parse(parte[5]);
                    Integer version = Integer.parseInt(parte[6]);
                    String Actividades= parte[7];
                    String logInCreador = parte[8];
                    learningPaths.put(learningPaths.size() + 1, new LearningPath(titulo, descripcion, dificultad, duracion, fechaCreacion, fechaModificacion, version, null, logInCreador)); // Crea un nuevo estudiante
                }
            }
            System.out.println("Datos cargados exitosamente desde " + archivo.getAbsolutePath() + ". Total de estudiantes: " + learningPaths.size());
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe. Se creará al guardar.");
        } catch (IOException e) {
            System.err.println("Error al cargar los datos: " + e.getMessage());
            throw e;
        }
    }
	
	
}
