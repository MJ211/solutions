import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProfitabilityDiscriminatorTest {

    @Mock
    InputReader inputReader = mock(InputReader.class);
    ProfitabilityDiscriminator sut = new ProfitabilityDiscriminator(inputReader);

    static class InputOutput {
        int[] input;
        String output;

        public InputOutput(int[] input, String output) {
            this.input = input;
            this.output = output;
        }
    }

    private static Stream<InputOutput> successCases() {
        return Stream.of(
                new InputOutput(new int[]{0, -2, 5, 1, -4, 8, 2, 2, 0, -6, 2, 10}, "3 12 20"),
                new InputOutput(new int[]{2, -6, 4, 2, 8, -12, 7, -9, 10, 2, 1, -3}, "3 5 14"),
                new InputOutput(new int[]{-1,-2,-1,-3,1,-1,-2,-5,-7}, "5 5 1"),
                new InputOutput(new int[]{-378, 221, -184, -29, 417, -811, 122, 365, -279, -750}, "7 8 487"),
                new InputOutput(new int[]{-249, -139, 484, 148, -342, -734, 198, -304, -496, -913}, "3 4 632"),
                new InputOutput(new int[]{-644, 718, 373, -366, -926, -703, -292, -387, -939, 66}, "2 3 1091"),
                new InputOutput(new int[]{566, 267, -250, 905, 956, -871, -945, 30, 963, -486}, "1 5 2444"),
                new InputOutput(new int[]{78, -105, 916, -17, 734, -351, 683, 625, 335, 173}, "3 10 3098"),
                new InputOutput(new int[]{79, -105, -511, -127, -88, 132, 180, 23, -710, -514}, "6 8 335"),
                new InputOutput(new int[]{20, -100, -100, -100, 5, 5, 5, 5}, "1 1 20"),
                new InputOutput(new int[]{-100, -100, 20, -100, 5, 5, 5, 5}, "3 3 20"),
                new InputOutput(new int[]{10, 10, -100, -100, 20, -100, 5, 5, 5, 5}, "5 5 20"),
                new InputOutput(new int[]{7, -10, -10, 7, -10, 7}, "1 1 7"),
                new InputOutput(new int[]{-5, 0, 7, -6, 4, 3, -5, 0, 2}, "3 6 8")
        );
    }

    @ParameterizedTest
    @MethodSource("successCases")
    void shouldReturnProperResult(InputOutput io) {
        when(inputReader.readPlotsProfitability(notNull())).thenReturn(io.input);
        assertEquals(io.output, sut.obtainMaxProfitability());
    }
}