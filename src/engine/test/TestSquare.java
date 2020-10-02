package engine.test;

import engine.entity.graphics.AABBDrawable;
import engine.entity.space.SVector;
import engine.entity.space.polygon.SAABB;

public class TestSquare extends SAABB implements AABBDrawable {

    protected TestSquare(SVector position, SVector size) {
        super(position, size);
    }

    @Override
    public String getTexture() {
        return "TestSquare";
    }

    @Override
    public SAABB getDrawBox() {
        return this;
    }
}
