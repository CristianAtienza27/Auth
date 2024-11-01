package es.atz.software.products.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@EqualsAndHashCode(callSuper = false)
@Data
public class AuditEntity extends AuditEntityMod{

    @Column(name="Usu_Int", nullable = false, updatable = false)
    private String usuInt;

    @Column(name="Fecha_Int",  nullable = false, updatable = false)
    @CreationTimestamp
    protected Date fechaInt;

    @PrePersist
    @Override
    protected void setInitialValues(){
        super.setInitialValues();
        var now = new Date();
        this.fechaInt = now;
        DateFormat dateFormat = new SimpleDateFormat("HHmmss");
    }

}
