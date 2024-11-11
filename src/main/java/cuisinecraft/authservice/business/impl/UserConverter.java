package cuisinecraft.authservice.business.impl;

import cuisinecraft.authservice.domain.User;
import cuisinecraft.authservice.persistence.entity.UserEntity;

final class UserConverter {
    private UserConverter() {
    }

    public static User convert(UserEntity usere) {
        return User.builder()
                .id(usere.getId())
                .name(usere.getName())
                .username(usere.getUsername())
                .passwordhash(usere.getPasswordhash())
                .phonenumber(usere.getPhonenumber())
                .email(usere.getEmail())
                .address(usere.getAddress())
                .gender(usere.getGender())
                .birthdate(usere.getBirthdate())
                .profilePictureUrl(usere.getProfilePictureUrl())
                .role(usere.getRole())
                .balance(usere.getBalance())
                .build();


    }
}
