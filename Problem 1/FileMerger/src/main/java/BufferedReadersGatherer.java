import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BufferedReadersGatherer {
    private final Path FOLDER_PATH;

    public BufferedReadersGatherer(Path folderPath) {
        FOLDER_PATH = folderPath;
    }

    private Stream<Path> collectOnlyTxtFiles(){
        try {
            return Files.walk(FOLDER_PATH).filter(path -> path.toString().endsWith(".txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Stream.of();
    }

    private static BufferedReader createBufferedReader(Path filePath) {
        try {
            return Files.newBufferedReader(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BufferedReader(Reader.nullReader());
    }

    public List<BufferedReader> gather() {
        return collectOnlyTxtFiles().map(BufferedReadersGatherer::createBufferedReader).collect(Collectors.toList());
    }
}

