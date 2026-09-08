import java.util.Scanner;

public class main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        gestionbuses gestion = new gestionbuses();

        boolean continuar = true;

        while (continuar) {

            System.out.println(
                "\n=== GESTIÓN DE PASAJEROS Y BUSES ==="
            );

            System.out.println("1. Reservar viaje");
            System.out.println("2. Cancelar viaje");
            System.out.println("3. Reagendar viaje");
            System.out.println("4. Mostrar buses");
            System.out.println("5. Mostrar viajes");
            System.out.println("6. Salir");

            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {

                // ==========================================
                // RESERVAR
                // ==========================================

                case 1:

                    System.out.println(
                        "\n-- RESERVAR VIAJE --"
                    );

                    System.out.print(
                        "ID del pasajero: "
                    );

                    int idPasajero =
                            scanner.nextInt();

                    scanner.nextLine();

                    System.out.print(
                        "Nombre del pasajero: "
                    );

                    String nombre =
                            scanner.nextLine();

                    System.out.print(
                        "Edad del pasajero: "
                    );

                    int edad =
                            scanner.nextInt();

                    scanner.nextLine();

                    System.out.print(
                        "Origen: "
                    );

                    String origen =
                            scanner.nextLine();

                    System.out.print(
                        "Destino: "
                    );

                    String destino =
                            scanner.nextLine();

                    System.out.print(
                        "Día y hora "
                        + "(ej. Lunes 14:30): "
                    );

                    String fechaHora =
                            scanner.nextLine();

                    gestion.reservarViaje(
                        idPasajero,
                        nombre,
                        edad,
                        origen,
                        destino,
                        fechaHora
                    );

                    break;

                // ==========================================
                // CANCELAR
                // ==========================================

                case 2:

                    System.out.println(
                        "\n-- CANCELAR VIAJE --"
                    );

                    System.out.print(
                        "Ingrese el ID del pasajero: "
                    );

                    int idCancelar =
                            scanner.nextInt();

                    scanner.nextLine();

                    gestion.cancelarViaje(
                            idCancelar
                    );

                    break;

                // ==========================================
                // REAGENDAR
                // ==========================================

                case 3:

                    System.out.println(
                        "\n-- REAGENDAR VIAJE --"
                    );

                    System.out.print(
                        "Ingrese el ID del pasajero: "
                    );

                    int idReagendar =
                            scanner.nextInt();

                    scanner.nextLine();

                    System.out.print(
                        "Ingrese el nuevo día y hora: "
                    );

                    String nuevaFechaHora =
                            scanner.nextLine();

                    gestion.reagendarViaje(
                            idReagendar,
                            nuevaFechaHora
                    );

                    break;

                // ==========================================
                // MOSTRAR BUSES
                // ==========================================

                case 4:

                    gestion.mostrarBuses();

                    break;

                // ==========================================
                // MOSTRAR VIAJES
                // ==========================================

                case 5:

                    gestion.mostrarViajes();

                    break;

                // ==========================================
                // SALIR
                // ==========================================

                case 6:

                    continuar = false;

                    System.out.println(
                        "Saliendo del sistema..."
                    );

                    break;

                default:

                    System.out.println(
                        "Opción no válida."
                    );
            }
        }
        scanner.close();
    }
}