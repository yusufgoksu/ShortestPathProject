
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ShortestPath {
    public static void main(String[] args) {
        GraphofCity graphofCity = readCSV("C:\\Users\\yusuf\\OneDrive\\Desktop\\ShortestPath Project\\out\\production\\ShortestPath Project\\Turkish cities .csv");

        Scanner scanner = new Scanner(System.in);
        if (graphofCity == null) {
            System.out.println(" ");
            System.out.println("CSV file could not be loaded,pleas check your path . Exiting the program.");
            System.exit(100);  // exiting program to not show user interface
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
                        System.out.println("Invalid choice. Please enter 1, 2, 3, 4 or 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number (1, 2, 3, 4 or 0).");
                scanner.next();
            }


        }


    }



    private static GraphofCity readCSV(String csvFile) {
        GraphofCity graphofCity = new GraphofCity();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String[] cities = reader.readLine().split(",");  // İlk satırdaki şehir isimlerini oku

            String line;
            while ((line = reader.readLine()) != null) {
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


    private static void findShortestPath(GraphofCity graphofCity, Scanner scanner,int choice) {
        Map<String, String> cityNames = MakeCityNamesNormal(graphofCity); // Şehir isimlerini normalize et
        String startCity;
        String destinationCity;

        // Başlangıç şehri doğrulama
        while (true) {
            System.out.println("Avaliable Cities");
            System.out.println("1-Gaziantep");
            System.out.println("2-Konya");
            System.out.println("3-Samsun");
            System.out.println("4-Batman");
            System.out.println("5-Izmir");
            System.out.println("6-Urfa");
            System.out.println("7-Kayseri");
            System.out.println("8-Denizli");
            System.out.println("9-Ankara");
            System.out.println("10-Adana");
            System.out.println("11-Istanbul");
            System.out.println("12-Bursa");
            System.out.println("13-Diyarbakir");
            System.out.println("14-Trabzon");
            System.out.println("15-Antalya");
            System.out.println("16-Mersin");
            System.out.println("17-Malatya");
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
        if (choice == 1) {//Dfs Algorithm
            // DFS kullanarak en kısa yolu bulma
            ShortestPathDFS shortestPathDFS = new ShortestPathDFS(graphofCity);
            shortestPathDFS.addFinalState(destinationCity);
            Map<String, Object> shortestPathDFSResult = shortestPathDFS.findShortestPath(startCity, destinationCity);

            displayResult(startCity, destinationCity, shortestPathDFSResult, "DFS");
        } else if (choice == 2) {// Bfs Algorithm

            ShortestPathBFS shortestPathBFS = new ShortestPathBFS(graphofCity);
            shortestPathBFS.addFinalState(destinationCity);
            Map<String, Object> shortestPathBFSResult = shortestPathBFS.findShortestPath(startCity, destinationCity);

            displayResult(startCity, destinationCity, shortestPathBFSResult, "BFS");

        }
        else if (choice==3){ // Print the both algorithm
            //Dfs Algorithm
            ShortestPathDFS shortestPathDFS = new ShortestPathDFS(graphofCity);
            shortestPathDFS.addFinalState(destinationCity);
            Map<String, Object> shortestPathDFSResult = shortestPathDFS.findShortestPath(startCity, destinationCity);
            displayResult(startCity, destinationCity, shortestPathDFSResult, "DFS");

            // BFS Algorithm
            ShortestPathBFS shortestPathBFS = new ShortestPathBFS(graphofCity);
            shortestPathBFS.addFinalState(destinationCity);
            Map<String, Object> shortestPathBFSResult = shortestPathBFS.findShortestPath(startCity, destinationCity);

            displayResult(startCity, destinationCity, shortestPathBFSResult, "BFS");

        }
        else if (choice==4){ // Print the both algorithm to see how it different same algorith
            //Dfs Algorithm
            ShortestPathDFS shortestPathDFS = new ShortestPathDFS(graphofCity);
            shortestPathDFS.addFinalState(destinationCity);
            Map<String, Object> shortestPathDFSResult = shortestPathDFS.findShortestPath(startCity, destinationCity);
            displayResult(startCity, destinationCity, shortestPathDFSResult, "DFS");

            //Dfs Algorithm which its not optimized
            ShortestPathDFS2 shortestPathDFS2 = new ShortestPathDFS2(graphofCity);
            shortestPathDFS2.addFinalState(destinationCity);
            Map<String, Object> shortestPathDFSResult1 = shortestPathDFS2.findShortestPath(startCity, destinationCity);
            displayResult(startCity, destinationCity, shortestPathDFSResult1, "DFS not optimized one");




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
            System.out.println(" ");
            System.out.println("Shortest path from " + startCity + " to " + destinationCity + " using " + algorithm + ":");
            System.out.println("Path: " + String.join(" -> ", path));
            System.out.println("Total Distance: " + distance + " km");

        } else {
            System.out.println("No path found between " + startCity + " and " + destinationCity + " using " + algorithm);
        }
    }

}