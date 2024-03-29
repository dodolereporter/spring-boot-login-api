package fr.codesbuster.solidstock.api.repository;


import fr.codesbuster.solidstock.api.entity.invoice.InvoiceEntity;
import fr.codesbuster.solidstock.api.entity.invoice.InvoiceRowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRowRepository extends JpaRepository<InvoiceRowEntity, Long> {
    List<InvoiceRowEntity> findByInvoice_Id(long id);
}
