package com.example.demo.servlet.Seller;

import com.example.demo.DbService;
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

@WebServlet("/sales")
public class MySales extends HttpServlet {
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

        String sortBy = request.getParameter("sort");

        int businessPersonID = BUSINESSPERSON_ID;
        List<SaleRecord> sales = new ArrayList<>();
        try {
            // Pobierz wszystkie OrderProduct
            String sql = "SELECT * FROM OrderProduct";
            PreparedStatement stmt = connection.prepareStatement(sql);
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

            if (sortBy!=null && !sortBy.isEmpty()){
                if (sortBy.equals("date")) {
                    sales.sort(Comparator.comparing(SaleRecord::getOrderDate));
                } else if (sortBy.equals("name")) {
                    sales.sort(Comparator.comparing(SaleRecord::getProductName));
                }
            }

            request.setAttribute("sales", sales);
            request.getRequestDispatcher("BusinessPerson/sales.jsp").forward(request, response);


        } catch (ServletException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
