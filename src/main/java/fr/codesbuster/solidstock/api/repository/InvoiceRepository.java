package fr.codesbuster.solidstock.api.repository;


import fr.codesbuster.solidstock.api.entity.invoice.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository  extends JpaRepository<InvoiceEntity, Long> {
}
