import java.util.Comparator;
import java.util.TreeSet;

public class FileMerger {

    private BufferedWriterHandler bufferedWriterHandler;
    private CipherFragmentBuilder cipherFragmentBuilder;

    private TreeSet<CipherFragment> cipherSorter =
            new TreeSet<>(Comparator.comparingInt(CipherFragment::getLineNumber));

    public FileMerger(BufferedWriterHandler bufferedWriterHandler,
                      CipherFragmentBuilder cipherFragmentBuilder) {
        this.bufferedWriterHandler = bufferedWriterHandler;
        this.cipherFragmentBuilder = cipherFragmentBuilder;
    }

    private void fetchAndSaveInputLines() throws Exception {
        while(cipherSorter.size() > 0) {
            CipherFragment firstFragment = cipherSorter.first();
            bufferedWriterHandler.saveLineInOutputFile(firstFragment.getCipher());
            cipherSorter.pollFirst();
            addMessageFragment(firstFragment.getBufferedReaderIdx());
        }
        bufferedWriterHandler.tryToCloseBufferedWriter();
    }

    private void addMessageFragment(int bufferedReaderIdx) throws Exception {
        CipherFragment cipherFragment = cipherFragmentBuilder.build(bufferedReaderIdx);
        if(cipherFragment == null)
            return;
        cipherSorter.add(cipherFragment);
    }

    private void fetchFirstLineFromEachFile() throws Exception {
        for (int i = 0; i < cipherFragmentBuilder.buffersAmount(); ++i) {
            addMessageFragment(i);
        }
    }

    public void start() throws Exception {
        fetchFirstLineFromEachFile();
        fetchAndSaveInputLines();
    }
}
