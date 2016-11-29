package model;

public class ProductPurchase implements Purchase {
    private long id;

    public ProductPurchase(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return id;
    }
}
