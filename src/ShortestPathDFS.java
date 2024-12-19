import java.util.*;

public class ShortestPathDFS {

    private GraphofCity graphofCity; // Graph containing cities and distances
    private MyStack<String> stack; // Stack for DFS traversal
    private Set<String> closed; // Keeps track of visited cities
    private Map<String, String> path; // Stores the shortest path to each city
    private Map<String, Integer> distances; // Stores shortest distances from startCity
    private CustomList<List<String>> allPaths; // Stores all valid paths to destination
    private List<Integer> allDistances; // Stores distances corresponding to paths

    private long startTime;  // Timer start time
    private long endTime;    // Timer end time

    // Constructor to initialize the class with a graph
    public ShortestPathDFS(GraphofCity graphofCity) {
        this.graphofCity = graphofCity; // Assign the graph
        this.stack = new CustomStack<>(); // Initialize stack for traversal
        this.closed = new HashSet<>(); // Set to keep track of visited cities
        this.path = new HashMap<>(); // Map for tracking paths
        this.distances = new HashMap<>(); // Map for tracking distances
        this.allPaths = new CustomList<>(); // List to store all paths
        this.allDistances = new ArrayList<>(); // List to store distances
    }


      //Finds the shortest path from startCity to destinationCity using DFS.
     // Time Complexity: O(V + E), where V is the number of cities and E is the number of edges.

    public Map<String, Object> findShortestPath(String startCity, String destinationCity) {
        startTime = System.nanoTime();  // Start the timer

        // Initialize all distances to a high value
        for (String city : graphofCity.getGraph().keySet()) {
            distances.put(city, 9999);
        }

        stack.push(startCity); // Add the starting city to the stack
        distances.put(startCity, 0); // Distance to startCity is 0
        path.put(startCity, null); // No previous city for startCity

        // DFS algorithm  to explore all paths
        while (!stack.isEmpty()) {
            String currentCity = stack.pop(); // Process the next city
            closed.add(currentCity); // Mark it as visited

            if (currentCity.equals(destinationCity)) {
                savePath(destinationCity); // Save the path if destination is reached
            }

            // Explore all neighbors of the current city
            Map<String, Integer> successors = graphofCity.getGraph().get(currentCity);
            for (Map.Entry<String, Integer> entry : successors.entrySet()) {
                String successor = entry.getKey(); // Neighbor city
                int distanceToSuccessor = entry.getValue(); // Distance to neighbor

                if (distanceToSuccessor == 99999) { // Skip invalid paths
                    continue;
                }

                int newDist = distances.get(currentCity) + distanceToSuccessor;

                // Update distance and path if a shorter path is found
                if (!closed.contains(successor) && newDist < distances.get(successor)) {
                    distances.put(successor, newDist); // Update the shortest distance
                    path.put(successor, currentCity); // Update the path
                    stack.push(successor); // Add neighbor to the stack
                }
            }

            closed.remove(currentCity); // Remove from visited to explore alternative paths
        }

        endTime = System.nanoTime();  // Stop the timer
        System.out.println(" ");
        System.out.println("Running Time For DFS : " + (endTime - startTime) + " nanoseconds");

        return findShortestPathResult(destinationCity); // Return the shortest path result
    }

      // Saves the path to the destination city.
     // Time Complexity: O(V), where V is the number of cities in the path.

    private void savePath(String destinationCity) {
        CustomList<String> currentPath = new CustomList<>();// List to store the path
        String currentCity = destinationCity;

        while (currentCity != null) {
            currentPath.add(currentCity); // Add cities to the path
            currentCity = path.get(currentCity); // Move to the previous city
        }

        Collections.reverse(currentPath); // Reverse to get the correct order
        allPaths.add(currentPath); // Add the path to allPaths
        allDistances.add(distances.get(destinationCity)); // Add the distance to allDistances
    }


     // Finds the shortest path and distance from all saved paths.
     // Time Complexity: O(P), where P is the number of paths.
    private Map<String, Object> findShortestPathResult(String destinationCity) {
        Map<String, Object> pathMap = new LinkedHashMap<>(); // Result map

        if (allPaths.isEmpty()) { // If no path is found
            pathMap.put("message", "No path found");
        } else {
            int Index = 0;

            // Find the shortest path by comparing distances
            for (int i = 1; i < allDistances.size(); i++) {
                if (allDistances.get(i) < allDistances.get(Index)) {
                    Index = i;
                }
            }

            pathMap.put("path", allPaths.get(Index)); // Add the shortest path
            pathMap.put("distance", allDistances.get(Index)); // Add the shortest distance
        }

        return pathMap; // Return the result
    }

    // Additional functionality can be added here
    public void addFinalState(String finalState) {

    }
}
