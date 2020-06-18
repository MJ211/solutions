public class MaxProfitParam {
    public int beginIdx;
    public int endIdx;
    public int sum;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaxProfitParam that = (MaxProfitParam) o;
        return sum == that.sum;
    }

    public MaxProfitParam(int sum, int endIdx) {
        this.sum = sum;
        this.endIdx = endIdx;
    }

    public void addToBeginIdx(int num) {
        this.beginIdx += num;
    }

    public void setEndIdx(int endIdx) {
        this.endIdx = endIdx;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public void setBeginIdx(int beginIdx) {
        this.beginIdx = beginIdx;
    }

}
