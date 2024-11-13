package consola;

import java.io.IOException;
import java.util.Scanner;

import procesamiento.ProcesamientoActividad;
import procesamiento.ProcesamientoEstudiante;
import procesamiento.ProcesamientoLearningPath;
import procesamiento.ProcesamientoRegistros;
import procesamiento.ProcesamientoResena;

public class ConsolaEstudiante {
    private ProcesamientoActividad AC;
	private ProcesamientoEstudiante EC;
	private ProcesamientoLearningPath LPC;
	private ProcesamientoRegistros RGC;
	private ProcesamientoResena RC;
	private Scanner input;
	private String loginActual;
	private String rolActual;

    public ConsolaEstudiante() {
		this.AC = new ProcesamientoActividad();
        this.EC = new ProcesamientoEstudiante();
        this.LPC = new ProcesamientoLearningPath();
        this.RGC = new ProcesamientoRegistros();
        this.RC = new ProcesamientoResena();
        this.input = new Scanner(System.in);
    }

	public static void main(String[] args){
        ConsolaEstudiante ConsolaEstudiante = new ConsolaEstudiante();
        try {
            ConsolaEstudiante.mostrarMenu(); 
        }catch(IOException e) {
            System.err.println("Error al ejecutar la consola" +e.getMessage());
        }finally {
            ConsolaEstudiante.input.close();
        }   
    }
    public void mostrarMenu() throws IOException{
        int respuesta; 
        do {
			System.out.println("\nDigite el numero de la opcion que quiere usar.\n"
					+ "1. Crear reseña\n"
					+ "2. Ver actividades\n"
					+ "3. Ver reseñas de una actividad\n"
					+ "4. Ver learning paths\n"
					+ "5. Inscribir learning path\n"
					+ "6. Desarrollar actividad\n"
					+ "7. Revisar progreso estudiante\n"
					+ "8. Salvar datos\n"
					+ "9. Salir");
			respuesta = input.nextInt();
			input.nextLine();
			if (respuesta < 1 || respuesta > 15) {
				System.out.println("El numero que ha ingresado no esta en las opciones disponibles. Intente de nuevo.");
			}
			switch (respuesta) {
				case 1:
					CrearResena();
					break;
				case 2:
					VerActividades();
					break;
				case 3:
					VerResenasActividad();
					break;
				case 4:
					VerLearningPaths();
					break;
				case 5:
					InscribirLearningPath();
					break;
				case 6:
					DesarrollarActividad();
					break;
				case 7 :
					RevisarProgreso();
					break;
				case 8:
					SalvarDatos();
					break;
			}
		} while (respuesta != 9);
	}
    private void CrearResena() {
		System.out.println("Ingrese el id de la actividad que quiere reseñar");
		int id = input.nextInt();
		input.nextLine();
		
		System.out.println("Cual fue su opinion acerca de la actividad?");
		String opinion = input.nextLine();
		
		System.out.println("Que calificacion le da a esta actividad? (Ingrese un numero del 1 al 5)");
		int rating = input.nextInt();
		input.nextLine();
		
		RC.CrearResena(id, opinion, rating, loginActual, rolActual);
		System.out.println("Reseña creada de manera exitosa!");
	}
    private void VerActividades() {
		AC.ImprimirActividades();
	}
    private void VerResenasActividad() {
		System.out.println("Digite la id de la actividad que quiere revisar: ");
		int id = input.nextInt();
		input.nextLine();
		
		RC.RevisarResenas(id);
	}
    private void VerLearningPaths() {
		LPC.ImprimirLearningPaths();
	}
    private void InscribirLearningPath() {
		System.out.println("Inserte la id del learning path que quiere inscribir.");
		int idLP = input.nextInt();
		input.nextLine();
		RGC.InscribirEstudiante(idLP, loginActual, AC, LPC);
		System.out.println("Se ha inscrito exitosamente!");
	}
    private void DesarrollarActividad() {
		System.out.println("Ingrese la id del learning path al cual pertenece la actividad");
		int idLP = input.nextInt();
		input.nextLine();
		AC.ImprimirActividades(LPC.GetActividadesLP(idLP));
		System.out.println("Digite el id de la actividad que quiere realizar");
		int idA = input.nextInt();
		input.nextLine();
		String tipo = AC.GetActividad(idA).getTipo();
		RGC.DesarrollarActividad(idLP, idA, tipo, loginActual, input, AC);
	}
    private void RevisarProgreso() {
        String login;
        System.out.println("Ingrese el login del estudiante que quiere revisar: ");
        login = input.nextLine();
        System.out.println("Ingrese el id del learning path que quiere revisar");
        int idLP = input.nextInt();
        input.nextLine();
        RGC.MostrarProgreso(idLP, login);
    }
    private void SalvarDatos() throws IOException {
		EC.guardarEstudiantesEnArchivo("estudiantes.txt");
		LPC.guardarLPEnArchivo("learningPaths.txt");
		RC.guardarResenasEnArchivo("resenas.txt");
		AC.guardarActividadesEnArchivo("Actividades.txt");
	}
}
