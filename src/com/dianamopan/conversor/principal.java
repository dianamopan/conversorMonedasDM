package com.dianamopan.conversor;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


public class principal {
    public static void main(String[] args) throws IOException, InterruptedException {
        int opcion = 0;
        int mult = 0;
        double cantidad = 0;
        String base = "EUR";
        String convierteA = "COP";
        System.out.println("Conversor de Monedas");
        String menu= """
                \n*** Seleccione la opción para su conversión ***
                1- Dólar a Peso Argentino.
                2- Peso Argentino a Dolar.
                3- Dólar a Real Brasileño.
                4- Real Brasileño a Dolar.
                5- Dolar a Peso Colombiano.
                6- Peso Colombiano a Dolar.
                7- SALIR
                Por favor, ingrese una opción válida.
                """;
        Scanner teclado = new Scanner(System.in);
        while (opcion!=7) {
            System.out.println(menu);
            opcion = teclado.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Dolar a Peso Argentino");
                    base = "USD";
                    convierteA ="ARS";
                    break;

                case 2:
                    System.out.println("Peso Argentino a Dolarr");
                    base = "ARS";
                    convierteA ="USD";
                    break;

                case 3:
                    System.out.println("Dólar a Real Brasileño");
                    base = "USD";
                    convierteA ="BRL";
                    break;

                case 4:
                    System.out.println("Real Brasileño a Dolar");
                    base = "BRL";
                    convierteA ="USD";
                    break;

                case 5:
                    System.out.println("Dolar a Peso Colombiano");
                    base = "USD";
                    convierteA ="COP";
                    break;

                case 6:
                    System.out.println("Peso Colombiano a Dolar");
                    base = "COP";
                    convierteA ="USD";
                    break;
                case 7:
                    System.out.println("Gracias por utilizar el conversor");
                    break;
            }

            if(opcion==7){
                break;
            }

            System.out.println("Ingrese la cantidad a convertir");
            cantidad = teclado.nextDouble();

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .setPrettyPrinting()
                    .create();

            // Definir URL para hacer la consulta
            String direccion = "https://v6.exchangerate-api.com/v6/7661e4eae5d4abceb914a769/pair/"
                                +base+"/"+convierteA;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();

            JsonObject miMoneda2 = gson.fromJson(json, JsonObject.class);
            Double tasaConv = miMoneda2.get("conversion_rate").getAsDouble();
            System.out.println("---->"+cantidad+ " " +base+ " Equivalen a: "
                    +cantidad * tasaConv+ " "+convierteA+"<----");

        }

    }
}
