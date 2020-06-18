import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class InputReaderTest {
    InputReader sut = new InputReader();

    static class InputOutput {
        String input;
        int[] output;

        public InputOutput(String input, int[] output) {
            this.input = input;
            this.output = output;
        }
    }

    private static Stream<InputOutput> successCases() {
        return Stream.of(
                new InputOutput("4\n1 2 3 4\n", new int[]{1, 2, 3, 4}),
                new InputOutput("1\n1\n", new int[]{1}),
                new InputOutput("3\n-1 2 -3 \n", new int[]{-1, 2, -3}),
                new InputOutput("4\n0 4 -3 1000", new int[]{0, 4, -3, 1000}),
                new InputOutput("9 1 2 3 4 5 6 7 8 9", new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9})
        );
    }

    @ParameterizedTest
    @MethodSource("successCases")
    void shouldReturnProperArray(InputOutput io) {
        assertArrayEquals(io.output, sut.readPlotsProfitability(new Scanner(io.input)));
    }
}