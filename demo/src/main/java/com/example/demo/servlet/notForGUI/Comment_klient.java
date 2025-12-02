package com.example.demo.servlet.notForGUI;

import com.example.demo.model.notForGUI.Comment;
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

@WebServlet("/Comment")
public class Comment_klient extends HttpServlet {
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
        int articleId = Integer.parseInt(req.getParameter("articleId"));
        List<Comment> comments = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Comment WHERE ArticleID = ? ORDER BY Date DESC";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, articleId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Comment c = new Comment(
                        rs.getInt("ID"),
                        rs.getInt("ArticleID"),
                        rs.getInt("AuthorID"),
                        rs.getString("Content"),
                        rs.getDate("Date").toLocalDate()
                );
                comments.add(c);
            }

            req.setAttribute("comments", comments);
            req.setAttribute("articleId", articleId);
            req.getRequestDispatcher("comments.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int articleId = Integer.parseInt(req.getParameter("ArticleID"));
        int authorId = Integer.parseInt(req.getParameter("AuthorID"));
        String content = req.getParameter("Content");

        try {
            String sql = "INSERT INTO Comment (ArticleID, AuthorID, Content, Date) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, articleId);
            stmt.setInt(2, authorId);
            stmt.setString(3, content);
            stmt.setDate(4, Date.valueOf(LocalDate.now()));

            stmt.executeUpdate();
            resp.sendRedirect("comments?articleId=" + articleId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
