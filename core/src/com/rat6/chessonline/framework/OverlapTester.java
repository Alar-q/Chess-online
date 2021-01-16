package com.rat6.chessonline.framework;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class OverlapTester {
    public static boolean pointInRectangle(Rectangle r, Vector2 p) {
        return r.x <= p.x && r.x + r.width >= p.x &&
                r.y <= p.y && r.y + r.height >= p.y;
    }
    public static boolean pointInRectangle(Rectangle r, float x, float y) {
        return pointInRectangle(r, new Vector2(x, y));
    }
    public static boolean pointInRectangle(Rectangle r, Vector3 v) {
        return pointInRectangle(r, new Vector2(v.x, v.y));
    }
}
