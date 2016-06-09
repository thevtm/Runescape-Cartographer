package com.TheVTM.Cartographer.Data;

import com.TheVTM.Cartographer.Constants;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.Region;
import com.runemate.game.api.script.data.GameType;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;

/**
 * Created by VTM on 18/4/2016.
 */
public class CollisionData {

    // Constants
    public static final int NORTH = Region.CollisionFlags.NORTH_BOUNDARY_OBJECT;
    public static final int SOUTH = Region.CollisionFlags.SOUTH_BOUNDARY_OBJECT;
    public static final int WEST = Region.CollisionFlags.WEST_BOUNDARY_OBJECT;
    public static final int EAST = Region.CollisionFlags.EAST_BOUNDARY_OBJECT;
    public static final int NORTH_WEST = Region.CollisionFlags.NORTH_WEST_BOUNDARY_OBJECT;
    public static final int NORTH_EAST = Region.CollisionFlags.NORTH_EAST_BOUNDARY_OBJECT;
    public static final int SOUTH_WEST = Region.CollisionFlags.SOUTH_WEST_BOUNDARY_OBJECT;
    public static final int SOUTH_EAST = Region.CollisionFlags.SOUTH_EAST_BOUNDARY_OBJECT;

    /* FIELDS */
    public final GameType gameType;
    public final Coordinate base;
    public final int collisionFlags[][];
    public final int width;
    public final int height;

    /* LOGGER */
    private static final Logger LOGGER = Logger.getLogger(CollisionData.class.getName());

    /* FUNCTIONS */

    public static CollisionData fromFile(File file) throws IOException {
        String filename = file.getName();

        LOGGER.log(Level.INFO, String.format("Creating CollisionData from file %s...", file.getAbsolutePath()));

        // Parse filename info
        Matcher filenameMatcher = Constants.FILENAME_PATTERN.matcher(filename);
        filenameMatcher.matches();
        
        GameType gameType = GameType.valueOf(filenameMatcher.group(1));

        // Region base coordinate
        int x = Integer.parseInt(filenameMatcher.group(2));
        int y = Integer.parseInt(filenameMatcher.group(3));
        int z = Integer.parseInt(filenameMatcher.group(4));
        Coordinate base = new Coordinate(x, y, z);


        // Read image
        Image image = new Image(new FileInputStream(file));
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        int collisionFlags[][] = new int[width][height];
        PixelReader prImage = image.getPixelReader();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                collisionFlags[i][j] = prImage.getArgb(i, j) & 0x00FFFFFF; // Remove alpha
            }
        }

        return new CollisionData(gameType, base, collisionFlags, width, height);
    }

    public CollisionData(GameType gametype, Coordinate base, int[][] collisionFlags, int width, int height) {
        this.gameType = gametype;
        this.base = base;
        this.collisionFlags = collisionFlags;
        this.width = width;
        this.height = height;
    }

    public static List<CollisionData> fromDirectory(String gametype, Integer plane, Path inputDirectory) throws IOException {
        List<CollisionData> collisionData = new ArrayList<>();

        LOGGER.log(Level.INFO, String.format("Loading collision data from directory \"%s\"...", inputDirectory.toString()));

        // Open directory
        File directory = new File(inputDirectory.toUri());

        // Filter valid collision data files
        // and process the collision data
        for (File file : directory.listFiles(createFilenameFilter(gametype, plane))) {
            collisionData.add(fromFile(file));
        }

        return collisionData;
    }

    private static FilenameFilter createFilenameFilter(String gametype, Integer plane) {
        return (dir, name) -> {
            Matcher matcher = Constants.FILENAME_PATTERN.matcher(name);

            return matcher.matches()
                    && gametype.equals(matcher.group(1))
                    && plane == Integer.parseInt(matcher.group(4));
        };
    }

    public static boolean isBlocked(int v) {
        final int BLOCKED_FLAG = Region.CollisionFlags.PRIMARY_OBJECT
                | Region.CollisionFlags.IMPASSIBLE_PRIMARY_OBJECT
                | Region.CollisionFlags.FLOOR_OBJECT
                | Region.CollisionFlags.CLIPPED;

        return (v & BLOCKED_FLAG) != 0;
    }

    public static boolean isBlocked(int v, int d) {
        return (v & d) != 0;
    }
}
