import promotion.BuyNGet1FreePromotion;
import promotion.MultiPricedPromotion;

class Item {
    private char sku;
    private double price;
    private BuyNGet1FreePromotion buyNGet1FreePromotion;
    private MultiPricedPromotion multiPricedPromotion;

    private int promotionQuantity;

    public Item(char sku, double price) {
        this.sku = sku;
        this.price = price;
    }

    public void setBuyNGet1FreePromotion(BuyNGet1FreePromotion promotion) {
        this.buyNGet1FreePromotion = promotion;
    }

    public void setMultiPricedPromotion(MultiPricedPromotion promotion) {
        this.multiPricedPromotion = promotion;
    }

    public boolean hasBuyNGet1FreePromotion() {
        return buyNGet1FreePromotion != null;
    }

    public boolean hasMultiPricedPromotion() {
        return multiPricedPromotion != null;
    }

    public BuyNGet1FreePromotion getBuyNGet1FreePromotion() {
        return buyNGet1FreePromotion;
    }

    public MultiPricedPromotion getMultiPricedPromotion() {
        return multiPricedPromotion;
    }

    public char getSku() {
        return sku;
    }

    public double getPrice() {
        return price;
    }

    public int getPromotionQuantity() {
        if (hasBuyNGet1FreePromotion()) {
            return buyNGet1FreePromotion.getPromotionQuantity();
        } else if (hasMultiPricedPromotion()) {
            return multiPricedPromotion.getPromotionQuantity();
        }
        return 0;
    }

    public void setPromotionQuantity(int promotionQuantity) {
        this.promotionQuantity = promotionQuantity;
    }


}