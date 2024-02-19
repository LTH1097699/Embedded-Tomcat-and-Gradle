package com.auth0.samples;

import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    private static final int PORT = getPort();

    public static void main(String[] args) throws Exception {
        String appBase = ".";
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(createTempDir().getAbsolutePath());
        tomcat.setPort(PORT);
        tomcat.getHost().setAppBase(appBase);
        tomcat.addWebapp("", appBase);
        tomcat.getConnector();
        tomcat.start();
        tomcat.getServer().await();
    }

    private static int getPort() {
        String port = System.getenv("PORT");
        if (port != null) {
            return Integer.parseInt(port);
        }
        return 8080;
    }

    //  clone from `TomcatServletWebServerFactory.class`
    //  of `org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
    private static File createTempDir() {
        try {
            File tempDir = Files.createTempDirectory("tomcat" + "." + getPort() + ".").toFile();
            tempDir.deleteOnExit();
            return tempDir;
        } catch (IOException ex) {
            throw new RuntimeException(
                    "Unable to create tempDir. java.io.tmpdir is set to " + System.getProperty("java.io.tmpdir"), ex);
        }
    }
}
