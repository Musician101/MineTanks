package io.musician101.minetanks.common.tank;

public abstract class AbstractTankType<I> extends Info<I> {

    private final int ramModifier;

    protected AbstractTankType(String name, I item, int ramModifier) {
        super(name);
        setItem(item);
        this.ramModifier = ramModifier;
    }

    public int getRamModifier() {
        return ramModifier;
    }
}
