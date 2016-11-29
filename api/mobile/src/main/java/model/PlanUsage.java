package model;

public class PlanUsage implements Usage {
    private final String type = "plan";
    private final long id;
    private final int planId;
    private final int year;
    private final int month;

    public PlanUsage(long id, int planId, int year, int month) {
        this.id = id;
        this.planId = planId;
        this.year = year;
        this.month = month;
    }

    public long getId() {
        return id;
    }

    public int getPlanId() {
        return planId;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public String getType() {
        return type;
    }
}
