package in.chinmaychaudhari.moneymanager.service;

import in.chinmaychaudhari.moneymanager.dto.ProfileDto;
import in.chinmaychaudhari.moneymanager.entity.ProfileEntity;
import in.chinmaychaudhari.moneymanager.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final EmailService emailService;
    public ProfileDto registerProfile(ProfileDto profileDto){

       ProfileEntity newProfile =  toEntity(profileDto);
       newProfile.setActivationToken(UUID.randomUUID().toString());
       newProfile = profileRepository.save(newProfile);

       String avtivateionLink = "http://localhost:8080/api/v1.0/activate?token=" + newProfile.getActivationToken();
       String subject = "Activate your Money Manager account";
       String body = "Click on the following link to activate your account " + avtivateionLink;
       emailService.sendEmail(newProfile.getEmail(),subject,body);
       return toDto(newProfile);
    }

    public ProfileEntity toEntity(ProfileDto profileDto){
        return ProfileEntity.builder()
                .id(profileDto.getId())
                        .fullName(profileDto.getFullName())
                .email(profileDto.getEmail())
                .profileImageUrl(profileDto.getProfileImageUrl())
                .createdAt(profileDto.getCreatedAt())
                .updatedAT(profileDto.getUpdatedAT())
                .build();
    }
    public ProfileDto toDto(ProfileEntity profileEntity){
        return ProfileDto.builder()
                .id(profileEntity.getId())
                .fullName(profileEntity.getFullName())
                .email(profileEntity.getEmail())
                .profileImageUrl(profileEntity.getProfileImageUrl())
                .createdAt(profileEntity.getCreatedAt())
                .updatedAT(profileEntity.getUpdatedAT())
                .build();
    }

    public boolean activateProfile(String activationToken) {
        return profileRepository.findByActivationToken(activationToken)
                .map(profile -> {
                    profile.setIsActive(true);
                    profileRepository.save(profile);
                    return true;
                })
                .orElse(false);
    }


}
