package main.promotion;

public class BuyNGet1FreePromotion {
    private int promotionQuantity;
    private int triggerQuantity;

    public BuyNGet1FreePromotion(int promotionQuantity, int triggerQuantity) {
        this.promotionQuantity = promotionQuantity;
        this.triggerQuantity = triggerQuantity;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public int getTriggerQuantity() {
        return triggerQuantity;
    }

}

