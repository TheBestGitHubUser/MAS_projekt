package com.example.demo.servlet.notForGUI;

import com.example.demo.model.notForGUI.Event;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/events")
public class Events extends HttpServlet {
    private Connection connection;

    public void init() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            String url = "jdbc:derby:locale;create=true";
            connection = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println("błąd init: " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String ID = request.getParameter("ID");
        List<Event> events = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Event";
            PreparedStatement stmt;

            if (ID != null) {
                int sellerId = Integer.parseInt(ID);
                sql += " WHERE BusinessPersonID = ?";
                stmt = connection.prepareStatement(sql);
                stmt.setInt(1, sellerId);
            } else {
                stmt = connection.prepareStatement(sql);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Event e = new Event(
                        rs.getInt("ID"),
                        rs.getInt("BusinessPersonID"),
                        rs.getString("Description"),
                        rs.getDate("DateTime").toLocalDate(),
                        rs.getString("Location"),
                        rs.getInt("Capacity")
                );
                events.add(e);
            }

            request.setAttribute("events", events);
            request.getRequestDispatcher("events.jsp").forward(request, response);

        } catch (SQLException | ServletException e) {
            System.out.println("Błąd w doGet: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}