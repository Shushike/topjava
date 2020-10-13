package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        String paramUserId = request.getParameter("userId");
        log.debug("set user {}", paramUserId);
        if (paramUserId != null && !paramUserId.isEmpty()) {
            final int userId = Integer.parseInt(paramUserId);
            SecurityUtil.setAuthUserId(userId);
            response.sendRedirect("meals");
            return;
        }
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
}
