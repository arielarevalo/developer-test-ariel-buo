package cr.co.arevalo.test_ariel.queues;

import cr.co.arevalo.test_ariel.exceptions.QueueOverflowException;
import cr.co.arevalo.test_ariel.exceptions.QueueUnderflowException;

/**
 * Simple queue that holds String objects.
 */
public class SimpleStringQueue implements Queue< String >
{
    private final String[] queue;

    private int head;

    private int tail;

    /**
     * Constructor for the simple string queue.
     * @param size maximum size of the queue
     */
    public SimpleStringQueue( final int size )
    {
        this.queue = new String[ size + 1 ];
        this.head = 0;
        this.tail = 0;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void push( String data ) throws QueueOverflowException
    {
        if ( head == getNextPos( tail ) )
        {
            throw new QueueOverflowException();
        }

        queue[ tail ] = data;
        tail = getNextPos( tail );
    }

    /**
     * @inheritDoc
     */
    @Override
    public String pop() throws QueueUnderflowException
    {
        if ( head == tail )
        {
            throw new QueueUnderflowException();
        }

        String out = queue[ head ];
        head = getNextPos( head );
        return out;
    }

    /**
     * Returns the next position for the head or tail.
     * @param pos current position
     * @return the next position¿
     */
    private int getNextPos( int pos )
    {
        if ( pos == queue.length - 1 )
        {
            return 0;
        } else
        {
            return ++pos;
        }
    }
}
