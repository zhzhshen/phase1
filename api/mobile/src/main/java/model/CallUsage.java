package model;

public class CallUsage implements Usage {
    private final String type = "call";
    private final String toNumber;
    private final String outgoing;
    private final int duration;
    private long id;

    public CallUsage(long id, String toNumber, String outgoing, int duration) {
        this.id = id;
        this.toNumber = toNumber;
        this.outgoing = outgoing;
        this.duration = duration;
    }

    @Override
    public long getId() {
        return id;
    }

    public String getToNumber() {
        return toNumber;
    }

    public String getOutgoing() {
        return outgoing;
    }

    public int getDuration() {
        return duration;
    }

    public String getType() {
        return type;
    }
}
