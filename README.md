
# Network Traffic Analyzer

## Description

Network Traffic Analyzer is a Java application designed to read, analyze, and report network traffic data. It processes CSV files containing network traffic information, calculates various statistics, identifies anomalies, and produces reports. This tool aims to assist network administrators and cybersecurity professionals in understanding network behaviors and spotting potential issues.

## Features

- Load network traffic data from CSV files
- Calculate various network statistics:
  - Total number of connections
  - Unique local IPs
  - Unique remote ASNs
  - Average connections per IP
  - Date with highest connections
- Display traffic for a specific local IP
- Save statistics to an output file
- User-friendly console interface

## Getting Started

### Prerequisites

- Java JDK 11 or higher
- CSV file containing network traffic data

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/BantosBen/network-analyzer.git
   ```
2. Navigate to and open project in IntelliJ IDEA:

### Usage
Run the project IntelliJ IDEA and follow the on-screen prompts to analyze network traffic data.

## Documentation

- `NetworkData.java`: Represents a single row of network traffic data.
- `DataAnalyzer.java`: Contains methods for loading data from CSV, calculating statistics, and saving reports.
- `Main.java`: Entry point of the application, handling the user interface and program flow.

For detailed documentation, refer to inline comments within each Java file.


## License

Distributed under the MIT License. See `LICENSE` for more information.

## Contact

Angatia Benson - [@bensonetia](https://twitter.com/bensonetia) - angatiabenson1@gmail.com

Project Link: [https://github.com/BantosBen/network-analyzer](https://github.com/BantosBen/network-analyzer)

## Acknowledgments

- [Java](https://www.oracle.com/java/)
