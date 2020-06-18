import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.Mockito.*;

class BufferedWriterHandlerTest {
    BufferedWriter bufferedWriterMock = mock(BufferedWriter.class);

    @Test
    void shouldNotCallCloseMethodIfBufferedReaderIsNull() throws IOException {
        BufferedWriterHandler sut = new BufferedWriterHandler(null);
        sut.tryToCloseBufferedWriter();
        verify(bufferedWriterMock, times(0)).close();
    }

    @Test
    void shouldCloseFileIfBufferedWriterIsNotNull() throws IOException {
        BufferedWriterHandler sut = new BufferedWriterHandler(bufferedWriterMock);
        sut.tryToCloseBufferedWriter();
        verify(bufferedWriterMock, times(1)).close();
    }
}