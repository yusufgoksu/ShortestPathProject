import java.util.*;

public class ShortestPathDFS {
    private GraphofCity graphofCity;
    private MyStack<String> open;
    private Set<String> closed;
    private Map<String, String> path;
    private Set<String> finalStates;
    private Map<String, Integer> distances;

    public ShortestPathDFS(GraphofCity graphofCity) {
        this.graphofCity = graphofCity;
        this.open = new CustomStack<>();
        this.closed = new HashSet<>();
        this.path = new HashMap<>();
        this.finalStates = new HashSet<>();
        this.distances = new HashMap<>();
    }

    public Map<String, Object> findShortestPath(String startCity, String destinationCity) {
        open.push(startCity);
        distances.put(startCity, 0);

        while (!open.isEmpty()) {
            String currentCity = open.pop();
            closed.add(currentCity);

            if (currentCity.equals(destinationCity)) {
                return constructPath(startCity, destinationCity);
            }

            Map<String, Integer> successors = graphofCity.getGraph().get(currentCity);
            for (Map.Entry<String, Integer> entry : successors.entrySet()) {
                String successor = entry.getKey();
                int distanceToSuccessor = entry.getValue();

                if (!closed.contains(successor) && !open.contains(successor)) {
                    open.push(successor);
                    path.put(successor, currentCity);
                    distances.put(successor, distances.get(currentCity) + distanceToSuccessor);
                } else if (distances.get(successor) > distances.get(currentCity) + distanceToSuccessor) {
                    path.put(successor, currentCity);
                    distances.put(successor, distances.get(currentCity) + distanceToSuccessor);
                    open.push(successor);
                }
            }
        }

        return null; // Yol bulunamadı
    }

    private Map<String, Object> constructPath(String startCity, String destinationCity) {
        Map<String, Object> pathMap = new LinkedHashMap<>();
        String currentCity = destinationCity;
        List<String> pathList = new ArrayList<>();
        int totalDistance = 0;

        while (currentCity != null) {
            pathList.add(currentCity);
            String parentCity = path.get(currentCity);

            if (parentCity != null) {
                totalDistance += graphofCity.getGraph().get(parentCity).get(currentCity);
            }
            currentCity = parentCity;
        }

        Collections.reverse(pathList);
        pathMap.put("path", pathList);
        pathMap.put("distance", totalDistance);

        return pathMap;
    }

    public void addFinalState(String finalState) {
        finalStates.add(finalState);
    }
}
