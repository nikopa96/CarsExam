package file;

import car.Car;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Text writing.
 */
public class TextWriting implements WritingToFile {
    @Override
    public void writeInformationToFile(Map<Integer, Car> environmentCentreDatabase) throws IOException {
        Path path = Paths.get("C:\\Users\\nikolay\\IdeaProjects\\idk0051\\EKSAM\\files\\info.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            Map<Integer, Car> allCars = environmentCentreDatabase;

            allCars.forEach((key, value) -> {
                String number = String.valueOf(value.getNumber());
                String engine = value.getEngine().getInformation();
                String tire = value.getTire().getInformation();

                try {
                    writer.write("Car #" + number + ": engine is " + engine + " tire is " + tire + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            writer.close();
        }

        System.out.println("-------------------------------------------------    TEXT FILE IS WRITTEN    -------------------------------------------------");
    }
}
