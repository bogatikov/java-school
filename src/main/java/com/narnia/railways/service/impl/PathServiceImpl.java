package com.narnia.railways.service.impl;

import com.narnia.railways.dao.PathDAO;
import com.narnia.railways.model.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PathServiceImpl {

    private final PathDAO pathDAO;

    private StationServiceImpl stationService;

    public PathServiceImpl(PathDAO pathDAO) {
        this.pathDAO = pathDAO;
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
        Graph graph = new Graph(6);
        getAll().stream()
                .forEach(path -> {
                    graph.addEdge(path.getF_node().getId().intValue() - 1, path.getS_node().getId().intValue() - 1);
                    graph.addEdge(path.getS_node().getId().intValue() - 1, path.getF_node().getId().intValue() - 1);
                });

        System.out.println("From C to F");
        graph.printAllPaths(2, 5);

        System.out.println("From F to E");
        graph.printAllPaths(5, 4);

        System.out.println("From A to D");
        graph.printAllPaths(0, 3);
    }

    @Autowired
    public void setStationService(StationServiceImpl stationService) {
        this.stationService = stationService;
    }

    class Graph {
        private int v;

        private ArrayList<Integer>[] adjList;

        public Graph(int vertices) {
            this.v = vertices;

            initAdjList();
        }

        private void initAdjList() {
            adjList = new ArrayList[v];
            for (int i = 0; i < v; i++) {
                adjList[i] = new ArrayList<>();
            }
        }

        public void addEdge(int u, int v) {
            adjList[u].add(v);
        }

        public void printAllPaths(int s, int d) {
            boolean[] isVisited = new boolean[v];
            ArrayList<Integer> pathList = new ArrayList<>();
            pathList.add(s);
            printAllPathsUtil(s, d, isVisited, pathList);
        }

        private void printAllPathsUtil(Integer u, Integer d, boolean[] isVisited, List<Integer> localPathList) {
            isVisited[u] = true;
            if (u.equals(d)) {
                System.out.println(localPathList);
                isVisited[u] = false;
                return;
            }

            for (Integer i : adjList[u]) {
                if (!isVisited[i]) {
                    localPathList.add(i);
                    printAllPathsUtil(i, d, isVisited, localPathList);
                    localPathList.remove(i);
                }
            }
            isVisited[u] = true;
        }
    }
}
