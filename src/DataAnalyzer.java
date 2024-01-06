import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Handles the analysis of network data.
 */
public class DataAnalyzer {

    private List<NetworkData> dataList;

    public List<NetworkData> getDataList(){
        return this.dataList;
    }

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
    public boolean loadDataFromFile(String filePath) {
        this.dataList = new ArrayList<>();
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
            return true;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Calculates and returns a comprehensive set of network statistics.
     *
     * @return Map of statistic names to their values.
     */
    public Map<String, String> calculateStatistics() {
        Map<String, String> statistics = new HashMap<>();
        statistics.put("Total Connections", String.valueOf(calculateTotalConnections()));
        statistics.put("Unique Local IPs", String.valueOf(calculateUniqueLocalIPs()));
        statistics.put("Unique Remote ASNs", String.valueOf(calculateUniqueRemoteASNs()));
        statistics.put("Average Connections per IP", String.valueOf(calculateAverageConnectionsPerIP()));
        statistics.put("Maximum Number of Connections", String.valueOf(calculateMaxConnections()));
        statistics.put("Minimum Number of Connections", String.valueOf(calculateMinConnections()));
        statistics.put("Average Number of Connections", String.valueOf(calculateAverageConnections()));
        statistics.put("Peak Connection Date", calculatePeakConnectionDate());

        return statistics;
    }

    private int calculateTotalConnections() {
        return dataList.stream().mapToInt(NetworkData::getFlows).sum();
    }

    private int calculateUniqueLocalIPs() {
        return (int) dataList.stream().map(NetworkData::getLocalIP).distinct().count();
    }

    private int calculateUniqueRemoteASNs() {
        return (int) dataList.stream().map(NetworkData::getRemoteASN).distinct().count();
    }

    private int calculateAverageConnectionsPerIP() {
        return (int) dataList.stream().collect(Collectors.groupingBy(NetworkData::getLocalIP,
                        Collectors.summingInt(NetworkData::getFlows)))
                .values().stream().mapToInt(val -> val).average().orElse(0);
    }

    private int calculateMaxConnections() {
        return dataList.stream().mapToInt(NetworkData::getFlows).max().orElse(0);
    }

    private int calculateMinConnections() {
        return dataList.stream().mapToInt(NetworkData::getFlows).min().orElse(0);
    }

    private int calculateAverageConnections() {
        return (int) dataList.stream().mapToInt(NetworkData::getFlows).average().orElse(0);
    }

    private String calculatePeakConnectionDate() {
        Map<String, Integer> dateConnectionsMap = new HashMap<>();

        for (NetworkData data : dataList) {
            int currentCount = dateConnectionsMap.getOrDefault(data.getDate(), 0);
            dateConnectionsMap.put(data.getDate(), currentCount + data.getFlows());
        }

        return dateConnectionsMap.entrySet()
                .stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse("No Data"); // Return "No Data" if the list is empty or no max found
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
        Map<String, String> statistics = calculateStatistics();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : statistics.entrySet()) {
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
