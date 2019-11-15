package sunrise.entity;

import sunrise.entity.base.Updateable;

public abstract class SEntity implements Updateable {
    private static final Object ID_LOCK = new Object();
    private static volatile long CURRENT_ID;
    public final long id;

    protected SEntity() {
        synchronized(ID_LOCK) {
            id = ++CURRENT_ID;
        }
    }
}
