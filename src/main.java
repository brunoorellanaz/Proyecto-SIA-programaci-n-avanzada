import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Instancia de la clase principal que maneja la lógica
        gestionbuses gestion = new gestionbuses(); 
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n=== GESTIÓN DE PASAJEROS Y BUSES ===");
            System.out.println("1. Reservar viaje");
            System.out.println("2. Cancelar viaje");
            System.out.println("3. Reagendar viaje");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    System.out.println("\n-- RESERVAR VIAJE --");
                    System.out.print("ID del pasajero: ");
                    int idPasajero = scanner.nextInt();
                    scanner.nextLine();
                    
                    System.out.print("Nombre del pasajero: ");
                    String nombre = scanner.nextLine();
                    
                    System.out.print("Origen: ");
                    String origen = scanner.nextLine();
                    
                    System.out.print("Destino: ");
                    String destino = scanner.nextLine();
                    
                    System.out.print("Día y hora (ej. 'Lunes 14:30'): ");
                    String fechaHora = scanner.nextLine();
                    
                    // Llamada al método de la clase GestionBuses
                    gestion.reservarViaje(idPasajero, nombre, origen, destino, fechaHora);
                    break;

                case 2:
                    System.out.println("\n-- CANCELAR VIAJE --");
                    System.out.print("Ingrese el ID del pasajero o reserva a cancelar: ");
                    int idCancelar = scanner.nextInt();
                    
                    gestion.cancelarViaje(idCancelar);
                    break;

                case 3:
                    System.out.println("\n-- REAGENDAR VIAJE --");
                    System.out.print("Ingrese el ID del pasajero o reserva: ");
                    int idReagendar = scanner.nextInt();
                    scanner.nextLine();
                    
                    System.out.print("Ingrese el nuevo día y hora: ");
                    String nuevaFechaHora = scanner.nextLine();
                    
                    gestion.reagendarViaje(idReagendar, nuevaFechaHora);
                    break;

                case 4:
                    continuar = false;
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
        scanner.close();
    }
}
