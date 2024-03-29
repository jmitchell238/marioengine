package com.github.jmitchell238.marioengine.scenes;

import com.github.jmitchell238.marioengine.components.GridLines;
import com.github.jmitchell238.marioengine.components.MouseControls;
import com.github.jmitchell238.marioengine.components.Sprite;
import com.github.jmitchell238.marioengine.components.SpriteRenderer;
import com.github.jmitchell238.marioengine.components.Spritesheet;
import com.github.jmitchell238.marioengine.jade.Camera;
import imgui.ImGui;
import imgui.ImVec2;
import com.github.jmitchell238.marioengine.jade.GameObject;
import com.github.jmitchell238.marioengine.jade.Prefabs;
import com.github.jmitchell238.marioengine.jade.Transform;
import org.joml.Vector2f;
import com.github.jmitchell238.marioengine.util.AssetPool;

public class LevelEditorScene extends Scene {

    private GameObject obj1;
    private Spritesheet sprites;
    private SpriteRenderer obj1SpriteRenderer;

    GameObject levelEditorStuff = new GameObject("Level Editor", new Transform(), 0);

    public LevelEditorScene() {
    }

    @Override
    public void init() {
        this.levelEditorStuff.addComponent(new MouseControls());
        this.levelEditorStuff.addComponent(new GridLines());

        loadResources();
        this.camera = new Camera(new Vector2f(-250, 0));
        this.sprites = AssetPool.getSpriteSheet("assets/images/spritesheets/decorationsAndBlocks.png");

        if(levelLoaded) {
            if(!gameObjects.isEmpty()) {
                this.activeGameObject = gameObjects.get(0);
            }
            return;
        }

//        this.obj1 = new GameObject("Object 1", new Transform(new Vector2f(200, 100),
//                new Vector2f(256, 256)), 2);
//        this.obj1SpriteRenderer = new SpriteRenderer();
//        this.obj1SpriteRenderer.setColor(new Vector4f(1,0,0,1));
//        this.obj1.addComponent(obj1SpriteRenderer);
//        this.obj1.addComponent(new Rigidbody());
//        this.addGameObjectToScene(obj1);
//        this.activeGameObject = obj1;
//
//        GameObject obj2 = new GameObject("Object 2",
//                new Transform(new Vector2f(400, 100), new Vector2f(256, 256)), 3);
//        SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();
//        Sprite obj2Sprite = new Sprite();
//        obj2Sprite.setTexture(AssetPool.getTexture("assets/images/blendImage2.png"));
//        obj2SpriteRenderer.setSprite(obj2Sprite);
//        obj2.addComponent(obj2SpriteRenderer);
//        this.addGameObjectToScene(obj2);
    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        // TODO: FIX TEXTURE SAVE SYSTEM TO USE PATH INSTEAD OF ID
        AssetPool.addSpritesheet("assets/images/spritesheets/decorationsAndBlocks.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/decorationsAndBlocks.png"),
                        16, 16, 81, 0));

        AssetPool.getTexture("assets/images/blendImage2.png");

        for(GameObject g : gameObjects) {
            if(g.getComponent(SpriteRenderer.class) != null) {
                SpriteRenderer spr = g.getComponent(SpriteRenderer.class);
                if(spr.getTexture() != null) {
                    spr.setTexture(AssetPool.getTexture(spr.getTexture().getFilepath()));
                }
            }
        }
    }

//    float x = 0.0f;
//    float y = 0.0f;
    @Override
    public void update(float dt) {
        this.levelEditorStuff.update(dt);
//        DebugDraw.addCircle(new Vector2f(x, y), 64, new Vector3f(0,0,0), 1);
//        x += 50f * dt;
//        y += 50f * dt;

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }

        this.renderer.render();
    }

    @Override
    public void imgui() {
        ImGui.begin("Test Window");

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowsX2 = windowPos.x + windowSize.x;

        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.getSprite(i);
            float spriteWidth = sprite.getWidth() * 4;
            float spriteHeight = sprite.getHeight() * 4;
            int id = sprite.getTexID();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y,
                    texCoords[0].x, texCoords[2].y)) {
                GameObject object = Prefabs.generateSpriteObject(sprite, 32, 32);

                this.levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;

            if (i + 1 < sprites.size() && nextButtonX2 < windowsX2) {
                ImGui.sameLine();
            }


        }

        ImGui.end();
    }
}
