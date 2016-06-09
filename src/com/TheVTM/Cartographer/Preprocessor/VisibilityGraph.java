package com.TheVTM.Cartographer.Preprocessor;

import com.TheVTM.Cartographer.Common;
import com.TheVTM.Cartographer.Data.*;
import com.TheVTM.Cartographer.Pathfinder;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by VTM on 1/6/2016.
 */
public class VisibilityGraph {

  public static Graph createVisibilityGraph(CollisionData collisionData, Vec2i[] corners) {
    final int MAX_EDGE_DISTANCE = 100;

    Set<Vertex> vertices = Arrays.stream(corners).map(Vertex::new).collect(Collectors.toSet());
    Set<Edge> edges = new HashSet<>();

    // Build the Visibility Graph
    // For each vertex find the other vertices that are visible (can traverse in a straight line)
    // within MAX_EDGE_DISTANCE and create an edge.
    for (Vertex vertex : vertices) {

      vertices.stream()
          .filter(otherVertex -> { // Find visible vertices
            return otherVertex != vertex // Not itself
                && !vertex.hasEdgeTo(otherVertex) // Doesn't have it already
                && vertex.position.distanceTo(otherVertex.position) < MAX_EDGE_DISTANCE // Within specified distance
                && canTraversePath(collisionData, vertex.position, otherVertex.position); // Can see
          })
          .forEach(otherVertex -> {
            // Add the new edge to the respected vertices and the edges set
            Edge edge = new Edge(vertex, otherVertex);
            vertex.addEdge(edge);
            otherVertex.addEdge(edge);
            edges.add(edge);
          });

    }

    /* Pruning */

    // Remove inferior vertices
    // For every vertex check if another vertex is a superset of it.
    List<Vertex> inferiorVerticesToRemove = new ArrayList<>();
    for (Vertex vertex : vertices) {
      // Get vertices connected to *vertex* and add itself to the Set (required for comparison)
      Set<Vertex> visibleVertices = vertex.getVisibleVertices();
      visibleVertices.add(vertex);

      visibleVertices.stream()
          .filter(otherVertex -> {
            // Get vertices connected to *otherVertex* and add itself to the Set (required for comparison)
            Set<Vertex> otherVisibleVertices = otherVertex.getVisibleVertices();
            otherVisibleVertices.add(otherVertex);

            return otherVertex != vertex &&
                visibleVertices.size() < otherVisibleVertices.size() &&
                otherVisibleVertices.containsAll(visibleVertices);
          })
          .findAny()
          .ifPresent(otherVertex -> inferiorVerticesToRemove.add(vertex));
    }

    // Remove vertex marked to be removed
    for (Vertex vertex : inferiorVerticesToRemove) {
      vertices.remove(vertex);

      // Remove Edges
      Set<Edge> vEdges = new HashSet<>(vertex.getEdges());
      for (Edge edge : vEdges) {
        edge.vertexA.removeEdge(edge);
        edge.vertexB.removeEdge(edge);
        edges.remove(edge);
      }
    }

    // Remove close edges
    // Remove edges that goes to similar directions
    final double EDGE_ANGLE_LIMIT = 25;

    List<Edge> closeEdgesToBeRemoved = new ArrayList<>();

    for (Edge edge : edges) {
      Vertex vA = edge.vertexA;
      Vertex vB = edge.vertexB;

      /* Check if *vertexA* has a close edge */
      double vAAngle = edge.getAngle(vA);

      boolean vAhasACloseEdge = vA.getEdges().stream()
          .filter(otherVAEdge -> {
            Vertex otherVAEdgeOppositeVertex = otherVAEdge.getOther(vA); // Get the other vertex in the *otherVAEdge*
            double otherVAEdgeAngle = otherVAEdge.getAngle(vA); // Get edge angle from *vA* as starting point
            double deltaAngles = 180 - Math.abs(Math.abs(vAAngle - otherVAEdgeAngle) - 180); // Calculate the difference between angles

            return edge != otherVAEdge // Not itself
                && deltaAngles < EDGE_ANGLE_LIMIT // Within angle limit
                && edge.length > otherVAEdge.length // *edge* is longer than *otherEdge*
                && otherVAEdgeOppositeVertex.hasEdgeTo(vB); // *otherVAEdge* can see *vB*
          })
          .findAny().isPresent();

      if (!vAhasACloseEdge) continue; // If *vA* doesn't have a close edge skip


      /* Check if *vertexB* has a close edge */
      double vBAngle = edge.getAngle(vB);

      boolean vBhasACloseEdge = vB.getEdges().stream()
          .filter(otherVBEdge -> {
            Vertex otherVBEdgeOppositeVertex = otherVBEdge.getOther(vB); // Get the other vertex in the *otherVBEdge*
            double otherVBEdgeAngle = otherVBEdge.getAngle(vB); // Get edge angle from *vB* as starting point
            double deltaAngles = 180 - Math.abs(Math.abs(vBAngle - otherVBEdgeAngle) - 180); // Calculate the difference between angles

            return edge != otherVBEdge // Not itself
                && deltaAngles < EDGE_ANGLE_LIMIT // Within angle limit
                && edge.length > otherVBEdge.length // *edge* is longer than *otherEdge*
                && otherVBEdgeOppositeVertex.hasEdgeTo(vB); // *otherVBEdge* can see *vB*
          })
          .findAny().isPresent();

      if (!vBhasACloseEdge) continue; // If *vB* doesn't have a close edge skip

      // Has a closer edge, so add *edge* to be removed
      closeEdgesToBeRemoved.add(edge);
    }

    // Remove close edges marked to be removed
    for (Edge edge : closeEdgesToBeRemoved) {
      edge.vertexA.removeEdge(edge);
      edge.vertexB.removeEdge(edge);
      edges.remove(edge);
    }


    return new Graph(vertices.toArray(new Vertex[vertices.size()]), edges.toArray(new Edge[edges.size()]));
  }

  private static Map<Pair<CollisionData, Pair<Vec2i, Vec2i>>, Boolean> canTraversePathCache = new HashMap<>();

  public static boolean canTraversePath(CollisionData collisionData, Vec2i a, Vec2i b) {
    Vec2i[] vec2is = Vec2i.orderVertex(a, b);
    Pair<CollisionData, Pair<Vec2i, Vec2i>> ce = new Pair<>(collisionData, new Pair<>(vec2is[0], vec2is[1]));


    return canTraversePathCache.computeIfAbsent(ce, p -> {
      Pair<Vec2i, Vec2i> vs = p.getValue();
      Vec2i[] path = Common.GenerateBresenhamLine(vs.getKey(), vs.getValue());

      return canTraversePath(p.getKey(), path);
    });
  }

  public static boolean canTraversePath(CollisionData collisionData, Vec2i[] path) {

    for (int i = 0; i < (path.length - 1); i++) {
      Vec2i v = path[i];
      Vec2i vNext = path[i + 1];
      final int direction = getDirection(v, vNext);

      if (!Pathfinder.canTraverse(collisionData, v, direction)) {
        return false;
      }
    }

    return true;
  }

  public static int getDirection(Vec2i origin, Vec2i target) {
    final int deltaX = target.x - origin.x;
    final int deltaY = target.y - origin.y;

    if (Math.abs(deltaX) > 1 || Math.abs(deltaY) > 1) {
      throw new RuntimeException("Distance between origin and target must not be greater than 1.");
    }

    final int delta = deltaX + deltaY * 10;

    //    .SW ..S .SE
    // SW -11 -10 -09 .SE
    // .W -01 000 +01 ..E
    // NW +09 +10 +11 .NE
    //    .NW ..N .NE
    switch (delta) {
      case 0:
        // origin and target are equals
        throw new RuntimeException("Origin and target cannot be equal.");

      case -11:
        return CollisionData.SOUTH_WEST;

      case -10:
        return CollisionData.SOUTH;

      case -9:
        return CollisionData.SOUTH_EAST;

      case -1:
        return CollisionData.WEST;

      case +1:
        return CollisionData.EAST;

      case +9:
        return CollisionData.NORTH_WEST;

      case +10:
        return CollisionData.NORTH;

      case +11:
        return CollisionData.NORTH_EAST;
    }

    throw new RuntimeException(String.format("Unknow delta %d.", delta));
  }
}
