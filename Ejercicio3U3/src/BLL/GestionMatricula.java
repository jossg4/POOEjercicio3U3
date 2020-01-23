package BLL;

import BEU.Curso;
import BEU.Estudiante;
import BEU.Matricula;
import Garcia.BaseBLLCrud;
import Garcia.BasePersistencia;
import Garcia.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestionMatricula extends BasePersistencia<Matricula> implements BaseBLLCrud<Matricula> {

    private Matricula matricula;
    private final String directorio = "matriculas";

    public GestionMatricula() {
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    //public String imprimir() {
    //    return this.matricula.toString();
    //}
    public String calificar(float valor) {
        String mensaje = "";
        int num = this.matricula.addCalificacion(valor);
        switch (num) {
            case 0:
                mensaje = "Todas las notas están registradas.\n";
                break;
            case 1:
                mensaje = "Calificación de la Unidad I ingresada correctamente.\n";
                break;
            case 2:
                mensaje = "Calificación de la Unidad II ingresada correctamente.\n";
                break;
            case 3:
                mensaje = "Calificación de la Unidad III ingresada correctamente.\n";
                break;
            default:
                mensaje = "Hubo un error al registrar la calificación.\n";
        }
        return mensaje;
    }

    public void promediar() {
        this.matricula.calcularPromedio();
    }

    public String imprimir() {
        StringBuilder sb = new StringBuilder();
        sb.append("Estudiante: ");
        sb.append(matricula.getEstudiante()).append("\n");
        sb.append("Curso: ");
        sb.append(matricula.getCurso().getTitulo()).append("\n");
        sb.append("Promedio: ");
        sb.append(matricula.getPromedio());
        sb.append("\n");
        sb.append(matricula.imprimirDetalle()).append("\n");

        return sb.toString();

    }

    public void archivar() throws IOException {
        this.escribir(directorio, this.matricula.getNumero(), matricula);
    }

    public void configurar(Curso cr, Estudiante est) {
        this.matricula.setCurso(cr);
        this.matricula.setEstudiante(est);
    }

    public List<Matricula> reportar(String titulo) throws IOException {
        List<Matricula> resultado = new ArrayList();
        List<String> contenidos = leerDirectorio(directorio, titulo);
        for (String texto : contenidos) {
            GsonBuilder gb = new GsonBuilder();
            gb.setPrettyPrinting();
            Gson gson = gb.create();
            try {
                Matricula m = gson.fromJson(texto, Matricula.class);
                resultado.add(m);
            } catch (JsonSyntaxException ex) {
                Util.imprimir("El texto no se pudo convertir en matrícula");
                Util.imprimir(ex.toString() + "\n");
            }

        }
        return resultado;
    }

    @Override
    public void crear() {
        matricula = new Matricula();
    }

    @Override
    public void consultar(String id) throws IOException {
        String archivo = id + ".json";
        String contenido = this.leer(directorio, archivo);
        GsonBuilder gb = new GsonBuilder();
        gb.setPrettyPrinting();
        Gson gson = gb.create();
        matricula = gson.fromJson(contenido, Matricula.class);
    }

    @Override
    public void actualizar() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
