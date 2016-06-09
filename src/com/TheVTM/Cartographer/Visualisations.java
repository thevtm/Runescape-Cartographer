package com.TheVTM.Cartographer;

import com.TheVTM.Cartographer.Data.*;
import com.runemate.game.api.hybrid.region.Region;
import com.sun.javafx.geom.Vec2d;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * Created by VTM on 20/5/2016.
 */
public class Visualisations {

    public static Image CreateCornersMask(List<Vec2d> corners, int width, int height) {
        WritableImage image = new WritableImage(width, height);
        PixelWriter pw = image.getPixelWriter();

        for (Vec2d corner : corners) {
            pw.setColor((int) corner.x, (int) corner.y, Color.WHITE);
        }

        return image;
    }

    public static Image drawMap5(CollisionData collisionData) {
        final int TILE_SIZE = 5;
        final int TILE_SOLID_RECT_COLOR = 0xFFFFFFFF;
        final int TILE_WALKABLE_COLOR = 0xFFFFFFFF;

        WritableImage image = new WritableImage(collisionData.width * TILE_SIZE, collisionData.height * TILE_SIZE);
        PixelWriter pw = image.getPixelWriter();

        for (int x = 0; x < collisionData.width; x++) {
            for (int y = 0; y < collisionData.height; y++) {
                final int xPos = x * TILE_SIZE;
                final int yPos = y * TILE_SIZE;
                int cf = collisionData.collisionFlags[x][y] + 0xFF000000;
                Vec2i NWCorner = new Vec2i(xPos                , yPos + TILE_SIZE - 1);
                Vec2i NECorner = new Vec2i(xPos + TILE_SIZE - 1, yPos + TILE_SIZE - 1);
                Vec2i SWCorner = new Vec2i(xPos                , yPos                );
                Vec2i SECorner = new Vec2i(xPos + TILE_SIZE - 1, yPos                );


                // Draw tile background
                for (int i = 0; i < TILE_SIZE; i++) {
                    for (int j = 0; j < TILE_SIZE; j++) {
                        pw.setArgb(xPos + i, yPos + j, cf);
                    }
                }

                /* Solid */
                if (CollisionData.isBlocked(cf)) {
                    DrawBresenhamLine(pw, NWCorner, NECorner, TILE_SOLID_RECT_COLOR);
                    DrawBresenhamLine(pw, NWCorner, SWCorner, TILE_SOLID_RECT_COLOR);
                    DrawBresenhamLine(pw, NECorner, SECorner, TILE_SOLID_RECT_COLOR);
                    DrawBresenhamLine(pw, SECorner, SWCorner, TILE_SOLID_RECT_COLOR);
                }
                else
                {
                    /* Walkable sides */
                    Vec2i centerPos = new Vec2i(xPos + (TILE_SIZE / 2), yPos + (TILE_SIZE / 2));

                    // North
                    if (CollisionData.isBlocked(cf, Region.CollisionFlags.NORTH_BOUNDARY_OBJECT)) {
                        pw.setArgb(centerPos.x, yPos + TILE_SIZE - 1, TILE_WALKABLE_COLOR);
                    }

                    // South
                    if (CollisionData.isBlocked(cf, Region.CollisionFlags.SOUTH_BOUNDARY_OBJECT)) {
                        pw.setArgb(centerPos.x, yPos, TILE_WALKABLE_COLOR);
                    }

                    // West
                    if (CollisionData.isBlocked(cf, Region.CollisionFlags.WEST_BOUNDARY_OBJECT)) {
                        pw.setArgb(xPos, centerPos.y, TILE_WALKABLE_COLOR);
                    }

                    // East
                    if (CollisionData.isBlocked(cf, Region.CollisionFlags.EAST_BOUNDARY_OBJECT)) {
                        pw.setArgb(xPos + TILE_SIZE - 1, centerPos.y, TILE_WALKABLE_COLOR);
                    }

                    // North West
                    if (CollisionData.isBlocked(cf, Region.CollisionFlags.NORTH_WEST_BOUNDARY_OBJECT)) {
                        pw.setArgb(NWCorner.x, NWCorner.y, TILE_WALKABLE_COLOR);
                    }

                    // North East
                    if (CollisionData.isBlocked(cf, Region.CollisionFlags.NORTH_EAST_BOUNDARY_OBJECT)) {
                        pw.setArgb(NECorner.x, NECorner.y, TILE_WALKABLE_COLOR);
                    }

                    // South West
                    if (CollisionData.isBlocked(cf, Region.CollisionFlags.SOUTH_WEST_BOUNDARY_OBJECT)) {
                        pw.setArgb(SWCorner.x, SWCorner.y, TILE_WALKABLE_COLOR);
                    }

                    // South East
                    if (CollisionData.isBlocked(cf, Region.CollisionFlags.SOUTH_EAST_BOUNDARY_OBJECT)) {
                        pw.setArgb(SECorner.x, SECorner.y, TILE_WALKABLE_COLOR);
                    }
                }

            }
        }

        return image;
    }

    public static Image drawCornersMap5(CollisionData collisionData, Vec2i[] corners) {
        final int TILE_SIZE = 5;
        final int TILE_CORNER_COLOR = 0xFFFFFFFF;

        Image map5 = drawMap5(collisionData);
        WritableImage image = new WritableImage(map5.getPixelReader(), (int) map5.getWidth(), (int) map5.getHeight());
        PixelWriter pw = image.getPixelWriter();

        // Corners
        if (corners != null) {
            for (Vec2i corner : corners) {
                int x = corner.x * TILE_SIZE;
                int y = corner.y * TILE_SIZE;

                int cf = collisionData.collisionFlags[corner.x][corner.y] + 0xFF000000;

                Vec2i centerPos = new Vec2i(x + (TILE_SIZE / 2), y + (TILE_SIZE / 2));

                pw.setArgb(centerPos.x, centerPos.y, TILE_CORNER_COLOR);
            }
        }

        return image;
    }

    public static Image drawGraphMap5(CollisionData collisionData, Graph graph) {
        final int TILE_SIZE = 5;
        final int TILE_VERTEX_COLOR = 0xFFFF0000;
        final int TILE_EDGE_COLOR = 0xFF00FF00;

        Image map5 = drawMap5(collisionData);
        WritableImage image = new WritableImage(map5.getPixelReader(), (int) map5.getWidth(), (int) map5.getHeight());
        PixelWriter pw = image.getPixelWriter();

        /* Draw Edges */
        for(Edge edge : graph.edges) {
            Vec2i vA = new Vec2i(edge.vertexA.position.x * TILE_SIZE + (TILE_SIZE / 2),
                                 edge.vertexA.position.y * TILE_SIZE + (TILE_SIZE / 2));

            Vec2i vB = new Vec2i(edge.vertexB.position.x * TILE_SIZE + (TILE_SIZE / 2),
                                 edge.vertexB.position.y * TILE_SIZE + (TILE_SIZE / 2));

            DrawBresenhamLine(pw, vA, vB, TILE_EDGE_COLOR);
        }

        /* Draw Vertices */
        for(Vertex vertex : graph.vertices) {
            Vec2i v = new Vec2i(vertex.position.x * TILE_SIZE + (TILE_SIZE / 2),
                                vertex.position.y * TILE_SIZE + (TILE_SIZE / 2));

            pw.setArgb(v.x, v.y, TILE_VERTEX_COLOR);
        }

        return image;
    }

    private static void DrawBresenhamLine(PixelWriter pw, Vec2i a, Vec2i b, int color) {
        Vec2i[] linePoints = Common.GenerateBresenhamLine(a, b);

        for(Vec2i point : linePoints) {
            try {
                pw.setArgb(point.x, point.y, color);
            } catch (Exception e) {
//                System.out.println(String.format("a: %s, b: %s = %s", a.toString(), b.toString(), point.toString()));
            }
        }
    }
}
