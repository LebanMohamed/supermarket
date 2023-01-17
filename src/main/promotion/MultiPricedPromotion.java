package main.promotion;

public class MultiPricedPromotion {
    private int promotionQuantity;
    private double promotionPrice;

    public MultiPricedPromotion(int promotionQuantity, double promotionPrice) {
        this.promotionQuantity = promotionQuantity;
        this.promotionPrice = promotionPrice;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }

    public double getPromotionPrice() {
        return promotionPrice;
    }
}