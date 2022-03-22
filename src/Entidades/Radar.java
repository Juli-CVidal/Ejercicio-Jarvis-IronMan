/*
// Curso Egg FullStack
 */
package Entidades;

// @author JulianCVidal
public class Radar {

    private Objetivo[] objs;
    private int detectados;

    public Radar() {
        objs = new Objetivo[10];
        limpiarRadar();
    }

    public Objetivo getObjetivoInPos(int index) {
        if (index <= 10) {
            return objs[index];
        }
        return null;
    }

    public void addObjetivoInPos(int index) {
        if (index < 10) {
            objs[index] = new Objetivo();
        }
    }
    
    public void limpiarRadar(){
        for (int i = 0; i < 10; i++) {
            objs[i] = null;
        }
    }

    public void killObjetivoInPos(int index) {
        if (index < 10) {
            objs[index] = null;
        }
    }

    public void setDetectados(int nuevos) {
        if (detectados + nuevos < 10) {
            detectados += nuevos;
        }
        //System.out.println("Detectados: " + detectados);
    }

    public int getDetectados() {
        return detectados;
    }

    public void ordenarPorDistancia() { //bubble sort?
        Objetivo aux;
        int len = detectados;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (objs[j] == null) {
                    objs[j] = objs[j + 1];
                    objs[j + 1] = null;
                    continue;
                }
                if (objs[j + 1] != null && objs[j].getDist() > objs[j + 1].getDist()) {
                    aux = objs[j];
                    objs[j] = objs[j + 1];
                    objs[j + 1] = aux;
                }

            }

        }

    }
}
