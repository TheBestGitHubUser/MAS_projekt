package com.example.demo.servlet;

import com.example.demo.DbService;
import com.example.demo.model.MyPurchase;
import com.example.demo.model.OrderProduct;
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

import static com.example.demo.DbService.CLIENT_ID;

@WebServlet(value = "/MyPurchases")
public class MyPurchases extends HttpServlet {
    private Connection connection;

    public void init() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            String url = "jdbc:derby:locale;create=true";
            connection = DriverManager.getConnection(url);
            //DbService.dropTables(connection);
            //DbService.create(connection);
            //DbService.add(connection);
        } catch (Exception e) {
            System.out.println("błąd init");
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int ID = CLIENT_ID;

        List<MyPurchase> myPurchase = new ArrayList<>();

        try {
            String sql = "SELECT * FROM OrderProduct WHERE ClientID = ?";
            PreparedStatement stmt;


            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, ID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("ID");
                int productID = rs.getInt("ProductID");
                LocalDate orderDate = rs.getDate("OrderDate").toLocalDate();
                String status = rs.getString("Status");

                // Pobieramy produkt
                String productName = "";
                int businessPersonID = 1;
                String imageURL = "";
                PreparedStatement productStmt = connection.prepareStatement("SELECT * FROM Product WHERE ID = ?");
                productStmt.setInt(1, productID);
                ResultSet productRS = productStmt.executeQuery();
                if (productRS.next()) {
                    productName = productRS.getString("Name");
                    businessPersonID = productRS.getInt("BusinessPersonID");
                    imageURL = productRS.getString("ImageURL");
                }
                // Pobieramy BusinessPerson

                int userID = 0;
                PreparedStatement bpStmt = connection.prepareStatement("SELECT * FROM BusinessPerson WHERE ID = ?");
                bpStmt.setInt(1, businessPersonID);
                ResultSet bpRS = bpStmt.executeQuery();
                if (bpRS.next()) {
                    userID = bpRS.getInt("UserID");
                }
                // Pobieramy SiteUser
                String sellerName = "";
                PreparedStatement userStmt = connection.prepareStatement("SELECT * FROM SiteUser WHERE ID = ?");
                userStmt.setInt(1, userID);
                ResultSet userRS = userStmt.executeQuery();
                if (userRS.next()) {
                    sellerName = userRS.getString("Name");
                }

                // Tworzymy obiekt MyPurchases
                MyPurchase purchase = new MyPurchase(orderId, productID,sellerName, productName, orderDate, status, imageURL);
                myPurchase.add(purchase);
            }



            request.setAttribute("myPurchase",myPurchase);
            request.getRequestDispatcher("MyPurchases.jsp").forward(request, response);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
