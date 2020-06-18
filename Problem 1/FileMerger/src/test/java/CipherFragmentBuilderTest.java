import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CipherFragmentBuilderTest {

    private static final int FIRST_BUFFERED_READER_INDEX = 0;

    private static List<BufferedReader> createListWithReader(String str) {
        return new ArrayList<>(){{
            add(new BufferedReader(new StringReader(str)));
        }};
    }

    static class LineWithCipherFragment {
        public List<BufferedReader> list;
        public CipherFragment cipherFragment;

        public LineWithCipherFragment(String line, CipherFragment cipherFragment) {
            this.list = createListWithReader(line);
            this.cipherFragment = cipherFragment;
        }
    }

    private final BufferedReadersGatherer bufferedReadersGatherer = mock(BufferedReadersGatherer.class);

    private CipherFragmentBuilder sut;
    private static Stream<LineWithCipherFragment> linesWithExpectedFormat() {
        return Stream.of(
                new LineWithCipherFragment("1 A",
                                           new CipherFragment(FIRST_BUFFERED_READER_INDEX, 1, "A")),
                new LineWithCipherFragment("11 K",
                                           new CipherFragment(FIRST_BUFFERED_READER_INDEX, 11, "K")),
                new LineWithCipherFragment("823781 KJAHDKAaasAD",
                                           new CipherFragment(FIRST_BUFFERED_READER_INDEX, 823781, "KJAHDKAaasAD")),
                new LineWithCipherFragment("5 &%$!@^(%#$%",
                                           new CipherFragment(FIRST_BUFFERED_READER_INDEX, 5, "&%$!@^(%#$%")),
                new LineWithCipherFragment("999999 A",
                                           new CipherFragment(FIRST_BUFFERED_READER_INDEX, 999999, "A")));
    }
    private static Stream<List<BufferedReader>> listsWithMoreOrLessThanTwoStrings() {
        return Stream.of(createListWithReader("1 2 A\n"), createListWithReader("AAAA\n"), createListWithReader("&\n"));
    }

    void initTest(List<BufferedReader> lbr) {
        when(bufferedReadersGatherer.gather()).thenReturn(lbr);
        sut = new CipherFragmentBuilder(bufferedReadersGatherer);
    }

    @Test
    void shouldReturnNullIfBufferIsEmpty() throws Exception {
        initTest(createListWithReader(""));
        assertNull(sut.build(FIRST_BUFFERED_READER_INDEX));
    }

    @ParameterizedTest
    @MethodSource("listsWithMoreOrLessThanTwoStrings")
    void shouldThrowIllegalLineFormatExceptionWhenLineContainMoreOrLessThanTwoStringSeparateBySpace(
            List<BufferedReader> lbr) {
        initTest(lbr);
        assertThrows(IllegalLineFormatException.class, () -> sut.build(FIRST_BUFFERED_READER_INDEX));
    }

    @ParameterizedTest
    @MethodSource("linesWithExpectedFormat")
    void shouldReturnTrueForExpectedLineFormat(LineWithCipherFragment param) throws Exception {
        initTest(param.list);
        assertEquals(param.cipherFragment, sut.build(FIRST_BUFFERED_READER_INDEX));
    }

    @Test
    void shouldThrowIfFirstStringInLineIsNotNumber() {
        initTest(createListWithReader("AAA AAA"));
        assertThrows(FirstElementInArrayIsNotInteger.class, () -> sut.build(FIRST_BUFFERED_READER_INDEX));
    }
}
