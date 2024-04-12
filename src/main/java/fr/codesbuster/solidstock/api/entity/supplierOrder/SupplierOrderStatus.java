package fr.codesbuster.solidstock.api.entity.supplierOrder;

public enum SupplierOrderStatus {
    TO_BE_VALIDATED,
    VALIDATED,
    PENDING,
    DELIVERED,
    CANCELED,
    REFUSED,
    PARTIALLY_DELIVERED,
    PARTIALLY_CANCELED,
    PARTIALLY_REFUSED
}
