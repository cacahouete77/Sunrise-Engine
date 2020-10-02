package engine.entity;

import engine.environment.SEnvironment;

public abstract class SEntity {
    private static final Object ID_LOCK = new Object();
    private static volatile long CURRENT_ID;
    public final long id;

    protected SEnvironment parentEnvironment;

    protected SEntity() {
        synchronized(ID_LOCK) {
            id = ++CURRENT_ID;
        }
    }

    public SEnvironment getParentEnvironment() {
        return parentEnvironment;
    }

    public void setParentEnvironment(SEnvironment se) {
        parentEnvironment = se;
    }
}
