package cr.co.arevalo.test_ariel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestArielApplication
{
    @Bean
    public Logger log() {
        return LoggerFactory.getLogger(TestArielApplication.class);
    }
    public static void main( String[] args )
    {
        SpringApplication.run( TestArielApplication.class, args );
    }

}
