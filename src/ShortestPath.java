import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ShortestPath {
    public static void main(String[] args) {
        // Load the graph from a CSV file
        GraphofCity graphofCity = readCSV("C:\\Users\\yusuf\\OneDrive\\Desktop\\ShortestPath Project\\out\\production\\ShortestPath Project\\Turkish cities .csv");

        Scanner scanner = new Scanner(System.in);
        if (graphofCity == null) {
            System.out.println(" ");
            System.out.println("CSV file could not be loaded, please check your path. Exiting the program.");
            System.exit(100);  // Exiting program to avoid errors
        }

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Find shortest path using DFS");
            System.out.println("2. Find shortest path using BFS");
            System.out.println("3. Print both algorithm Distance and Path");
            System.out.println("4. Compare Optimized DFS algorithm and not optimized one");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        findShortestPath(graphofCity, scanner, choice);
                        break;
                    case 2:
                        findShortestPath(graphofCity, scanner, choice);
                        break;
                    case 3:
                        findShortestPath(graphofCity, scanner, choice);
                        break;
                    case 4:
                        findShortestPath(graphofCity, scanner, choice);
                        break;
                    case 0:
                        System.out.println("Exiting the program. Thank you for choosing us");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1, 2, 3, 4, or 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number (1, 2, 3, 4, or 0).");
                scanner.next();
            }
        }
    }

    private static GraphofCity readCSV(String csvFile) {
        // Reads the graph data from a CSV file
        GraphofCity graphofCity = new GraphofCity();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String[] cities = reader.readLine().split(",");  // Read city names from the first row

            String line;
            while ((line = reader.readLine()) != null) {
                String[] distances = line.split(",");
                String currentCity = distances[0];
                Map<String, Integer> distancesMap = new HashMap<>();

                // Add distances to the city map
                for (int i = 1; i < distances.length; i++) {
                    try {
                        int distance = Integer.parseInt(distances[i]);
                        distancesMap.put(cities[i], distance);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid distance value.");
                    }
                }
                graphofCity.addCity(currentCity, distancesMap);
            }
        } catch (IOException e) {
            return null;
        }

        return graphofCity;
    }

    private static void findShortestPath(GraphofCity graphofCity, Scanner scanner, int choice) {
        // Normalize city names for consistent user input
        Map<String, String> cityNames = MakeCityNamesNormal(graphofCity);
        String startCity;
        String destinationCity;

        // Validate the start city
        while (true) {
            System.out.println("Available Cities: " + String.join(", ", cityNames.values() + "."));
            System.out.println(" ");
            System.out.print("Enter the start city: ");
            startCity = scanner.next().trim().toLowerCase();

            if (cityNames.containsKey(startCity)) {
                startCity = cityNames.get(startCity);
                break;
            } else {
                System.out.println("Invalid city name. Please enter a valid start city.");
            }
        }

        // Validate the destination city
        while (true) {
            System.out.print("Enter the destination city: ");
            destinationCity = scanner.next().trim().toLowerCase();

            if (cityNames.containsKey(destinationCity)) {
                destinationCity = cityNames.get(destinationCity);
                break;
            } else {
                System.out.println("Invalid city name. Please enter a valid destination city.");
            }
        }

        // Process based on user choice
        if (choice == 1) {
            // Depth-First Search (DFS) - Time Complexity: O(V + E)
            ShortestPathDFS shortestPathDFS = new ShortestPathDFS(graphofCity);
            shortestPathDFS.addFinalState(destinationCity);
            Map<String, Object> shortestPathDFSResult = shortestPathDFS.findShortestPath(startCity, destinationCity);

            displayResult(startCity, destinationCity, shortestPathDFSResult, "DFS");
        } else if (choice == 2) {
            // Breadth-First Search (BFS) - Time Complexity: O(V + E)
            ShortestPathBFS shortestPathBFS = new ShortestPathBFS(graphofCity);
            shortestPathBFS.addFinalState(destinationCity);
            Map<String, Object> shortestPathBFSResult = shortestPathBFS.findShortestPath(startCity, destinationCity);

            displayResult(startCity, destinationCity, shortestPathBFSResult, "BFS");
        } else if (choice == 3) {
            // Compare DFS and BFS
            ShortestPathDFS shortestPathDFS = new ShortestPathDFS(graphofCity);
            shortestPathDFS.addFinalState(destinationCity);
            Map<String, Object> shortestPathDFSResult = shortestPathDFS.findShortestPath(startCity, destinationCity);
            displayResult(startCity, destinationCity, shortestPathDFSResult, "DFS");

            ShortestPathBFS shortestPathBFS = new ShortestPathBFS(graphofCity);
            shortestPathBFS.addFinalState(destinationCity);
            Map<String, Object> shortestPathBFSResult = shortestPathBFS.findShortestPath(startCity, destinationCity);
            displayResult(startCity, destinationCity, shortestPathBFSResult, "BFS");
        } else if (choice == 4) {
            // Compare optimized and non-optimized DFS
            ShortestPathDFS shortestPathDFS = new ShortestPathDFS(graphofCity);
            shortestPathDFS.addFinalState(destinationCity);
            Map<String, Object> shortestPathDFSResult = shortestPathDFS.findShortestPath(startCity, destinationCity);
            displayResult(startCity, destinationCity, shortestPathDFSResult, "DFS");

            ShortestPathDFS2 shortestPathDFS2 = new ShortestPathDFS2(graphofCity);
            shortestPathDFS2.addFinalState(destinationCity);
            Map<String, Object> shortestPathDFSResult1 = shortestPathDFS2.findShortestPath(startCity, destinationCity);
            displayResult(startCity, destinationCity, shortestPathDFSResult1, "DFS not optimized");
        }
    }

    private static Map<String, String> MakeCityNamesNormal(GraphofCity graphofCity) {
        // Normalize city names to lowercase
        Map<String, String> normalizedMap = new HashMap<>();
        for (String city : graphofCity.getGraph().keySet()) {
            normalizedMap.put(city.toLowerCase(), city);
        }
        return normalizedMap;
    }

    private static void displayResult(String startCity, String destinationCity, Map<String, Object> result, String algorithm) {
        // Display the result of the algorithm
        if (result != null) {
            List<String> path = (List<String>) result.get("path");
            int distance = (int) result.get("distance");
            System.out.println(" ");
            System.out.println("Shortest path from " + startCity + " to " + destinationCity + " using " + algorithm + ":");
            System.out.println("Path: " + String.join(" -> ", path));
            System.out.println("Total Distance: " + distance + " km");
        } else {
            System.out.println("No path found between " + startCity + " and " + destinationCity + " using " + algorithm);
        }
    }
}
