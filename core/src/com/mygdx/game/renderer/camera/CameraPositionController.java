package com.mygdx.game.renderer.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Config;
import com.mygdx.game.actor.Actor;

public class CameraPositionController {

    public static final CameraPositionController INSTANCE = new CameraPositionController();

    private Point coord = new Point(0f,0f);
    private float zoom = 1.0f;
    private float focusedZoom = 0.2f;
    private Actor focusedOn = null;
    private Point focusedOnPoint = new Point(0,0);

    public Point getCameraposition() {
        if(focusedOn != null) {
            focusedOnPoint.update(focusedOn.getX() + focusedOn.getxOffset(), focusedOn.getY() + focusedOn.getyOffset());
            return focusedOnPoint;
        }
        return coord;
    }

    public Actor getFocusedOn() {
        return focusedOn;
    }

    public void updateCamera(OrthographicCamera camera, Viewport viewport) {
        Point p = getCameraposition();
        camera.position.x = p.x;
        camera.position.y = p.y;
        camera.zoom = focusedOn == null ? zoom : focusedZoom;
        camera.update();
        viewport.update(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, focusedOn == null ? true : false);
    }

    public void focusOn(Actor actor) {
        if(!actor.equals(focusedOn)) {
            focusedOn = actor;
        } else {
            focusedOn = null;
        }
    }

    public void removeFocus() {
        this.focusedOn = null;
    }

    public void updateZoomLevel(float level) {
        if(focusedOn == null) {
            zoom += level;
            if(zoom < 0.1f) {
                zoom = 0.1f;
            }
            if(zoom > 2) {
                zoom = 2f;
            }
        } else {
            focusedZoom += level;
            if(focusedZoom < 0.1f) {
                focusedZoom = 0.1f;
            }
            if(focusedZoom > 2 ) {
                focusedZoom = 2f;
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
            this.x = x;
            this.y = y;
        }

        void update(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
