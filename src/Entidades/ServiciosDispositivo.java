/*
// Curso Egg FullStack
 */
package Entidades;

// @author JulianCVidal
public class ServiciosDispositivo {

    //Métodos para ver el estado de los dispositivos
    public boolean estaSano(Dispositivo Disp) {
        return (Disp.getEstado().equals("sano"));
    }

    public boolean botasDisponibles(Dispositivo botaIzq, Dispositivo botaDer) {
        return (estaSano(botaIzq) && estaSano(botaDer));
    }

    public boolean guantesDisponibles(Dispositivo guanteIzq, Dispositivo guanteDer) {
        return (estaSano(guanteIzq) && estaSano(guanteDer));
    }

    public boolean todosDisponibles(Armadura Armor) {
        return (botasDisponibles(Armor.getBotaIzq(), Armor.getBotaDer()) && guantesDisponibles(Armor.getGuanteIzq(), Armor.getGuanteDer()));
    }

    public boolean botasDestruidas(Dispositivo botaIzq, Dispositivo botaDer) {
        return (botaIzq.getEstado().equals("destruido") || botaDer.getEstado().equals("destruido"));
    }

    public boolean guantesDestruidos(Dispositivo guanteIzq, Dispositivo guanteDer) {
        return (guanteIzq.getEstado().equals("destruido") || guanteDer.getEstado().equals("destruido"));
    }

    public boolean todosDestruidos(Armadura Armor) {
        return (botasDestruidas(Armor.getBotaIzq(), Armor.getBotaDer()) && guantesDestruidos(Armor.getGuanteIzq(), Armor.getGuanteDer()));
    }

    public void mostrarEstadoDispositivos(Armadura Armor) {
        Dispositivo[] disps = {Armor.getBotaIzq(), Armor.getBotaDer(), Armor.getGuanteIzq(), Armor.getGuanteDer()};

        for (Dispositivo disp : disps) {
            disp.mostrarInfo();
        }
    }

    //Métodos para utilizar los dispositivos (quienes llamarán a los métodos para modificar su estado)
    public boolean usarBotas(Dispositivo botaIzq, Dispositivo botaDer) {
        return resultaDañado(botaIzq) || resultaDañado(botaDer);
    }

    public boolean usarGuantes(Dispositivo guanteIzq, Dispositivo guanteDer) {
        return resultaDañado(guanteIzq) || resultaDañado(guanteDer);
    }

    public boolean usarTodos(Armadura Armor) {
        return (usarBotas(Armor.getBotaIzq(), Armor.getBotaDer()) || usarGuantes(Armor.getGuanteIzq(), Armor.getGuanteDer()));
    }

    public String seleccionarYUsarGuante(Dispositivo guanteIzq, Dispositivo guanteDer) {
        if (guantesDisponibles(guanteIzq, guanteDer)) {
            usarGuantes(guanteIzq, guanteDer);
            return "ambos";
        }
        if (guanteIzq.estaSano()) {
            resultaDañado(guanteIzq);
            return "izq";
        }
        if (guanteDer.estaSano()) {
            resultaDañado(guanteDer);
            return "der";
        }
        return "ninguno";
    }

    public void revisarDispositivos(Armadura Armor) {
        Dispositivo[] disps = {Armor.getBotaIzq(), Armor.getBotaDer(), Armor.getGuanteIzq(), Armor.getGuanteDer()};
        for (int i = 0; i < 4; i++) {
            if (disps[i].getEstado().equals("dañado")) {
                System.out.println("Forzando la reparación de " + disps[i].getNombre());
                if (forzarReparacion(disps[i]).equals("sano")) {
                    System.out.println("Se ha reparado el dispositivo");
                } else {
                    System.out.println("Se ha destruído el dispositivo al intentar repararlo");
                }
            }
        }
    }

    //Métodos para modificar el estado de los dispositivos
    public boolean resultaDañado(Dispositivo Disp) {
        String estado = Disp.getEstado();
        if (estado.equals("sano")) {
            if (Math.random() <= 0.3) {
                Disp.setEstado("dañado");
                return true;
            }
        }
        return false;
    }

    public boolean resultaDestruido(Dispositivo Disp) {
        String estado = Disp.getEstado();
        if (estado.equals("dañado")) {
            if (Math.random() <= 0.4) {
                Disp.setEstado("destruido");
                return true;
            }
        }
        return false;
    }

    public boolean seHaReparado(Dispositivo Disp) {
        String estado = Disp.getEstado();
        if (estado.equals("dañado")) {
            if (Math.random() <= 0.3) {
                Disp.setEstado("sano");
                return true;
            }
        }
        return false;
    }

    public String forzarReparacion(Dispositivo Disp) {
        String estado = Disp.getEstado();
        if (!estado.equals("dañado")) {
            return estado;
        }

        while (true) {
            if (seHaReparado(Disp)) {
                break;
            }
            if (resultaDestruido(Disp)) {
                break;
            }

        }
        estado = Disp.getEstado();
        return estado;
    }
}
