import java.util.Scanner;

public class InputReader {
    public static String readPathFromStandardInput(Scanner scanner) {
        String result = scanner.nextLine();
        scanner.close();
        return result;
    }
}
