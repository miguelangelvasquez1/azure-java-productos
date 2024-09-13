public class Controlador {

    public static String email;
    public static String productName;
    public static String productDescription;
    public static int quantity;
    public static double totalPrice;
    public static String plantilla;

    public static void setProductDetails(String email, String name, String description, int quantity, double price, String plantilla) {
        Controlador.email = email;
        Controlador.productName = name;
        Controlador.productDescription = description;
        Controlador.quantity = quantity;
        Controlador.totalPrice = price;
        Controlador.plantilla = plantilla;
    }

    public void ejecutar() {
        Servicio servicio = new Servicio();
        System.out.println("Realizando llamada http");
        String response = servicio.realizarLlamadaHttp();
        System.out.println(response);
    }

    public void setPlantilla(String plantilla) {
        this.plantilla = plantilla;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}