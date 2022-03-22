/*
// Curso Egg FullStack
 */
package Entidades;

// @author JulianCVidal
public class Armadura {

    private final String nombre;
    private final String colorPrinc;
    private final String colorSec;

    private final double energiaMaxima;
    private double energiaActual;
    private double porcentajeEnergia;

    private final Dispositivo botaIzq;
    private final Dispositivo botaDer;
    private final double consumoBasicoBotas;

    private final Dispositivo guanteIzq;
    private final Dispositivo guanteDer;
    private final double consumoBasicoGuantes;

    private final Radar radar;

    public Armadura(String nombre, String colorPrinc, String colorSec) {
        this.nombre = nombre;
        this.colorPrinc = colorPrinc;
        this.colorSec = colorSec;
        
        this.energiaMaxima = 2147483647;
        this.energiaActual = this.energiaMaxima; 
        this.porcentajeEnergia = 100;

        this.botaIzq = Dispositivo.crearBota("bota izquierda");
        this.botaDer = Dispositivo.crearBota("bota derecha");
        this.consumoBasicoBotas = botaIzq.getConsumoBasico();

        this.guanteIzq = Dispositivo.crearGuante("guante izquierdo");
        this.guanteDer = Dispositivo.crearGuante("guante derecho");
        this.consumoBasicoGuantes = guanteIzq.getConsumoBasico();
        this.radar = new Radar();
    }

    public String getNombre() {
        return nombre;
    }

    public String getColorPrinc() {
        return colorPrinc;
    }

    public String getColorSec() {
        return colorSec;
    }

    public double getEnergiaMaxima() {
        return energiaMaxima;
    }

    public void setEnergiaActual(double energiaActual){
        this.energiaActual = energiaActual;
    }
    
    public double getEnergiaActual() {
        return energiaActual;
    }
    
    public void setPorcentajeEnergia(double porcentaje){
        this.porcentajeEnergia = porcentaje;
    }

    public double getPorcentajeEnergia() {
        return porcentajeEnergia;
    }

    public Dispositivo getBotaIzq() {
        return botaIzq;
    }

    public Dispositivo getBotaDer() {
        return botaDer;
    }

    public Dispositivo getGuanteIzq() {
        return guanteIzq;
    }

    public Dispositivo getGuanteDer() {
        return guanteDer;
    }

    public double getConsumoBasicoBotas() {
        return consumoBasicoBotas;
    }

    public double getConsumoBasicoGuantes() {
        return consumoBasicoGuantes;
    }

    public Radar getRadar() {
        return radar;
    }
}
