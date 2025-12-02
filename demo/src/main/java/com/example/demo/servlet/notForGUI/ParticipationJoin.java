package com.example.demo.servlet.notForGUI;

import com.example.demo.model.notForGUI.Event;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;

import static com.example.demo.DbService.CLIENT_ID;

@WebServlet("/join")
public class ParticipationJoin extends HttpServlet {
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("ID");

        int eventId = Integer.parseInt(idParam);
        Event event = null;

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Event WHERE ID = ?");
            stmt.setInt(1, eventId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                event = new Event(
                        rs.getInt("ID"),
                        rs.getInt("businessPersonID"),
                        rs.getString("Description"),
                        rs.getDate("Date").toLocalDate(),
                        rs.getString("location"),
                        rs.getInt("capacity")

                );
            }

            if (event == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Event not found");
                return;
            }

            req.setAttribute("event", event);
            req.getRequestDispatcher("eventDetails.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new RuntimeException("Error loading event", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int eventId = Integer.parseInt(req.getParameter("eventID"));
        int userId = CLIENT_ID;

        try {
            String sql = "INSERT INTO Participation (EventID, UserID) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, eventId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();

            String update = "UPDATE FROM EVENT SET capacity = capacity + 1 Where ID = ?";
             stmt = connection.prepareStatement(update);
            stmt.setInt(1,userId);
            stmt.executeUpdate();

            resp.sendRedirect("event?ID=" + eventId);
        } catch (SQLException e) {
            throw new ServletException();
        }
    }
}
