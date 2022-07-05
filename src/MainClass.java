import java.io.*;
import java.util.*;

public class MainClass {
    public static final String pathToFile = "src/newFile1.csv";
    public static final String pathToFile2 = "src/newFile2.csv";


    public static void main(String[] args) throws IOException {
        AppData myData = new AppData();
        myData.setHeader(new String[]{"Value 1", "Value 2", "Value 3", "Value 4"});
        myData.setData(new int[][]{{1, 2, 3, 4}, {10, 20, 30, 40}, {100, 200, 300, 400}});
        writeToFile(myData, pathToFile);
        AppData readFromFile = readToObject(pathToFile);
        writeToFile(readFromFile, pathToFile2);
    }

    public static void writeToFile(AppData data, String filePath) throws IOException {
        File directory = new File(filePath).getParentFile();
        if (directory != null) {
            directory.mkdirs();
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            int length = data.getHeader().length;
            for (int i = 0; i < length; i++) {
                writer.write(data.getHeader()[i]);
                if (i == length - 1) {
                    writer.write(System.getProperty("line.separator"));
                } else {
                    writer.write(";");
                }
            }

            for (int[] dataArray : data.getData()) {
                length = dataArray.length;
                for (int i = 0; i < length; i++) {
                    writer.write(String.valueOf(dataArray[i]));
                    if (i == length - 1) {
                        writer.write(System.getProperty("line.separator"));
                    } else {
                        writer.write(";");
                    }
                }
            }
        }
    }

    public static AppData readToObject(String filePath) throws IOException {
        AppData appData = new AppData();
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            appData.setHeader(line.split(";"));
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                records.add(Arrays.asList(values));
            }
        }
        int[][] resultData = new int[records.size()][records.get(0).size()];
        for (int i = 0; i < records.size(); i++) {
            for (int j = 0; j < records.get(i).size(); j++) {
                resultData[i][j] = Integer.valueOf(records.get(i).get(j));
            }
        }
        appData.setData(resultData);
        return appData;
    }
}