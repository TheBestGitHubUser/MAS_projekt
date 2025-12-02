package com.example.demo.servlet;


import com.example.demo.DbService;
import com.example.demo.model.Product;
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

@WebServlet(value = "/details")
public class Details extends HttpServlet {
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
                int productID = Integer.parseInt(ID);
                sql+=" WHERE ID = ?";
                stmt = connection.prepareStatement(sql);
                stmt.setInt(1, productID);
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

            request.setAttribute("product", products.get(0));
            request.getRequestDispatcher("details.jsp").forward(request, response);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productID = req.getParameter("productId");
        int ID = Integer.parseInt(productID);

        try {
            String updateStockSql = "UPDATE Product SET Stock = Stock - 1 WHERE ID = ?";
            String sql = "INSERT INTO OrderProduct ( ClientID, ProductID, OrderDate, Status) VALUES ( ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, CLIENT_ID);
            stmt.setInt(2, ID);
            stmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            stmt.setString(4, "Pending");

            stmt.executeUpdate();

            stmt = connection.prepareStatement(updateStockSql);
            stmt.setInt(1,ID);

            stmt.executeUpdate();

            resp.sendRedirect("ThankYou.jsp");
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
}
