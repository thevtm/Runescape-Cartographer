package com.TheVTM.Cartographer;

import com.TheVTM.Cartographer.Data.Vec2i;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by VTM on 18/4/2016.
 */
public class Common {

    public static void blit(PixelWriter target, PixelReader source, Integer offsetX, Integer offsetY,
                            Integer srcBeginX, Integer srcBeginY, Integer srcEndX, Integer srcEndY) {
        for (Integer x = srcBeginX; x < srcEndX; x++)
            for (Integer y = srcBeginY; y < srcEndY; y++)
                target.setColor(x + offsetX, y + offsetY, source.getColor(x, y));
    }

    public static WritableImage createBlankImage(Integer width, Integer height) {
        return new WritableImage(width, height);
    }

    public static boolean saveImage(Image image, Path filepath) throws IOException {
        // Resolve file path
        File outFile = new File(filepath.toUri());

        // Convert javafx Image to BufferedImage
        BufferedImage bi = SwingFXUtils.fromFXImage(image, null);

        // Save file as a png
        return ImageIO.write(bi, "png", outFile);
    }

    public static Vec2i[] GenerateBresenhamLine(Vec2i a, Vec2i b) {
        // Bresenham's algorithm
        ArrayList<Vec2i> points = new ArrayList<>();

        int x1 = a.x;
        int y1 = a.y;
        int x2 = b.x;
        int y2 = b.y;

        // delta of exact value and rounded value of the dependant variable
        int d = 0;

        int dy = Math.abs(y2 - y1);
        int dx = Math.abs(x2 - x1);

        int dy2 = (dy << 1); // slope scaling factors to avoid floating
        int dx2 = (dx << 1); // point

        int ix = x1 < x2 ? 1 : -1; // increment direction
        int iy = y1 < y2 ? 1 : -1;

        if (dy <= dx) {
            for (;;) {
                points.add(new Vec2i(x1, y1));
                if (x1 == x2)
                    break;
                x1 += ix;
                d += dy2;
                if (d > dx) {
                    y1 += iy;
                    d -= dx2;
                }
            }
        } else {
            for (;;) {
                points.add(new Vec2i(x1, y1));
                if (y1 == y2)
                    break;
                y1 += iy;
                d += dx2;
                if (d > dy) {
                    x1 += ix;
                    d -= dy2;
                }
            }
        }

        return points.toArray(new Vec2i[points.size()]);
    }
}
