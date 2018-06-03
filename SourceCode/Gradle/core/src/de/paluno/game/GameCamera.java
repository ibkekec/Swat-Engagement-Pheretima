package de.paluno.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import de.paluno.game.gameobjects.PhysicsObject;

public class GameCamera {

    // normal camera used for drawing sprites, etc., metrics given in pixels
    private OrthographicCamera camera;
    // debug camera used for drawing hitboxes, metrics given in meters
    private OrthographicCamera debugCamera;
    private PhysicsObject cameraFocus;

    private float viewportWidth;
    private float viewportHeight;

    private Vector2 debugPosition;
    private Vector2 position;

    private int horizontalMovement;
    private int verticalMovement;

    // in world space (in meters)
    private float bottomLimit;

    private Vector2 focusDirectionVector = new Vector2();

    /**
     *
     * @param viewportWidth in pixels
     * @param viewportHeight in pixels
     */
    public GameCamera(float viewportWidth, float viewportHeight) {
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;

        // initialize the cameras according to the window size
        camera = new OrthographicCamera();
        camera.setToOrtho(false, viewportWidth, viewportHeight);

        debugCamera = new OrthographicCamera();
        debugCamera.setToOrtho(false, viewportWidth * Constants.WORLD_SCALE,
                viewportHeight * Constants.WORLD_SCALE);

        debugPosition = new Vector2();
        position = new Vector2();
    }

    // set the object the camera will follow, or null to follow nothing
    public void setCameraFocus(PhysicsObject object) {
        cameraFocus = object;
    }

    public PhysicsObject getCameraFocus() {
        return cameraFocus;
    }

    /**
     * sets the lower vertical movement limit of the camera
     * @param bottom the bottom limit in world space
     */
    public void setBottomLimit(float bottom) {
        // the position of the camera refers to the center of the screen, so add that offset to the limit
        bottomLimit = bottom + viewportHeight / 2.0f * Constants.WORLD_SCALE;
    }

    public void setHorizontalMovement(int movement) {
        horizontalMovement = movement;
    }

    public int getHorizontalMovement() {
        return horizontalMovement;
    }

    public void setVerticalMovement(int movement) {
        verticalMovement = movement;
    }

    public int getVerticalMovement() {
        return verticalMovement;
    }

    public void update(float delta) {
        if (cameraFocus != null) {
            // smooth following the object
            focusDirectionVector.set(cameraFocus.getBody().getPosition()).add(-debugPosition.x, -debugPosition.y);
            focusDirectionVector.nor();
            float distance = cameraFocus.getBody().getPosition().dst(debugPosition);
            focusDirectionVector.scl(distance / Constants.CAMERA_FOCUS_TIME * delta);
            debugPosition.add(focusDirectionVector);

            // if we have a camera focus then we center the camera on the given object
            //debugPosition.set(cameraFocus.getBody().getPosition());
        }

        // move the camera according to user input
        debugPosition.add(horizontalMovement * Constants.CAMERA_MOVE_VELOCITY * delta,
                verticalMovement * Constants.CAMERA_MOVE_VELOCITY * delta);

        // limit the vertical camera position so it does not go under the bottom limit
        debugPosition.y = Math.max(debugPosition.y, bottomLimit);

        // since we calculated the camera position in meters for debug drawing, convert that to pixels for normal drawing
        position.set(Constants.getScreenSpaceVector(debugPosition));

        // apply the new camera position to the camera objects
        debugCamera.position.set(debugPosition, 0);
        camera.position.set(position, 0);

        // let the Camera class calculate its new projection matrices
        debugCamera.update();
        camera.update();
    }

    public Matrix4 getDebugProjection() {
        return debugCamera.combined;
    }

    public Matrix4 getScreenProjection() {
        // return the camera matrix to be used with a SpriteBatch
        return camera.combined;
    }

    public OrthographicCamera getOrthoCamera() {
        return camera;
    }

    public OrthographicCamera getWorldCamera() {
        return debugCamera;
    }
}
