package sunrise.entity;

public abstract class SEntity {
    private static final Object ID_LOCK = new Object();
    private static volatile long CURRENT_ID;
    public final long id;

    protected SEntity() {
        synchronized(ID_LOCK) {
            id = ++CURRENT_ID;
        }
    }
}
