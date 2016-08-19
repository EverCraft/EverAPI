package fr.evercraft.everapi.services.worldguard.region;

public enum  MoveType {
	RESPAWN(false, true),
    EMBARK(true),
    MOVE(true),
    TELEPORT(true, true),
    RIDE(true),
    OTHER_NON_CANCELLABLE(false),
    OTHER_CANCELLABLE(true);

    private final boolean cancellable;
    private final boolean teleport;

    MoveType(boolean cancellable) {
        this(cancellable, false);
    }

    MoveType(boolean cancellable, boolean teleport) {
        this.cancellable = cancellable;
        this.teleport = teleport;
    }
    
    public boolean isCancellable() {
        return cancellable;
    }

    public boolean isTeleport() {
        return teleport;
    }
}
