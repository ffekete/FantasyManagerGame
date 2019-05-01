package com.mygdx.game.renderer.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.actor.Actor;

public class CameraPositionController {

    public static final CameraPositionController INSTANCE = new CameraPositionController();

    private Point coord = new Point(0f,0f);
    private float zoom = 1.0f;
    private Actor focusedOn = null;
    private Point focusedOnPoint = new Point(0,0);

    public Point getCameraposition() {
        if(focusedOn != null) {
            focusedOnPoint.update(focusedOn.getX() + focusedOn.getxOffset(), focusedOn.getY() + focusedOn.getyOffset());
            return focusedOnPoint;
        }
        return coord;
    }

    public void updateCamera(OrthographicCamera camera) {
        Point p = getCameraposition();
        camera.position.x = p.x;
        camera.position.y = p.y;
        camera.zoom = zoom;
        camera.update();
    }

    public void focusOn(Actor actor) {
        if(!actor.equals(focusedOn)) {
            focusedOn = actor;
            zoom = 0.5f;
        } else {
            zoom = 1;
            focusedOn = null;
        }
    }

    public void removeFocus() {
        this.focusedOn = null;
    }

    private CameraPositionController() {
    }

    class Point {
        float x;
        float y;

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
