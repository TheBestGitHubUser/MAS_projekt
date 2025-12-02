package com.example.demo.servlet.notForGUI;


import com.example.demo.model.notForGUI.ArticleDisplay;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/articleList")
public class ArticleList extends HttpServlet {
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
        List<ArticleDisplay> articleList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Article";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int articleId = rs.getInt("ID");
                String title = rs.getString("Title");
                String content = rs.getString("Content");
                int employeeId = rs.getInt("EmployeeID");

                // Pobranie imienia pracownika osobnym zapytaniem
                String employeeName = "";
                PreparedStatement empStmt = connection.prepareStatement("SELECT Name FROM Employee WHERE ID = ?");
                empStmt.setInt(1, employeeId);
                ResultSet empRs = empStmt.executeQuery();
                if (empRs.next()) {
                    employeeName = empRs.getString("Name");
                }

                // Tworzymy obiekt widoku
                ArticleDisplay article = new ArticleDisplay(articleId, title, content, employeeName);
                articleList.add(article);
            }

            req.setAttribute("articles", articleList);
            req.getRequestDispatcher("articles.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving articles", e);
        }
    }
}
