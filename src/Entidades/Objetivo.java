/*
// Curso Egg FullStack
 */
package Entidades;

// @author JulianCVidal
public class Objetivo {

    private final int[] coords;
    private final double dist;
    private final boolean hostil;
    public double resistencia;
    private boolean vivo;

    public Objetivo() {
        this.coords = new int[3];
        for (int i = 0; i < 3; i++) {
            coords[i] = (int) (Math.random() * 25 - 12);
        }

        this.dist = Math.sqrt(Math.pow(this.coords[0], 2) + Math.pow(this.coords[1], 2) + Math.pow(this.coords[2], 2)) * 330;
        this.hostil = ((Math.random()) > 0.45);
        this.resistencia = Math.random() * 60001 + 10000;
        this.vivo = true;
    }

    public int[] getCoords() {
        return coords;
    }

    public double getDist() {
        return dist;
    }

    public boolean isHostil() {
        return hostil;
    }

    public double getResistencia() {
        return resistencia;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void destruirObjetivo(double daño) {
        resistencia -= daño;
        if (resistencia <= 0) {
            vivo = false;
        }
    }

    public void mostrarInfo() {
        System.out.println("Posición: [" + coords[0] + ", " + coords[1] + ", " + coords[1] + "]");
        System.out.printf("Distancia: %.4f", dist, " metros","%n%n");
        System.out.println("\nHostil: " + hostil);
        System.out.printf("Resistencia: %.4f", resistencia, "\n");
        System.out.println("");
    }

}
