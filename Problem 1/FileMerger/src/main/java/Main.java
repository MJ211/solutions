import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BufferedWriterHandler bufferedWriterHandler = null;
        try {
            Files.deleteIfExists(Consts.OUTPUT_PATH);
            bufferedWriterHandler = new BufferedWriterHandler(Files.newBufferedWriter(Consts.OUTPUT_PATH));
            Path folderPath = Paths.get(InputReader.readPathFromStandardInput(new Scanner(System.in)));
            BufferedReadersGatherer bufferedReadersGatherer = new BufferedReadersGatherer(folderPath);
            CipherFragmentBuilder cipherFragmentBuilder = new CipherFragmentBuilder(bufferedReadersGatherer);

            new FileMerger(bufferedWriterHandler, cipherFragmentBuilder).start();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bufferedWriterHandler.tryToCloseBufferedWriter();
        }
    }
}

