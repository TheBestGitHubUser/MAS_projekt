package com.example.demo.servlet.Seller;

import com.example.demo.DbService;
import com.example.demo.model.Product;
import com.example.demo.model.SaleRecord;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.DbService.BUSINESSPERSON_ID;

@WebServlet("/MyProducts")
public class MyProducts extends HttpServlet {
    private Connection connection;

    public void init() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            String url = "jdbc:derby:locale;create=true";
            connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println("błąd init");
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int businessPersonID = BUSINESSPERSON_ID;
        List<Product> products = new ArrayList<>();
        try {
            // Pobierz wszystkie OrderProduct
            String sql = "SELECT * FROM Product where BusinessPersonID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, businessPersonID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                int price = rs.getInt("Price");
                int stock = rs.getInt("Stock");
                String category = rs.getString("Category");
                String imageURL = rs.getString("ImageURL");
                products.add(new Product(id,businessPersonID,name,description,price,stock,category,imageURL));
            }

            request.setAttribute("products", products);
            request.getRequestDispatcher("BusinessPerson/myProducts.jsp").forward(request, response);


        } catch (ServletException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
