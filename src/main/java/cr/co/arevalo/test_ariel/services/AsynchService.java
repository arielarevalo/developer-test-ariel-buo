package cr.co.arevalo.test_ariel.services;

import cr.co.arevalo.test_ariel.concurrent.prodcons.TextNumberConsumer;
import cr.co.arevalo.test_ariel.concurrent.prodcons.TextNumberProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

/**
 * Service that sets the asynchronous processes involved going.
 */
@Service
public class AsynchService
{
    @Autowired
    public TaskExecutor simpleAsyncTaskExecutor;

    @Autowired
    private ApplicationContext applicationContext;

    @Value( "${prodcons.consumers.number}" )
    private int consumers;

    @Value( "${prodcons.producers.number}" )
    private int producers;

    /**
     * Starts the producers and consumers.
     */
    @EventListener( ApplicationReadyEvent.class )
    public void startProdcons()
    {
        for ( int i = 0; i < producers; ++i )
        {
            simpleAsyncTaskExecutor.execute( applicationContext.getBean( TextNumberProducer.class ) );
        }

        for ( int i = 0; i < consumers; ++i )
        {
            simpleAsyncTaskExecutor.execute( applicationContext.getBean( TextNumberConsumer.class ) );
        }
    }
}
