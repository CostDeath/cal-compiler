import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalIntegrationTests {
    private final ClassLoader classLoader = getClass().getClassLoader();
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void successfullyParsesValidFile() {
        String path = classLoader.getResource("file.cal").getPath();
        Cal.main(new String[] {path});

        assertTrue(outputStreamCaptor.toString().contains(path + " parsed successfully"));
        assertFalse(outputStreamCaptor.toString()
                .contains("Please provide a proper file as the program's first argument!"));
    }
}
