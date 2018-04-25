package file;

import car.Car;

import java.io.IOException;
import java.util.Map;

/**
 * WritingToFile.
 */
public interface WritingToFile {
    void writeInformationToFile(Map<Integer, Car> environmentCentreDatabase) throws IOException;
}
