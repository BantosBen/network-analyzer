import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DataAnalyzerTest {
    private DataAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        analyzer = new DataAnalyzer();
        analyzer.loadDataFromFile("data/data.csv");
    }

    @AfterEach
    void tearDown() {
        analyzer = null;
    }

    @Test
    void testLoadDataFromFile() {
        analyzer.loadDataFromFile("data/data.csv");
        assertFalse(analyzer.getDataList().isEmpty(), "Data list should not be empty after loading data");

    }

    @Test
    void testCalculateStatistics() {
        Map<String, String> statistics = analyzer.calculateStatistics();
        assertNotNull(statistics, "Statistics map should not be null");
        assertTrue(statistics.containsKey("Total Connections"), "Statistics should include total connections");
    }

    @Test
    void displayTrafficForUser() {
        List<NetworkData> userTraffic = analyzer.displayTrafficForUser(10);
        assertNotNull(userTraffic, "User traffic list should not be null");
        assertFalse(userTraffic.isEmpty(), "User traffic list should not be empty for an existing user");

    }

    @Test
    void saveStatisticsToFile() {
        boolean result = analyzer.saveStatisticsToFile("data/test_output_stats.txt");
        assertTrue(result, "Statistics should be saved successfully to a file");
    }

    @Test
    void testLoadDataFromInvalidFile() {
        boolean result = analyzer.loadDataFromFile("path/to/nonexistent_file.csv");
        assertFalse(result, "An error message is displayed to show the file doesn't exist");
    }

}