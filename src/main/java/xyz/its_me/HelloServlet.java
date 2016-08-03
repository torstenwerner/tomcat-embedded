package xyz.its_me;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebServlet(name = "MyServlet", urlPatterns = {"/hello"})
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (final ServletOutputStream out = resp.getOutputStream()) {
            final InputStream messageStream = getClass().getResourceAsStream("message.properties");

            final Properties messages = new Properties();
            messages.load(messageStream);

            final String welcome = messages.getProperty("welcome");

            out.write(welcome.getBytes());
            out.flush();
        }
    }
}