import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VentanaPrincipal extends JFrame {

    private final gestionbuses gestion;
    private final String archivo;

    private final JTextArea salida = new JTextArea();
    private final JPanel panelBotones = new JPanel();

    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public VentanaPrincipal(gestionbuses gestion, String archivo) {

        this.gestion = gestion;
        this.archivo = archivo;

        setTitle("Sistema de Información - SIA");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        // =====================================================
        // CONFIGURACIÓN PRINCIPAL DE LA VENTANA
        // =====================================================

        setLayout(new BorderLayout(10, 10));

        panelBotones.setLayout(new GridLayout(0, 2, 8, 8));

        salida.setEditable(false);
        salida.setLineWrap(true);
        salida.setWrapStyleWord(true);

        add(panelBotones, BorderLayout.NORTH);
        add(new JScrollPane(salida), BorderLayout.CENTER);

        // Mostrar menú principal
        mostrarMenuPrincipal();

        // =====================================================
        // EVENTO AL CERRAR LA VENTANA
        // =====================================================

        addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                gestion.guardarEnArchivo(archivo);

                dispose();
                System.exit(0);
            }
        });
    }

    // =========================================================
    // CAMBIAR BOTONES DEL MENÚ
    // =========================================================

    private void cambiarBotones() {

        panelBotones.removeAll();

        panelBotones.setLayout(
                new GridLayout(0, 2, 8, 8)
        );

        panelBotones.revalidate();
        panelBotones.repaint();
    }

    // =========================================================
    // CREAR BOTÓN
    // =========================================================

    private void agregarBoton(
            String texto,
            Runnable accion) {

        JButton boton = new JButton(texto);

        boton.addActionListener(e -> {

            try {

                accion.run();

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        panelBotones.add(boton);
    }

    // =========================================================
    // MENÚ PRINCIPAL
    // =========================================================

    private void mostrarMenuPrincipal() {

        cambiarBotones();

        agregarBoton(
                "Gestionar",
                this::mostrarMenuGestionar
        );

        agregarBoton(
                "Consultar información",
                this::mostrarMenuConsultar
        );

        agregarBoton(
                "Utilidades",
                this::mostrarMenuUtilidades
        );

        agregarBoton(
                "Guardar datos",
                () -> gestion.guardarEnArchivo(archivo)
        );

        agregarBoton(
                "Salir",
                this::salir
        );

        mostrar(
                "========================================\n" +
                "       SISTEMA DE INFORMACIÓN SIA\n" +
                "========================================\n\n" +
                "Seleccione una opción."
        );

        panelBotones.revalidate();
        panelBotones.repaint();
    }

    // =========================================================
    // MENÚ GESTIONAR
    // =========================================================

    private void mostrarMenuGestionar() {

        cambiarBotones();

        agregarBoton(
                "Gestionar reservas",
                this::mostrarMenuReservas
        );

        agregarBoton(
                "Gestionar buses",
                this::mostrarMenuBuses
        );

        agregarBoton(
                "Gestionar viajes",
                this::mostrarMenuViajes
        );

        agregarBoton(
                "Volver",
                this::mostrarMenuPrincipal
        );

        mostrar(
                "========================================\n" +
                "              GESTIONAR\n" +
                "========================================\n\n" +
                "Seleccione qué desea gestionar."
        );

        actualizar();
    }

    // =========================================================
    // GESTIONAR RESERVAS
    // =========================================================

    private void mostrarMenuReservas() {

        cambiarBotones();

        agregarBoton(
                "Reservar viaje",
                this::reservar
        );

        agregarBoton(
                "Cancelar reserva",
                this::cancelar
        );

        agregarBoton(
                "Reagendar reserva",
                this::reagendar
        );

        agregarBoton(
                "Volver",
                this::mostrarMenuGestionar
        );

        mostrar(
                "========================================\n" +
                "         GESTIONAR RESERVAS\n" +
                "========================================\n"
        );

        actualizar();
    }

    // =========================================================
    // GESTIONAR BUSES
    // =========================================================

    private void mostrarMenuBuses() {

        cambiarBotones();

        agregarBoton(
                "Agregar bus",
                this::agregarBus
        );

        agregarBoton(
                "Modificar bus",
                this::modificarBus
        );

        agregarBoton(
                "Eliminar bus",
                this::eliminarBus
        );

        agregarBoton(
                "Volver",
                this::mostrarMenuGestionar
        );

        mostrar(
                "========================================\n" +
                "           GESTIONAR BUSES\n" +
                "========================================\n"
        );

        actualizar();
    }

    // =========================================================
    // GESTIONAR VIAJES
    // =========================================================

    private void mostrarMenuViajes() {

        cambiarBotones();

        agregarBoton(
                "Agregar viaje",
                this::agregarViaje
        );

        agregarBoton(
                "Modificar viaje",
                this::modificarViaje
        );

        agregarBoton(
                "Eliminar viaje",
                this::eliminarViaje
        );

        agregarBoton(
                "Volver",
                this::mostrarMenuGestionar
        );

        mostrar(
                "========================================\n" +
                "          GESTIONAR VIAJES\n" +
                "========================================\n"
        );

        actualizar();
    }

    // =========================================================
    // CONSULTAR INFORMACIÓN
    // =========================================================

    private void mostrarMenuConsultar() {

        cambiarBotones();

        agregarBoton(
                "Mostrar información",
                this::mostrarMenuMostrar
        );

        agregarBoton(
                "Buscar información",
                this::mostrarMenuBuscar
        );

        agregarBoton(
                "Volver",
                this::mostrarMenuPrincipal
        );

        mostrar(
                "========================================\n" +
                "       CONSULTAR INFORMACIÓN\n" +
                "========================================\n\n" +
                "Seleccione el tipo de consulta."
        );

        actualizar();
    }

    // =========================================================
    // MOSTRAR INFORMACIÓN
    // =========================================================

    private void mostrarMenuMostrar() {

        cambiarBotones();

        agregarBoton(
                "Mostrar buses",
                () -> mostrar(
                        gestion.listarBusesTexto()
                )
        );

        agregarBoton(
                "Mostrar pasajeros",
                this::mostrarPasajeros
        );

        agregarBoton(
                "Mostrar viajes",
                () -> mostrar(
                        gestion.listarViajesTexto()
                )
        );

        agregarBoton(
                "Volver",
                this::mostrarMenuConsultar
        );

        mostrar(
                "========================================\n" +
                "          MOSTRAR INFORMACIÓN\n" +
                "========================================\n\n" +
                "Seleccione qué información desea mostrar."
        );

        actualizar();
    }

    // =========================================================
    // BUSCAR INFORMACIÓN
    // =========================================================

    private void mostrarMenuBuscar() {

        cambiarBotones();

        agregarBoton(
                "Buscar pasajero",
                this::buscarPasajero
        );

        agregarBoton(
                "Buscar bus",
                this::buscarBus
        );

        agregarBoton(
                "Buscar viaje",
                this::buscarViaje
        );

        agregarBoton(
                "Volver",
                this::mostrarMenuConsultar
        );

        mostrar(
                "========================================\n" +
                "           BUSCAR INFORMACIÓN\n" +
                "========================================\n\n" +
                "Seleccione qué desea buscar."
        );

        actualizar();
    }

    // =========================================================
    // UTILIDADES
    // =========================================================

    private void mostrarMenuUtilidades() {

        cambiarBotones();

        agregarBoton(
                "Viajes rentables",
                this::mostrarRentables
        );

        agregarBoton(
                "Mostrar estructura completa",
                this::mostrarEstructuraCompleta
        );

        agregarBoton(
                "Volver",
                this::mostrarMenuPrincipal
        );

        mostrar(
                "========================================\n" +
                "              UTILIDADES\n" +
                "========================================\n\n" +
                "Seleccione una utilidad."
        );

        actualizar();
    }

    // =========================================================
    // BÚSQUEDAS
    // =========================================================

    private void buscarBus() {

        mostrar(
                gestion.buscarBusTexto(
                        pedirInt("ID del bus:")
                )
        );
    }

    private void buscarViaje() {

        try {

            mostrar(
                    gestion.buscarViaje(
                            pedirInt("ID del viaje:")
                    ).toString()
            );

        } catch (ElementoNoEncontradoException e) {

            mostrar(e.getMessage());
        }
    }

    private void buscarPasajero() {

        try {

            String tipo = pedir(
                    "Buscar por:\n" +
                    "1 = ID\n" +
                    "2 = Nombre"
            );

            if ("1".equals(tipo)) {

                mostrar(
                        gestion.buscarPasajero(
                                pedirInt("ID:")
                        ).toString()
                );

            } else if ("2".equals(tipo)) {

                mostrar(
                        gestion.buscarPasajero(
                                pedir("Nombre:")
                        ).toString()
                );

            } else {

                mostrar("Opción de búsqueda no válida.");
            }

        } catch (Exception e) {

            mostrar(e.getMessage());
        }
    }

    // =========================================================
    // BUSES
    // =========================================================

    private void agregarBus() {

        gestion.agregarBus(
                pedirInt("ID del bus:"),
                pedirInt("Capacidad:")
        );

        mostrar(
                gestion.listarBusesTexto()
        );
    }

    private void modificarBus() {

        try {

            gestion.modificarBus(
                    pedirInt("ID del bus:"),
                    pedirInt("Nueva capacidad:")
            );

            mostrar(
                    gestion.listarBusesTexto()
            );

        } catch (Exception e) {

            mostrar(e.getMessage());
        }
    }

    private void eliminarBus() {

        try {

            gestion.eliminarBus(
                    pedirInt("ID del bus:")
            );

            mostrar(
                    gestion.listarBusesTexto()
            );

        } catch (Exception e) {

            mostrar(e.getMessage());
        }
    }

    // =========================================================
    // VIAJES
    // =========================================================

    private void agregarViaje() {

        String origen = pedir("Origen:");
        String destino = pedir("Destino:");

        double costoViaje =
                pedirDouble("Costo del viaje:");

        double costoPasaje =
                pedirDouble("Costo del pasaje:");

        LocalDateTime fecha =
                pedirFecha(
                        "Fecha/hora (dd/MM/yyyy HH:mm):"
                );

        int cantidadBuses =
                pedirInt("Cantidad de buses:");

        gestion.agregarViaje(
                origen,
                destino,
                costoViaje,
                costoPasaje,
                fecha,
                cantidadBuses
        );

        mostrar(
                gestion.listarViajesTexto()
        );
    }

    private void modificarViaje() {

        int id =
                pedirInt("ID del viaje:");

        String origen =
                pedir("Nuevo origen:");

        String destino =
                pedir("Nuevo destino:");

        double costoViaje =
                pedirDouble("Nuevo costo del viaje:");

        double costoPasaje =
                pedirDouble("Nuevo costo del pasaje:");

        LocalDateTime fecha =
                pedirFecha(
                        "Nueva fecha/hora (dd/MM/yyyy HH:mm):"
                );

        try {

            gestion.modificarViaje(
                    id,
                    origen,
                    destino,
                    costoViaje,
                    costoPasaje,
                    fecha
            );

            mostrar(
                    gestion.listarViajesTexto()
            );

        } catch (Exception e) {

            mostrar(e.getMessage());
        }
    }

    private void eliminarViaje() {

        try {

            gestion.eliminarViaje(
                    pedirInt("ID del viaje:")
            );

            mostrar(
                    gestion.listarViajesTexto()
            );

        } catch (Exception e) {

            mostrar(e.getMessage());
        }
    }

    // =========================================================
    // RESERVAS
    // =========================================================

    private void reservar() {

        gestion.reservarViaje(
                pedirInt("ID del pasajero:"),
                pedir("Nombre:"),
                pedirInt("Edad:"),
                pedir("Origen:"),
                pedir("Destino:"),
                pedir(
                        "Fecha/hora (dd/MM/yyyy HH:mm):"
                )
        );

        mostrar(
                gestion.listarViajesTexto()
        );
    }

    private void cancelar() {

        gestion.cancelarViaje(
                pedirInt(
                        "ID del pasajero/reserva:"
                )
        );

        mostrar(
                gestion.listarViajesTexto()
        );
    }

    private void reagendar() {

        gestion.reagendarViaje(
                pedirInt(
                        "ID del pasajero/reserva:"
                ),
                pedir(
                        "Nueva fecha/hora (dd/MM/yyyy HH:mm):"
                )
        );

        mostrar(
                gestion.listarViajesTexto()
        );
    }

    // =========================================================
    // MOSTRAR PASAJEROS
    // =========================================================

    private void mostrarPasajeros() {

        StringBuilder sb =
                new StringBuilder(
                        "=== PASAJEROS ===\n"
                );

        for (Pasajeros pasajero :
                gestion.getListaPasajeros()) {

            sb.append(pasajero)
              .append("\n");
        }

        if (gestion.getListaPasajeros().isEmpty()) {

            sb.append(
                    "No existen pasajeros registrados."
            );
        }

        mostrar(sb.toString());
    }

    // =========================================================
    // VIAJES RENTABLES
    // =========================================================

    private void mostrarRentables() {

        StringBuilder sb =
                new StringBuilder(
                        "=== VIAJES RENTABLES ===\n"
                );

        for (Viajes viaje :
                gestion.obtenerViajesRentables()) {

            sb.append(viaje)
              .append("\n");
        }

        if (gestion.obtenerViajesRentables().isEmpty()) {

            sb.append("No hay resultados.");
        }

        mostrar(sb.toString());
    }

    // =========================================================
    // ESTRUCTURA COMPLETA
    // =========================================================

    private void mostrarEstructuraCompleta() {

        StringBuilder sb =
                new StringBuilder();

        sb.append(
                "========================================\n"
        );

        sb.append(
                "        ESTRUCTURA COMPLETA SIA\n"
        );

        sb.append(
                "========================================\n\n"
        );

        sb.append(
                gestion.listarBusesTexto()
        );

        sb.append("\n");

        sb.append(
                gestion.listarViajesTexto()
        );

        sb.append("\n");

        sb.append(
                "=== PASAJEROS ===\n"
        );

        for (Pasajeros pasajero :
                gestion.getListaPasajeros()) {

            sb.append(pasajero)
              .append("\n");
        }

        if (gestion.getListaPasajeros().isEmpty()) {

            sb.append(
                    "No existen pasajeros registrados.\n"
            );
        }

        mostrar(sb.toString());
    }

    // =========================================================
    // FUNCIONES AUXILIARES
    // =========================================================

    private String pedir(String mensaje) {

        return JOptionPane.showInputDialog(
                this,
                mensaje
        );
    }

    private int pedirInt(String mensaje) {

        return Integer.parseInt(
                pedir(mensaje)
        );
    }

    private double pedirDouble(String mensaje) {

        return Double.parseDouble(
                pedir(mensaje)
        );
    }

    private LocalDateTime pedirFecha(String mensaje) {

        return LocalDateTime.parse(
                pedir(mensaje),
                FORMATO
        );
    }

    private void mostrar(String texto) {

        salida.setText(texto);
        salida.setCaretPosition(0);
    }

    private void actualizar() {

        panelBotones.revalidate();
        panelBotones.repaint();
    }

    // =========================================================
    // SALIR
    // =========================================================

    private void salir() {

        int respuesta =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Desea guardar los datos antes de salir?",
                        "Salir",
                        JOptionPane.YES_NO_CANCEL_OPTION
                );

        if (respuesta ==
                JOptionPane.CANCEL_OPTION) {

            return;
        }

        if (respuesta ==
                JOptionPane.YES_OPTION) {

            gestion.guardarEnArchivo(archivo);
        }

        dispose();
        System.exit(0);
    }
}