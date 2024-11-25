package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import procesamiento.ProcesamientoProfesor;

public class ProcesamientoProfesorTest {

    private ProcesamientoProfesor procesamiento;

    @BeforeEach
    public void setUp() {
        procesamiento = new ProcesamientoProfesor();
    }

    @Test
    public void testCrearProfesor() {
        String login = "profesor1";
        String password = "pass123";

        procesamiento.CrearProfesor(login, password);

        // Verificar que el profesor se ha añadido
        assertTrue(procesamiento.ExisteProfesor(login), "El profesor debe existir después de crearlo");

        // Opcional: Verificar que la contraseña es correcta
        assertTrue(procesamiento.IngresoProfesor(login, password), "Las credenciales deben coincidir");
    }

    @Test
    public void testExisteProfesor() {
        String login = "profesor2";

        // Aún no existe
        assertFalse(procesamiento.ExisteProfesor(login), "El profesor no debe existir inicialmente");

        // Crear el profesor
        procesamiento.CrearProfesor(login, "password");

        // Ahora debe existir
        assertTrue(procesamiento.ExisteProfesor(login), "El profesor debe existir después de crearlo");
    }

    @Test
    public void testIngresoProfesor() {
        String login = "profesor3";
        String password = "securePass";

        procesamiento.CrearProfesor(login, password);

        // Ingreso correcto
        assertTrue(procesamiento.IngresoProfesor(login, password), "El ingreso debe ser exitoso con las credenciales correctas");

        // Ingreso con contraseña incorrecta
        assertFalse(procesamiento.IngresoProfesor(login, "wrongPass"), "El ingreso debe fallar con una contraseña incorrecta");

        // Ingreso con usuario inexistente
        assertFalse(procesamiento.IngresoProfesor("profesorNoExiste", "anyPass"), "El ingreso debe fallar con un usuario inexistente");
    }

    @Test
    public void testGuardarProfesoresEnArchivo(@TempDir Path tempDir) throws IOException {
        // Crear profesores
        procesamiento.CrearProfesor("prof1", "pass1");
        procesamiento.CrearProfesor("prof2", "pass2");

        // Guardar en archivo temporal
        String nombreArchivo = "profesores_test.txt";
        File tempFile = tempDir.resolve(nombreArchivo).toFile();

        procesamiento.guardarProfesoresEnArchivo(tempFile.getName());

        // Leer el archivo y verificar el contenido
        List<String> lines = Files.readAllLines(tempFile.toPath());
        assertEquals(2, lines.size(), "Debe haber dos líneas en el archivo");

        assertTrue(lines.contains("prof1,pass1"), "El archivo debe contener los datos del primer profesor");
        assertTrue(lines.contains("prof2,pass2"), "El archivo debe contener los datos del segundo profesor");
    }

    @Test
    public void testCargarProfesoresDesdeArchivo(@TempDir Path tempDir) throws IOException {
        // Crear archivo temporal con datos de profesores
        String nombreArchivo = "profesores_cargar_test.txt";
        File tempFile = tempDir.resolve(nombreArchivo).toFile();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("profA,passA\n");
            writer.write("profB,passB\n");
        }

        // Cargar profesores desde el archivo
        procesamiento.cargarProfesoresDesdeArchivo(tempFile.getName());

        // Verificar que los profesores se han cargado correctamente
        assertTrue(procesamiento.ExisteProfesor("profA"), "El profesor profA debe existir después de cargar");
        assertTrue(procesamiento.ExisteProfesor("profB"), "El profesor profB debe existir después de cargar");

        // Verificar las contraseñas
        assertTrue(procesamiento.IngresoProfesor("profA", "passA"), "Las credenciales de profA deben ser correctas");
        assertTrue(procesamiento.IngresoProfesor("profB", "passB"), "Las credenciales de profB deben ser correctas");
    }

    @Test
    public void testIngresoProfesorConUsuarioInexistente() {
        // Intentar ingresar con un usuario que no existe
        assertFalse(procesamiento.IngresoProfesor("profesorInexistente", "password"), "El ingreso debe fallar con un usuario inexistente");
    }

    @Test
    public void testCrearProfesorDuplicado() {
        String login = "profesorDuplicado";
        String password1 = "pass1";
        String password2 = "pass2";

        procesamiento.CrearProfesor(login, password1);

        // Intentar crear otro profesor con el mismo login
        procesamiento.CrearProfesor(login, password2);

        // Verificar que la contraseña no ha cambiado
        assertTrue(procesamiento.IngresoProfesor(login, password1), "La contraseña debe seguir siendo la original");
        assertFalse(procesamiento.IngresoProfesor(login, password2), "La nueva contraseña no debe haber reemplazado a la original");
    }
}
