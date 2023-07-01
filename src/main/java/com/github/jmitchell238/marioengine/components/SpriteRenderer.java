package com.github.jmitchell238.marioengine.components;

import imgui.ImGui;
import com.github.jmitchell238.marioengine.jade.Transform;
import lombok.Getter;
import org.joml.Vector2f;
import org.joml.Vector4f;
import com.github.jmitchell238.marioengine.renderer.Texture;

@Getter
public class SpriteRenderer extends Component {

    private final Vector4f color = new Vector4f(1,1,1,1);
    private Sprite sprite = new Sprite();

    private Transform lastTransform;
    private boolean isDirty = true;

    @Override
    public void start() {
        this.lastTransform = gameObject.transform.copy();
    }
    @Override
    public void update(float dt) {
        if (!this.lastTransform.equals(this.gameObject.transform)) {
            this.gameObject.transform.copy(this.lastTransform);
            isDirty = true;
        }
    }

    @Override
    public void imgui() {
        float[] imColors = {color.x, color.y, color.z, color.w};
        if (ImGui.colorPicker4("Color Picker: ", imColors)) {
            this.color.set(imColors[0], imColors[1], imColors[2], imColors[3]);
            this.isDirty = true;
        }
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public Vector2f[] getTexCoords() {
        return sprite.getTexCoords();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.isDirty = true;
    }

    public void setColor(Vector4f color) {
        if (this.color.equals(color)) {
            this.isDirty = true;
            this.color.set(color);
        }
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    public void setClean() {
        this.isDirty = false;
    }
}
