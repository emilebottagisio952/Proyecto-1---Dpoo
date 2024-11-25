package procesamiento;

import java.util.ArrayList;

import elementos.Resena;

import java.io.*;

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
	
    // Método para guardar reseñas en un archivo
    public void guardarResenasEnArchivo(String nombreArchivo) throws IOException {
        String directorioRelativo = "Persistencia";
        File directorio = new File(directorioRelativo);

        // Asegúrate de que el directorio existe
        if (!directorio.exists()) {
            directorio.mkdirs(); // Crea el directorio si no existe
        }

        // Crea el archivo en la ruta deseada con extensión .txt
        File archivo = new File(directorio, nombreArchivo + ".txt");

        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            for (Resena resena : resenas) {
                writer.printf("%d,%s,%d,%s,%s%n", resena.getIdActividad(), resena.getOpinion(), 
                              resena.getRating(), resena.getLoginAutor(), resena.getRolAutor());
            }
            System.out.println("Reseñas guardadas exitosamente en " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al guardar las reseñas: " + e.getMessage());
            throw e;
        }
    }

    // Método para cargar reseñas desde un archivo
    public void cargarResenasDesdeArchivo(String nombreArchivo) throws IOException {
        String directorioRelativo = "bin/Persistencia";
        File archivo = new File(directorioRelativo, nombreArchivo + ".txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(","); // Divide en los atributos
                if (partes.length == 5) {
                    int idActividad = Integer.parseInt(partes[0]);
                    String opinion = partes[1];
                    int rating = Integer.parseInt(partes[2]);
                    String loginAutor = partes[3];
                    String rolAutor = partes[4];
                    CrearResena(idActividad, opinion, rating, loginAutor, rolAutor); // Crea la nueva reseña
                }
            }
            System.out.println("Reseñas cargadas exitosamente desde " + archivo.getAbsolutePath());
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe. Se creará al guardar.");
        } catch (IOException e) {
            System.err.println("Error al cargar las reseñas: " + e.getMessage());
            throw e; // Propagar la excepción para manejarla más arriba si es necesario
        }
    }
	
}
