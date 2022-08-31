package cr.co.arevalo.test_ariel;

import cr.co.arevalo.test_ariel.exceptions.QueueOverflowException;
import cr.co.arevalo.test_ariel.exceptions.QueueUnderflowException;

/**
 * QUEUE CAPACITY IS ONE LESS THAN TOTAL LENGTH OF ARRAY
 */
public class SimpleStringQueue implements Queue< String >
{
    private final String[] queue;

    private int head;

    private int tail;

    public SimpleStringQueue( final int length )
    {
        this.queue = new String[ length + 1 ];
        this.head = 0;
        this.tail = 0;
    }

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
