package com.TheVTM.Cartographer.Data;

/**
 * Created by VTM on 1/6/2016.
 */
public class Edge {
  public final Vertex vertexA, vertexB;
  public final double length;
  private final double angle;

  public Edge(Vertex vertexA, Vertex vertexB) {
    Vertex[] vertices = orderVertex(vertexA, vertexB);
    this.vertexA = vertices[0];
    this.vertexB = vertices[1];

    length = Vec2i.distance(vertexA.position, vertexB.position);
    angle = calculateAngle(this);
  }

  public static Vertex[] orderVertex(Vertex a, Vertex b) {
    boolean aXisSmaller = a.position.x < b.position.x;
    boolean aXisEquals = a.position.x == b.position.x;
    boolean aYisSmaller = a.position.y < b.position.y;

    if (aXisEquals) {
      if (aYisSmaller) {
        return new Vertex[]{a, b};
      } else {
        return new Vertex[]{b, a};
      }

    } else {
      if (aXisSmaller) {
        return new Vertex[]{a, b};
      } else {
        return new Vertex[]{b, a};
      }
    }
  }

  private static Double calculateAngle(Edge edge) {
    double deltaX = edge.vertexA.position.x - edge.vertexB.position.x;
    double deltaY = edge.vertexA.position.y - edge.vertexB.position.y;

    return Math.atan2(deltaY, deltaX) * (180 / Math.PI);
  }

  public Vertex getOther(Vertex vertex) {
    if (vertex == vertexA) {
      return vertexB;
    } else if (vertex == vertexB) {
      return vertexA;
    } else {
      throw new RuntimeException("vertex must be a member of the Edge.");
    }
  }

  public double getAngle(Vertex fromVertex) {
    if (fromVertex == vertexA) {
      return angle;
    } else if (fromVertex == vertexB) {
      return 180 - Math.abs(Math.abs(angle - 180) - 180);
    } else {
      throw new RuntimeException("vertex must be a member of the Edge.");
    }
  }

  @Override
  public String toString() {
    return "Edge{" + "vertexA=" + vertexA.position + ", vertexB=" + vertexB.position + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Edge edge = (Edge) o;

    if (!vertexA.equals(edge.vertexA)) return false;
    return vertexB.equals(edge.vertexB);

  }

  @Override
  public int hashCode() {
    int result = vertexA.hashCode();
    result = 31 * result + vertexB.hashCode();
    return result;
  }
}
