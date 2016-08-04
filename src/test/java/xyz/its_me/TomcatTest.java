package xyz.its_me;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TomcatTest {
    private static final int PORT = 9273;

    @BeforeClass
    public static void setupClass() {
        TomcatLauncher.start(PORT);
        TomcatLauncher.waitForStart();
    }

    @AfterClass
    public static void teardownClass() {
        TomcatLauncher.stop();
        TomcatLauncher.waitForStop();
    }

    /**
     * Simple test of the response of the {@link HelloServlet}.
     */
    @Test
    public void testHelloServlet() throws Exception {
        final BufferedReader reader = readFromUrl("http://localhost:" + PORT + "/hello");
        final String line = reader.readLine();
        assertThat(line, is("Welcome from your beautiful Servlet!"));
        reader.close();
    }

    /**
     * Changes the state of the {@link ConfigurableServlet} and checks the response.
     */
    @Test
    public void testConfigurableServlet() throws Exception {
        final UUID newUuid = UUID.randomUUID();
        ConfigurableServlet.setUuid(newUuid);

        final BufferedReader reader = readFromUrl("http://localhost:" + PORT + "/uuid");
        final String line = reader.readLine();
        assertThat(line, is(newUuid.toString()));
        reader.close();
    }

    private BufferedReader readFromUrl(String url) throws IOException {
        final InputStream inputStream = new URL(url).openStream();
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
