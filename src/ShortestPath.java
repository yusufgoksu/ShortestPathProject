
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Scanner;

public class ShortestPath {
    public static void main(String[] args) {
        GraphofCity graphofCity = readCSV("C:\\Users\\yusuf\\OneDrive\\Desktop\\ShortestPath Project\\out\\production\\ShortestPath Project\\Turkish cities .csv");

        Scanner scanner = new Scanner(System.in);
        if (graphofCity == null) {
            System.out.println("CSV file could not be loaded. Exiting the program.");
            System.exit(1);  // Programdan hata kodu ile çıkış yap
        }

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Find shortest path using DFS");
            System.out.println("2. Find shortest path using BFS");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    findShortestPath(graphofCity, scanner,false);
                    break;
                case 2:
                    findShortestPath(graphofCity, scanner,true);
                    break;
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 0.");
            }
        }
    }



    private static GraphofCity readCSV(String csvFile) {
        GraphofCity graphofCity = new GraphofCity();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String[] cities = br.readLine().split(",");  // İlk satırdaki şehir isimlerini oku

            String line;
            while ((line = br.readLine()) != null) {
                String[] distances = line.split(",");
                String currentCity = distances[0];
                Map<String, Integer> distancesMap = new HashMap<>();

                // Şehirlere olan mesafeleri ekle
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


    private static void findShortestPath(GraphofCity graphofCity, Scanner scanner, boolean useBFS) {
        Map<String, String> cityNames = MakeCityNamesNormal(graphofCity); // Şehir isimlerini normalize et
        String startCity;
        String destinationCity;

        // Başlangıç şehri doğrulama
        while (true) {
            System.out.print("Enter the start city: ");
            startCity = scanner.next().trim().toLowerCase();

            if (cityNames.containsKey(startCity)) {
                startCity = cityNames.get(startCity); // Normalize edilmiş ismi geri al
                break;
            } else {
                System.out.println("Invalid city name. Please enter a valid start city.");
            }
        }

        // Hedef şehri doğrulama
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

        // Şehirler doğrulandıktan sonra algoritmaya göre işlem yap
        if (useBFS) {
            ShortestPathBFS shortestPathBFS = new ShortestPathBFS(graphofCity);
            shortestPathBFS.addFinalState(destinationCity);
            Map<String, Object> shortestPathBFSResult = shortestPathBFS.findShortestPath(startCity, destinationCity);

            displayResult(startCity, destinationCity, shortestPathBFSResult, "BFS");
        } else {
            ShortestPathDFS shortestPathDFS = new ShortestPathDFS(graphofCity);
            shortestPathDFS.addFinalState(destinationCity);
            Map<String, Object> shortestPathDFSResult = shortestPathDFS.findShortestPath(startCity, destinationCity);

            displayResult(startCity, destinationCity, shortestPathDFSResult, "DFS");
        }
    }


    private static Map<String, String> MakeCityNamesNormal(GraphofCity graphofCity) {
        Map<String, String> normalizedMap = new HashMap<>();
        for (String city : graphofCity.getGraph().keySet()) {
            normalizedMap.put(city.toLowerCase(), city); // Şehir isimlerini küçük harfe çevirerek ekle
        }
        return normalizedMap;
    }


    private static void displayResult(String startCity, String destinationCity, Map<String, Object> result, String algorithm) {
        if (result != null) {
            List<String> path = (List<String>) result.get("path");
            int distance = (int) result.get("distance");

            System.out.println("Shortest path from " + startCity + " to " + destinationCity + " using " + algorithm + ":");
            System.out.println("Path: " + String.join(" -> ", path));
            System.out.println("Total Distance: " + distance + " km");
        } else {
            System.out.println("No path found between " + startCity + " and " + destinationCity + " using " + algorithm);
        }
    }

}

