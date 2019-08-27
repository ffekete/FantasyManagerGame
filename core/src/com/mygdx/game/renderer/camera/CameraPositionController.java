package com.mygdx.game.renderer.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;
import com.mygdx.game.logic.Point;
import com.mygdx.game.map.Map2D;
import com.mygdx.game.registry.MapRegistry;
import com.mygdx.game.registry.RendererToolsRegistry;

import java.util.HashMap;
import java.util.Map;

public class CameraPositionController {

    public static final CameraPositionController INSTANCE = new CameraPositionController();

    private Map<Map2D, Point> coord = new HashMap<>();
    private float zoom = 0.5f;
    private float focusedZoom = 0.5f;
    private Actor focusedOn = null;
    private Point focusedOnPoint = new Point(0, 0);

    public float getZoom() {
        return focusedOn == null ? zoom : focusedZoom;
    }

    public void update() {
        coord.computeIfAbsent(MapRegistry.INSTANCE.getCurrentMapToShow(), value -> new Point(0, 0));
        Point newCoord = new Point(0, 0);
        newCoord.x = coord.get(MapRegistry.INSTANCE.getCurrentMapToShow()).x;
        newCoord.y = coord.get(MapRegistry.INSTANCE.getCurrentMapToShow()).y;
    }

    public void offset(float x, float y) {
        coord.computeIfAbsent(MapRegistry.INSTANCE.getCurrentMapToShow(), value -> new Point(0, 0));
        coord.get(MapRegistry.INSTANCE.getCurrentMapToShow()).x += x;
        coord.get(MapRegistry.INSTANCE.getCurrentMapToShow()).y += y;
    }

    public Point getCameraposition() {
        if (focusedOn != null) {
            focusedOnPoint.update(focusedOn.getX() + focusedOn.getxOffset(), focusedOn.getY() + focusedOn.getyOffset());
            return focusedOnPoint;
        }
        coord.computeIfAbsent(MapRegistry.INSTANCE.getCurrentMapToShow(), value -> new Point(0, 0));
        return coord.get(MapRegistry.INSTANCE.getCurrentMapToShow());
    }

    public Actor getFocusedOn() {
        return focusedOn;
    }

    public void updateCamera(OrthographicCamera camera) {
        Point p = getCameraposition();
        camera.position.x = p.x;
        camera.position.y = p.y;
        camera.zoom = focusedOn == null ? zoom : focusedZoom;
        camera.update();
    }

    public void focusOn(Actor actor) {
        if (!actor.equals(focusedOn)) {
            focusedOn = actor;
        } else {
            coord.computeIfAbsent(MapRegistry.INSTANCE.getCurrentMapToShow(), value -> new Point(focusedOn.getX(), focusedOn.getY()));
            coord.put(MapRegistry.INSTANCE.getCurrentMapToShow(), new Point(focusedOn.getX(), focusedOn.getY()));
            focusedOn = null;
        }
    }

    public void removeFocus() {
        this.focusedOn = null;
    }

    public boolean isFocusedOn() {
        return focusedOn != null;
    }

    public void updateZoomLevel(float level) {
        if (focusedOn == null) {
            zoom += level;
            if (zoom < 0.1f) {
                zoom = 0.1f;
            }
            if (zoom > Config.Engine.ZOOM_MAX) {
                zoom = Config.Engine.ZOOM_MAX;
            }
        } else {
            focusedZoom += level;
            if (focusedZoom < 0.1f) {
                focusedZoom = 0.1f;
            }
            if (focusedZoom > 1) {
                focusedZoom = 1f;
            }
        }
    }

    private CameraPositionController() {
    }

    public class Point {
        float x;
        float y;

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public Point(float x, float y) {
            this.x = Math.round(x);
            this.y = Math.round(y);
        }

        void update(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
