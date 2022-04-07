/*
// Curso Egg FullStack
 */
package Servicios;

// @author JulianCVidal

import Entidades.Armadura;
import Entidades.Objetivo;
import Entidades.Radar;
import Servicios.ServiciosDispositivo;
import Servicios.ServiciosArmadura;

public class ServiciosRadar {
    private final ServiciosArmadura ServArm;
    private final ServiciosDispositivo ServDisp;

    public ServiciosRadar() {
        this.ServArm = new ServiciosArmadura();
        this.ServDisp = new ServiciosDispositivo();
    }

    public boolean mostrarObjetivosRadar(Radar radar) {
        Objetivo obj;
        boolean hayObjetivos = false;
        int detect = radar.getDetectados();
        for (int i = 0; i < detect; i++) {
            obj = radar.getObjetivoInPos(i);
            if (obj != null) {
                hayObjetivos = true;
                System.out.println("\nObjetivo Nº " + (i + 1));
                obj.mostrarInfo();
            }

        }
        return hayObjetivos;
    }

    public void simular(Radar radar) {
        int cantidad = (int) (Math.random() * 3) + 1;
        int detectados = radar.getDetectados();
        if (detectados + cantidad >= 10) {
            for (int i = 10 - cantidad; i < 11; i++) { //
                //System.out.println("Array lleno: " + i);
                radar.addObjetivoInPos(i);
            }
        } else {
            for (int i = detectados; i < detectados + cantidad; i++) {
                //System.out.println("Llenando Array: " + i);
                radar.addObjetivoInPos(i);
            }
        }

        radar.setDetectados(cantidad);
        radar.ordenarPorDistancia();
        System.out.println("Se han añadido " + cantidad + " objetivos");
    }

    public double atacar(Armadura Armor, int index) {
        Radar radar = Armor.getRadar();
        double cBasicoGuantes = Armor.getConsumoBasicoGuantes() * 2; //consumo de ambos guantes
        double energiaConsumida = 0;
        Objetivo obj = radar.getObjetivoInPos(index);
        
        if (obj.getDist() > 5000) {
            System.out.println("El objetivo está a más de 5km, no es posible alcanzarle");
            return 0;
        }
        if (obj.isHostil() == false) {
            System.out.println("El objetivo no es hostil, no se le permitirá atacarle");
            return 0;
        }

        double resObj = obj.getResistencia(), dist = obj.getDist();
        double daño = cBasicoGuantes / dist; //daño que pueden hacer ambos guantes
        String guanteAUsar = ServDisp.seleccionarYUsarGuante(Armor.getGuanteIzq(), Armor.getGuanteDer());

        if (guanteAUsar.equals("ninguno")) {
            System.out.println("No se puede atacar, no hay guantes sanos");
            return 0;
        }
        if (!guanteAUsar.equals("ambos")) {
            System.out.println("Se utilizará un sólo guante para atacar");
            cBasicoGuantes /= 2;
            daño /= 2;
        } else {
            System.out.println("Se utilizarán ambos guantes para atacar");
        }

        while (obj.isVivo()) {
            obj.destruirObjetivo(daño);
            ServArm.consumirEnergia(Armor, cBasicoGuantes);
            energiaConsumida += cBasicoGuantes;

            if (Armor.getPorcentajeEnergia() < 10) {
                huir(Armor);
                return energiaConsumida;
            }
        }
        radar.killObjetivoInPos(index);
        radar.ordenarPorDistancia();
        System.out.println("\nSe ha destruído el objetivo");

        return energiaConsumida;
    }

    private void huir(Armadura Armor) {
        System.out.println("Queda 10% de energía restante, activando acciones evasivas..."); //demorará 0,03 hs (2 minutos) en recorrer 10km a 300km/h
        Armor.getRadar().limpiarRadar();
    }
}
