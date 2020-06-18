import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import java.io.IOException;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

class FileMergerTest {
    @Mock
    private BufferedWriterHandler bufferedWriterHandlerMock = mock(BufferedWriterHandler.class);
    @Mock
    private CipherFragmentBuilder cipherFragmentBuilderMock = mock(CipherFragmentBuilder.class);

    private FileMerger sut = new FileMerger(bufferedWriterHandlerMock, cipherFragmentBuilderMock);
    private InOrder io = inOrder(bufferedWriterHandlerMock);

    private void expectFragmentBuild3Readers2Fragments() throws Exception {
        CipherFragment _02B = new CipherFragment(0,2,"B");
        CipherFragment _21A = new CipherFragment(2,1,"A");

        when(cipherFragmentBuilderMock.buffersAmount()).thenReturn(3);
        when(cipherFragmentBuilderMock.build(0)).thenReturn(_02B).thenReturn(null);
        when(cipherFragmentBuilderMock.build(1)).thenReturn(null);
        when(cipherFragmentBuilderMock.build(2)).thenReturn(_21A).thenReturn(null);
    }

    private void expectFragmentBuild3Readers5Fragments() throws Exception {
        CipherFragment _02B = new CipherFragment(0,2,"B");
        CipherFragment _03C = new CipherFragment(0,3,"C");
        CipherFragment _05E = new CipherFragment(0,5,"E");
        CipherFragment _11A = new CipherFragment(1,1,"A");
        CipherFragment _24D = new CipherFragment(2,4,"D");
        CipherFragment _26F = new CipherFragment(2,6,"F");

        when(cipherFragmentBuilderMock.buffersAmount()).thenReturn(3);
        when(cipherFragmentBuilderMock.build(0)).thenReturn(_02B).thenReturn(_03C).thenReturn(_05E).thenReturn(null);
        when(cipherFragmentBuilderMock.build(1)).thenReturn(_11A).thenReturn(null);
        when(cipherFragmentBuilderMock.build(2)).thenReturn(_24D).thenReturn(_26F).thenReturn(null);
    }

    private void expectFragmentBuild2Readers0Fragments() throws Exception {
        CipherFragment _02B = new CipherFragment(0,2,"B");
        CipherFragment _03C = new CipherFragment(0,3,"C");
        CipherFragment _05E = new CipherFragment(0,5,"E");
        CipherFragment _11A = new CipherFragment(1,1,"A");
        CipherFragment _24D = new CipherFragment(2,4,"D");
        CipherFragment _26F = new CipherFragment(2,6,"F");

        when(cipherFragmentBuilderMock.buffersAmount()).thenReturn(3);
        when(cipherFragmentBuilderMock.build(0)).thenReturn(null);
        when(cipherFragmentBuilderMock.build(1)).thenReturn(null);
    }

    private void verifyOrder(Stream<String> cipherInOrder) throws Exception {
        sut.start();
        cipherInOrder.forEach(cipher -> {
            try {
                io.verify(bufferedWriterHandlerMock).saveLineInOutputFile(cipher);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @AfterEach
    void closeBuffer() {
        verify(bufferedWriterHandlerMock, times(1)).tryToCloseBufferedWriter();
    }

    @Test
    void shouldSaveTwoFragmentElements() throws Exception {
        expectFragmentBuild3Readers2Fragments();
        verifyOrder(Stream.of("A", "B"));
    }

    @Test
    void shouldSaveFiveFragmentElements() throws Exception {
        expectFragmentBuild3Readers5Fragments();
        verifyOrder(Stream.of("A", "B", "C", "D", "E", "F"));
    }

    @Test
    void shouldSaveZeroFragmentElements() throws Exception {
        expectFragmentBuild2Readers0Fragments();
        verifyOrder(Stream.of());
    }
}