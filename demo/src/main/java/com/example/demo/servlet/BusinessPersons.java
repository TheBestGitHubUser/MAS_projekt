package com.example.demo.servlet;

import com.example.demo.DbService;
import com.example.demo.model.BusinessPerson;
import com.example.demo.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@WebServlet(value = "/BusinessPersons")
public class BusinessPersons extends HttpServlet {
    private Connection connection;

    public void init() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            String url = "jdbc:derby:locale;create=true";
            connection = DriverManager.getConnection(url);
            //DbService.dropTables(connection);
            //DbService.create(connection);
            //DbService.add(connection);
        } catch (Exception e) {
            System.out.println("błąd init");
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String search = request.getParameter("search");

        try {
            List<User> users = new ArrayList<>();
            Map<Integer, User> userMap = new HashMap<>();

            Statement statement = connection.createStatement();

            // 1. Wczytaj wszystkich SiteUser
            ResultSet userResult = statement.executeQuery("SELECT * FROM SiteUser");

            while (userResult.next()) {
                int id = userResult.getInt("ID");
                String name = userResult.getString("Name");
                String email = userResult.getString("Email");
                String password = userResult.getString("Password");

                User user = new User(id, name, email, password);
                users.add(user);
                userMap.put(id, user); // Do mapy po ID
            }

            // 2. Wczytaj Businessperson
            ResultSet businessResult = statement.executeQuery("SELECT * FROM Businessperson");

            List<BusinessPerson> businessPeople = new ArrayList<>();

            while (businessResult.next()) {
                int id = businessResult.getInt("ID");
                int userId = businessResult.getInt("UserID");
                int nip = businessResult.getInt("NIPnumber");
                String role = businessResult.getString("Role");
                float rating = businessResult.getFloat("Rating");
                float attendance = businessResult.getFloat("AverageAttendance");

                // ręczne "połączenie"
                User user = userMap.get(userId);

                if (user != null) {
                    BusinessPerson bp = new BusinessPerson(user, id, nip, role, rating, attendance);
                    businessPeople.add(bp);
                }
            }

            if (search!=null && !search.isEmpty()){
                Pattern pattern = Pattern.compile(".*" + Pattern.quote(search) + ".*", Pattern.CASE_INSENSITIVE);
                businessPeople.removeIf(bp -> !pattern.matcher(bp.getName()).matches());
            }


            request.setAttribute("businessPersons", businessPeople);
            request.getRequestDispatcher("BusinessPersons.jsp").forward(request, response);

        } catch (SQLException | ServletException e) {
            System.out.println(e.getMessage());
        }
    }
}
