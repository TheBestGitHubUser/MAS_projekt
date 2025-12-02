package com.example.demo.servlet.Seller;

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
import java.util.Comparator;
import java.util.List;

import static com.example.demo.DbService.BUSINESSPERSON_ID;

@WebServlet("/editOrder")
public class Sale extends HttpServlet {

    Connection connection;
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
        int idOrder = Integer.parseInt(req.getParameter("ID"));
        int businessPersonID = BUSINESSPERSON_ID;
        List<SaleRecord> sales = new ArrayList<>();
        try {
            // Pobierz wszystkie OrderProduct
            String sql = "SELECT * FROM OrderProduct where ID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1,idOrder);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int orderID = rs.getInt("ID");
                int productId = rs.getInt("ProductID");
                int clientId = rs.getInt("ClientID");
                LocalDate orderDate = rs.getDate("OrderDate").toLocalDate();
                String status = rs.getString("Status");

                // Sprawdź czy produkt należy do tego sprzedawcy
                String sqlProduct = "SELECT * FROM Product WHERE ID = ?";
                PreparedStatement stmtProduct = connection.prepareStatement(sqlProduct);
                stmtProduct.setInt(1, productId);
                ResultSet rsProduct = stmtProduct.executeQuery();

                if (rsProduct.next()) {
                    int productOwnerId = rsProduct.getInt("BusinessPersonID");
                    if (productOwnerId != businessPersonID) {
                        continue; // nie należy do tego sprzedawcy
                    }

                    String productName = rsProduct.getString("Name");
                    float price = rsProduct.getFloat("Price");

                    // Pobierz dane klienta
                    String sqlUser = "SELECT * FROM SiteUser WHERE ID = ?";
                    PreparedStatement stmtUser = connection.prepareStatement(sqlUser);
                    stmtUser.setInt(1, clientId);
                    ResultSet rsUser = stmtUser.executeQuery();

                    if (rsUser.next()) {
                        String clientName = rsUser.getString("Name");
                        String clientEmail = rsUser.getString("Email");

                        SaleRecord sale = new SaleRecord(orderID,clientName, clientEmail, productName, price, orderDate, status);
                        sales.add(sale);
                    }

                    rsUser.close();
                    stmtUser.close();
                }

                rsProduct.close();
                stmtProduct.close();
            }



            req.setAttribute("sale", sales.get(0));
            req.getRequestDispatcher("BusinessPerson/editOrder.jsp").forward(req, resp);


        } catch (ServletException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        String orderDate = req.getParameter("orderDate");
        String status = req.getParameter("status");

        String sql = "UPDATE OrderProduct SET OrderDate = ?, Status = ? WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(orderDate));
            stmt.setString(2, status);
            stmt.setInt(3, orderId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath()+"/sales");
    }
}
