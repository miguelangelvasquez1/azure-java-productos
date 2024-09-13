import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    static Controlador controlador = new Controlador();
    static String email;
    static String plantilla = "";

    // Almacena los usuarios registrados con sus contraseñas
    private static final Map<String, String> userDatabase = new HashMap<>();
    // Almacena la información de los productos
    private static final Map<String, Product> productCatalog = new HashMap<>();

    @SuppressWarnings("static-access")
    public static void main(String[] args) {

        // Inicializa el catálogo de productos
        initializeProducts();

        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.println("=== Registro / Login ===");
            System.out.print("Correo electrónico: ");
            email = scanner.nextLine();
            System.out.print("Contraseña: ");
            String password = scanner.nextLine();

            if (userDatabase.containsKey(email)) {
                // Usuario existente, intentar iniciar sesión
                if (userDatabase.get(email).equals(password)) {
                    loggedIn = true;
                } else {
                    System.out.println("Contraseña incorrecta. Inténtelo de nuevo.");
                }
            } else {
                // Usuario nuevo, registrarse
                userDatabase.put(email, password);
                loggedIn = true;
                plantilla = "productosConfirmacion.html";
                controlador.setPlantilla(plantilla);
                controlador.setEmail(email);
                controlador.ejecutar();
            }
        }

        // Mostrar menú de productos
        while (true) {
            System.out.println("\n=== Menú ===");
            System.out.println("1. Comprar zapatos");
            System.out.println("2. Comprar maleta");
            System.out.println("3. Comprar pocillo");
            System.out.println("4. Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

            if (option == 4) {
                System.out.println("Sesión cerrada.");
                break;
            }

            Product selectedProduct = null;
            String productName = "";

            switch (option) {
                case 1:
                    productName = "Zapatos";
                    break;
                case 2:
                    productName = "Maleta";
                    break;
                case 3:
                    productName = "Pocillo";
                    break;
                default:
                    System.out.println("Opción inválida. Inténtelo de nuevo.");
                    continue;
            }

            selectedProduct = productCatalog.get(productName);
            if (selectedProduct == null) {
                System.out.println("Producto no encontrado.");
                continue;
            }

            System.out.println("\nEl " +selectedProduct.getName()+ " tiene un precio de: " +selectedProduct.getPrice());
            System.out.println(selectedProduct.getDescription());
            System.out.print("\nIngrese la cantidad: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();  // Limpiar el buffer

            double totalPrice = selectedProduct.getPrice() * quantity;
            System.out.printf("Precio total: %.2f\n", totalPrice);

            System.out.println("\n¿Desea comprar el prodcuto? (s/n)");
            String confirmacion = scanner.nextLine();

            if(confirmacion.equals("s")) {

                plantilla = "productos.html";
                controlador.setProductDetails(
                        email,
                        selectedProduct.getName(),
                        selectedProduct.getDescription(),
                        quantity,
                        totalPrice,
                        plantilla
                );
                controlador.ejecutar();
                System.out.println("Email enviado");
            } else {
                System.out.println("Compra cancelada");
            }
        }
        scanner.close();
    }

    private static void initializeProducts() {
        productCatalog.put("Zapatos", new Product("Zapatos", 50.0, "Zapatos cómodos y elegantes para cualquier ocasión."));
        productCatalog.put("Maleta", new Product("Maleta", 80.0, "Maleta resistente y espaciosa para tus viajes."));
        productCatalog.put("Pocillo", new Product("Pocillo", 20.0, "Pocillo de cerámica para disfrutar tu café."));
    }

    private static class Product {
        private final String name;
        private final double price;
        private final String description;

        public Product(String name, double price, String description) {
            this.name = name;
            this.price = price;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public String getDescription() {
            return description;
        }
    }
}
