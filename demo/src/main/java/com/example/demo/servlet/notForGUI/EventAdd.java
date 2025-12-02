package com.example.demo.servlet.notForGUI;

import com.example.demo.model.notForGUI.Event;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/event")
public class EventAdd extends HttpServlet {

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
                sql += " WHERE ID = ?";
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

            request.setAttribute("event", events.get(0));
            request.getRequestDispatcher("events.jsp").forward(request, response);

        } catch (SQLException | ServletException e) {
            System.out.println("Błąd w doGet: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String dateStr = req.getParameter("date");

        try {
            LocalDate date = LocalDate.parse(dateStr); // format: yyyy-MM-dd

            String sql = "INSERT INTO Event (Name, Date, Description) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setDate(2, Date.valueOf(date));
            stmt.setString(3, description);

            stmt.executeUpdate();
            resp.sendRedirect("events"); // przekierowanie np. do listy wydarzeń

        } catch (SQLException | DateTimeParseException e) {
            throw new ServletException(e.getMessage());
        }
    }
}
