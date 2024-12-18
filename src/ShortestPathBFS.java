import java.util.*;

public class ShortestPathBFS {
    private GraphofCity graphofCity; // Graph representation
    private MyQueue<String> que; // Custom queue defination
    private Set<String> closed; // to keep  visited cities
    private Map<String, String> path; // Map to store the path
    private Map<String, Integer> distances; // Map to store distances from start city

    private long startTime; // Timer start time
    private long endTime; // Timer end time

    /**
     * Constructor to initialize the BFS class with a graph.
     * Time Complexity: O(1)
     */
    public ShortestPathBFS(GraphofCity graphofCity) {
        this.graphofCity = graphofCity;
        this.que = new CustomQueue<>(); // Initialize custom queue
        this.closed = new HashSet<>(); // Initialize visited set
        this.path = new HashMap<>(); // Initialize path map
        this.distances = new HashMap<>(); // Initialize distance map
    }

    /**
     * Finds the shortest path using BFS.
     * @return Map containing the shortest path and distance.
     * Time Complexity: O(V + E), where V is the number of cities (vertices) and E is the number of connections (edges).
     */
    public Map<String, Object> findShortestPath(String startCity, String destinationCity) {
        startTime = System.nanoTime(); // Start timer

        // Initialize distances with a high value
        for (String city : graphofCity.getGraph().keySet()) {
            distances.put(city, 9999); // Set all distances to "infinity"
        }

        que.offer(startCity); // Add starting city to queue
        distances.put(startCity, 0); // Distance to start city is 0
        path.put(startCity, null); // Start city has no predecessor

        // Perform BFS
        while (!que.isEmpty()) {
            String currentCity = que.poll(); // Remove city from queue
            closed.add(currentCity); // put city as visited

            if (currentCity.equals(destinationCity)) {
                // Destination found, calculate execution time
                endTime = System.nanoTime();
                System.out.println(" ");
                System.out.println("BFS Search Execution Time: " + (endTime - startTime) + " nanoseconds");
                return constructPath(startCity, destinationCity); // Construct and return path
            }

            // Process successors (neighbors)
            Map<String, Integer> successors = graphofCity.getGraph().get(currentCity);
            for (Map.Entry<String, Integer> entry : successors.entrySet()) {
                String successor = entry.getKey(); // Neighboring city
                int distanceToSuccessor = entry.getValue(); // Distance to neighbor

                if (distanceToSuccessor == 99999) {
                    continue; // Skip unreachable neighbors
                }

                int newDist = distances.get(currentCity) + distanceToSuccessor;

                // Update distances and path if a shorter path is found
                if (!closed.contains(successor) && newDist < distances.get(successor)) {
                    distances.put(successor, newDist); // Update distance
                    path.put(successor, currentCity); // Update path
                    que.offer(successor); // Add neighbor to queue
                }
            }
        }

        // No path found, calculate execution time
        endTime = System.nanoTime();
        System.out.println(" ");
        System.out.println("BFS Search Execution Time: " + (endTime - startTime) + " nanoseconds");
        return null; // Return null if no path exists
    }

    /**
     * Constructs the path from start to destination city.
     * Time Complexity: O(V), where V is the number of cities in the path.
     */
    private Map<String, Object> constructPath(String startCity, String destinationCity) {
        Map<String, Object> pathMap = new LinkedHashMap<>(); // Result map
        List<String> pathList = new ArrayList<>(); // List to store the path
        String currentCity = destinationCity;

        // Trace back the path from destination to start
        while (currentCity != null) {
            pathList.add(currentCity);
            currentCity = path.get(currentCity);
        }

        Collections.reverse(pathList); // Reverse to get the correct order

        if (distances.get(destinationCity) == 9999) {
            pathMap.put("message", "No path found"); // No valid path
        } else {
            pathMap.put("path", pathList); // Add path
            pathMap.put("distance", distances.get(destinationCity)); // Add distance
        }

        return pathMap;
    }

    /**
     * Adds a final state or custom logic for destination city if needed.
     * @param finalState The final state city.
     * Time Complexity: O(1)
     */
    public void addFinalState(String finalState) {
        // Placeholder for additional functionality
    }
}
