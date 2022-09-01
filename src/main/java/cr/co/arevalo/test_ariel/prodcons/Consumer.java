package cr.co.arevalo.test_ariel.prodcons;

import cr.co.arevalo.test_ariel.queues.Queue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * NO INTERFACE SINCE NO CONTACT
 */
@Component
@Slf4j
public class Consumer
{
    @Autowired
    private Queue< String > concurrentQueue;

    @PostConstruct
    private void run()
    {
        ExecutorService executorService = Executors.newFixedThreadPool( 2 );
        for ( int i = 0; i < 15; ++i )
        {
            executorService.submit( this::consume );
        }
    }

    private void consume()
    {
        try
        {
            String numberWithText = concurrentQueue.pop();
            String number = convertCharacters( numberWithText );
            number = trimAndFormat( number );

            String prefix = "800-";
            log.info( numberWithText + " => " + prefix + number );
        } catch ( InterruptedException ie )
        {
            log.error( "Thread has been interrupted.", ie );
        }

    }

    private String trimAndFormat( String number )
    {
        while ( number.length() < 8 )
        {
            StringBuilder numberBuilder = new StringBuilder( number );
            numberBuilder.append( "0" );
            number = numberBuilder.toString();
        }

        String part1 = number.substring( 0, 4 );
        String part2 = number.substring( 4, 8 );

        return part1 + "-" + part2;
    }

    private String convertCharacters( String numberWithText ) throws InterruptedException
    {
        return numberWithText.chars().skip( 4 ).map( this::convertLetter )
                             .collect( StringBuilder::new, StringBuilder::append, StringBuilder::append ).toString();
    }

    private int convertLetter( int letter )
    {
        switch ( letter )
        {
            case 'A':
            case 'B':
            case 'C':
                return 2;
            case 'D':
            case 'E':
            case 'F':
                return 3;
            case 'G':
            case 'H':
            case 'I':
                return 4;
            case 'J':
            case 'K':
            case 'L':
                return 5;
            case 'M':
            case 'N':
            case 'O':
                return 6;
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
                return 7;
            case 'T':
            case 'U':
            case 'V':
                return 8;
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
                return 9;
            default:
                throw new IllegalArgumentException();
        }
    }
}