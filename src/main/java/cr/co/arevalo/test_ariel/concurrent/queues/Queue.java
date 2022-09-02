package cr.co.arevalo.test_ariel.concurrent.queues;

import org.springframework.stereotype.Component;

/**
 * Generic queue interface.
 * @param <T> type of objects to hold in queue
 */
@Component
public interface Queue< T >
{
    /**
     * Pushes and object onto the queue.
     * @param data object to push
     */
    void push( final T data ) throws InterruptedException;

    /**
     * Pops an object from the queue.
     * @return next object from the queue
     */
    T pop() throws InterruptedException;
}
