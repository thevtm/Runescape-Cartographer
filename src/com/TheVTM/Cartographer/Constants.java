package com.TheVTM.Cartographer;

import java.util.regex.Pattern;

/**
 * Created by VTM on 27/4/2016.
 */
public class Constants {
    /* FILE PATTERNS */
    public static final Pattern GAMETYPE_PATTERN = Pattern.compile("RS3|OSRS");
    public static final Pattern FILENAME_PATTERN = Pattern.compile("(RS3|OSRS) (\\d+) (\\d+) (\\d+).png");
}
