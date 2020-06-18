import java.io.BufferedWriter;
import java.io.IOException;

public class BufferedWriterHandler {
    private BufferedWriter bufferedWriter;

    public BufferedWriterHandler(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }

    public void tryToCloseBufferedWriter() {
        try {
            if(bufferedWriter != null ) {
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveLineInOutputFile(String code) throws IOException {
        bufferedWriter.write(code);
        bufferedWriter.newLine();
    }
}
