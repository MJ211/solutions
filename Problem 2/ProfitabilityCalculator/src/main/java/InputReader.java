import java.util.Scanner;

public class InputReader {
    public int[] readPlotsProfitability(Scanner scanner) {
        int[] plotsProfitability = new int[scanner.nextInt()];

        for (int i = 0; scanner.hasNext(); ++i) {
            plotsProfitability[i] = scanner.nextInt();
        }
        scanner.close();
        return plotsProfitability;
    }
}
