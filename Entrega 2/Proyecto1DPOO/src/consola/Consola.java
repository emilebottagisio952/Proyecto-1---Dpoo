package consola;
import java.util.Scanner;

import procesamiento.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Consola {
	private ProcesamientoActividad AC;
	private ProcesamientoEstudiante EC;
	private ProcesamientoLearningPath LPC;
	private ProcesamientoProfesor PC;
	private ProcesamientoRegistros RGC;
	private ProcesamientoResena RC;
	private Scanner input;
	private String loginActual;
	private String rolActual;
	
	public Consola() {
		this.AC = new ProcesamientoActividad();
        this.EC = new ProcesamientoEstudiante();
        this.LPC = new ProcesamientoLearningPath();
        this.PC = new ProcesamientoProfesor();
        this.RGC = new ProcesamientoRegistros();
        this.RC = new ProcesamientoResena();
        this.input = new Scanner(System.in);
    }
	
	public static void main(String[] args) throws IOException {
		
		Consola c = new Consola();
		c.ConsolaRegistro();
		c.input.close();
	}
	
	private void ConsolaRegistro() throws IOException {
		int respuesta;
		do {
			System.out.println("\nDigite el numero de la opcion que quiere usar.\n"
					+ "1. Ingresar como estudiante\n"
					+ "2. Ingresar como profesor\n"
					+ "3. Registrarse como estudiante\n"
					+ "4. Registrarse como profesor\n"
					+ "5. Cargar datos");
			respuesta = input.nextInt();
			input.nextLine();
			if (respuesta < 1 || respuesta > 5) {
				System.out.println("El numero que ha ingresado no esta en las opciones disponibles. Intente de nuevo.");
			}
			switch (respuesta) {
				case 1:
					IngresarEstudiante();
					break;
				case 2:
					IngresarProfesor();
					break;
				case 3:
					RegistrarEstudiante();
					break;
				case 4:
					RegistrarProfesor();
					break;
				case 5:
                    try {
                        EC.cargarEstudiantesDesdeArchivo("estudiantes.txt");
                        PC.cargarProfesoresDesdeArchivo("profesores.txt");
                        LPC.cargarLPDesdeArchivo("learningPaths.txt");
                        RC.cargarResenasDesdeArchivo("resenas.txt");
                    } catch (IOException e) {
                        System.err.println("Error al cargar los datos: " + e.getMessage());
                    }
                    break;
                case 6:
                    try {
                        SalvarDatos();
                    } catch (IOException e) {
                        System.err.println("Error al salvar los datos: " + e.getMessage());
                    }
                    System.out.println("Datos guardados y programa terminado.");
                    return; // Salir del bucle y terminar el programa
                default:
                    System.out.println("Opción no válida.");
                    break;
			}
		} while (rolActual == null);
		
		do {
			System.out.println("\nDigite el numero de la opcion que quiere usar.\n"
					+ "1. Crear actividad\n"
					+ "2. Editar actividad\n"
					+ "3. Crear learning path\n"
					+ "4. Editar learning path\n"
					+ "5. Crear reseña\n"
					+ "6. Ver actividades\n"
					+ "7. Ver reseñas de una actividad\n"
					+ "8. Ver learning paths\n"
					+ "9. Inscribir learning path\n"
					+ "10. Desarrollar actividad\n"
					+ "11. Revisar progreso estudiante\n"
					+ "12. Salvar datos\n"
					+ "13. Ingresar como estudiante\n"
					+ "14. Ingresar como profesor\n"
					+ "15. Salir");
			respuesta = input.nextInt();
			input.nextLine();
			if (respuesta < 1 || respuesta > 15) {
				System.out.println("El numero que ha ingresado no esta en las opciones disponibles. Intente de nuevo.");
			}
			switch (respuesta) {
				case 1:
					CrearActividad();
					break;
				case 2:
					EditarActividad();
					break;
				case 3:
					CrearLearningPath();
					break;
				case 4:
					EditarLearningPath();
					break;
				case 5:
					CrearResena();
					break;
				case 6:
					VerActividades();
					break;
				case 7:
					VerResenasActividad();
					break;
				case 8:
					VerLearningPaths();
					break;
				case 9:
					InscribirLearningPath();
					break;
				case 10:
					DesarrollarActividad();
					break;
				case 11 :
					RevisarProgreso();
					break;
				case 12:
					SalvarDatos();
					break;
				case 13:
					IngresarEstudiante();
					break;
				case 14:
					IngresarProfesor();
					break;
			}
		} while (respuesta != 15);
	}
	

	private void SalvarDatos() throws IOException {
			EC.guardarEstudiantesEnArchivo("estudiantes.txt");
			PC.guardarProfesoresEnArchivo("profesores.txt");
			LPC.guardarLPEnArchivo("learningPaths.txt");
			RC.guardarResenasEnArchivo("resenas.txt");
			AC.guardarActividadesEnArchivo("Actividades.txt");
		}
	
	private void CargarDatos() throws IOException {
		EC.cargarEstudiantesDesdeArchivo("estudiantes.txt");
		PC.cargarProfesoresDesdeArchivo("profesores.txt");
		LPC.cargarLPDesdeArchivo("learningPaths.txt");
		RC.cargarResenasDesdeArchivo("resenas.txt");
	}

	private void RegistrarEstudiante() {
        System.out.println("Ingrese su login:");
        String login = input.nextLine();
        System.out.println("Ingrese su password:");
        String password = input.nextLine();
        
        if (EC.ExisteEstudiante(login)) {
            System.out.println("Ya existe otro estudiante con este usuario.");
        } else {
            EC.CrearEstudiante(login, password);
            System.out.println("Usuario registrado exitosamente!");
            // Llama a la función para guardar los estudiantes en un archivo
            try {
                EC.guardarEstudiantesEnArchivo("estudiantes.txt");
            } catch (IOException e) {
                System.err.println("Error al guardar los estudiantes: " + e.getMessage());
            }
        }
    }

	private void RegistrarProfesor() {
        System.out.println("Ingrese su login:");
        String login = input.nextLine();
        System.out.println("Ingrese su password:");
        String password = input.nextLine();
        
        if (PC.ExisteProfesor(login)) {
            System.out.println("Ya existe otro profesor con este usuario.");
        } else {
            PC.CrearProfesor(login, password);
            System.out.println("Usuario registrado exitosamente!");
            // Llama a la función para guardar los profesores en un archivo
            try {
                PC.guardarProfesoresEnArchivo("profesores.txt");
            } catch (IOException e) {
                System.err.println("Error al guardar los profesores: " + e.getMessage());
            }
        }
    }

	private void IngresarEstudiante() {
		
		System.out.println("Ingrese su login:");
		String login = input.nextLine();
		System.out.println("Ingrese su password:");
		String password = input.nextLine();
		
		if (!(EC.ExisteEstudiante(login))) {
			System.out.println("El login ingresado no esta registrado.");
		} else {
			if (EC.IngresoEstudiante(login, password)) {
				 this.loginActual = login;
				 this.rolActual = "Estudiante";
				 System.out.println("Bienvenido.");
			} else {
				 System.out.println("Contraseña incorrecta.");
			}
		}
	}
	private void IngresarProfesor() {
		
		System.out.println("\nIngrese su login:");
		String login = input.nextLine();
		
		System.out.println("Ingrese su password:");
		String password = input.nextLine();
		
		if (!(PC.ExisteProfesor(login))) {
			System.out.println("El login ingresado no esta registrado.");
		} else {
			if (PC.IngresoProfesor(login, password)) {
				 this.loginActual = login;
				 this.rolActual = "Profesor";
				 System.out.println("Bienvenido.");
			} else {
				 System.out.println("Contraseña incorrecta.");
			}
		}
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
	
	private HashMap<Integer, Boolean> SeleccionadorActividadesLearningPath() {
		int id;
		Boolean obligatoria;
		HashMap<Integer, Boolean> ids = new HashMap<>();
		System.out.println("Ingrese las id de las actividades que quiere seleccionar, ingrese 0 para terminar.");
		do {
			System.out.println("Ingrese id: ");
			id = input.nextInt();
			input.nextLine();
			if (id != 0) {
				System.out.println("Digite 1 si la actividad es obligatoria y 0 si no lo es.");
				int resp = input.nextInt();
				if (resp == 1) {
					obligatoria = true;
				} else {
					obligatoria = false;
				}
				ids.put(id, obligatoria);
			}
		} while (id != 0);
		
		return ids;
	}
	private ArrayList<Integer> SeleccionadorActividades() {
		int id;
		ArrayList<Integer> ids = new ArrayList<>();
		System.out.println("Ingrese las id de las actividades que quiere seleccionar, ingrese 0 para terminar.");
		do {
			id = input.nextInt();
			input.nextLine();
			if (id != 0) {
				ids.add(id);
			}
		} while (id != 0);
		
		return ids;
	}
	private String SeleccionadorTipo() {
		int respuesta;
		do {
			System.out.println("\nDigite el numero del tipo de actividad que quiere crear.\n"
					+ "1. ActividadTarea\n"
					+ "2. ActividadRecursoEducativo\n"
					+ "3. ActividadEncuesta\n"
					+ "4. ActividadQuiz\n"
					+ "5. ActividadExamen");
			respuesta = input.nextInt();
			input.nextLine();
			if (respuesta < 1 || respuesta > 5) {
				System.out.println("El numero que ha ingresado no esta en las opciones disponibles. Intente de nuevo.");
			}
		} while (respuesta < 1 || respuesta > 5);
		
		switch (respuesta) {
			case 1:
				return "Tarea";
			case 2:
				return "RecursoEducativo";
			case 3:
				return "Encuesta";
			case 4:
				return "Quiz";
			case 5:
				return "Examen";
		}
		return "";
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
	private void VerLearningPaths() {
		LPC.ImprimirLearningPaths();
	}
	
	private void CrearActividadExamen(String descripcion, String objetivoString, String nivelDificultad, int duracion,
			String fechaLimite, String tipo, List<Integer> idActividadesPrevias, List<Integer>idActividadesSeguimiento) {
		String pregunta;
		List<String> preguntas = new ArrayList<>();
		System.out.println("Ingrese la nota minima de la actividad");
		float notaMinima = input.nextFloat();
		input.nextLine();
		System.out.println("Ingrese las preguntas que quiere realizar en el examen una por una, escriba N cuando quiera parar.");
		do {
			pregunta = input.nextLine();
			if (!(pregunta.equals("N"))) {
				preguntas.add(pregunta);
			}
			
		} while (!(pregunta.equals("N")));
		AC.CrearActividadExamen(loginActual, tipo, descripcion, objetivoString, nivelDificultad, duracion, idActividadesPrevias, fechaLimite, idActividadesSeguimiento, preguntas, notaMinima);
		System.out.println("Actividad creada exitosamente!");
	}
	private void CrearActividadQuiz(String descripcion, String objetivoString, String nivelDificultad, int duracion,
			String fechaLimite, String tipo, List<Integer> idActividadesPrevias, List<Integer>idActividadesSeguimiento) {
		
		String pregunta, opcion1, opcion2, opcion3, opcion4, explicacion1, explicacion2, explicacion3, explicacion4;
		int correcta;
		
		HashMap<String, HashMap<String, String>> preguntas = new HashMap<>();
		ArrayList<Integer> correctas = new ArrayList<>();
		System.out.println("Ingrese la nota minima de la actividad");
		float notaMinima = input.nextFloat();
		input.nextLine();
		System.out.println("Ingrese las preguntas que quiere realizar en el examen una por una, escriba N cuando quiera parar.");
		do {
			System.out.println("Ingrese la pregunta:");
			pregunta = input.nextLine();
			if (!(pregunta.equals("N"))) {
				HashMap<String, String> opciones = new HashMap<>();
				
				System.out.println("Ingrese la primera opcion:");
				opcion1 = input.nextLine();
				
				System.out.println("Ingrese la explicacion de la primera opcion:");
				explicacion1 = input.nextLine();
				opciones.put(opcion1, explicacion1);
				
				System.out.println("Ingrese la segunda opcion:");
				opcion2 = input.nextLine();
				
				System.out.println("Ingrese la explicacion de la segunda opcion:");
				explicacion2 = input.nextLine();
				opciones.put(opcion2, explicacion2);

				System.out.println("Ingrese la tercera opcion:");
				opcion3 = input.nextLine();
				
				System.out.println("Ingrese la explicacion de la tercera opcion:");
				explicacion3 = input.nextLine();
				opciones.put(opcion3, explicacion3);
				
				System.out.println("Ingrese la cuarta opcion:");
				opcion4 = input.nextLine();
				
				System.out.println("Ingrese la explicacion de la cuarta opcion:");
				explicacion4 = input.nextLine();
				opciones.put(opcion4, explicacion4);
				
				System.out.println("Ingrese el numero de la opcion correcta:");
				correcta = input.nextInt();
				input.nextLine();
				correctas.add(correcta);
				
				preguntas.put(pregunta, opciones);
			}
		} while (!(pregunta.equals("N")));
		AC.CrearActividadQuiz(loginActual, tipo, descripcion, objetivoString, nivelDificultad, duracion, idActividadesPrevias, 
				fechaLimite, idActividadesSeguimiento, preguntas, correctas, notaMinima);
		System.out.println("Actividad creada exitosamente!");
	}
	private void CrearActividadEncuesta(String descripcion, String objetivoString, String nivelDificultad, int duracion,
			String fechaLimite, String tipo, List<Integer> idActividadesPrevias, List<Integer>idActividadesSeguimiento) {
		
		String pregunta;
		List<String> preguntas = new ArrayList<>();
		System.out.println("Ingrese las preguntas que quiere realizar en el examen una por una, escriba N cuando quiera parar.");
		do {
			pregunta = input.nextLine();
			if (!(pregunta.equals("N"))) {
				preguntas.add(pregunta);
			}
			
		} while (!(pregunta.equals("N")));
		AC.CrearActividadEncuesta(loginActual, tipo, descripcion, objetivoString, nivelDificultad, duracion, idActividadesPrevias, fechaLimite, idActividadesSeguimiento, preguntas);
		System.out.println("Actividad creada exitosamente!");
	}
	private void CrearActividadRecurso(String descripcion, String objetivoString, String nivelDificultad, int duracion,
			String fechaLimite, String tipo, List<Integer> idActividadesPrevias, List<Integer>idActividadesSeguimiento) {
		
		System.out.println("Digite la url del recurso que quiere mostrar");
		String url = input.nextLine();
		
		AC.CrearActividadRecurso(loginActual, tipo, descripcion, objetivoString, nivelDificultad, duracion, idActividadesPrevias, fechaLimite, idActividadesSeguimiento, url);
		System.out.println("Actividad creada exitosamente!");
	}
	private void CrearActividadTarea(String descripcion, String objetivoString, String nivelDificultad, int duracion,
			String fechaLimite, String tipo, List<Integer> idActividadesPrevias, List<Integer>idActividadesSeguimiento) {
		AC.CrearActividadTarea(loginActual, tipo, descripcion, objetivoString, nivelDificultad, duracion, idActividadesPrevias, fechaLimite, idActividadesSeguimiento);
		System.out.println("Actividad creada exitosamente!");
	}

	private void EditarActividadExamen(String descripcion, String objetivoString, String nivelDificultad, int duracion,
			String fechaLimite, String tipo, List<Integer> idActividadesPrevias, List<Integer>idActividadesSeguimiento, int idActividad) {
		String pregunta;
		List<String> preguntas = new ArrayList<>();
		System.out.println("Ingrese la nota minima de la actividad");
		float notaMinima = input.nextFloat();
		input.nextLine();
		System.out.println("Ingrese las preguntas que quiere realizar en el examen una por una, escriba N cuando quiera parar.");
		do {
			pregunta = input.nextLine();
			if (!(pregunta.equals("N"))) {
				preguntas.add(pregunta);
			}
			
		} while (!(pregunta.equals("N")));
		AC.CrearActividadExamen(loginActual, tipo, descripcion, objetivoString, nivelDificultad, duracion, idActividadesPrevias, fechaLimite, idActividadesSeguimiento, preguntas, notaMinima);
		System.out.println("Actividad creada exitosamente!");
	}
	private void EditarActividadQuiz(String descripcion, String objetivoString, String nivelDificultad, int duracion,
			String fechaLimite, String tipo, List<Integer> idActividadesPrevias, List<Integer>idActividadesSeguimiento, int idActividad) {
		
		String pregunta, opcion1, opcion2, opcion3, opcion4, explicacion1, explicacion2, explicacion3, explicacion4;
		int correcta;
		
		HashMap<String, HashMap<String, String>> preguntas = new HashMap<>();
		ArrayList<Integer> correctas = new ArrayList<>();
		System.out.println("Ingrese la nota minima de la actividad");
		float notaMinima = input.nextFloat();
		input.nextLine();
		System.out.println("Ingrese las preguntas que quiere realizar en el examen una por una, escriba N cuando quiera parar.");
		do {
			System.out.println("Ingrese la pregunta:");
			pregunta = input.nextLine();
			if (!(pregunta.equals("N"))) {
				HashMap<String, String> opciones = new HashMap<>();
				
				System.out.println("Ingrese la primera opcion:");
				opcion1 = input.nextLine();
				
				System.out.println("Ingrese la explicacion de la primera opcion:");
				explicacion1 = input.nextLine();
				opciones.put(opcion1, explicacion1);
				
				System.out.println("Ingrese la segunda opcion:");
				opcion2 = input.nextLine();
				
				System.out.println("Ingrese la explicacion de la segunda opcion:");
				explicacion2 = input.nextLine();
				opciones.put(opcion2, explicacion2);

				System.out.println("Ingrese la tercera opcion:");
				opcion3 = input.nextLine();
				
				System.out.println("Ingrese la explicacion de la tercera opcion:");
				explicacion3 = input.nextLine();
				opciones.put(opcion3, explicacion3);
				
				System.out.println("Ingrese la cuarta opcion:");
				opcion4 = input.nextLine();
				
				System.out.println("Ingrese la explicacion de la cuarta opcion:");
				explicacion4 = input.nextLine();
				opciones.put(opcion4, explicacion4);
				
				System.out.println("Ingrese el numero de la opcion correcta:");
				correcta = input.nextInt();
				input.nextLine();
				correctas.add(correcta);
				
				preguntas.put(pregunta, opciones);
			}
		} while (!(pregunta.equals("N")));
		AC.CrearActividadQuiz(loginActual, tipo, descripcion, objetivoString, nivelDificultad, duracion, idActividadesPrevias, fechaLimite, idActividadesSeguimiento, preguntas, correctas, notaMinima);
		System.out.println("Actividad creada exitosamente!");
	}
	private void EditarActividadEncuesta(String descripcion, String objetivoString, String nivelDificultad, int duracion,
			String fechaLimite, String tipo, List<Integer> idActividadesPrevias, List<Integer>idActividadesSeguimiento, int idActividad) {
		
		String pregunta;
		List<String> preguntas = new ArrayList<>();
		System.out.println("Ingrese las preguntas que quiere realizar en el examen una por una, escriba N cuando quiera parar.");
		do {
			pregunta = input.nextLine();
			if (!(pregunta.equals("N"))) {
				preguntas.add(pregunta);
			}
			
		} while (!(pregunta.equals("N")));
		AC.CrearActividadEncuesta(loginActual, tipo, descripcion, objetivoString, nivelDificultad, duracion, idActividadesPrevias, fechaLimite, idActividadesSeguimiento, preguntas);
		System.out.println("Actividad creada exitosamente!");
	}
	private void EditarActividadRecurso(String descripcion, String objetivoString, String nivelDificultad, int duracion,
			String fechaLimite, String tipo, List<Integer> idActividadesPrevias, List<Integer>idActividadesSeguimiento, int idActividad) {
		
		System.out.println("Digite la url del recurso que quiere mostrar");
		String url = input.nextLine();
		
		AC.CrearActividadRecurso(loginActual, tipo, descripcion, objetivoString, nivelDificultad, duracion, idActividadesPrevias, fechaLimite, idActividadesSeguimiento, url);
		System.out.println("Actividad creada exitosamente!");
	}
	private void EditarActividadTarea(String descripcion, String objetivoString, String nivelDificultad, int duracion,
			String fechaLimite, String tipo, List<Integer> idActividadesPrevias, List<Integer>idActividadesSeguimiento, int idActividad) {
		AC.CrearActividadTarea(loginActual, tipo, descripcion, objetivoString, nivelDificultad, duracion, idActividadesPrevias, fechaLimite, idActividadesSeguimiento);
		System.out.println("Actividad creada exitosamente!");
	}
	
	private void InscribirLearningPath() {
		System.out.println("Inserte la id del learning path que quiere inscribir.");
		int idLP = input.nextInt();
		input.nextLine();
		RGC.InscribirEstudiante(idLP, loginActual, AC, LPC);
		System.out.println("Se ha inscrito exitosamente!");
	}
	
	private void DesarrollarActividad() {
		if (!(rolActual.equals("Estudiante"))) {
			System.out.println("\nUsted es un profesor, no tiene porque realizar actividades");
		} else {
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
}