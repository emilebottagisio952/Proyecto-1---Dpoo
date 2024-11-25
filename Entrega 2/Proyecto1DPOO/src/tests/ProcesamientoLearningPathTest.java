package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import procesamiento.ProcesamientoLearningPath;
import procesamiento.ProcesamientoActividad;

import elementos.Actividad;
import elementos.LearningPath;

public class ProcesamientoLearningPathTest {

    private ProcesamientoLearningPath procesamientoLP;
    private ProcesamientoActividad procesamientoActividad;

    @BeforeEach
    public void setUp() {
        procesamientoLP = new ProcesamientoLearningPath();
        procesamientoActividad = new ProcesamientoActividad();

        // Crear algunas actividades de ejemplo
        procesamientoActividad.CrearActividadTarea("creador1", "Tarea", "Tarea 1", "Objetivo1", "Fácil", 30, new ArrayList<>(), "2024-11-30", new ArrayList<>(), 0);
        procesamientoActividad.CrearActividadRecurso("creador2", "Recurso", "Recurso 1", "Objetivo2", "Medio", 45, new ArrayList<>(), "2024-12-15", new ArrayList<>(), "http://ejemplo.com");

    }

    @Test
    public void testCrearLearningPath() {
        String titulo = "Learning Path 1";
        String descripcion = "Descripción del LP 1";
        String nivelDificultad = "Medio";
        int duracion = 75;
        String loginActual = "usuarioCreador";

        // Mapear IDs de actividades a booleanos (por ejemplo, si es obligatorio o no)
        HashMap<Integer, Boolean> idActividades = new HashMap<>();
        idActividades.put(1, true);
        idActividades.put(2, false);

        procesamientoLP.CrearLearningPath(titulo, descripcion, nivelDificultad, duracion, idActividades, procesamientoActividad, loginActual);

        // Verificar que el learning path se ha añadido
        assertEquals(1, procesamientoLP.getNumeroLearningPaths(), "Debe haber un learning path creado");

        LearningPath lp = procesamientoLP.getLearningPath(1);
        assertNotNull(lp, "El learning path no debe ser nulo");
        assertEquals(titulo, lp.getTitulo(), "El título debe coincidir");
        assertEquals(descripcion, lp.getDescripcionGeneral(), "La descripción debe coincidir");
        assertEquals(nivelDificultad, lp.getNivelDificultad(), "El nivel de dificultad debe coincidir");
        assertEquals(duracion, lp.getDuracion(), "La duración debe coincidir");
        assertEquals(loginActual, lp.getLoginCreador(), "El creador debe coincidir");
        assertEquals(0, lp.getVersion(), "La versión inicial debe ser 0");

        // Verificar que las actividades se han asignado correctamente
        HashMap<Actividad, Boolean> actividades = lp.getActividades();
        assertNotNull(actividades, "Las actividades no deben ser nulas");
        assertEquals(2, actividades.size(), "Debe haber dos actividades asignadas");

        // Verificar que las actividades son correctas
        Actividad actividad1 = procesamientoActividad.GetActividad(1);
        Actividad actividad2 = procesamientoActividad.GetActividad(2);

        assertTrue(actividades.containsKey(actividad1), "Debe contener la actividad 1");
        assertTrue(actividades.get(actividad1), "La actividad 1 debe ser obligatoria");

        assertTrue(actividades.containsKey(actividad2), "Debe contener la actividad 2");
        assertFalse(actividades.get(actividad2), "La actividad 2 no debe ser obligatoria");
    }

    @Test
    public void testCrearLearningPathEdicion() {
        // Primero, crear un learning path original
        String titulo = "Learning Path Original";
        String descripcion = "Descripción original";
        String nivelDificultad = "Medio";
        int duracion = 60;
        String loginActual = "usuarioCreador";

        HashMap<Integer, Boolean> idActividades = new HashMap<>();
        idActividades.put(1, true);

        procesamientoLP.CrearLearningPath(titulo, descripcion, nivelDificultad, duracion, idActividades, procesamientoActividad, loginActual);

        // Ahora, editar el learning path existente
        String nuevoTitulo = "Learning Path Editado";
        String nuevaDescripcion = "Descripción editada";
        String nuevoNivelDificultad = "Alto";
        int nuevaDuracion = 90;
        HashMap<Integer, Boolean> nuevasActividades = new HashMap<>();
        nuevasActividades.put(2, true);

        int idLP = 1; // ID del learning path a editar

        procesamientoLP.CrearLearningPath(nuevoTitulo, nuevaDescripcion, nuevoNivelDificultad, nuevaDuracion, nuevasActividades, procesamientoActividad, loginActual, idLP);

        // Verificar que se ha añadido una nueva versión del learning path
        assertEquals(2, procesamientoLP.getNumeroLearningPaths(), "Debe haber dos learning paths después de la edición");

        LearningPath lpEditado = procesamientoLP.getLearningPath(2); // La nueva versión se añade con un nuevo ID
        assertNotNull(lpEditado, "El learning path editado no debe ser nulo");
        assertEquals(nuevoTitulo, lpEditado.getTitulo(), "El título debe coincidir");
        assertEquals(nuevaDescripcion, lpEditado.getDescripcionGeneral(), "La descripción debe coincidir");
        assertEquals(nuevoNivelDificultad, lpEditado.getNivelDificultad(), "El nivel de dificultad debe coincidir");
        assertEquals(nuevaDuracion, lpEditado.getDuracion(), "La duración debe coincidir");
        assertEquals(1, lpEditado.getVersion(), "La versión debe incrementarse a 1");

        // Verificar que las actividades se han actualizado
        HashMap<Actividad, Boolean> actividades = lpEditado.getActividades();
        assertNotNull(actividades, "Las actividades no deben ser nulas");
        assertEquals(1, actividades.size(), "Debe haber una actividad asignada");

        Actividad actividad2 = procesamientoActividad.GetActividad(2);
        assertTrue(actividades.containsKey(actividad2), "Debe contener la actividad 2");
        assertTrue(actividades.get(actividad2), "La actividad 2 debe ser obligatoria");
    }

    @Test
    public void testCrearLearningPathEdicionNoCreador() {
        // Crear un learning path original
        String titulo = "Learning Path Original";
        String descripcion = "Descripción original";
        String nivelDificultad = "Medio";
        int duracion = 60;
        String loginCreador = "usuarioCreador";

        HashMap<Integer, Boolean> idActividades = new HashMap<>();
        idActividades.put(1, true);

        procesamientoLP.CrearLearningPath(titulo, descripcion, nivelDificultad, duracion, idActividades, procesamientoActividad, loginCreador);

        // Intentar editar el learning path con un usuario diferente
        String loginOtroUsuario = "otroUsuario";

        // Capturar la salida de consola
        ByteArrayOutputStream salidaConsola = new ByteArrayOutputStream();
        System.setOut(new PrintStream(salidaConsola));

        procesamientoLP.CrearLearningPath("Nuevo Título", "Nueva Descripción", "Alto", 90, idActividades, procesamientoActividad, loginOtroUsuario, 1);

        // Restaurar la salida estándar
        System.setOut(System.out);

        String salida = salidaConsola.toString();
        assertTrue(salida.contains("Usted no es el creador de este learning path, no tiene derecho a modificarlo"), "Debe mostrar un mensaje indicando que no es el creador");

        // Verificar que no se ha añadido una nueva versión
        assertEquals(1, procesamientoLP.getNumeroLearningPaths(), "No debe haberse añadido un nuevo learning path");
    }

    @Test
    public void testGetActividadesLP() {
        // Crear un learning path con dos actividades
        HashMap<Integer, Boolean> idActividades = new HashMap<>();
        idActividades.put(1, true);
        idActividades.put(2, false);

        procesamientoLP.CrearLearningPath("LP para prueba", "Descripción", "Fácil", 75, idActividades, procesamientoActividad, "usuarioCreador");

        // Obtener los IDs de las actividades del learning path
        ArrayList<Integer> idsActividades = procesamientoLP.GetActividadesLP(1);

        // Verificar que se han obtenido los IDs correctos
        assertNotNull(idsActividades, "La lista de IDs no debe ser nula");
        assertEquals(2, idsActividades.size(), "Debe haber dos IDs de actividades");

        assertTrue(idsActividades.contains(1), "Debe contener el ID 1");
        assertTrue(idsActividades.contains(2), "Debe contener el ID 2");
    }

    @Test
    public void testGuardarYcargarLPDesdeArchivo(@TempDir Path tempDir) throws IOException {
        // Crear un learning path
        HashMap<Integer, Boolean> idActividades = new HashMap<>();
        idActividades.put(1, true);

        procesamientoLP.CrearLearningPath("LP Archivo", "Descripción Archivo", "Medio", 60, idActividades, procesamientoActividad, "usuarioCreador");

        // Guardar en un archivo temporal
        String nombreArchivo = "learningpaths_test.txt";
        File tempFile = tempDir.resolve(nombreArchivo).toFile();

        procesamientoLP.guardarLPEnArchivo(tempFile.getName());

        // Crear una nueva instancia y cargar desde el archivo
        ProcesamientoLearningPath nuevoProcesamientoLP = new ProcesamientoLearningPath();
        nuevoProcesamientoLP.cargarLPDesdeArchivo(tempFile.getName());

        // Verificar que se ha cargado correctamente
        assertEquals(1, nuevoProcesamientoLP.getNumeroLearningPaths(), "Debe haber un learning path cargado");

        LearningPath lpCargado = nuevoProcesamientoLP.getLearningPath(1);
        assertNotNull(lpCargado, "El learning path cargado no debe ser nulo");
        assertEquals("LP Archivo", lpCargado.getTitulo(), "El título debe coincidir");
        assertEquals("Descripción Archivo", lpCargado.getDescripcionGeneral(), "La descripción debe coincidir");
    }
}
