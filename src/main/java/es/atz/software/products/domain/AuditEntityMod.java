package es.atz.software.products.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@MappedSuperclass
@Data
public abstract class AuditEntityMod {

    @Column(name="Usu_Mod")
    private String usuMod;

    @Column(name="Fecha_Mod")
    private Date fechaMod;

    @Column(name="Hora_Mod")
    private String horaMod;

    @Column(name="Estad_Reg")
    private String estadoReg;

    @PrePersist
    protected void setInitialValues(){
        setModValues();
    }

    @PreUpdate
    public void setAuditValues() {
        setModValues();
    }

    private void setModValues() {
        var now = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HHmmss");
        this.fechaMod = now;
        this.horaMod = dateFormat.format(now);

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (this.usuMod == null && authentication != null && authentication.getPrincipal() != null) {
            User user = (User) authentication.getPrincipal();
            this.usuMod = user.getUsername();
        }
    }

}

