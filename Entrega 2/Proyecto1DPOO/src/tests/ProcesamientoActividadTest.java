package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import procesamiento.ProcesamientoActividad;

import elementos.Actividad;
import elementos.PreguntaAbierta;
import elementos.PreguntaMultiple;
import elementos.Opcion;

public class ProcesamientoActividadTest {

    private ProcesamientoActividad procesamiento;

    @BeforeEach
    public void setUp() {
        procesamiento = new ProcesamientoActividad();
    }

    @Test
    public void testCrearActividadExamen() {
        String loginCreador = "usuario1";
        String tipo = "Examen";
        String descripcion = "Examen de Matemáticas";
        String stringObjetivos = "Algebra,Geometría";
        String nivelDificultad = "Medio";
        int duracion = 60;
        List<Integer> idActividadesPrevias = new ArrayList<>();
        String fechaLimite = "2024-12-31";
        List<Integer> idActividadesSeguimiento = new ArrayList<>();
        List<String> preguntas = Arrays.asList("¿Cuál es la derivada de x^2?", "Resuelve la integral de 1/x.");
        float notaMinima = 5.0f;

        procesamiento.CrearActividadExamen(loginCreador, tipo, descripcion, stringObjetivos,
                nivelDificultad, duracion, idActividadesPrevias, fechaLimite,
                idActividadesSeguimiento, preguntas, notaMinima);

        // Verificar que la actividad se ha añadido
        assertEquals(1, procesamiento.getNumeroActividades(), "Debe haber una actividad creada");

        Actividad actividad = procesamiento.GetActividad(1);
        assertNotNull(actividad, "La actividad con ID 1 no debe ser nula");
        assertEquals(loginCreador, actividad.getLoginCreador(), "El creador debe coincidir");
        assertEquals(tipo, actividad.getTipo(), "El tipo debe coincidir");
        assertEquals(descripcion, actividad.getDescripcion(), "La descripción debe coincidir");
        assertArrayEquals(new String[]{"Algebra", "Geometría"}, actividad.getObjetivos(), "Los objetivos deben coincidir");
        assertEquals(nivelDificultad, actividad.getNivelDificultad(), "El nivel de dificultad debe coincidir");
        assertEquals(duracion, actividad.getDuracion(), "La duración debe coincidir");
        assertEquals(fechaLimite, actividad.getFechaLimite(), "La fecha límite debe coincidir");
        assertEquals(notaMinima, actividad.getNotaMinima(), 0.001, "La nota mínima debe coincidir");

        // Verificar que las preguntas abiertas se han añadido correctamente
        List<PreguntaAbierta> preguntasAbiertas = actividad.getPreguntasAbiertas();
        assertNotNull(preguntasAbiertas, "Las preguntas abiertas no deben ser nulas");
        assertEquals(2, preguntasAbiertas.size(), "Debe haber dos preguntas abiertas");
        assertEquals("¿Cuál es la derivada de x^2?", preguntasAbiertas.get(0).getTextoPregunta(), "La primera pregunta debe coincidir");
        assertEquals("Resuelve la integral de 1/x.", preguntasAbiertas.get(1).getTextoPregunta(), "La segunda pregunta debe coincidir");
    }

    @Test
    public void testVerificarCreador() {
        // Crear una actividad y obtener su ID
        String loginCreador = "usuario2";
        int idActividad = procesamiento.CrearActividadTarea(loginCreador, "Tarea", "Tarea de Física", "Mecánica",
                "Fácil", 30, new ArrayList<>(), "2024-11-30", new ArrayList<>(), 0);

        // Verificar con el creador correcto
        assertTrue(procesamiento.VerificarCreador(idActividad, "usuario2"), "El creador debe ser verificado correctamente");

        // Verificar con un creador incorrecto
        assertFalse(procesamiento.VerificarCreador(idActividad, "usuario3"), "El creador no debe ser verificado incorrectamente");

        // Verificar con un ID inexistente
        assertFalse(procesamiento.VerificarCreador(9999, "usuario2"), "Debe retornar falso para un ID inexistente");
    }

    @Test
    public void testCargarActividadesDesdeArchivo(@TempDir Path tempDir) throws IOException {
    	// Crear un archivo temporal con datos de actividades
        File tempFile = tempDir.resolve("actividades_test.txt").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            // Formato: descripcion;duracion;loginCreador;nivelDificultad;tipo;objetivos;actividadesPrevias;fechaLimite;actividadesSeguimiento;notaMinima
            writer.write("Descripción Examen 1;90;creador1;Alto;Examen;Objetivo1,Objetivo2;;2024-12-15;;7.5\n");
            writer.write("Descripción Tarea 1;60;creador2;Medio;Tarea;Objetivo3,Objetivo4;;2024-11-20;;5.0\n");
        }

        // Cargar actividades desde el archivo usando la ruta absoluta
        procesamiento.cargarACDesdeArchivo(tempFile.getAbsolutePath());

        // Verificar que las actividades se han cargado correctamente
        assertEquals(2, procesamiento.getNumeroActividades(), "Debe haber dos actividades cargadas");

        Actividad actividad1 = procesamiento.GetActividad(1);
        assertNotNull(actividad1, "La primera actividad no debe ser nula");
        assertEquals("Descripción Examen 1", actividad1.getDescripcion(), "La descripción de la primera actividad debe coincidir");
        assertEquals(90, actividad1.getDuracion(), "La duración de la primera actividad debe coincidir");
        assertEquals("creador1", actividad1.getLoginCreador(), "El creador de la primera actividad debe coincidir");
        assertEquals("Alto", actividad1.getNivelDificultad(), "El nivel de dificultad de la primera actividad debe coincidir");
        assertEquals("Examen", actividad1.getTipo(), "El tipo de la primera actividad debe coincidir");
        assertArrayEquals(new String[]{"Objetivo1", "Objetivo2"}, actividad1.getObjetivos(), "Los objetivos de la primera actividad deben coincidir");
        assertEquals("2024-12-15", actividad1.getFechaLimite(), "La fecha límite de la primera actividad debe coincidir");
        assertEquals(7.5f, actividad1.getNotaMinima(), 0.001, "La nota mínima de la primera actividad debe coincidir");

        Actividad actividad2 = procesamiento.GetActividad(2);
        assertNotNull(actividad2, "La segunda actividad no debe ser nula");
        assertEquals("Descripción Tarea 1", actividad2.getDescripcion(), "La descripción de la segunda actividad debe coincidir");
        assertEquals(60, actividad2.getDuracion(), "La duración de la segunda actividad debe coincidir");
        assertEquals("creador2", actividad2.getLoginCreador(), "El creador de la segunda actividad debe coincidir");
        assertEquals("Medio", actividad2.getNivelDificultad(), "El nivel de dificultad de la segunda actividad debe coincidir");
        assertEquals("Tarea", actividad2.getTipo(), "El tipo de la segunda actividad debe coincidir");
        assertArrayEquals(new String[]{"Objetivo3", "Objetivo4"}, actividad2.getObjetivos(), "Los objetivos de la segunda actividad deben coincidir");
        assertEquals("2024-11-20", actividad2.getFechaLimite(), "La fecha límite de la segunda actividad debe coincidir");
        assertEquals(5.0f, actividad2.getNotaMinima(), 0.001, "La nota mínima de la segunda actividad debe coincidir");
    }

    @Test
    public void testGuardarActividadesEnArchivo(@TempDir Path tempDir) throws IOException {
        // Crear algunas actividades
    	procesamiento.CrearActividadRecurso("creador1", "Recurso", "Recurso de Química", "ObjetivoA,ObjetivoB",
    	        "Bajo", 15, new ArrayList<>(), "2024-10-10", new ArrayList<>(), "http://ejemplo.com");
    	procesamiento.CrearActividadEncuesta("profesor2", "Encuesta", "Encuesta 1", "Objetivo2", "Medio", 45,
    	        new ArrayList<>(), "2024-11-30", new ArrayList<>(), Arrays.asList("Pregunta 1", "Pregunta 2"));

                Arrays.asList("Pregunta 1", "Pregunta 2", 0);

        // Guardar actividades en el archivo temporal usando la ruta absoluta
        File tempFile = tempDir.resolve("actividades_guardar_test.txt").toFile();
        procesamiento.guardarActividadesEnArchivo(tempFile.getAbsolutePath());

        // Leer el archivo y verificar su contenido
        List<String> lines = Files.readAllLines(tempFile.toPath());

        assertEquals(2, lines.size(), "Debe haber dos líneas en el archivo");

        // Verificar que las líneas contienen información clave
        assertTrue(lines.get(0).contains("Recurso de Química"), "La primera línea debe contener la descripción de la primera actividad");
        assertTrue(lines.get(1).contains("Encuesta de Historia"), "La segunda línea debe contener la descripción de la segunda actividad");

        // Debido a que los arrays y listas se imprimen con su referencia, no podemos comparar exactamente.
        // En su lugar, verificaremos que las descripciones y otros campos clave están presentes.

        assertTrue(lines.get(0).contains("Recurso de Química"), "La primera línea debe contener la descripción correcta");
        assertTrue(lines.get(0).contains("15"), "La primera línea debe contener la duración correcta");
        assertTrue(lines.get(0).contains("creador1"), "La primera línea debe contener el creador correcto");
        assertTrue(lines.get(0).contains("Bajo"), "La primera línea debe contener el nivel de dificultad correcto");
        assertTrue(lines.get(0).contains("Recurso"), "La primera línea debe contener el tipo correcto");
        assertTrue(lines.get(0).contains("ObjetivoA,ObjetivoB"), "La primera línea debe contener los objetivos correctos");
        assertTrue(lines.get(0).contains("http://ejemplo.com"), "La primera línea debe contener la URL correcta");

        assertTrue(lines.get(1).contains("Encuesta de Historia"), "La segunda línea debe contener la descripción correcta");
        assertTrue(lines.get(1).contains("20"), "La segunda línea debe contener la duración correcta");
        assertTrue(lines.get(1).contains("creador2"), "La segunda línea debe contener el creador correcto");
        assertTrue(lines.get(1).contains("Medio"), "La segunda línea debe contener el nivel de dificultad correcto");
        assertTrue(lines.get(1).contains("Encuesta"), "La segunda línea debe contener el tipo correcto");
        assertTrue(lines.get(1).contains("ObjetivoC,ObjetivoD"), "La segunda línea debe contener los objetivos correctos");
    }

    @Test
    public void testCrearActividadQuiz() {
        String loginCreador = "usuarioQuiz";
        String tipo = "Quiz";
        String descripcion = "Quiz de Programación";
        String stringObjetivos = "Variables,Control de flujo";
        String nivelDificultad = "Alto";
        int duracion = 45;
        List<Integer> idActividadesPrevias = new ArrayList<>();
        String fechaLimite = "2024-12-01";
        List<Integer> idActividadesSeguimiento = new ArrayList<>();

        // Definir preguntas y opciones
        HashMap<String, HashMap<String, String>> preguntas = new HashMap<>();
        HashMap<String, String> opciones1 = new HashMap<>();
        opciones1.put("Opción A", "Respuesta A");
        opciones1.put("Opción B", "Respuesta B");
        opciones1.put("Opción C", "Respuesta C");
        preguntas.put("¿Qué es una variable?", opciones1);

        List<Integer> correctas = Arrays.asList(2); // Supongamos que la segunda opción es la correcta

        float notaMinima = 6.0f;

        procesamiento.CrearActividadQuiz(loginCreador, tipo, descripcion, stringObjetivos,
                nivelDificultad, duracion, idActividadesPrevias, fechaLimite,
                idActividadesSeguimiento, preguntas, correctas, notaMinima);

        // Verificar que la actividad se ha añadido
        assertEquals(1, procesamiento.getNumeroActividades(), "Debe haber una actividad de tipo Quiz creada");

        Actividad actividad = procesamiento.GetActividad(1);
        assertNotNull(actividad, "La actividad de tipo Quiz no debe ser nula");
        assertEquals(tipo, actividad.getTipo(), "El tipo de actividad debe ser Quiz");
        assertEquals(descripcion, actividad.getDescripcion(), "La descripción debe coincidir");
        assertArrayEquals(new String[]{"Variables", "Control de flujo"}, actividad.getObjetivos(), "Los objetivos deben coincidir");
        assertEquals(nivelDificultad, actividad.getNivelDificultad(), "El nivel de dificultad debe coincidir");
        assertEquals(duracion, actividad.getDuracion(), "La duración debe coincidir");
        assertEquals(fechaLimite, actividad.getFechaLimite(), "La fecha límite debe coincidir");
        assertEquals(notaMinima, actividad.getNotaMinima(), 0.001, "La nota mínima debe coincidir");

        // Verificar las preguntas múltiples
        List<PreguntaMultiple> preguntasMultiples = actividad.getPreguntasMultiples();
        assertNotNull(preguntasMultiples, "Las preguntas múltiples no deben ser nulas");
        assertEquals(1, preguntasMultiples.size(), "Debe haber una pregunta múltiple");

        PreguntaMultiple preguntaMultiple = preguntasMultiples.get(0);
        assertEquals("¿Qué es una variable?", preguntaMultiple.getTextoPregunta(), "La pregunta debe coincidir");

        List<Opcion> opciones = preguntaMultiple.getOpciones();
        assertEquals(3, opciones.size(), "Debe haber tres opciones");

        // Verificar que la opción correcta está marcada
        assertFalse(opciones.get(0).getCorrecta(), "La primera opción no debe ser correcta");
        assertTrue(opciones.get(1).getCorrecta(), "La segunda opción debe ser correcta");
        assertFalse(opciones.get(2).getCorrecta(), "La tercera opción no debe ser correcta");
    }
}

