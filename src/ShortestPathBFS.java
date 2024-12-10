import java.util.*;

public class ShortestPathBFS {
    private GraphofCity graphofCity;
    private MyQueue<String> open;
    private Set<String> closed;
    private Map<String, String> path;
    private Set<String> finalStates;
    private Map<String, Integer> distances;

    public ShortestPathBFS(GraphofCity graphofCity) {
        this.graphofCity = graphofCity;
        this.open = new CustomQueue<>();
        this.closed = new HashSet<>();
        this.path = new HashMap<>();
        this.finalStates = new HashSet<>();
        this.distances = new HashMap<>();
    }

    public Map<String, Object> findShortestPath(String startCity, String destinationCity) {
        // Initialize distances for all cities as unreachable (max value)
        for (String city : graphofCity.getGraph().keySet()) {
            distances.put(city, Integer.MAX_VALUE);
        }

        open.offer(startCity);
        distances.put(startCity, 0);

        while (!open.isEmpty()) {
            String currentCity = open.poll();
            closed.add(currentCity);

            if (currentCity.equals(destinationCity)) {
                return constructPath(startCity, destinationCity);
            }

            Map<String, Integer> successors = graphofCity.getGraph().get(currentCity);
            for (Map.Entry<String, Integer> entry : successors.entrySet()) {
                String successor = entry.getKey();
                int distanceToSuccessor = entry.getValue();

                if (distanceToSuccessor == 99999) {
                    continue; // Skip unreachable cities
                }

                // If the successor is not in the closed or open sets, or we found a shorter path
                if (!closed.contains(successor) && !open.contains(successor)) {
                    open.offer(successor);
                    path.put(successor, currentCity);
                    distances.put(successor, distances.get(currentCity) + distanceToSuccessor);
                } else if (distances.get(successor) > distances.get(currentCity) + distanceToSuccessor) {
                    // Found a shorter path
                    path.put(successor, currentCity);
                    distances.put(successor, distances.get(currentCity) + distanceToSuccessor);
                    open.offer(successor);
                }
            }
        }

        // If we exit the loop, no path was found
        return null; // Return null when no path is found
    }

    private Map<String, Object> constructPath(String startCity, String destinationCity) {
        Map<String, Object> pathMap = new LinkedHashMap<>();
        String currentCity = destinationCity;
        List<String> pathList = new ArrayList<>();

        // Build the path from destination back to start by following the 'path' map
        while (currentCity != null) {
            pathList.add(currentCity);
            currentCity = path.get(currentCity);
        }

        Collections.reverse(pathList);

        // If the distance to the destination is still unreachable, return a message
        if (distances.get(destinationCity) == Integer.MAX_VALUE) {
            pathMap.put("message", "No path found");
        } else {
            pathMap.put("path", pathList);
            pathMap.put("distance", distances.get(destinationCity));
        }

        return pathMap;
    }

    public void addFinalState(String finalState) {
        finalStates.add(finalState);
    }
}