package cr.co.arevalo.test_ariel.prodcons;

import cr.co.arevalo.test_ariel.queues.Queue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Produces phone numbers with text endings.
 */
@Component
@Slf4j
public class TextNumberProducer
{
    @Value("${prodcons.producers}")
    private int producers;

    @Value( "${prodcons.iterations}" )
    private int iterations;

    @Autowired
    private Queue< String > concurrentQueue;

    @PostConstruct
    private void run()
    {
        ExecutorService executorService = Executors.newFixedThreadPool( producers );
        for ( int i = 0; i < iterations; ++i )
        {
            executorService.submit( this::produce );
        }
    }

    /**
     * Produces a phone number with a text ending.
     */
    private void produce()
    {
        try
        {
            String prefix = "800-";
            concurrentQueue.push( prefix + getRandomString() );
        } catch ( InterruptedException ie )
        {
            log.error( "Thread has been interrupted.", ie );
        }
    }

    /**
     * Returns a random string.
     * @return a random string
     */
    private String getRandomString()
    {
        final int letterA = 65;
        final int letterZ = 90;

        final int minLen = 3;
        final int maxLen = 14;
        Random random = new Random();

        return random.ints( letterA, letterZ + 1 )
                     .limit( random.nextInt( maxLen - minLen ) + minLen )
                     .collect( StringBuilder::new, StringBuilder::appendCodePoint,
                             StringBuilder::append )
                     .toString();
    }
}
