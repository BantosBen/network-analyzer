import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles the analysis of network data.
 */
public class DataAnalyzer {

    private final List<NetworkData> dataList;

    /**
     * Constructor initializing the list.
     */
    public DataAnalyzer() {
        this.dataList = new ArrayList<>();
    }

    /**
     * Reads network data from a CSV file and populates the dataList.
     *
     * @param filePath the path to the CSV file
     */
    public void loadDataFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header row if present
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                NetworkData data = new NetworkData(
                        values[0], // date
                        Integer.parseInt(values[1]), // localIP
                        Integer.parseInt(values[2]), // remoteASN
                        Integer.parseInt(values[3])); // numberOfConnections
                dataList.add(data);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Calculates and returns a comprehensive set of network statistics.
     *
     * @return Map of statistic names to their values.
     */
    public Map<String, Integer> calculateStatistics() {
        Map<String, Integer> statistics = new HashMap<>();
        statistics.put("Total Connections", calculateTotalConnections());
        statistics.put("Unique Local IPs", calculateUniqueLocalIPs());
        statistics.put("Unique Remote ASNs", calculateUniqueRemoteASNs());
        statistics.put("Average Connections per IP", calculateAverageConnectionsPerIP());
        statistics.put("Maximum Number of Connections", calculateMaxConnections());
        statistics.put("Minimum Number of Connections", calculateMinConnections());
        statistics.put("Average Number of Connections", calculateAverageConnections());

        return statistics;
    }

    private int calculateTotalConnections() {
        return dataList.stream().mapToInt(NetworkData::getNumberOfConnections).sum();
    }

    private int calculateUniqueLocalIPs() {
        return (int) dataList.stream().map(NetworkData::getLocalIP).distinct().count();
    }

    private int calculateUniqueRemoteASNs() {
        return (int) dataList.stream().map(NetworkData::getRemoteASN).distinct().count();
    }

    private int calculateAverageConnectionsPerIP() {
        return (int) dataList.stream().collect(Collectors.groupingBy(NetworkData::getLocalIP,
                        Collectors.summingInt(NetworkData::getNumberOfConnections)))
                .values().stream().mapToInt(val -> val).average().orElse(0);
    }

    private int calculateMaxConnections() {
        return dataList.stream().mapToInt(NetworkData::getNumberOfConnections).max().orElse(0);
    }

    private int calculateMinConnections() {
        return dataList.stream().mapToInt(NetworkData::getNumberOfConnections).min().orElse(0);
    }

    private int calculateAverageConnections() {
        return (int) dataList.stream().mapToInt(NetworkData::getNumberOfConnections).average().orElse(0);
    }

    /**
     * Displays sections of traffic for a given user (local IP).
     *
     * @param localIP the local IP to filter by
     * @return List of NetworkData for the given user.
     */
    public List<NetworkData> displayTrafficForUser(int localIP) {
        return dataList.stream()
                .filter(data -> data.getLocalIP() == localIP)
                .collect(Collectors.toList());
    }

    /**
     * Saves the analyzed statistics to a separate file.
     *
     * @param filePath the file path to save statistics.
     * @return boolean indicating success or failure of the operation.
     */
    public boolean saveStatisticsToFile(String filePath) {
        Map<String, Integer> statistics = calculateStatistics();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, Integer> entry : statistics.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue());
                writer.newLine();
            }
            return true; // File written successfully
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            return false; // File writing failed
        }
    }
}
