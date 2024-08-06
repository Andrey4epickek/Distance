package by.epic.Distance.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class CustomBanner implements Banner {

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        out.println(getText() + " " + getClass().getPackage().getImplementationVersion());
    }

    private String getText() {
        try (InputStream is = getClass().getResourceAsStream("/banner_up.txt")) {
            if (is != null) {
                return IOUtils.toString(is, StandardCharsets.UTF_8);
            }
        } catch (Exception ignored) {
        }
        return "";
    }
}
