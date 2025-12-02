package com.example.demo.servlet;

import com.example.demo.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


@WebServlet(value = "/products")
public class Products extends HttpServlet {
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
        String ID = request.getParameter("ID");


        List<Product> products = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Product";
            PreparedStatement stmt;
            if (!(ID==null) ){
                int sellerId = Integer.parseInt(ID);
                sql+=" WHERE BusinessPersonID = ?";
                 stmt = connection.prepareStatement(sql);
                stmt.setInt(1, sellerId);
            }else{
                 stmt = connection.prepareStatement(sql);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("ID"),
                        rs.getInt("BusinessPersonID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getFloat("Price"),
                        rs.getInt("Stock"),
                        rs.getString("Category"),
                        rs.getString("ImageURL")
                );
                products.add(p);
            }


            request.setAttribute("products", products);
            request.getRequestDispatcher("products.jsp").forward(request, response);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

    }
}
