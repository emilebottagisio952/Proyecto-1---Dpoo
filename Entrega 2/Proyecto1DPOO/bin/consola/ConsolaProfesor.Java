package consola;

import java.io.IOException;
import java.util.Scanner;

import procesamiento.ProcesamientoActividad;
import procesamiento.ProcesamientoProfesor;
import procesamiento.ProcesamientoLearningPath;
import procesamiento.ProcesamientoRegistros;
import procesamiento.ProcesamientoResena;

public class consolaProfesor{
    private ProcesamientoActividad AC;
    private ProcesamientoProfesor EP;
    private ProcesamientoLearningPath LPC;
    private ProcesamientoRegistros RGC;
    private ProcesamientoResena RC;
	private Scanner input;
	private String loginActual;
	private String rolActual;

    public ConsolaProfesor (){
        this.AC = new ProcesamientoActividad();
        this.EP = new ProcesamientoProfesor();
        this.LPC = new ProcesamientoLearningPath();
        this.RGC = new ProcesamientoRegistros();
        this.RC = new ProcesamientoResena();
        this.input = new Scanner(System.in);
    }
    public void mostrarMenu() throws IOException{
        int respuesta; 
        do {
			System.out.println("\nDigite el numero de la opcion que quiere usar.\n"
					+ "1. Crear reseña\n"
                    + "2. Crear actividad\n"
                    + "3. Editar actividad\n"
                    + "4. Crear learning path\n"
                    + "5. Editar learning path\n"
					+ "6. Ver actividades\n"
					+ "7. Ver reseñas de una actividad\n"
					+ "8. Ver learning paths\n"
					+ "9. Revisar progreso estudiante\n"
					+ "10. Salvar datos\n"
                    + "11. Ingresar como profesor\n"
					+ "12. Salir");
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
					CrearActividad();
					break;
				case 3:
					EditarActividad();
					break;
				case 4:
                CrearLearningPath();
					break;
				case 5:
                EditarLearningPath();
					break;
				case 6:
                VerActividades();
					break;
				case 7 :
                VerResenasActividad();
					break;
				case 8:
                VerLearningPaths();
					break;

                case 9:
                RevisarProgreso();
					break;

                case 10:
                VerLearningPaths();
                    break;

                case 11:
                VerLearningPaths();
                    break;

                case 12:
                VerLearningPaths();
                    break;
			}
		} while (respuesta != 9);
	}
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

private void CrearActividad() {
    if (!(rolActual.equals("Profesor"))) {
        System.out.println("\nNo tiene permisos para realizar esta accion");
    } else {
        
        System.out.println("\nIngrese la descripcion de la actividad:");
        String descripcion = input.nextLine();
        
        System.out.println("\nIngrese los objetivos separados por comas:");
        String objetivoString = input.nextLine();
        
        System.out.println("\nIngrese el nivel de dificultad de la actividad:");
        String nivelDificultad = input.nextLine();
        
        System.out.println("\nIngrese la duracion de la actividad en minutos:");
        int duracion = input.nextInt();
        input.nextLine();
        
        System.out.println("\nIngrese la fecha limite de la actividad:");
        String fechaLimite = input.nextLine();
        
        String tipo = SeleccionadorTipo();
        
        System.out.println("\nIngrese las actividades previas de la actividad:");
        ArrayList<Integer> idActividadesPrevias = SeleccionadorActividades();
        System.out.println("\nIngrese las actividades de seguimiento de la actividad:");
        ArrayList<Integer> idActividadesSeguimiento = SeleccionadorActividades();
        
        switch (tipo) {
            case "Tarea":
                CrearActividadTarea(descripcion, objetivoString, nivelDificultad, duracion, fechaLimite, tipo, idActividadesPrevias, idActividadesSeguimiento);
                break;
            case "RecursoEducativo":
                CrearActividadRecurso(descripcion, objetivoString, nivelDificultad, duracion, fechaLimite, tipo, idActividadesPrevias, idActividadesSeguimiento);
                break;
            case "Encuesta":
                CrearActividadEncuesta(descripcion, objetivoString, nivelDificultad, duracion, fechaLimite, tipo, idActividadesPrevias, idActividadesSeguimiento);
                break;
            case "Quiz":
                CrearActividadQuiz(descripcion, objetivoString, nivelDificultad, duracion, fechaLimite, tipo, idActividadesPrevias, idActividadesSeguimiento);
                break;
            case "Examen":
                CrearActividadExamen(descripcion, objetivoString, nivelDificultad, duracion, fechaLimite, tipo, idActividadesPrevias, idActividadesSeguimiento);
                break;
        }
        
    }
}
private void EditarActividad() {
    if (!(rolActual.equals("Profesor"))) {
        System.out.println("\nNo tiene permisos para realizar esta accion");
    } else {
        System.out.println("Digite la id de la actividad que quiere editar (Solo puede editarla si usted es el creador.)");
        int idActividad = input.nextInt();
        input.nextLine();
        if (AC.VerificarCreador(idActividad, loginActual)) {
        
            System.out.println("\nIngrese la descripcion de la actividad:");
            String descripcion = input.nextLine();
            
            System.out.println("\nIngrese los objetivos separados por comas:");
            String objetivoString = input.nextLine();
            
            System.out.println("\nIngrese el nivel de dificultad de la actividad:");
            String nivelDificultad = input.nextLine();
            
            System.out.println("\nIngrese la duracion de la actividad en minutos:");
            int duracion = input.nextInt();
            input.nextLine();
            
            System.out.println("\nIngrese la fecha limite de la actividad:");
            String fechaLimite = input.nextLine();
            
            String tipo = SeleccionadorTipo();
            
            System.out.println("\nIngrese las actividades previas de la actividad:");
            ArrayList<Integer> idActividadesPrevias = SeleccionadorActividades();
            System.out.println("\nIngrese las actividades de seguimiento de la actividad:");
            ArrayList<Integer> idActividadesSeguimiento = SeleccionadorActividades();
            
            switch (tipo) {
                case "Tarea":
                    EditarActividadTarea(descripcion, objetivoString, nivelDificultad, duracion, fechaLimite, tipo, idActividadesPrevias, idActividadesSeguimiento, idActividad);
                    break;
                case "RecursoEducativo":
                    EditarActividadRecurso(descripcion, objetivoString, nivelDificultad, duracion, fechaLimite, tipo, idActividadesPrevias, idActividadesSeguimiento, idActividad);
                    break;
                case "Encuesta":
                    EditarActividadEncuesta(descripcion, objetivoString, nivelDificultad, duracion, fechaLimite, tipo, idActividadesPrevias, idActividadesSeguimiento, idActividad);
                    break;
                case "Quiz":
                    EditarActividadQuiz(descripcion, objetivoString, nivelDificultad, duracion, fechaLimite, tipo, idActividadesPrevias, idActividadesSeguimiento, idActividad);
                    break;
                case "Examen":
                    EditarActividadExamen(descripcion, objetivoString, nivelDificultad, duracion, fechaLimite, tipo, idActividadesPrevias, idActividadesSeguimiento, idActividad);
                    break;
            }
        } else {
            System.out.println("Usted no es el creador de esta actividad, no puede editarla a menos que la clone");
        }
    }
}
private void CrearLearningPath() {
    if (!(rolActual.equals("Profesor"))) {
        System.out.println("\nNo tiene permisos para realizar esta accion");
    } else {
        
        System.out.println("\nIngrese el titulo del Learning Path:");
        String titulo = input.nextLine();
        
        System.out.println("\nIngrese la descripcion del Learning Path:");
        String descripcion = input.nextLine();
        
        System.out.println("\nIngrese el nivel de dificultad del Learning Path:");
        String nivelDificultad = input.nextLine();
        
        System.out.println("\nIngrese la duracion del Learning Path en minutos:");
        int duracion = input.nextInt();
        input.nextLine();
        
        System.out.println("\nIngrese las actividades que quiere incluir en el Learning Path:");
        HashMap<Integer, Boolean> idActividades = SeleccionadorActividadesLearningPath();
        
        LPC.CrearLearningPath(titulo, descripcion, nivelDificultad, duracion, idActividades, AC, loginActual);
        System.out.println("Learning Path Creado exitosamente!");
    }
}
private void EditarLearningPath() {
    System.out.println("Digite el id del learning path que quiere editar (Usted debe ser el creador de esta actividad)");
    int id = input.nextInt();
    input.nextLine();
    
    if (!(rolActual.equals("Profesor"))) {
        System.out.println("\nNo tiene permisos para realizar esta accion");
    } else {
        
        System.out.println("\nIngrese el titulo del Learning Path:");
        String titulo = input.nextLine();
        
        System.out.println("\nIngrese la descripcion del Learning Path:");
        String descripcion = input.nextLine();
        
        System.out.println("\nIngrese el nivel de dificultad del Learning Path:");
        String nivelDificultad = input.nextLine();
        
        System.out.println("\nIngrese la duracion del Learning Path en minutos:");
        int duracion = input.nextInt();
        input.nextLine();
        
        System.out.println("\nIngrese las actividades que quiere incluir en el Learning Path:");
        HashMap<Integer, Boolean> idActividades = SeleccionadorActividadesLearningPath();
        
        LPC.CrearLearningPath(titulo, descripcion, nivelDificultad, duracion, idActividades, AC, loginActual, id);
        System.out.println("Learning Path editado exitosamente!");
    }
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
private void VerResenasActividad() {
    System.out.println("Digite la id de la actividad que quiere revisar: ");
    int id = input.nextInt();
    input.nextLine();
    
    RC.RevisarResenas(id);
}
private void VerLearningPaths() {
    LPC.ImprimirLearningPaths();
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
private void RevisarProgreso() {
    String login;
    if (rolActual.equals("Estudiante")) {
        login = loginActual;
    } else {
        System.out.println("Ingrese el login del estudiante que quiere revisar: ");
        login = input.nextLine();
    }
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

  