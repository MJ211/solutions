public class CipherFragment {
    private int bufferedReaderIndex;
    private int lineNumber;
    private String cipher;

    public CipherFragment(int bufferedReaderIndex, int lineNumber, String cipher) {
        this.bufferedReaderIndex = bufferedReaderIndex;
        this.lineNumber = lineNumber;
        this.cipher = cipher;
    }

    public int getBufferedReaderIdx() {
        return bufferedReaderIndex;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getCipher() {
        return cipher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CipherFragment that = (CipherFragment) o;
        return bufferedReaderIndex == that.bufferedReaderIndex &&
                getLineNumber() == that.getLineNumber() &&
                getCipher().equals(that.getCipher());
    }

}
