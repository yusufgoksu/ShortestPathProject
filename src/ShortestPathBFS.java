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

                if (!closed.contains(successor) && !open.contains(successor)) {
                    open.offer(successor);
                    path.put(successor, currentCity);
                    distances.put(successor, distances.get(currentCity) + distanceToSuccessor);
                } else if (distances.get(successor) > distances.get(currentCity) + distanceToSuccessor) {
                    // Daha kısa bir yol bulunduysa güncelle
                    path.put(successor, currentCity);
                    distances.put(successor, distances.get(currentCity) + distanceToSuccessor);
                    open.offer(successor);
                }
            }
        }

        return null; // Yol bulunamadı
    }

    private Map<String, Object> constructPath(String startCity, String destinationCity) {
        Map<String, Object> pathMap = new LinkedHashMap<>();
        String currentCity = destinationCity;
        List<String> pathList = new ArrayList<>();

        while (currentCity != null) {
            pathList.add(currentCity);
            currentCity = path.get(currentCity);
        }

        Collections.reverse(pathList);
        pathMap.put("path", pathList);
        pathMap.put("distance", distances.get(destinationCity));
        return pathMap;
    }

    public void addFinalState(String finalState) {
        finalStates.add(finalState);
    }
}
