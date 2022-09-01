package cr.co.arevalo.test_ariel.queues;

import cr.co.arevalo.test_ariel.exceptions.QueueOverflowException;
import cr.co.arevalo.test_ariel.exceptions.QueueUnderflowException;

/**
 * QUEUE CAPACITY IS ONE LESS THAN TOTAL LENGTH OF ARRAY
 */
public class SimpleStringQueue implements Queue< String >
{
    public final int size;

    private final String[] queue;

    private int head;

    private int tail;

    private int length;

    public SimpleStringQueue( final int size )
    {
        this.queue = new String[ size + 1 ];
        this.size = size;
        this.head = 0;
        this.tail = 0;
        this.length = 0;
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
        ++length;
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
        --length;
        return out;
    }

    public int getLength()
    {
        return length;
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
