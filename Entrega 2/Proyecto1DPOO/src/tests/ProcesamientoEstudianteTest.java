package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import procesamiento.ProcesamientoEstudiante;


public class ProcesamientoEstudianteTest {

    private ProcesamientoEstudiante procesamiento;

    @BeforeEach
    public void setUp() {
        procesamiento = new ProcesamientoEstudiante();
    }

    @Test
    public void testCrearEstudiante() {
        String login = "usuario1";
        String password = "pass123";
        
        procesamiento.CrearEstudiante(login, password);
        
        // Verificar que el estudiante se ha añadido
        assertTrue(procesamiento.ExisteEstudiante(login), "El estudiante debe existir después de crearlo");
        
        // Verificar que la contraseña es correcta
        assertTrue(procesamiento.IngresoEstudiante(login, password), "Las credenciales deben coincidir");
    }

    @Test
    public void testExisteEstudiante() {
        String login = "usuario2";
        
        // Aún no existe
        assertFalse(procesamiento.ExisteEstudiante(login), "El estudiante no debe existir inicialmente");
        
        // Crear el estudiante
        procesamiento.CrearEstudiante(login, "password");
        
        // Ahora debe existir
        assertTrue(procesamiento.ExisteEstudiante(login), "El estudiante debe existir después de crearlo");
    }

    @Test
    public void testIngresoEstudiante() {
        String login = "usuario3";
        String password = "securePass";
        
        procesamiento.CrearEstudiante(login, password);
        
        // Ingreso correcto
        assertTrue(procesamiento.IngresoEstudiante(login, password), "El ingreso debe ser exitoso con las credenciales correctas");
        
        // Ingreso con contraseña incorrecta
        assertFalse(procesamiento.IngresoEstudiante(login, "wrongPass"), "El ingreso debe fallar con una contraseña incorrecta");
        
        // Ingreso con usuario inexistente
        assertFalse(procesamiento.IngresoEstudiante("usuarioNoExiste", "anyPass"), "El ingreso debe fallar con un usuario inexistente");
    }

    @Test
    public void testGuardarEstudiantesEnArchivo(@TempDir Path tempDir) throws IOException {
        // Crear estudiantes
        procesamiento.CrearEstudiante("user1", "pass1");
        procesamiento.CrearEstudiante("user2", "pass2");
        
        // Guardar en archivo temporal
        String nombreArchivo = "estudiantes_test.txt";
        File tempFile = tempDir.resolve(nombreArchivo).toFile();
        
        procesamiento.guardarEstudiantesEnArchivo(tempFile.getName());
        
        // Leer el archivo y verificar el contenido
        List<String> lines = Files.readAllLines(tempFile.toPath());
        assertEquals(2, lines.size(), "Debe haber dos líneas en el archivo");
        
        assertTrue(lines.contains("user1,pass1"), "El archivo debe contener los datos del primer estudiante");
        assertTrue(lines.contains("user2,pass2"), "El archivo debe contener los datos del segundo estudiante");
    }

    @Test
    public void testCargarEstudiantesDesdeArchivo(@TempDir Path tempDir) throws IOException {
        // Crear archivo temporal con datos de estudiantes
        String nombreArchivo = "estudiantes_cargar_test.txt";
        File tempFile = tempDir.resolve(nombreArchivo).toFile();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("userA,passA\n");
            writer.write("userB,passB\n");
        }
        
        // Cargar estudiantes desde el archivo
        procesamiento.cargarEstudiantesDesdeArchivo(tempFile.getName());
        
        // Verificar que los estudiantes se han cargado correctamente
        assertTrue(procesamiento.ExisteEstudiante("userA"), "El estudiante userA debe existir después de cargar");
        assertTrue(procesamiento.ExisteEstudiante("userB"), "El estudiante userB debe existir después de cargar");
        
        // Verificar las contraseñas
        assertTrue(procesamiento.IngresoEstudiante("userA", "passA"), "Las credenciales de userA deben ser correctas");
        assertTrue(procesamiento.IngresoEstudiante("userB", "passB"), "Las credenciales de userB deben ser correctas");
    }

    @Test
    public void testIngresoEstudianteConUsuarioInexistente() {
        // Intentar ingresar con un usuario que no existe
        assertFalse(procesamiento.IngresoEstudiante("usuarioInexistente", "password"), "El ingreso debe fallar con un usuario inexistente");
    }

    @Test
    public void testCrearEstudianteDuplicado() {
        String login = "usuarioDuplicado";
        String password1 = "pass1";
        String password2 = "pass2";
        
        procesamiento.CrearEstudiante(login, password1);
        
        // Intentar crear otro estudiante con el mismo login
        procesamiento.CrearEstudiante(login, password2);
        
        // Verificar que la contraseña no ha cambiado
        assertTrue(procesamiento.IngresoEstudiante(login, password1), "La contraseña debe seguir siendo la original");
        assertFalse(procesamiento.IngresoEstudiante(login, password2), "La nueva contraseña no debe haber reemplazado a la original");
    }
}

