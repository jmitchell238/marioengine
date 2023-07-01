package com.github.jmitchell238.marioengine.components;

import static com.github.jmitchell238.marioengine.jade.Window.getScene;

import org.joml.Vector2f;
import org.joml.Vector3f;
import com.github.jmitchell238.marioengine.renderer.DebugDraw;
import com.github.jmitchell238.marioengine.util.Settings;

public class GridLines extends Component {

    @Override
    public void update(float dt) {
        Vector2f cameraPos = getScene().camera().position;
        Vector2f projectionSize = getScene().camera().getProjectionSize();

        int firstX = ((int)cameraPos.x / Settings.GRID_WIDTH - 1) * Settings.GRID_WIDTH;
        int firstY = ((int)cameraPos.y / Settings.GRID_HEIGHT - 1) * Settings.GRID_HEIGHT;

        int numVerticalLines = (int)projectionSize.x / Settings.GRID_WIDTH + 2;
        int numHorizontalLines = (int)projectionSize.y / Settings.GRID_HEIGHT + 2;

        int height = (int)projectionSize.y + Settings.GRID_HEIGHT * 2;
        int width = (int)projectionSize.x + Settings.GRID_WIDTH * 2;

        int maxLines = Math.max(numVerticalLines, numHorizontalLines);
        Vector3f color = new Vector3f(0.2f, 0.2f, 0.2f);
        for (int i = 0; i < maxLines; i++) {
            int x = firstX + i * Settings.GRID_WIDTH;
            int y = firstY + i * Settings.GRID_HEIGHT;

            if (i < numVerticalLines) {
                DebugDraw.addLine2D(new Vector2f(x, firstY), new Vector2f(x, y + height), color);
            }

            if (i < numHorizontalLines) {
                DebugDraw.addLine2D(new Vector2f(firstX, y), new Vector2f(firstX + width, y), color);
            }
        }
    }
}
