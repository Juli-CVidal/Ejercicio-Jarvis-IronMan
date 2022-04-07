/*
// Curso Egg FullStack
 */
package Servicios;

// @author JulianCVidal

import Entidades.Armadura;

public class ServiciosArmadura {

    private final ServiciosDispositivo ServDisp;

    public ServiciosArmadura() {
        this.ServDisp = new ServiciosDispositivo();
    }

    //el uso normal consume el doble que el básico, y el intensivo el triple
    //cada acción utiliza de forma distinta a los dispositivos, lo que afectará su consumo
    public double calcularConsumo(double cBasicoBotas, double cBasicoGuantes, String usoBotas, String usoGuantes, double segs) {
        double consumoBotas = cBasicoBotas * segs * 2 ; //consumo de ambas botas
        double consumoGuantes = cBasicoGuantes * segs * 2; //consumo de ambos guantes

        switch (usoBotas) {
            case "ninguno":
                consumoBotas = 0;
                break;

            case "normal":
                consumoBotas *= 2;
                break;

            case "intensivo":
                consumoBotas *= 3;
                break;
        }

        switch (usoGuantes) {
            case "ninguno":
                consumoGuantes = 0;
                break;

            case "normal":
                consumoGuantes *= 2;
                break;

            case "intensivo":
                consumoGuantes *= 3;
                break;
        }

        return consumoBotas + consumoGuantes;
    }

    public void mostrarEnergiaRestante(double energia) {
        System.out.printf( "Energia restante: %.4f %s %n", energia , "kWs" );
        System.out.print("Equivalente a " + (energia * 3600) + "kJ");
        System.out.println(" o " + (energia * 1.341) + " caballos de fuerza mecánicos");
    }

    public void mostrarConsumo(Armadura Armor, double energiaConsumida) {
        System.out.printf("Energía consumida: %,4f %s \n", energiaConsumida, "kWs");
    }
    
    public void consumirEnergia(Armadura Armor, double consumo) {
        if (consumo != -1) {
            double energiaRestante =  Armor.getEnergiaActual() - consumo;
            Armor.setEnergiaActual(energiaRestante);
            Armor.setPorcentajeEnergia((energiaRestante*100) / Armor.getEnergiaMaxima());
        }
    }
    
    public void consumirYMostrarConsumo(Armadura Armor, double energiaConsumida) {
        mostrarConsumo(Armor, energiaConsumida);
        consumirEnergia(Armor, energiaConsumida);
    }
}
