package cr.co.arevalo.test_ariel.prodcons;

import cr.co.arevalo.test_ariel.queues.Queue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Consumes phone numbers with text endings and converts the ending to numbers.
 */
@Component
@Slf4j
public class TextNumberConsumer
{
    @Value("${prodcons.consumers}")
    private int consumers;

    @Value( "${prodcons.iterations}" )
    private int iterations;

    @Autowired
    private Queue< String > concurrentQueue;

    @PostConstruct
    private void run()
    {
        ExecutorService executorService = Executors.newFixedThreadPool( consumers );
        for ( int i = 0; i < iterations; ++i )
        {
            executorService.submit( this::consume );
        }
    }

    /**
     * Consumes a phone number with text, turning it into a formatted phone number.
     */
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

    /**
     * Trims and formats a number according to telephone format.
     * @param number number to format
     * @return formatted number
     */
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

    /**
     * Converts characters into their corresponding dial pad numbers.
     * @param characters characters to convert
     * @return characters converted to their dial pad numbers
     */
    private String convertCharacters( String characters )
    {
        return characters.chars().skip( 4 ).map( this::convertLetter )
                             .collect( StringBuilder::new, StringBuilder::append, StringBuilder::append ).toString();
    }

    /**
     * Converts each character to its appropriate number on a phone dial pad.
     * @param letter numerical value of the character to convert
     * @return the appropriate number on a dial pad
     */
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