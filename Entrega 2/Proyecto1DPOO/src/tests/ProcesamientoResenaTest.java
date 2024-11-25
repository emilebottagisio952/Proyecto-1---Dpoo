package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import procesamiento.ProcesamientoResena;

import elementos.Resena;

public class ProcesamientoResenaTest {

    private ProcesamientoResena procesamientoResena;

    @BeforeEach
    public void setUp() {
        procesamientoResena = new ProcesamientoResena();
    }

    @Test
    public void testCrearResena() {
        int idActividad = 1;
        String opinion = "Excelente actividad";
        int rating = 5;
        String loginAutor = "usuario1";
        String rolAutor = "Estudiante";

        procesamientoResena.CrearResena(idActividad, opinion, rating, loginAutor, rolAutor);

        // Verificar que la reseña se ha agregado
        ArrayList<Resena> resenas = procesamientoResena.getResenas();
        assertEquals(1, resenas.size(), "Debe haber una reseña en la lista");

        Resena resena = resenas.get(0);
        assertEquals(idActividad, resena.getIdActividad(), "El ID de la actividad debe coincidir");
        assertEquals(opinion, resena.getOpinion(), "La opinión debe coincidir");
        assertEquals(rating, resena.getRating(), "El rating debe coincidir");
        assertEquals(loginAutor, resena.getLoginAutor(), "El login del autor debe coincidir");
        assertEquals(rolAutor, resena.getRolAutor(), "El rol del autor debe coincidir");
    }

    @Test
    public void testResenasActividad() {
        // Crear reseñas para distintas actividades
        procesamientoResena.CrearResena(1, "Reseña 1", 4, "usuario1", "Estudiante");
        procesamientoResena.CrearResena(2, "Reseña 2", 5, "usuario2", "Profesor");
        procesamientoResena.CrearResena(1, "Reseña 3", 3, "usuario3", "Estudiante");

        // Obtener reseñas para la actividad con ID 1
        ArrayList<Resena> resenasActividad1 = procesamientoResena.ResenasActividad(1);

        // Verificar que se han obtenido las reseñas correctas
        assertEquals(2, resenasActividad1.size(), "Debe haber dos reseñas para la actividad 1");
        for (Resena resena : resenasActividad1) {
            assertEquals(1, resena.getIdActividad(), "Todas las reseñas deben ser de la actividad 1");
        }
    }

    @Test
    public void testCalcularRating() {
        // Crear reseñas para una actividad
        procesamientoResena.CrearResena(3, "Reseña 1", 4, "usuario1", "Estudiante");
        procesamientoResena.CrearResena(3, "Reseña 2", 5, "usuario2", "Profesor");
        procesamientoResena.CrearResena(3, "Reseña 3", 3, "usuario3", "Estudiante");

        // Calcular el rating promedio para la actividad 3
        float ratingPromedio = procesamientoResena.calcularRating(3);

        // El promedio debe ser (4 + 5 + 3) / 3 = 4.0
        assertEquals(4.0, ratingPromedio, 0.01, "El rating promedio debe ser 4.0");
    }

    @Test
    public void testGuardarResenasEnArchivo(@TempDir Path tempDir) throws IOException {
        // Crear reseñas
        procesamientoResena.CrearResena(1, "Reseña 1", 4, "usuario1", "Estudiante");
        procesamientoResena.CrearResena(2, "Reseña 2", 5, "usuario2", "Profesor");

        // Guardar en archivo temporal
        String nombreArchivo = "resenas_test";
        File tempFile = tempDir.resolve(nombreArchivo + ".txt").toFile();

        procesamientoResena.guardarResenasEnArchivo(tempFile.getAbsolutePath());

        // Leer el archivo y verificar el contenido
        List<String> lines = Files.readAllLines(tempFile.toPath());
        assertEquals(2, lines.size(), "Debe haber dos líneas en el archivo");

        String expectedLine1 = "1,Reseña 1,4,usuario1,Estudiante";
        String expectedLine2 = "2,Reseña 2,5,usuario2,Profesor";

        assertTrue(lines.contains(expectedLine1), "El archivo debe contener la primera reseña");
        assertTrue(lines.contains(expectedLine2), "El archivo debe contener la segunda reseña");
    }

    @Test
    public void testCargarResenasDesdeArchivo(@TempDir Path tempDir) throws IOException {
        // Crear archivo temporal con datos de reseñas
        String nombreArchivo = "resenas_cargar_test";
        File tempFile = tempDir.resolve(nombreArchivo + ".txt").toFile();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("3,Reseña cargada 1,5,usuario3,Estudiante\n");
            writer.write("4,Reseña cargada 2,2,usuario4,Profesor\n");
        }

        // Cargar reseñas desde el archivo
        procesamientoResena.cargarResenasDesdeArchivo(tempFile.getAbsolutePath());

        // Verificar que las reseñas se han cargado correctamente
        ArrayList<Resena> resenas = procesamientoResena.getResenas();
        assertEquals(2, resenas.size(), "Debe haber dos reseñas cargadas");

        Resena resena1 = resenas.get(0);
        assertEquals(3, resena1.getIdActividad(), "El ID de la actividad debe ser 3");
        assertEquals("Reseña cargada 1", resena1.getOpinion(), "La opinión debe coincidir");
        assertEquals(5, resena1.getRating(), "El rating debe ser 5");
        assertEquals("usuario3", resena1.getLoginAutor(), "El login del autor debe coincidir");
        assertEquals("Estudiante", resena1.getRolAutor(), "El rol del autor debe coincidir");

        Resena resena2 = resenas.get(1);
        assertEquals(4, resena2.getIdActividad(), "El ID de la actividad debe ser 4");
        assertEquals("Reseña cargada 2", resena2.getOpinion(), "La opinión debe coincidir");
        assertEquals(2, resena2.getRating(), "El rating debe ser 2");
        assertEquals("usuario4", resena2.getLoginAutor(), "El login del autor debe coincidir");
        assertEquals("Profesor", resena2.getRolAutor(), "El rol del autor debe coincidir");
    }
}

