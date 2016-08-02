package xyz.its_me;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class TomcatLauncher {
    public static void main(String[] args) throws Exception {
        String webappDirLocation = "src/main/webapp/";
        final Tomcat tomcat = new Tomcat();

        final Path basePath = Files.createTempDirectory("tomcat-base-dir");
        tomcat.setBaseDir(basePath.toString());

        tomcat.setPort(8080);

        final String webappPath = new File(webappDirLocation).getAbsolutePath();
        StandardContext ctx = (StandardContext) tomcat.addWebapp("/", webappPath);

        // does not work, sorry
        ctx.setReloadable(true);

        System.out.println("configuring app with basedir: " + webappPath);

        // Declare an alternative location for your "WEB-INF/classes" dir
        // Servlet 3.0 annotation will work
        final File additionWebInfClasses = new File("build/classes");
        final WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(
                new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();
    }
}
