package com.library;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class LibraryManagement {

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("     Library Management System (Java 17)");
        System.out.println("=========================================");
        System.out.println("1. Search Book");
        System.out.println("2. Issue Book");
        System.out.println("3. Return Book");
        System.out.println("4. Reserve Book");
        System.out.println("=========================================");

        int port = 8080;
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", new RootHandler());
            server.createContext("/books", new BooksHandler());
            server.setExecutor(null);
            server.start();
            System.out.println("Library Management Web Server started on port: " + port);
            System.out.println("Access in browser at: http://localhost:" + port);
        } catch (IOException e) {
            System.err.println("Could not start HTTP server: " + e.getMessage());
        }
    }

    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "<!DOCTYPE html><html><head><title>Library Management System</title></head>"
                    + "<body style='font-family: Arial, sans-serif; text-align: center; margin-top: 50px; background-color: #f4f6f9;'>"
                    + "<div style='background: white; padding: 30px; border-radius: 10px; display: inline-block; box-shadow: 0 4px 6px rgba(0,0,0,0.1);'>"
                    + "<h1 style='color: #2c3e50;'>Library Management System</h1>"
                    + "<p style='color: #27ae60; font-weight: bold;'>Application running successfully on Java 17 inside Docker container!</p>"
                    + "<hr style='border: 0; border-top: 1px solid #eee; margin: 20px 0;'>"
                    + "<ul style='text-align: left; font-size: 16px; color: #34495e; list-style-type: none; padding: 0;'>"
                    + "<li>1. Search Book</li>"
                    + "<li>2. Issue Book</li>"
                    + "<li>3. Return Book</li>"
                    + "<li>4. Reserve Book</li>"
                    + "</ul>"
                    + "</div></body></html>";
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            byte[] bytes = response.getBytes("UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }

    static class BooksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "[{\"id\":101,\"title\":\"Software Engineering\",\"author\":\"Roger Pressman\",\"available\":true},"
                    + "{\"id\":102,\"title\":\"Clean Code\",\"author\":\"Robert C. Martin\",\"available\":false}]";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            byte[] bytes = response.getBytes("UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }
}