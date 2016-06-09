package com.TheVTM.Cartographer.Data;

import java.util.Arrays;

/**
 * Created by VTM on 1/6/2016.
 */
public class Graph {
    public final Vertex[] vertices;
    public final Edge[] edges;

    public Graph(Vertex[] vertices, Edge[] edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "vertices=" + Arrays.toString(vertices) +
                ", edges=" + Arrays.toString(edges) +
                '}';
    }
}
