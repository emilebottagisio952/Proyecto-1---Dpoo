package procesamiento;

import elementos.Resena;
import java.io.*;
import java.util.ArrayList;

public class ProcesamientoResena {

	ArrayList<Resena> resenas;
	
	public ProcesamientoResena() {
		this.resenas = new ArrayList<>(); 
	}
    // (Tests) Getter para acceder a la lista de reseñas
    public ArrayList<Resena> getResenas() {
        return resenas;
    }
	
	public void CrearResena(int id, String opinion, int rating, String loginAutor, String rolAutor) {
		Resena r = new Resena(id, opinion, rating, loginAutor, rolAutor);
		resenas.add(r);
	}
	
	public ArrayList<Resena> ResenasActividad(int idActividad) {
		ArrayList<Resena> listaResenas = new ArrayList<>();
		for (Resena resena : resenas) {
			if (resena.getIdActividad() == idActividad) {
				listaResenas.add(resena);
			}
		}
		return listaResenas;
	}
	
	public void RevisarResenas(int idActividad) {
		ArrayList<Resena> listaResenas = ResenasActividad(idActividad);
		System.out.println("Las reseñas de la actividad son:");
		for (Resena resena : listaResenas) {
			System.out.printf("\n Login del autor: %s.", resena.getLoginAutor());
			System.out.printf("\n Rol del autor: %s.", resena.getRolAutor());
			System.out.printf("\n Opinion: %s.", resena.getOpinion());
			System.out.printf("\n Rating: %s. \n", resena.getRating());
		}
		System.out.printf("La actividad tiene un rating promedio de: %f.", calcularRating(idActividad) );
	}
	
	public float calcularRating(int idActividad) {
		ArrayList<Resena> listaResenas = ResenasActividad(idActividad);
		float sumaRatings = 0;
		for (Resena resena : listaResenas) {
			sumaRatings += resena.getRating();
		}
		if (sumaRatings != 0) {
			return sumaRatings/listaResenas.size();
		}
		return 0;
	}
	
    public void guardarResenasEnArchivo(String nombreArchivo) throws IOException {
        String directorioRelativo = "Persistencia"; 
        File directorio = new File(directorioRelativo);
        
        if (!directorio.exists()) {
            directorio.mkdirs(); 
        }

        File archivo = new File(directorio, nombreArchivo); 

        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo, true))) { 
            for (Resena resena : resenas) {
                writer.println(resena.getIdActividad() + ";" + resena.getOpinion() + ";" + resena.getRating() + ";" + resena.getLoginAutor() + ";" + resena.getRolAutor()); // Guarda como login,password
            }
            System.out.println("Datos guardados exitosamente en " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
            throw e;
        }
    }
    public void cargarResenasDesdeArchivo(String nombreArchivo) throws IOException {
        if (resenas == null) {
        	resenas = new ArrayList<>();
        }

        String directorioRelativo = "Persistencia"; 
        File archivo = new File(directorioRelativo, nombreArchivo);

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parte = line.split(";"); 
                if (parte.length >= 5) {
                    Integer idActividad = Integer.parseInt(parte[0]);
                    String opinion = parte[1];
                    Integer rating = Integer.parseInt(parte[2]);
                    String loginAutor = parte[3];
                    String rolAutor = parte[4];
                    CrearResena(idActividad, opinion, rating, loginAutor, rolAutor);
                }
            }
            System.out.println("Datos cargados exitosamente desde " + archivo.getAbsolutePath() + ". Total de estudiantes: " + resenas.size());
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe. Se creará al guardar.");
        } catch (IOException e) {
            System.err.println("Error al cargar los datos: " + e.getMessage());
            throw e;
        }
    }
	
}
