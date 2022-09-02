package cr.co.arevalo.test_ariel.concurrent.prodcons;

import cr.co.arevalo.test_ariel.concurrent.queues.Queue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Produces phone numbers with text endings.
 */
@Component
@Slf4j
@Scope( "prototype" )
public class TextNumberProducer implements Runnable
{
    @Value( "${phone.prefix}" )
    String prefix;

    @Value( "${prodcons.producers.iterations}" )
    private int iterationsPerProducer;

    private final Queue< String > concurrentQueue;

    @Autowired
    public TextNumberProducer( Queue< String > concurrentQueue )
    {
        this.concurrentQueue = concurrentQueue;
    }

    @Async
    @Override
    public void run()
    {
        assert ( concurrentQueue != null );

        log.info( "Beginning production for {} iterations - Thread {}.",
                iterationsPerProducer, Thread.currentThread().getId() );

        for ( int i = 0; i < iterationsPerProducer; ++i )
        {
            produce();
        }
    }

    /**
     * Produces a phone number with a text ending.
     */
    private void produce()
    {
        try
        {
            String random = getRandomString();

            log.info( "Producing {} - Thread {}", random, Thread.currentThread().getId() );

            concurrentQueue.push( prefix + random );

        } catch ( InterruptedException ie )
        {
            log.error( "Thread has been interrupted.", ie );
        }
    }

    /**
     * Returns a random string.
     *
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
