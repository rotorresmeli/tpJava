public class Currency {
    private String symbol;
    private String description;
    private int decimalPlaces;

    public Currency(String symbol, String description, int decimalPlaces) {
        this.symbol = symbol;
        this.description = description;
        this.decimalPlaces = decimalPlaces;
    }

    @Override
    public String toString() {
        return "Currency{" +
                ", symbol='" + symbol + '\'' +
                ", description='" + description + '\'' +
                ", decimalPlaces=" + decimalPlaces +
                '}';
    }
}