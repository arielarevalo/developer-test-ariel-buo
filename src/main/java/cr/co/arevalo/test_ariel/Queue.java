package cr.co.arevalo.test_ariel;

public interface Queue< T >
{
    void push( final T data );

    T pop() throws InterruptedException;
}
