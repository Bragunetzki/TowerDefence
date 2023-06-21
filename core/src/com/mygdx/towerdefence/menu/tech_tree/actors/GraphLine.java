package com.mygdx.towerdefence.menu.tech_tree.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GraphLine {
    private Texture texture;

    public GraphLine(Texture texture) {
        this.texture = texture;
    }

    public Image getLineToCenter(Actor node1, Actor node2) {
        float centerX = node1.getX() + (node1.getWidth() / 2);
        float centerY = node1.getY() + (node1.getHeight() / 2);
        float nodeX = node2.getX() + (node2.getWidth() / 2);
        float nodeY = node2.getY() + (node2.getHeight() / 2);

        return getLine(centerX, centerY, nodeX, nodeY, 0, -100);
    }

    public Image getLine(float x1, float y1, float x2, float y2) {
        return getLine(x1, y1, x2, y2, 0, 100);
    }
    public Image getLine(float x1, float y1, float x2, float y2, float angleLineX, float angleLineY) {
        Image lineImage = new Image(texture);

        double xDistance = x2 - x1;
        double yDistance = y2 - y1;
        double distance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

        lineImage.setSize((float) distance, 5);

        double angle1 = Math.atan2(angleLineX, angleLineY);
        double angle2 = Math.atan2(yDistance, xDistance);
        double res = Math.toDegrees(angle1 - angle2);
        lineImage.rotateBy( (float) res);

        lineImage.setPosition(x1, y1);

        return lineImage;
    }
}
