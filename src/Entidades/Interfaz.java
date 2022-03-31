/*
// Curso Egg FullStack
 */
package Entidades;

// @author JulianCVidal
import java.util.Scanner;

public class Interfaz {

    private Armadura Armor;
    public ServiciosArmadura ServArm;
    public ServiciosDispositivo ServDisp;
    public ServiciosRadar ServRad;
    public boolean objetivosSimulados = false; //para evitar mostrar objetivos si no se ha creado ninguno
    public Scanner leer = new Scanner(System.in).useDelimiter("\n");

    public void crearArmadura() {
        System.out.println("Bienvenido de vuelta, Tony");
        System.out.println("Antes de comenzar, necesita personalizar su armadura...");
        System.out.print("¿Cómo quiere llamarla? ");
        String nombre = pedirTexto();

        System.out.print("¿Qué color quiere como primario? ");
        String colorPrim = pedirTexto();

        System.out.print("¿Y como secundario? ");
        String colorSec = pedirTexto();

        this.Armor = new Armadura(nombre, colorPrim, colorSec);

        this.ServDisp = new ServiciosDispositivo();
        this.ServArm = new ServiciosArmadura(); //contiene servicios de dispositivo
        this.ServRad = new ServiciosRadar(); //contiene servicios de armadura y dispositivo

        System.out.println("Buena elección!");
        presionarEnter();
    }

    public void menuPrincipal() {
        String[] acciones = {"ir al menú de movilidad", "ir al menú de acciones ofensivas", "ir al menú de acciones de mantenimiento", "terminar aventura"};
        int opc;
        float segs, energiaConsumida;

        do {
            System.out.printf("\nEnergia restante: %.4f %s \n", Armor.getPorcentajeEnergia(), "%");
            mostrarOpciones("Acciones disponibles: ", acciones);
            opc = leer.nextInt() - 1;

            if (opc < 0 || opc > 3) {
                System.out.println("No ha ingresado una opción válida");
                continue;
            }

            switch (opc) {
                case 0:
                    if (ServDisp.botasDestruidas(Armor.getBotaIzq(), Armor.getBotaDer()) == false) {
                        menuAccionesMovilidad();
                    } else {
                        System.out.println("Una bota se encuentra destruída, no es posible desplazarse");
                    }
                    break;
                case 1:
                    if (ServDisp.guantesDestruidos(Armor.getGuanteIzq(), Armor.getGuanteDer()) == false) {
                        menuAccionesOfensivas();
                    } else {
                        System.out.println("Un guante se encuentra destruído, no es posible atacar");
                    }
                    break;

                case 2:
                    menuAccionesMantenimiento();
                    break;
            }
        } while (Armor.getPorcentajeEnergia() > 0 && opc != 3 && !ServDisp.todosDestruidos(Armor)); //Si hay al menos una bota y un guante destruído, no puede realizar acciones

    }

    public void menuAccionesMovilidad() {
        Dispositivo botaIzq = Armor.getBotaIzq(), botaDer = Armor.getBotaDer();
        Dispositivo guanteIzq = Armor.getGuanteIzq(), guanteDer = Armor.getGuanteDer();
        String[] acciones = {"caminar", "correr", "propulsarse", "volar", "volver al menú principal"};
        String usoBotas, usoGuantes;
        int opc;
        double segs, energiaConsumir;
        do {
            System.out.printf("\nEnergia restante: %.4f %s \n", Armor.getPorcentajeEnergia(), "%");
            usoBotas = "ninguno";
            usoGuantes = "ninguno";

            mostrarOpciones("Opciones disponibles: ", acciones);
            opc = leer.nextInt() - 1;
            if (opc < 0 || opc > 3) {
                if (opc != 4) {
                    System.out.println("No ha ingresado una opción válida");
                }
                continue;
            }

            if (ServDisp.botasDisponibles(botaIzq, botaDer) == false || (opc == 3 && ServDisp.guantesDisponibles(guanteIzq, guanteDer) == false)) {
                System.out.println("Un dispositivo está dañado, no se puede realizar la acción");
                continue;
            }

            System.out.print("¿Por cuántos segundos quiere " + acciones[opc] + "? ");
            segs = pedirTiempo();

            switch (opc) {
                case 0:
                    usoBotas = "basico";
                    break;

                case 1:
                    usoBotas = "normal";
                    break;

                case 3: //tanto la opción 3 como la 2 requieren un uso intensivo de botas
                    usoGuantes = "normal";
                case 2:
                    usoBotas = "intensivo";
                    break;
            }

            energiaConsumir = ServArm.calcularConsumo(Armor.getConsumoBasicoBotas(), Armor.getConsumoBasicoGuantes(), usoBotas, usoGuantes, segs);
            if (energiaConsumir > Armor.getEnergiaActual()) {
                System.out.println("No hay energía suficiente para realizar la acción.");
                continue;
            }

            ServArm.consumirYMostrarConsumo(Armor, energiaConsumir);
            if (!usoGuantes.equals("ninguno")) {
                if (ServDisp.usarTodos(Armor)) {
                    System.out.println("Se ha dañado uno o más dispositivos");
                }
            } else {
                if (ServDisp.usarBotas(botaIzq, botaDer)) {
                    System.out.println("Se ha dañado uno o más dispositivos");
                }
            }

        } while (opc != 4 && Armor.getPorcentajeEnergia() > 0);
    }

    public void menuAccionesOfensivas() {
        Radar radar = Armor.getRadar();
        String[] acciones = {"simular", "atacar", "volver al menú principal"};
        int opc, target;
        double energiaConsumida;

        do {
            System.out.printf("\nEnergia restante: %.4f %s \n", Armor.getPorcentajeEnergia(), "%");
            mostrarOpciones("Opciones disponibles: ", acciones);
            opc = leer.nextInt() - 1;

            if (opc < 0 || opc > 1) {
                if (opc != 2) {
                    System.out.println("No ha ingresado una opción válida");
                }
                continue;
            }

            switch (opc) {
                case 0:
                    ServRad.simular(radar);
                    objetivosSimulados = true;
                    break;

                case 1:
                    if (objetivosSimulados == false) {
                        System.out.println("No ha añadido objetivos, simule para añadirlos");
                        continue;
                    }
                    if (ServRad.mostrarObjetivosRadar(radar) == false) {
                        System.out.println("No hay objetivos disponibles para atacar");
                        continue;
                    }

                    System.out.print("\n¿A qué objetivo quiere atacar? ");
                    target = pedirObjetivo();

                    energiaConsumida = ServRad.atacar(Armor, target);
                    if (energiaConsumida != 0) {
                        ServArm.consumirYMostrarConsumo(Armor, energiaConsumida);
                    }

                    break;
            }
        } while (opc != 2 && Armor.getPorcentajeEnergia() >= 10);
    }

    private void menuAccionesMantenimiento() {
        String[] acciones = {"mostrar estado de los dispositivos", "mostrar el estado de la batería", "mostrar info del reactor", "reparar un dispositivo", "revisar dispositivos", "volver al menú principal"};
        int opc;

        do {
            System.out.printf("\nEnergia restante: %.4f %s \n", Armor.getPorcentajeEnergia(), "%");
            mostrarOpciones("Opciones disponibles: ", acciones);
            opc = leer.nextInt() - 1;
            if (opc < 0 || opc > 4) {
                if (opc != 5) {
                    System.out.println("No ha ingresado una opción válida");
                }
                continue;
            }

            switch (opc) {
                case 0:
                    ServDisp.mostrarEstadoDispositivos(Armor);
                    break;

                case 1:
                    System.out.println("A la batería le queda " + Armor.getPorcentajeEnergia() + "% de energía");
                    break;

                case 2:
                    ServArm.mostrarEnergiaRestante(Armor.getEnergiaActual());
                    break;

                case 3:
                    Dispositivo rep = pedirDispositivoAReparar();
                    if (!rep.getEstado().equals("dañado")) {
                        System.out.println("No es necesario/posible reparar el dispositivo seleccionado");
                        continue;
                    }
                    if (ServDisp.seHaReparado(rep)) {
                        System.out.println("Se ha logrado reparar el dispositivo");
                    } else {
                        System.out.println("No ha sido posible reparar el dispositivo");
                    }
                    break;

                case 4:
                    System.out.println("Revisando dispositivos...");
                    ServDisp.revisarDispositivos(Armor);
                    break;
            }

        } while (opc != 5);
    }

    private void mostrarOpciones(String msg, String[] acciones) {
        int len = acciones.length;
        System.out.println(msg);
        for (int i = 0; i < len; i++) {
            System.out.println((i + 1) + ". " + acciones[i]);
        }
        System.out.print("¿Qué quiere hacer? ");
    }

    private Dispositivo pedirDispositivoAReparar() {
        Dispositivo[] disps = {Armor.getBotaIzq(), Armor.getBotaDer(), Armor.getGuanteIzq(), Armor.getGuanteDer()};
        System.out.println("1.Bota Izquierda, 2.Bota Derecha, 3.Guante Izquierdo, 4.Guante Derecho ");
        System.out.print("Qué dispositivo quiere reparar? ");
        int sel = leer.nextInt() - 1;
        while (sel < 0 || sel > 3) {
            System.out.print("Elija una opción válida: ");
            sel = leer.nextInt() - 1;
        }

        return disps[sel];
    }

    private int pedirObjetivo() {
        int detectados = Armor.getRadar().getDetectados();
        int target = leer.nextInt() - 1;

        while (target < 0 || target > detectados) {
            System.out.print("Ingrese una opción válida: ");
            target = leer.nextInt() - 1;
        }
        return target;
    }

    private String pedirTexto() {
        String str = leer.nextLine();
        while (str.length() < 1) {
            System.out.print("Reintente: ");
            str = leer.nextLine();
        }
        return str;
    }

    private float pedirTiempo() {
        float num = leer.nextFloat();

        while (num <= 0) {
            System.out.print("Reintente: ");
            num = leer.nextFloat();
        }
        return num;
    }

    private void presionarEnter() {
        System.out.print("Presione Enter para continuar");
        String pass = leer.nextLine();
    }
}
