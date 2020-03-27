package com.narnia.railways.service.impl;

import com.narnia.railways.dao.PathDAO;
import com.narnia.railways.model.Path;
import com.narnia.railways.model.Station;
import com.narnia.railways.service.StationService;
import org.javatuples.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PathServiceImpl {

    private final PathDAO pathDAO;

    private final StationService stationService;

    public PathServiceImpl(PathDAO pathDAO, StationService stationService) {
        this.pathDAO = pathDAO;
        this.stationService = stationService;
    }

    public List<Path> getAll() {
        return pathDAO.list();
    }

    public void save(Path path) {
        pathDAO.save(path);
    }

    public Path getById(Long id) {
        return pathDAO.getById(id);
    }

    public void delete(Path path) {
        pathDAO.delete(path);
    }

    public void update(Path path) {
        pathDAO.update(path);
    }

    public void reserveFreeway(Path path) {
        path.reserveFreeway();
        pathDAO.update(path);
    }

    public void freeReservation(Path path) {
        path.freeReservation();
        pathDAO.update(path);
    }

    public void printWay() {
        /**
         * Before modernization
         * From C to F
         * [2, 1, 0, 5]
         * From F to E
         * [5, 0, 1, 2, 3, 4]
         * From A to D
         * [0, 1, 2, 3]
         */
        List<Station> stations = stationService.getActiveStations();
        Graph graph = new Graph(stations);

        System.out.println("From C to F");
        graph.printAllPaths(stations.get(2), stations.get(5));

        System.out.println("From F to E");
        graph.printAllPaths(stations.get(5), stations.get(4));

        System.out.println("From A to D");
        graph.printAllPaths(stations.get(0), stations.get(3));
    }

    class Graph {

        private Map<Station, Pair<Set<Station>, Boolean>> adjList = new HashMap<>();

        public Graph(List<Station> stations) {
            stations.forEach(station -> adjList.putIfAbsent(station, Pair.with(new HashSet<>(), false)));
            initAdjList();
        }

        private void initAdjList() {
            PathServiceImpl.this.getAll()
                    .forEach(this::addEdge);
        }

        private void addEdge(Path path) {
            adjList.get(path.getF_node()).getValue0().add(path.getS_node());
            adjList.get(path.getS_node()).getValue0().add(path.getF_node());
        }

        public void printAllPaths(Station from, Station to) {
            adjList.entrySet()
                    .forEach(stationPairEntry ->
                            stationPairEntry.setValue(
                                    stationPairEntry
                                            .getValue()
                                            .setAt1(false)
                            )
                    );
            ArrayList<Station> pathList = new ArrayList<>();
            pathList.add(from);
            printAllPathsUtil(from, to, pathList);
        }

        private void printAllPathsUtil(Station from, Station to, List<Station> localPathList) {
            Pair<Set<Station>, Boolean> adj = adjList.get(from);
            adjList.put(from, adjList.get(from).setAt1(true));
            if (from.equals(to)) {
                System.out.println(localPathList.stream().map(Station::getName).collect(Collectors.joining(", ")));
                adjList.put(from, adjList.get(from).setAt1(false));
                return;
            }

            for (Station s : adjList.get(from).getValue0()) {
                if (!adjList.get(s).getValue1()) {
                    localPathList.add(s);
                    printAllPathsUtil(s, to, localPathList);
                    localPathList.remove(s);
                }
            }
            adjList.put(from, adjList.get(from).setAt1(true));
        }
    }
}
