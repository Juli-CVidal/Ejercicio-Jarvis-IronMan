/*
// Curso Egg FullStack
 */
package Entidades;

// @author JulianCVidal
public class Dispositivo {

    private final String nombre;
    private final double consumoBasico; //por segundo
    private String estado;

    public Dispositivo(String nombre, float consumo) {
        this.nombre = nombre;
        this.consumoBasico = consumo;
        this.estado = "sano";
    }

    public static Dispositivo crearBota(String nombre) {
        return new Dispositivo(nombre, 280900);
    }

    public static Dispositivo crearGuante(String nombre) {
        return new Dispositivo(nombre, 314000);
    }

    public String getNombre() {
        return nombre;
    }

    public double getConsumoBasico() {
        return consumoBasico;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public boolean estaSano() {
        return estado.equals("sano");
    }

    public void mostrarInfo() {
        System.out.println(this.nombre + ": " + this.estado);
    }
}
