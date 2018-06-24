package de.paluno.game.gameobjects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class WorldObject {

    private World2 world;
    private Body body;
    private WorldObject parent;

    private ArrayList<WorldObject> children = new ArrayList<>();

    public void setWorld(World2 world) {
        this.world = world;
    }

    public World2 getWorld() {
        return world;
    }

    public void addChild(WorldObject object) {
        if (getWorld() != null)
            getWorld().registerAfterUpdate(object);
        children.add(object);
        object.parent = this;
    }

    public void removeChild(WorldObject object) {
        if (getWorld() != null)
            getWorld().forgetAfterUpdate(object);
        children.remove(object);
        object.parent = null;
    }

    public void removeFromWorld() {
        if (parent != null)
            parent.removeChild(this);
        else if (getWorld() != null)
            getWorld().forgetAfterUpdate(this);
    }

    public WorldObject getParent() {
        return parent;
    }

    public ArrayList<WorldObject> getChildren() {
        return children;
    }

    public void setupAssets(AssetManager manager) {

    }

    public void setBodyToNullReference() {
        body = null;
    }

    public void setupBody(World world) {
        body = onSetupBody(world);
        if (body != null)
            body.setUserData(this);
    }

    public Body getBody() {
        return body;
    }

    protected Body onSetupBody(World world) {
        return null;
    }

    public void update(float delta) {

    }

    public void render(SpriteBatch batch, float delta) {

    }
}
