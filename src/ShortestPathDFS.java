import java.util.*;

public class ShortestPathDFS {

    private GraphofCity graphofCity;
    private MyStack<String> stack;
    private Set<String> closed;
    private Map<String, String> path;
    private Map<String, Integer> distances;
    private CustomList<List<String>> allPaths;
    private List<Integer> allDistances;

    private long startTime;  // Timer başlangıç zamanı
    private long endTime;    // Timer bitiş zamanı

    public ShortestPathDFS(GraphofCity graphofCity) {
        this.graphofCity = graphofCity;
        this.stack = new CustomStack<>();
        this.closed = new HashSet<>();
        this.path = new HashMap<>();
        this.distances = new HashMap<>();
        this.allPaths = new CustomList<>();
        this.allDistances = new ArrayList<>();
    }

    public Map<String, Object> findShortestPath(String startCity, String destinationCity) {
        startTime = System.nanoTime();  // Timer başlat

        for (String city : graphofCity.getGraph().keySet()) {
            distances.put(city, 9999);
        }

        stack.push(startCity);
        distances.put(startCity, 0);
        path.put(startCity, null);

        while (!stack.isEmpty()) {
            String currentCity = stack.pop();
            closed.add(currentCity);

            if (currentCity.equals(destinationCity)) {
                savePath(destinationCity);
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
                    stack.push(successor);
                }
            }

            closed.remove(currentCity);  // Geçici olarak kaldır, başka yollar keşfetmek için
        }

        endTime = System.nanoTime();  // Timer durdur
        System.out.println(" ");

        System.out.println("Execution Time For DFS : " + (endTime - startTime) + " nanoseconds");

        return findShortestPathResult(destinationCity);
    }

    private void savePath(String destinationCity) {
        List<String> currentPath = new ArrayList<>();
        String currentCity = destinationCity;

        while (currentCity != null) {
            currentPath.add(currentCity);
            currentCity = path.get(currentCity);
        }

        Collections.reverse(currentPath);
        allPaths.add(currentPath);
        allDistances.add(distances.get(destinationCity));
    }

    private Map<String, Object> findShortestPathResult(String destinationCity) {
        Map<String, Object> pathMap = new LinkedHashMap<>();

        if (allPaths.isEmpty()) {
            pathMap.put("message", "No path found");
        } else {
            int Index = 0;
            for (int i = 1; i < allDistances.size(); i++) {
                if (allDistances.get(i) < allDistances.get(Index)) {
                    Index = i;
                }
            }

            pathMap.put("path", allPaths.get(Index));
            pathMap.put("distance", allDistances.get(Index));
        }

        return pathMap;
    }

    public void addFinalState(String finalState) {
        // Placeholder for any additional functionality if needed
    }
}
