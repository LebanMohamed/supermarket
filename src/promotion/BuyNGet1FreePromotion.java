package promotion;

public class BuyNGet1FreePromotion {
    private int promotionQuantity;
    private int triggerQuantity;
    private int freeQuantity;

    public BuyNGet1FreePromotion(int promotionQuantity, int triggerQuantity, int freeQuantity) {
        this.promotionQuantity = promotionQuantity;
        this.triggerQuantity = triggerQuantity;
        this.freeQuantity = freeQuantity;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public int getTriggerQuantity() {
        return triggerQuantity;
    }

    public int getFreeQuantity() {
        return freeQuantity;
    }
}

