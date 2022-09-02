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
@Scope("prototype")
public class TextNumberProducer implements Runnable
{
    @Value( "${prodcons.producers.iterations}" )
    private int iterationsPerProducer;

    @Autowired
    private Queue< String > concurrentQueue;

    @Async
    @Override
    public void run()
    {
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
            String prefix = "800-";
            concurrentQueue.push( prefix + getRandomString() );
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
