package BEU;

import Garcia.Persona;
import Garcia.Unidades;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class Matricula {
//Datos

    private final String numero;
    private Calendar fecha;
    private Estado estado;
    private Persona estudiante;
    private Curso curso;
    private final List<Calificacion> calificaciones = new ArrayList<>();
//Información    
    private float promedio;

    public Matricula() {
        fecha = Calendar.getInstance();
        estado = Estado.Registrada;
        UUID numeroAleatorio = UUID.randomUUID();
        this.numero = numeroAleatorio.toString();
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Persona getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Persona estudiante) {
        this.estudiante = estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getNumero() {
        return numero;
    }

    public float getPromedio() {
        return promedio;
    }

    public void calcularPromedio() {
        if (this.calificaciones.isEmpty()) {
            return;
        }
        float suma = 0;
        for (Calificacion c : calificaciones) {
            suma += c.valor;//Se puede acceder al atributo porque es una clase interna
        }
        int divisor = this.calificaciones.size();
        promedio = (float) suma / (float) divisor;
        if (divisor == 3) {
            if (promedio > 14) {
                this.estado = Estado.Aprobada;
            } else {
                this.estado = Estado.Reprobada;
            }
        }
    }

    //Este método registra una calificación y retorna un número
    //1 si es la nota de la unidad I
    //2 si es la nota de la unidad II
    //3 si es la nota de la unidad III
    //0 si ya tiene todas las notas
    public int addCalificacion(float v) {
        Calificacion cal = new Calificacion();
        int cuentaNotas = this.calificaciones.size();
        switch (cuentaNotas) {
            case 0:
                cal.setUnidad(Unidades.I);
                break;
            case 1:
                cal.setUnidad(Unidades.II);
                break;
            case 2:
                cal.setUnidad(Unidades.III);
                break;
            default:
                return 0;//en caso de tener todas las notas
        }
        cal.setValor(v);
        cal.setFecha(Calendar.getInstance());
        this.calificaciones.add(cal);
        this.calcularPromedio();
        return this.calificaciones.size();//    Retorna el tamaño de la lista
    }

    public String toString() {
        return estudiante.toString() + " #" + numero;

    }

    public String imprimirDetalle() {
        String str = "\n\t" + this.estudiante;
        for (Calificacion c : this.calificaciones) {
            str += "\t\t" + c.getValor();
        }
        str += "\t\t" + this.promedio + "\n";
        str += "\t\t" + this.estado + "\n";
        return str;
    }

    class Calificacion {

        private Calendar fecha;
        private float valor;
        private Unidades unidad;

        public Calificacion() {
        }

        public Calendar getFecha() {
            return fecha;
        }

        public void setFecha(Calendar fecha) {
            this.fecha = fecha;
        }

        public float getValor() {
            return valor;
        }

        public void setValor(float valor) {
            this.valor = valor;
        }

        public Unidades getUnidad() {
            return unidad;
        }

        public void setUnidad(Unidades unidad) {
            this.unidad = unidad;
        }

    }

}
