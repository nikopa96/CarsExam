package city;

/**
 * Street.
 */
public class Street {

    private Crossroad firstCrossroad;
    private Crossroad secondCrossroad;
    private String name;
    private boolean goodRoad;

    public Street(Crossroad firstCrossroad, Crossroad secondCrossroad, String name, boolean goodRoad) {
        this.firstCrossroad = firstCrossroad;
        this.secondCrossroad = secondCrossroad;
        this.name = name;
        this.goodRoad = goodRoad;
    }

    public Crossroad getFirstCrossroad() {
        return firstCrossroad;
    }

    public Crossroad getSecondCrossroad() {
        return secondCrossroad;
    }

    public String getName() {
        return name;
    }

    public boolean isGoodRoad() {
        return goodRoad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Street street = (Street) o;

        if (firstCrossroad != null ? !firstCrossroad.equals(street.firstCrossroad) : street.firstCrossroad != null)
            return false;
        if (secondCrossroad != null ? !secondCrossroad.equals(street.secondCrossroad) : street.secondCrossroad != null)
            return false;
        return name != null ? name.equals(street.name) : street.name == null;
    }

    @Override
    public int hashCode() {
        int result = firstCrossroad != null ? firstCrossroad.hashCode() : 0;
        result = 31 * result + (secondCrossroad != null ? secondCrossroad.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
