package cr.co.arevalo.test_ariel.concurrent.queues;

import cr.co.arevalo.test_ariel.exceptions.QueueOverflowException;
import cr.co.arevalo.test_ariel.exceptions.QueueUnderflowException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Concurrent capable queue that holds String objects.
 */
@Component
public class ConcurrentQueue implements Queue< String >
{
    private final Object mutex;

    private final Semaphore canConsume;

    private final Semaphore canProduce;

    private final SimpleStringQueue queue;

    /**
     * Constructor for the concurrent queue.
     */
    public ConcurrentQueue( @Value( "${prodcons.queue.size}" ) int size )
    {
        this.mutex = new Object();
        this.canConsume = new Semaphore( 0 );
        this.canProduce = new Semaphore( size );
        this.queue = new SimpleStringQueue( size );
    }

    /**
     * @inheritDoc
     */
    @Override
    public void push( String data ) throws QueueOverflowException, InterruptedException
    {
        canProduce.acquire();
        synchronized ( mutex )
        {
            queue.push( data );
        }
        canConsume.release();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String pop() throws QueueUnderflowException, InterruptedException
    {
        canConsume.acquire();
        String out;
        synchronized ( mutex )
        {
            out = queue.pop();
        }
        canProduce.release();
        return out;
    }
}