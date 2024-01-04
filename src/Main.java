import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DataAnalyzer analyzer = new DataAnalyzer();

        // Load the data from the file (specify the correct file path)
        String filePath = "data/data.csv";
        analyzer.loadDataFromFile(filePath);

        boolean running = true;
        while (running) {
            // Displaying menu options
            System.out.println("\nSelect an option:");
            System.out.println("1. Load data from the data file");
            System.out.println("2. Display All Statistics");
            System.out.println("3. Display Traffic for a Specific User");
            System.out.println("4. Save Statistics to File");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter data file path: ");
                    String dataFilePath = scanner.next();
                    analyzer.loadDataFromFile(dataFilePath);
                    break;
                case 2:
                    // Display all statistics
                    Map<String, String> statistics = analyzer.calculateStatistics();
                    System.out.println("\n"+formatColumn("Statistic", 40) + formatColumn("Value", 10));
                    System.out.println("------------------------------------------------------");
                    for (Map.Entry<String, String> entry : statistics.entrySet()) {
                        String statName = formatColumn(entry.getKey(), 40); // Statistic name column
                        String statValue = formatColumn(entry.getValue(), 10); // Value column
                        System.out.println(statName + statValue);
                    }
                    break;
                case 3:
                    // Display traffic for a specific user
                    System.out.println("Enter Local IP to display traffic for: ");
                    int localIP = scanner.nextInt();
                    List<NetworkData> traffic = analyzer.displayTrafficForUser(localIP);
                    System.out.println("\n" +formatColumn("Date", 15) + formatColumn("Local IP", 10) + formatColumn("Remote ASN", 15) + formatColumn("Connections", 15));
                    System.out.println("-----------------------------------------------------------");
                    for (NetworkData data : traffic) {
                        System.out.println(formatColumn(data.getDate(), 15) + formatColumn(String.valueOf(data.getLocalIP()), 10)
                                + formatColumn(String.valueOf(data.getRemoteASN()), 15) + formatColumn(String.valueOf(data.getNumberOfConnections()), 15));
                    }
                    break;
                case 4:
                    // Save statistics to file
                    String statsFilePath = "data/output_stats.txt";
                    boolean isSaved = analyzer.saveStatisticsToFile(statsFilePath);
                    if (isSaved) {
                        System.out.println("Statistics saved to " + statsFilePath);
                    } else {
                        System.out.println("Failed to save statistics.");
                    }
                    break;
                case 5:
                    // Exit the program
                    running = false;
                    System.out.println("Exiting program...");
                    break;
                default:
                    // Handle unexpected input
                    System.out.println("Invalid option. Please enter a number between 1 and 4.");
            }
        }
        scanner.close(); // Close the scanner when the program ends
    }

    /**
     * Formats the string to a fixed width for column display.
     *
     * @param input the input string
     * @param length the desired fixed length of the column
     * @return a string formatted to the desired length
     */
    private static String formatColumn(String input, int length) {
        // If the input string is shorter than the length, pad it with spaces
        if (input.length() < length) {
            return String.format("%1$-" + length + "s", input);
        }
        // If the input string is longer than the length, truncate it
        else if (input.length() > length) {
            return input.substring(0, length);
        }
        // If it's just right, return it as is
        return input;
    }
}
