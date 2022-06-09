package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //log.debug("redirect to meals");

        String action = request.getParameter("action");
        if (action == null || action.equalsIgnoreCase("meals")) {
            request.setAttribute("mealsTo", MealsUtil.getMealsTo());
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("insert")) {
            request.getRequestDispatcher("/meal.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("update")) {
            //request.setAttribute("meal", new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
            request.getRequestDispatcher("/meal.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int a = 0;
    }

}
