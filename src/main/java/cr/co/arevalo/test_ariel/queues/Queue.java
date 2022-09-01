package cr.co.arevalo.test_ariel.queues;

import org.springframework.stereotype.Component;

@Component
public interface Queue< T >
{
    void push( final T data ) throws InterruptedException;

    T pop() throws InterruptedException;
}
