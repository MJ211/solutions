import java.io.BufferedReader;
import java.util.List;

public class CipherFragmentBuilder {
    private List<BufferedReader> bufferedReaderList;

    public CipherFragmentBuilder(BufferedReadersGatherer bufferedReadersGatherer) {
        this.bufferedReaderList = bufferedReadersGatherer.gather();
    }

    private boolean isNumeric(String str) {
        return str.chars().allMatch( Character::isDigit );
    }

    private void handleCustomExceptions(String[] splitLine, String line)
            throws IllegalLineFormatException, FirstElementInArrayIsNotInteger {
        if (splitLine.length != 2) {
            throw new IllegalLineFormatException("Split line of \"" + line + "\" result in more/less than 2 elements");
        }
        if (!isNumeric(splitLine[0])) {
            throw new FirstElementInArrayIsNotInteger(splitLine[0] + " is not integer");
        }
    }

    public CipherFragment build(int bufferedReaderIdx) throws Exception {
        String line = bufferedReaderList.get(bufferedReaderIdx).readLine();
        if (line == null) return null;
        String[] splitLine = line.split("\\s+");
        handleCustomExceptions(splitLine, line);
        return new CipherFragment(bufferedReaderIdx, Integer.parseInt(splitLine[0]), splitLine[1]);
    }

    public int buffersAmount() {
        return bufferedReaderList.size();
    }
}
