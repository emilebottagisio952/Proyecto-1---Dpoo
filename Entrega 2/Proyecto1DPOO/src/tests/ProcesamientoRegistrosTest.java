package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.*;

import procesamiento.ProcesamientoRegistros;
import procesamiento.ProcesamientoActividad;
import procesamiento.ProcesamientoLearningPath;

import elementos.*;

public class ProcesamientoRegistrosTest {

    private ProcesamientoRegistros procesamientoRegistros;
    private ProcesamientoActividad procesamientoActividad;
    private ProcesamientoLearningPath procesamientoLearningPath;

    @BeforeEach
    public void setUp() {
        procesamientoRegistros = new ProcesamientoRegistros();
        procesamientoActividad = new ProcesamientoActividad();
        procesamientoLearningPath = new ProcesamientoLearningPath();

     // Crear actividades de ejemplo
        procesamientoActividad.CrearActividadTarea("profesor1", "Tarea", "Tarea 1", "Objetivo1", "Fácil", 30,
                new ArrayList<>(), "2024-12-31", new ArrayList<>(), 0);
        procesamientoActividad.CrearActividadEncuesta("profesor2", "Encuesta", "Encuesta 1", "Objetivo2", "Medio", 45, new ArrayList<>(), "2024-11-30", new ArrayList<>(), Arrays.asList("Pregunta 1", "Pregunta 2"));

        // Crear un learning path
        HashMap<Integer, Boolean> idActividades = new HashMap<>();
        idActividades.put(1, true); // Actividad 1 es obligatoria
        idActividades.put(2, false); // Actividad 2 es opcional
        procesamientoLearningPath.CrearLearningPath("LP 1", "Descripción LP 1", "Medio", 75, idActividades, procesamientoActividad, "profesor1");
    }

    @Test
    public void testInscribirEstudiante() {
        String loginEstudiante = "estudiante1";
        int idLP = 1;

        procesamientoRegistros.InscribirEstudiante(idLP, loginEstudiante, procesamientoActividad, procesamientoLearningPath);

        // Verificar que el estudiante ha sido inscrito en el learning path
        HashMap<Integer, ArrayList<RegistroLearningPath>> registrosLP = procesamientoRegistros.getRegistrosLP();
        assertTrue(registrosLP.containsKey(idLP), "El learning path debe existir en registrosLP");

        ArrayList<RegistroLearningPath> registros = registrosLP.get(idLP);
        assertNotNull(registros, "La lista de registros no debe ser nula");
        assertEquals(1, registros.size(), "Debe haber un registro de learning path");

        RegistroLearningPath registro = registros.get(0);
        assertEquals(loginEstudiante, registro.getLoginEstudiante(), "El login del estudiante debe coincidir");
        assertNotNull(registro.getRegistrosA(), "La lista de registros de actividades no debe ser nula");
        assertEquals(2, registro.getRegistrosA().size(), "Debe haber dos registros de actividades");
    }

    @Test
    public void testTiempoDedicadoPorActividad() {
        String loginEstudiante = "estudiante2";
        int idLP = 1;

        // Inscribir estudiante
        procesamientoRegistros.InscribirEstudiante(idLP, loginEstudiante, procesamientoActividad, procesamientoLearningPath);

        // Simular que el estudiante ha completado actividades
        HashMap<Integer, ArrayList<RegistroLearningPath>> registrosLP = procesamientoRegistros.getRegistrosLP();
        RegistroLearningPath registroLP = registrosLP.get(idLP).get(0);

        // Obtener registros de actividades
        List<RegistroActividad> registrosA = registroLP.getRegistrosA();

        // Simular que el estudiante completó la primera actividad en 30 minutos
        RegistroActividad actividad1 = registrosA.get(0);
        actividad1.setEstado("Completada");
        actividad1.setFechaInicio(LocalDateTime.now().minusMinutes(30));
        actividad1.setFechaTerminado(LocalDateTime.now());

        // Simular que el estudiante completó la segunda actividad en 45 minutos
        RegistroActividad actividad2 = registrosA.get(1);
        actividad2.setEstado("Completada");
        actividad2.setFechaInicio(LocalDateTime.now().minusMinutes(75));
        actividad2.setFechaTerminado(LocalDateTime.now().minusMinutes(30));

        // Calcular el tiempo dedicado por actividad
        float tiempoPromedio = procesamientoRegistros.TiempoDedicadoPorActividad(idLP);

        // Esperamos un promedio de (30 + 45) / 2 = 37.5 minutos
        assertEquals(37.5, tiempoPromedio, 0.1, "El tiempo promedio debe ser 37.5 minutos");
    }

    @Test
    public void testPorcentajeCompletado() {
        int idLP = 1;

        // Inscribir dos estudiantes
        procesamientoRegistros.InscribirEstudiante(idLP, "estudiante3", procesamientoActividad, procesamientoLearningPath);
        procesamientoRegistros.InscribirEstudiante(idLP, "estudiante4", procesamientoActividad, procesamientoLearningPath);

        // Simular que el primer estudiante completó el learning path
        RegistroLearningPath registroLP1 = procesamientoRegistros.getRegistrosLP().get(idLP).get(0);
        registroLP1.setEstado("Completada");

        // Simular que el segundo estudiante no lo ha completado
        RegistroLearningPath registroLP2 = procesamientoRegistros.getRegistrosLP().get(idLP).get(1);
        registroLP2.setEstado("En progreso");

        // Calcular el porcentaje completado
        float porcentaje = procesamientoRegistros.PorcentajeCompletado(idLP);

        // Esperamos un 50%
        assertEquals(50.0, porcentaje, 0.1, "El porcentaje completado debe ser 50%");
    }

    @Test
    public void testRevisarEstadoRLP() {
        RegistroLearningPath rlpCompletado = new RegistroLearningPath("estudiante5", LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>());
        rlpCompletado.setEstado("Completada");

        RegistroLearningPath rlpEnProgreso = new RegistroLearningPath("estudiante6", LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>());
        rlpEnProgreso.setEstado("En progreso");

        // Verificar que el método devuelve true para learning paths completados
        assertTrue(procesamientoRegistros.RevisarEstadoRLP(rlpCompletado), "Debe retornar true para estado 'Completada'");

        // Verificar que el método devuelve false para learning paths no completados
        assertFalse(procesamientoRegistros.RevisarEstadoRLP(rlpEnProgreso), "Debe retornar false para estado diferente de 'Completada'");
    }

    @Test
    public void testCrearRegistros() {
        int idLP = 1;

        // Crear registros de actividades para el learning path
        List<RegistroActividad> registrosA = procesamientoRegistros.CrearRegistros(idLP, procesamientoActividad, procesamientoLearningPath);

        // Verificar que se han creado los registros
        assertNotNull(registrosA, "La lista de registros de actividades no debe ser nula");
        assertEquals(2, registrosA.size(), "Debe haber dos registros de actividades");

        // Verificar los detalles de los registros
        RegistroActividad actividad1 = registrosA.get(0);
        assertEquals(1, actividad1.getIdActividad(), "El ID de la primera actividad debe ser 1");
        assertEquals("No enviado", actividad1.getEstado(), "El estado inicial debe ser 'No enviado'");

        RegistroActividad actividad2 = registrosA.get(1);
        assertEquals(2, actividad2.getIdActividad(), "El ID de la segunda actividad debe ser 2");
        assertEquals("No enviado", actividad2.getEstado(), "El estado inicial debe ser 'No enviado'");
    }
}

