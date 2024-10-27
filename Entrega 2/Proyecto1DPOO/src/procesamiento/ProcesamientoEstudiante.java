package procesamiento;
import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import elementos.Estudiante;

public class ProcesamientoEstudiante {

	private HashMap<String, Estudiante> estudiantes;
	
	public ProcesamientoEstudiante() {
	    this.estudiantes = new HashMap<>();
	}
	
	public void CrearEstudiante(String login, String password) {
		Estudiante e = new Estudiante(login, password);
		estudiantes.put(e.getLogin(), e);
	}
	
	public void MostrarEstudiantes() {
		Set<String> logins = estudiantes.keySet();
		Collection<Estudiante> passwords = estudiantes.values();
		for (String login : logins) {
			System.out.println(login);
		}
		for (Estudiante pass : passwords) {
			System.out.println(pass.getPassword());
		}
	}
	
	public boolean ExisteEstudiante(String login) {
		return estudiantes.containsKey(login);
	}
	
	public boolean IngresoEstudiante(String login, String password) {
		Estudiante estudiante = estudiantes.get(login);
		if (password.equals(estudiante.getPassword())) {
			return true;
		}
		return false;
	}
	
    public void guardarEstudiantesEnArchivo(String nombreArchivo) throws IOException {
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
            for (Estudiante estudiante : estudiantes.values()) {
                writer.println(estudiante.getLogin() + "," + estudiante.getPassword()); // Guarda como login,password
            }
            System.out.println("Datos guardados exitosamente en " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
            throw e;
        }
    }

    // Cargar estudiantes desde un archivo
    public void cargarEstudiantesDesdeArchivo(String nombreArchivo) throws IOException {
        // Inicializar estudiantes si no está inicializado
        if (estudiantes == null) {
            estudiantes = new HashMap<>();
        }

        String directorioRelativo = "Persistencia"; // Asegúrate de que este directorio sea correcto
        File archivo = new File(directorioRelativo, nombreArchivo);

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(","); // Divide el login y la contraseña
                if (partes.length == 2) {
                    String login = partes[0];
                    String password = partes[1];
                    estudiantes.put(login, new Estudiante(login, password)); // Crea un nuevo estudiante
                }
            }
            System.out.println("Datos cargados exitosamente desde " + archivo.getAbsolutePath() + ". Total de estudiantes: " + estudiantes.size());
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe. Se creará al guardar.");
        } catch (IOException e) {
            System.err.println("Error al cargar los datos: " + e.getMessage());
            throw e; // Propagar la excepción para manejarla más arriba si es necesario
        }
    }
}
