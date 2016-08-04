package xyz.its_me;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "ConfigurableServlet", urlPatterns = {"/uuid"})
public class ConfigurableServlet extends HttpServlet {
    private static UUID uuid = UUID.randomUUID();

    public static void setUuid(UUID uuid) {
        ConfigurableServlet.uuid = uuid;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (final ServletOutputStream out = resp.getOutputStream()) {
            out.write(uuid.toString().getBytes());
            out.flush();
        }
    }
}