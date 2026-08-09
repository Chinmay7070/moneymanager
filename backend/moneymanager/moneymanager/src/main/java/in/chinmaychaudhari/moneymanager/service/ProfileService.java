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

    public ProfileDto registerProfile(ProfileDto profileDto){

       ProfileEntity newProfile =  toEntity(profileDto);
       newProfile.setActivationToken(UUID.randomUUID().toString());
       newProfile = profileRepository.save(newProfile);
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


}
