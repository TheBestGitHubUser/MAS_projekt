package com.example.demo.servlet.Seller;

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

import static com.example.demo.DbService.BUSINESSPERSON_ID;

@WebServlet("/productAdd")
public class ProductAdd extends HttpServlet {
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productID = req.getParameter("ID");
        List<Product> products = new ArrayList<>();
        try {
            // Pobierz wszystkie OrderProduct
            String sql = "SELECT * FROM Product where ID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            if (productID!=null && !productID.isEmpty()){
                stmt.setInt(1, Integer.parseInt(productID));
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("ID");
                    int businessPersonID= rs.getInt("BusinessPersonID");
                    String name = rs.getString("Name");
                    String description = rs.getString("Description");
                    int price = rs.getInt("Price");
                    int stock = rs.getInt("Stock");
                    String category = rs.getString("Category");
                    String imageURL = rs.getString("ImageURL");
                    products.add(new Product(id,businessPersonID,name,description,price,stock,category,imageURL));
                }
            }


            req.setAttribute("product", (products.isEmpty()?null:products.get(0)));
            req.getRequestDispatcher("BusinessPerson/product.jsp").forward(req, resp);


        } catch (ServletException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = (req.getParameter("ID") != null && !req.getParameter("ID").isEmpty())
                    ? Integer.parseInt(req.getParameter("ID")) : 0;
            int businessPersonId = BUSINESSPERSON_ID;
            String name = req.getParameter("Name");
            String description = req.getParameter("Description");
            float price = Float.parseFloat(req.getParameter("Price"));
            int stock = Integer.parseInt(req.getParameter("Stock"));
            String category = req.getParameter("Category");
            String imageURL = req.getParameter("ImageURL");

            PreparedStatement stmt;
            if (id == 0) {
                // INSERT
                String sql = "INSERT INTO Product (BusinessPersonID, Name, Description, Price, Stock, Category, ImageURL) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
                stmt = connection.prepareStatement(sql);
                stmt.setInt(1, businessPersonId);
                stmt.setString(2, name);
                stmt.setString(3, description);
                stmt.setFloat(4, price);
                stmt.setInt(5, stock);
                stmt.setString(6, category);
                stmt.setString(7, imageURL);
            } else {
                // UPDATE
                String sql = "UPDATE Product SET Name=?, Description=?, Price=?, Stock=?, Category=?, ImageURL=? " +
                        "WHERE ID=? AND BusinessPersonID=?";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, description);
                stmt.setFloat(3, price);
                stmt.setInt(4, stock);
                stmt.setString(5, category);
                stmt.setString(6, imageURL);
                stmt.setInt(7, id);
                stmt.setInt(8, businessPersonId);
            }

            stmt.executeUpdate();
            resp.sendRedirect(req.getContextPath() + "/MyProducts");

        } catch (SQLException e) {
            throw new RuntimeException("Błąd SQL: " + e.getMessage(), e);
        }
    }
}
