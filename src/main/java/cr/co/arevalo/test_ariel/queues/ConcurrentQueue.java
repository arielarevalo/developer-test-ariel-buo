package cr.co.arevalo.test_ariel.queues;

import cr.co.arevalo.test_ariel.exceptions.QueueOverflowException;
import cr.co.arevalo.test_ariel.exceptions.QueueUnderflowException;
import org.springframework.stereotype.Component;

import java.util.concurrent.Semaphore;

@Component
public class ConcurrentQueue implements Queue< String >
{
    private final int DEFAULT_LENGTH = 10;

    private final SimpleStringQueue queue;

    public ConcurrentQueue()
    {
        this.queue = new SimpleStringQueue( DEFAULT_LENGTH );
    }

    @Override
    synchronized public void push( String data ) throws QueueOverflowException, InterruptedException
    {
        while(queue.getLength() >= queue.size) {
            wait();
        }
        queue.push( data );
        notifyAll();
    }

    @Override
    synchronized public String pop() throws QueueUnderflowException, InterruptedException
    {
        while(queue.getLength() <= 0) {
            wait();
        }
        String out = queue.pop();
        notifyAll();
        return out;
    }
}