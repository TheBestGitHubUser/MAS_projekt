package com.example.demo.servlet.notForGUI;

import com.example.demo.model.notForGUI.Article;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

@WebServlet("/article")
public class ArticleItem extends HttpServlet {
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
        Article article = null;

        if (idParam != null) {
            try {
                int articleId = Integer.parseInt(idParam);
                String sql = "SELECT * FROM Article WHERE ID = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, articleId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    article = new Article(
                            rs.getInt("ID"),
                            rs.getInt("AuthorID"),
                            rs.getString("Title"),
                            rs.getString("Content"),
                            rs.getDate("Date").toLocalDate(),
                            rs.getInt("views")
                    );
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("ID"));
        int authorId = Integer.parseInt(req.getParameter("AuthorID"));
        String title = req.getParameter("Title");
        String content = req.getParameter("Content");
        String sql;
        PreparedStatement stmt;

        try {
            if (id == 0) {
                sql = "INSERT INTO Article (AuthorID, Title, Content, Date) VALUES (?, ?, ?, ?)";
                stmt = connection.prepareStatement(sql);
                stmt.setInt(1, authorId);
                stmt.setString(2, title);
                stmt.setString(3, content);
                stmt.setDate(4, Date.valueOf(LocalDate.now()));
            } else {
                sql = "UPDATE Article SET Title = ?, Content = ? WHERE ID = ?";
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, title);
                stmt.setString(2, content);
                stmt.setInt(3, id);
            }

            stmt.executeUpdate();
            resp.sendRedirect("articles"); // lista artykułów
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
