package in.chinmaychaudhari.moneymanager.dto;

import jakarta.persistence.Column;
import jakarta.persistence.NamedAttributeNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProfileDto {
    private long id;

    private String fullName;
    private String email;
    private String passWord;
    private String profileImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAT;


}
