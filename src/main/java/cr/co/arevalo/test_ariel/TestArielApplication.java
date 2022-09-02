package cr.co.arevalo.test_ariel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class TestArielApplication
{
    @Value("${prodcons.consumers.number}")
    private int consumers;

    @Value("${prodcons.producers.number}")
    private int producers;

    /**
     * Sets up configuration for the task executor.
     * @return the task executor
     */
    @Bean
    public TaskExecutor simpleAsyncTaskExecutor() {
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
        executor.setConcurrencyLimit( consumers + producers );
        return executor;
    }

    /**
     * Sets up configuration for an application wide logger.
     * @return the logger
     */
    @Bean
    public Logger log() {
        return LoggerFactory.getLogger(TestArielApplication.class);
    }

    public static void main( String[] args )
    {
        SpringApplication.run( TestArielApplication.class, args );
    }

}
