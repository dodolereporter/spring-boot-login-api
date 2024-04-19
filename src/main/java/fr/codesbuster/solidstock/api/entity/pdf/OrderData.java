package fr.codesbuster.solidstock.api.entity.pdf;

import fr.codesbuster.solidstock.api.entity.orderForm.OrderFormEntity;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderData {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private int orderNumber;
    private Date orderDate;
    private String description;
    private CustomerCompanyData customerCompany;
    private OwnerCompanyData ownerCompany;
    private OrderRowData[] orderRows;
    private LocalDate orderDeliveryDate;
    private String orderStatus;

    public OrderData(OrderFormEntity orderFormEntity) {
        df.setRoundingMode(RoundingMode.UP);

        this.orderNumber = (int) orderFormEntity.getId();
        this.description = orderFormEntity.getDescription();
        this.orderDate = Date.from(orderFormEntity.getCreatedAt());
        this.orderDeliveryDate = orderFormEntity.getEstimateDate();
        String companyName = "";
        if (orderFormEntity.getCustomer().getCompanyName() != null) {
            companyName = orderFormEntity.getCustomer().getCompanyName();
        } else {
            companyName = orderFormEntity.getCustomer().getFirstName() + " " + orderFormEntity.getCustomer().getLastName();
        }
        this.customerCompany = new CustomerCompanyData(companyName, orderFormEntity.getCustomer().getStreetNumber(), orderFormEntity.getCustomer().getAddress(), orderFormEntity.getCustomer().getCity(), orderFormEntity.getCustomer().getZipCode(), orderFormEntity.getCustomer().getCountry());
        this.ownerCompany = new OwnerCompanyData(orderFormEntity.getOwnerCompany().getCompanyName(), orderFormEntity.getOwnerCompany().getStreetNumber(), orderFormEntity.getOwnerCompany().getStreetName(), orderFormEntity.getOwnerCompany().getCity(), orderFormEntity.getOwnerCompany().getZipCode(), orderFormEntity.getOwnerCompany().getCountry(), orderFormEntity.getOwnerCompany().getPhone(), orderFormEntity.getOwnerCompany().getEmail(), "www.test.com", orderFormEntity.getOwnerCompany().getSiret(), orderFormEntity.getOwnerCompany().getImage());
        this.orderRows = new OrderRowData[orderFormEntity.getOrderFormRows().size()];
        for (int i = 0; i < orderFormEntity.getOrderFormRows().size(); i++) {
            this.orderRows[i] = new OrderRowData(orderFormEntity.getOrderFormRows().get(i));
        }
        this.orderStatus = orderFormEntity.getStatus();
    }
    public Date getOrderDeliveryDateAsDate() {
        return Date.from(orderDeliveryDate.atStartOfDay().toInstant(ZoneOffset.UTC));
    }
}