package service;

import city.Crossroad;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Checkpoint.
 */
public class Checkpoint {

    private EnvironmentCentre environmentCentre;
    private Crossroad crossroad;

    public Checkpoint(EnvironmentCentre environmentCentre) {
        List<Integer> possibleCrossroadUniqueLabels = Arrays.asList(1, 9, 12, 15);
        Collections.shuffle(possibleCrossroadUniqueLabels);

        this.crossroad = new Crossroad(possibleCrossroadUniqueLabels.get(0));
        this.environmentCentre = environmentCentre;
    }

    public Crossroad getCrossroad() {
        return crossroad;
    }

    public EnvironmentCentre getEnvironmentCentre() {
        return environmentCentre;
    }

    @Override
    public String toString() {
        return "checkpoint on crossroad #" + crossroad.getUniqueLabel();
    }
}
