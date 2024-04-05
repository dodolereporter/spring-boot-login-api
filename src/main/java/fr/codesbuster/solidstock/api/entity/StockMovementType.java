package fr.codesbuster.solidstock.api.entity;

public enum StockMovementType {
    IN_DELIVERY,
    IN_RETURN,
    IN_TRANSFER,
    IN_ADJUSTMENT,

    OUT_SALE,
    OUT_LOSS,
    OUT_BREAKAGE,
    OUT_EXPIRATION,
    OUT_STOLEN,
    OUT_SALES_EVENT,
    OUT_TRANSFER,
    OUT_ADJUSTMENT
}
