package com.github.jmitchell238.marioengine.renderer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.joml.Vector2f;
import org.joml.Vector3f;

@AllArgsConstructor
public class Line2D {
    @Getter
    private Vector2f start;

    @Getter
    private Vector2f end;

    @Getter
    private Vector3f color;

    private int lifetime;

    public int beginFrame() {
        this.lifetime--;
        return lifetime;
    }
}
