package com.TheVTM.Cartographer.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by VTM on 1/6/2016.
 */
public class Vertex {
    public final Vec2i position;
    private Set<Edge> edges = new HashSet<>();

    public Vertex(Vec2i position) {
        this.position = position;
    }

    public final Set<Edge> getEdges() {
        return edges;
    }

    public boolean addEdge(Edge edge) {
        return edges.add(edge);
    }

    public boolean removeEdge(Edge edge) {
        return edges.remove(edge);
    }

    public boolean hasEdgeTo(Vertex otherVertex) {
        return edges.stream().anyMatch(edge -> edge.vertexA == otherVertex || edge.vertexB == otherVertex);
    }

    public Set<Vertex> getVisibleVertices() {
        return edges.stream()
                .map(edge -> (edge.vertexA == this) ? edge.vertexB : edge.vertexA)
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "Vertex{" + "position=" + position + ", edges=" + edges.toString() + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        return position.equals(vertex.position);
    }

    @Override
    public int hashCode() {
        int result = position.hashCode();
//        result = 31 * result + Arrays.hashCode(edges);
        return result;
    }
}
