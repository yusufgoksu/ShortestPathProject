import java.util.HashMap;
import java.util.Map;

public class GraphofCity {
    private Map<String, Map<String, Integer>> graph; // Stores the graph as an adjacency map

    /**
     * Constructor to initialize an empty graph.
     * Time Complexity: O(1)
     */
    public GraphofCity() {
        this.graph = new HashMap<>(); // Initialize the graph as a HashMap
    }

    /**
     * Adds a city to the graph with its distances to other cities.
     * @param city      The name of the city to add.
     * @param distances A map containing neighboring cities and their respective distances.
     * Time Complexity: O(k), where k is the number of neighboring cities in the distances map.
     */
    public void addCity(String city, Map<String, Integer> distances) {
        graph.put(city, new HashMap<>(distances)); // Add the city and its neighbors to the graph
    }

    /**
     * Retrieves the entire graph.
     * @return The adjacency map representing the graph.
     * Time Complexity: O(1)
     */
    public Map<String, Map<String, Integer>> getGraph() {
        return graph; // Return the graph
    }
}
