package file;

import car.Car;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * JSON writing.
 */
public class JSONWriting implements WritingToFile{
    @Override
    public void writeInformationToFile(Map<Integer, Car> environmentCentreDatabase) throws IOException {
        Path path = Paths.get("C:\\Users\\nikolay\\IdeaProjects\\idk0051\\EKSAM\\files\\info.json");

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("[");
            Map<Integer, Car> allCars = environmentCentreDatabase;
            Car car = allCars.remove(allCars.size() - 1);

            allCars.forEach((key, value) -> {
                String number = String.valueOf(value.getNumber());
                String engine = value.getEngine().getInformation();
                String tire = value.getTire().getInformation();

                try {
                    writer.write("{\"number\":" + number + ", \"engine\":\"" + engine + "\", \"tire\":\"" + tire + "\"}," + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            writer.write("{\"number\":" + car.getNumber() + ", \"engine\":\"" + car.getEngine().getInformation() + "\", \"tire\":\"" + car.getTire().getInformation() + "\"}" + "\n");
            writer.write("]");
            writer.close();
        }

        System.out.println("-------------------------------------------------    JSON FILE IS WRITTEN    -------------------------------------------------");
    }
}
