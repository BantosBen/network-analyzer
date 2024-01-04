/**
 * Represents a single row of network data from the CSV file.
 */
public class NetworkData {
    // Attributes corresponding to the CSV columns
    private String date; // Date in DD-MM-YYYY format
    private int localIP; // Local IP (integer 0-20)
    private int remoteASN; // Remote Autonomous System Number
    private int numberOfConnections; // Number of connections

    /**
     * Constructor to initialize NetworkData object.
     *
     * @param date the date of the network traffic
     * @param localIP the local IP address
     * @param remoteASN the remote ASN
     * @param numberOfConnections the number of connections
     */
    public NetworkData(String date, int localIP, int remoteASN, int numberOfConnections) {
        this.date = date;
        this.localIP = localIP;
        this.remoteASN = remoteASN;
        this.numberOfConnections = numberOfConnections;
    }

    // Getters and setters for each field

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLocalIP() {
        return localIP;
    }

    public void setLocalIP(int localIP) {
        this.localIP = localIP;
    }

    public int getRemoteASN() {
        return remoteASN;
    }

    public void setRemoteASN(int remoteASN) {
        this.remoteASN = remoteASN;
    }

    public int getNumberOfConnections() {
        return numberOfConnections;
    }

    public void setNumberOfConnections(int numberOfConnections) {
        this.numberOfConnections = numberOfConnections;
    }

    /**
     * Override the toString method for easy printing of NetworkData objects.
     * This is particularly useful for debugging or quick checks.
     */
    @Override
    public String toString() {
        return "NetworkData{" +
                "date='" + date + '\'' +
                ", localIP=" + localIP +
                ", remoteASN=" + remoteASN +
                ", numberOfConnections=" + numberOfConnections +
                '}';
    }
}

