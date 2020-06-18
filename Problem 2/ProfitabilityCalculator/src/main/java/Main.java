public class Main {
    public static void main(String[] args) {
        InputReader inputReader = new InputReader();
        ProfitabilityDiscriminator profitabilityDiscriminator = new ProfitabilityDiscriminator(inputReader);
        System.out.println(profitabilityDiscriminator.obtainMaxProfitability());
    }
}
