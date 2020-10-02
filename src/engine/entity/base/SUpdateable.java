package engine.entity.base;

public interface SUpdateable {
    void start();
    void update(long time);
    void end();
}
