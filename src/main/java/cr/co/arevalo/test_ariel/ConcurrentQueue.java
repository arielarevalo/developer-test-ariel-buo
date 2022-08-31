package cr.co.arevalo.test_ariel;

import cr.co.arevalo.test_ariel.exceptions.QueueOverflowException;
import cr.co.arevalo.test_ariel.exceptions.QueueUnderflowException;

import java.util.concurrent.Semaphore;

public class ConcurrentQueue implements Queue< String >
{
    private final Semaphore canConsume;

    private final SimpleStringQueue queue;

    public ConcurrentQueue( final int length )
    {
        this.canConsume = new Semaphore( 0 );
        this.queue = new SimpleStringQueue( length );
    }

    @Override
    synchronized public void push( String data ) throws QueueOverflowException
    {
        queue.push( data );
        canConsume.release();
    }

    @Override
    synchronized public String pop() throws QueueUnderflowException, InterruptedException
    {
        canConsume.acquire();
        return queue.pop();
    }
}