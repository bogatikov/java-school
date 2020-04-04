package com.narnia.railways.service.impl;

import com.narnia.railways.dao.PathDAO;
import com.narnia.railways.model.Path;
import com.narnia.railways.model.Station;
import com.narnia.railways.model.Train;
import com.narnia.railways.service.PathService;
import org.javatuples.Pair;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PathServiceImpl implements PathService {

    private final PathDAO pathDAO;

    public PathServiceImpl(PathDAO pathDAO) {
        this.pathDAO = pathDAO;
    }

    @Override
    public List<Path> getAll() {
        return pathDAO.list();
    }

    @Override
    public List<Path> getActivePaths() {
        return pathDAO.getActivePath();
    }

    @Override
    public void save(Path path) {
        pathDAO.save(path);
    }

    @Override
    public Path getById(Long id) {
        return pathDAO.getById(id);
    }

    @Override
    public void delete(Path path) {
        pathDAO.delete(path);
    }

    @Override
    public void update(Path path) {
        pathDAO.update(path);
    }

    @Override
    public Optional<Path> getPathBetweenStation(Station from, Station to) {
        return Optional.of(pathDAO.getPathByStations(from, to));
    }

    @Override
    public void reserveFreeway(Path path) {
        path.reserveFreeway();
        pathDAO.update(path);
    }

    @Override
    public void freeReservation(Path path) {
        path.freeReservation();
        pathDAO.update(path);
    }

    /**
     * If train have no track yet it means all paths available
     * otherwise only paths which can be reached from start and ends paths
     *
     * @param train
     * @return
     */
    @Override
    public List<Path> getAvailablePathsForTrain(Train train) {
        if (train.getTrack().size() == 0)
            return getAll();

        List<Path> track = train.getTrack();

        List<Path> pathsFromStartStation = pathDAO.getPathsByStation(track.get(0).getF_node());
        List<Path> pathsFromEndStation = pathDAO.getPathsByStation(track.get(track.size() - 1).getS_node());
        Set<Path> paths = new HashSet<>();
        paths.addAll(pathsFromStartStation);
        paths.addAll(pathsFromEndStation);
        paths.remove(track.get(0));
        paths.remove(track.get(track.size() - 1));
        return new ArrayList<>(paths);
    }

    @Override
    public List<List<Station>> findWayBetweenStations(Station from, Station to) {
        Graph graph = new Graph();

        return graph.printAllPaths(from, to);
    }

    private class Graph {

        private Map<Station, Pair<Set<Station>, Boolean>> adjList = new HashMap<>();

        public Graph() {
            PathServiceImpl.this.getActivePaths()
                    .forEach(path -> {
                        adjList.putIfAbsent(path.getF_node(), Pair.with(new HashSet<>(), false));
                        adjList.putIfAbsent(path.getS_node(), Pair.with(new HashSet<>(), false));
                        addEdge(path);
                    });
        }

        private void addEdge(Path path) {
            adjList.get(path.getF_node()).getValue0().add(path.getS_node());
            adjList.get(path.getS_node()).getValue0().add(path.getF_node());
        }

        public List<List<Station>> printAllPaths(Station from, Station to) {
            adjList.entrySet()
                    .forEach(stationPairEntry ->
                            stationPairEntry.setValue(
                                    stationPairEntry
                                            .getValue()
                                            .setAt1(false)
                            )
                    );

            List<List<Station>> tracks = new ArrayList<>();

            ArrayList<Station> pathList = new ArrayList<>();
            pathList.add(from);

            printAllPathsUtil(from, to, pathList, tracks);
            return tracks;
        }

        private void printAllPathsUtil(Station from, Station to, List<Station> localPathList, List<List<Station>> tracks) {

            adjList.put(from, adjList.get(from).setAt1(true));
            if (from.equals(to)) {
                System.out.println(localPathList.stream().map(Station::getName).collect(Collectors.joining(", ")));
                tracks.add(new ArrayList<>(localPathList));
                adjList.put(from, adjList.get(from).setAt1(false));
                return;
            }

            for (Station s : adjList.get(from).getValue0()) {
                if (!adjList.get(s).getValue1()) {
                    localPathList.add(s);
                    printAllPathsUtil(s, to, localPathList, tracks);
                    localPathList.remove(s);
                }
            }
            adjList.put(from, adjList.get(from).setAt1(false));
        }
    }
}
