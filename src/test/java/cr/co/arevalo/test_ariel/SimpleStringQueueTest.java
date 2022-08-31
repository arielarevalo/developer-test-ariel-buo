package cr.co.arevalo.test_ariel;

import cr.co.arevalo.test_ariel.exceptions.QueueOverflowException;
import cr.co.arevalo.test_ariel.exceptions.QueueUnderflowException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleStringQueueTest
{
    @Test
    void pushPopTest()
    {
        String[] testValues0 = { "EK3", "FTG", "qJgp" };
        String[] testValues1 = { "810", "osL", "osL", "V6DX" };

        SimpleStringQueue simpleStringQueue = new SimpleStringQueue( 10 );

        String actualResult0 = runValuesThruQueue( testValues0, simpleStringQueue );

        assertEquals( actualResult0, "EK3FTGqJgp" );

        // Test again right away to confirm state of head and tail are stable

        String actualResult1 = runValuesThruQueue( testValues1, simpleStringQueue );

        assertEquals( actualResult1, "810osLosLV6DX" );
    }

    @Test
    void underflowTest()
    {
        SimpleStringQueue simpleStringQueue = new SimpleStringQueue( 7 );

        assertThrows( QueueUnderflowException.class, simpleStringQueue::pop );
    }

    @Test
    void overflowTest()
    {
        final int LENGTH = 7;

        SimpleStringQueue simpleStringQueue = new SimpleStringQueue( LENGTH );

        for ( int i = 0; i < LENGTH; ++i )
        {
            simpleStringQueue.push( "" );
        }

        assertThrows( QueueOverflowException.class, () -> simpleStringQueue.push( "" ) );
    }

    private static String runValuesThruQueue( final String[] testValues, final SimpleStringQueue simpleStringQueue )
    {
        for ( String test : testValues )
        {
            simpleStringQueue.push( test );
        }

        StringBuilder actualResult = new StringBuilder();

        for ( String test : testValues )
        {
            actualResult.append( simpleStringQueue.pop() );
        }

        return actualResult.toString();
    }
}