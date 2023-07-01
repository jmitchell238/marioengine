package com.github.jmitchell238.marioengine.components;

import lombok.Getter;
import lombok.Setter;
import org.joml.Vector2f;
import com.github.jmitchell238.marioengine.renderer.Texture;

@Getter
public class Sprite {

    @Setter
    private Texture texture = null;

    @Getter
    @Setter
    private float width, height;

    @Getter
    @Setter
    private Vector2f[] texCoords = {
                new Vector2f(1, 1),
                new Vector2f(1, 0),
                new Vector2f(0, 0),
                new Vector2f(0, 1)
    };

    public int getTexID() {
        return texture == null ? -1 : texture.getId();
    }
}
