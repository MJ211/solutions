import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class BufferedReadersGathererTest {

    @Test
    void shouldReturnOnePath() {
        BufferedReadersGatherer sut = new BufferedReadersGatherer(Paths.get("testFiles\\filterTxtFilesTest"));
        assertEquals(2, sut.gather().size());
    }
}