package procesamiento;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import elementos.Profesor;

public class ProcesamientoProfesor {

	HashMap<String, Profesor> profesores;
	
	public ProcesamientoProfesor() {
	    this.profesores = new HashMap<>();
	}
	
	public void CrearProfesor(String login, String password) {
		Profesor p = new Profesor(login, password);
		profesores.put(p.getLogin(), p);
	}
	
	public void MostrarProfesores() {
		Set<String> logins = profesores.keySet();
		Collection<Profesor> passwords = profesores.values();
		for (String login : logins) {
			System.out.println(login);
		}
		for (Profesor pass : passwords) {
			System.out.println(pass.getPassword());
		}
	}
	
	public boolean ExisteProfesor(String login) {
		return profesores.containsKey(login);
	}
	
	public boolean IngresoProfesor(String login, String password) {
		Profesor profesor = profesores.get(login);
		if (password.equals(profesor.getPassword())) {
			return true;
		}
		return false;
	}
	
    public void guardarProfesoresEnArchivo(String nombreArchivo) throws IOException {
        String directorioRelativo = "Persistencia"; 
        File directorio = new File(directorioRelativo);
        
        // Asegúrate de que el directorio existe
        if (!directorio.exists()) {
            directorio.mkdirs(); // Crea el directorio si no existe
        }
    
        // Crea el archivo en la ruta deseada con el nombre proporcionado
        File archivo = new File(directorio, nombreArchivo); // Ahora usa el nombre del archivo proporcionado
    
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo, true))) { // Modo apéndice
            // Guarda todos los profesores en el archivo
            for (Profesor profesor : profesores.values()) {
                writer.println(profesor.getLogin() + "," + profesor.getPassword()); // Guarda como login,password
            }
            System.out.println("Datos guardados exitosamente en " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
            throw e;
        }
    }

    // Cargar profesores desde un archivo
    public void cargarProfesoresDesdeArchivo(String nombreArchivo) throws IOException {
        if (profesores == null) {
            profesores = new HashMap<>();
        }

        String directorioRelativo = "Persistencia"; 
        File archivo = new File(directorioRelativo, nombreArchivo);

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(",");
                if (partes.length == 2) {
                    String login = partes[0];
                    String password = partes[1];
                    profesores.put(login, new Profesor(login, password)); 
                }
            }
            System.out.println("Datos de profesores cargados exitosamente desde " + archivo.getAbsolutePath());
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe. Se creará al guardar.");
        } catch (IOException e) {
            System.err.println("Error al cargar los datos: " + e.getMessage());
            throw e;
        }
    }
}
