package by.epic.Distance;


import by.epic.Distance.utils.CustomBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DistanceStarter {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(DistanceStarter.class);
        application.setBanner(new CustomBanner());
        application.run(args);
    }
}
