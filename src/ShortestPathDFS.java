import java.util.*;

public class ShortestPathDFS {
    private GraphofCity graphofCity;
    private MyStack<String> open;
    private Set<String> closed;
    private Map<String, String> path;
    private Map<String, Integer> distances;

    public ShortestPathDFS(GraphofCity graphofCity) {
        this.graphofCity = graphofCity;
        this.open = new CustomStack<>();  // CustomStack kullanıyoruz
        this.closed = new HashSet<>();
        this.path = new HashMap<>();
        this.distances = new HashMap<>();
    }

    public Map<String, Object> findShortestPath(String startCity, String destinationCity) {
        for (String city : graphofCity.getGraph().keySet()) {
            distances.put(city, Integer.MAX_VALUE);
        }

        open.push(startCity);
        distances.put(startCity, 0);
        path.put(startCity, null);

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

                if (distanceToSuccessor == 99999) {
                    continue;
                }

                int newDist = distances.get(currentCity) + distanceToSuccessor;

                if (!closed.contains(successor) && newDist < distances.get(successor)) {
                    distances.put(successor, newDist);
                    path.put(successor, currentCity);
                    open.push(successor);
                }
            }
        }

        return null;
    }

    private Map<String, Object> constructPath(String startCity, String destinationCity) {
        Map<String, Object> pathMap = new LinkedHashMap<>();
        List<String> pathList = new ArrayList<>();
        String currentCity = destinationCity;

        while (currentCity != null) {
            pathList.add(currentCity);
            currentCity = path.get(currentCity);
        }

        Collections.reverse(pathList);

        if (distances.get(destinationCity) == 9999) {
            pathMap.put("message", "No path found");
        } else {
            pathMap.put("path", pathList);
            pathMap.put("distance", distances.get(destinationCity));
        }

        return pathMap;
    }

    public void addFinalState(String finalState) {
        // Additional functionality if needed
    }
}