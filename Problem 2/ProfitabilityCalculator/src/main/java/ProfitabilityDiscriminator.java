import java.util.ArrayList;
import java.util.Scanner;

public class ProfitabilityDiscriminator {
    private int[] plotsProfitability;
    private InputReader inputReader;
    private MaxProfitParam currentMax;
    private ArrayList<MaxProfitParam> potentialMaxs = new ArrayList<>();

    public ProfitabilityDiscriminator(InputReader inputReader) {
        this.inputReader = inputReader;
    }

    private void addNewPotentialMax(int maxProfit, int endIdx) {
        if (maxProfit == currentMax.sum) {
            potentialMaxs.add(new MaxProfitParam(maxProfit, endIdx));
        } else if(maxProfit > currentMax.sum) {
            potentialMaxs = new ArrayList<>();
            potentialMaxs.add(new MaxProfitParam(maxProfit, endIdx));
            currentMax.setSum(maxProfit);
            currentMax.setEndIdx(endIdx);
        }
    }

    private void findPotentialMaxs() {
        currentMax = new MaxProfitParam(plotsProfitability[0], 0);
        addNewPotentialMax(plotsProfitability[0], 0);
        int previousMax = currentMax.sum;
        for(int i = 1; i < plotsProfitability.length; ++i) {
            int maxProfit = Math.max(plotsProfitability[i], plotsProfitability[i] + previousMax);
            previousMax = maxProfit;
            addNewPotentialMax(maxProfit, i);
        }
    }

    private void calculateBeginIdx(MaxProfitParam elem) {
        int tempSum = elem.sum;
        for (elem.setBeginIdx(elem.endIdx); tempSum > 0; elem.addToBeginIdx(-1)) {
            tempSum -= plotsProfitability[elem.beginIdx];
        }
        elem.addToBeginIdx(1);
    }

    private int determineIdxOfShortestMaxProfit() {
        int bestMaxIdx = 0;
        int currentIndex = 0;
        for(MaxProfitParam elem : potentialMaxs) {
            calculateBeginIdx(elem);
            MaxProfitParam currentBest = potentialMaxs.get(bestMaxIdx);
            if((elem.endIdx - elem.beginIdx) < (currentBest.endIdx - currentBest.beginIdx)) {
                bestMaxIdx = currentIndex;
            }
            currentIndex++;
        }
        return bestMaxIdx;
    }

    public String obtainMaxProfitability() {
        plotsProfitability = inputReader.readPlotsProfitability(new Scanner(System.in));
        findPotentialMaxs();
        MaxProfitParam result = potentialMaxs.get(determineIdxOfShortestMaxProfit());
        return (result.beginIdx + 1) + " " + (result.endIdx + 1)  + " " + result.sum;
    }
}
