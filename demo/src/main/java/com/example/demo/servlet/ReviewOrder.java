package com.example.demo.servlet;

import com.example.demo.model.Review;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/reviewOrder")
public class ReviewOrder extends HttpServlet {

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
        int id = Integer.parseInt(request.getParameter("ID"));


        List<Review> reviewOrder = new ArrayList<>();

        try {
            String sql = "SELECT * FROM REVIEW WHERE OrderID = ?";
            PreparedStatement stmt= connection.prepareStatement(sql);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int reviewID = rs.getInt("ID");
                int orderId = rs.getInt("OrderID");
                String comment = rs.getString("Comment");

                reviewOrder.add(new Review(reviewID, orderId, comment));
            }
            request.setAttribute("review",(reviewOrder.isEmpty()?new Review(0,id,""):reviewOrder.get(0)) );
            request.getRequestDispatcher("review.jsp").forward(request, response);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("reviewID");
        int idReview = Integer.parseInt(id);
        int orderID = Integer.parseInt(req.getParameter("OrderID"));
        String comment = req.getParameter("Comment");
        String sql;
        PreparedStatement stmt;
        System.out.println(id + ""+ idReview+ " "+ orderID);
        try{

            if (id.equals("0") || idReview==0){
                sql = "INSERT INTO Review (OrderID, Comment) VALUES (?, ?)";
                stmt = connection.prepareStatement(sql);
                stmt.setInt(1,orderID);
                stmt.setString(2,comment);

            }else {
                sql = "UPDATE Review SET Comment = ? WHERE ID = ?";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1,comment);
                stmt.setInt(2,idReview);
            }

            stmt.executeUpdate();
            resp.sendRedirect(req.getContextPath()+"/MyPurchases");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
