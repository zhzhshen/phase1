package model;

public class Plan implements Product {
    private final long id;
    private final String name;
    private final int duration;
    private final int quota;

    public Plan(long id, String name, int duration, int quota) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.quota = quota;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getQuota() {
        return quota;
    }
}
