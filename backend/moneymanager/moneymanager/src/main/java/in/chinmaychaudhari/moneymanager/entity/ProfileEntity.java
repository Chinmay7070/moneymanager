package in.chinmaychaudhari.moneymanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProfileEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String passWord;

    private String profileImageUrl;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAT;

    private Boolean isActive;

    private String activationToken;

    @PrePersist
    public void prePersist(){
        if(this.isActive == null){
            isActive = false;
        }
    }
}
