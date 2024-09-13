import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class Servicio {
    private static final String URL = "https://function-192-adso.azurewebsites.net/api/httptrigger1";
    private static String jsonInputString = "";

    public String realizarLlamadaHttp() {
        try {
            @SuppressWarnings("deprecation")
            URL url = new URL(URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            // Obtener los detalles del producto desde el Controlador
            String email = Controlador.email;
            String productName = Controlador.productName;
            String productDescription = Controlador.productDescription;
            int quantity = Controlador.quantity;
            double totalPrice = Controlador.totalPrice;
            String plantilla = Controlador.plantilla;

            // Construir el cuerpo del JSON mediante concatenación
            String jsonInputString1 = "{"
            + "\"subject\": \"Confirmación de Compra\","
            + "\"to\": \"" + email +"\","
            + "\"dataTemplate\": {"
            + "    \"correo\": \"" + email + "\","
            + "    \"producto\": \"" + productName + "\","
            + "    \"descripcion\": \"" + productDescription + "\","
            + "    \"cantidad\": " + quantity + ","
            + "    \"precio\": \"" + String.format("%.2f", totalPrice) + "\""
            + "},"
            + "\"templateName\": \"" + plantilla + "\""
            + "}";

            String jsonInputString2 = "{"
                    + "\"subject\": \"Confirmación de Compra\","
                    + "\"to\": \"" + email +"\","
                    + "\"dataTemplate\": {"
                    + "    \"name\": \"" + email + "\","
                    + "    \"producto\": \"" + productName + "\","
                    + "    \"descripcion\": \"" + productDescription + "\","
                    + "    \"cantidad\": " + quantity + ","
                    + "    \"precio\": \"" + String.format("%.2f", totalPrice) + "\""
                    + "},"
                    + "\"templateName\": \"" + plantilla + "\""
                    + "}";

                    if(plantilla.equals("productosConfirmacion.html")) {
                        jsonInputString = jsonInputString1;
                    } else if(plantilla.equals("productos.html")) {
                        jsonInputString = jsonInputString2;
                    }

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } else {
                return "La llamada HTTP falló. Código de respuesta: " + responseCode;
            }
        } catch (Exception e) {
            return "Error al realizar la llamada HTTP: " + e.getMessage();
        }
    }
}
