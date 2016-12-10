package io.musician101.minetanks.common.tank;

public abstract class Info<I> {

    private final String name;
    private I item;

    protected Info(String name) {
        this.name = name;
    }

    public I getItem() {
        return item;
    }

    protected void setItem(I item) {
        this.item = item;
    }

    public String getName() {
        return name;
    }
}
