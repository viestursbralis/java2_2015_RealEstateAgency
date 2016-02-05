package lv.javaguru.java2.servlet.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by Viesturs on 05-Jan-16.
 */
@Configuration
@ComponentScan(basePackages = {"lv.javaguru.java2"})
public class MailConfig {

    private static final String DATABASE_PROPERTIES_FILE = "database.properties";


    @Bean
    public static PropertySourcesPlaceholderConfigurer prodPropertiesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer m = new PropertySourcesPlaceholderConfigurer();
        Resource[] resourceLocations = new Resource[] {
                new ClassPathResource(DATABASE_PROPERTIES_FILE)
        };
        m.setLocations(resourceLocations);
        return m;
    }

    @Value("${email.host}") String host1;
    @Value("${email.port}") int port1;





    @Bean
    public Properties mailProperties(
            @Value("${email.host}") String host,
            @Value("${email.port}") int port
            ) {
        Properties properties = new Properties();
        properties.put("email.host", host);
        properties.put("email.port", port);

        return properties;
    }

    @Bean
    public JavaMailSender javaMailService() {


        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(host1);
        javaMailSender.setPort(port1);
        javaMailSender.setUsername("viesturs.bralis@gmail.com");
        javaMailSender.setPassword("sebastians");


        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "false");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.debug", "false");
       // properties.setProperty("mail.smtp.ssl.trust","smtp.gmail.com");
        return properties;
    }
}
