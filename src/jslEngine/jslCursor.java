package jslEngine;

import java.awt.*;

public class jslCursor {

    public static boolean cursorChanged = false;
    public static Cursor cursor;

    public static final Cursor HAND = new Cursor(Cursor.HAND_CURSOR);
    public static final Cursor DEFAULT = new Cursor(Cursor.DEFAULT_CURSOR);
    public static final Cursor MOVE = new Cursor(Cursor.MOVE_CURSOR);

    public static void setCursor(Cursor c) {
        cursor = c;
        cursorChanged = true;
    }

    public static void reset() { cursorChanged = false; }
}
