package dev.austinbarnes.retailinventorymanagement.auth.oauth2;

import dev.austinbarnes.retailinventorymanagement.auth.CustomUserPrincipal;
import dev.austinbarnes.retailinventorymanagement.auth.entity.Role;
import dev.austinbarnes.retailinventorymanagement.auth.entity.User;
import dev.austinbarnes.retailinventorymanagement.auth.repo.RoleRepository;
import dev.austinbarnes.retailinventorymanagement.auth.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * OAuth2UserService is a custom implementation of DefaultOAuth2UserService.
 * It handles the loading of user details during OAuth2 authentication.
 * It processes the OAuth2 user information and registers or updates the user in the database.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    /**
     * Loads the user details from the OAuth2 provider.
     * It processes the OAuth2 user information and registers or updates the user in the database.
     *
     * @param userRequest the OAuth2 user request
     * @return OAuth2User object containing user information
     * @throws OAuth2AuthenticationException if an error occurs during authentication
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch(Exception e) {
            throw new OAuth2AuthenticationException(e.getMessage());
        }
    }

    /**
     * Processes the OAuth2 user information.
     * It checks if the user already exists in the database and updates or registers the user accordingly.
     *
     * @param userRequest the OAuth2 user request
     * @param oAuth2User the OAuth2 user object
     * @return CustomUserPrincipal object containing user information
     */
    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo userInfo = getOAuth2UserInfo(provider, oAuth2User.getAttributes());

        Optional<User> userOptional = userRepository.findByOauthProviderAndOauthProviderId(provider, userInfo.getId());

        User user;

        if(userOptional.isPresent()){
            user = userOptional.get();
            user = updateExistingUser(user, provider, userInfo);
        } else {
            Optional<User> userByEmailOptional = userRepository.findByEmail(userInfo.getEmail());
            if(userByEmailOptional.isPresent()){
                user = userByEmailOptional.get();
                user = updateExistingUser(user, provider, userInfo);
            } else {
                user = registerNewUser(provider, userInfo);
            }
        }

        return CustomUserPrincipal.create(user, oAuth2User.getAttributes(), provider + "_" + userInfo.getId());
    }

    /**
     * Retrieves the OAuth2 user information based on the provider.
     *
     * @param provider the OAuth2 provider
     * @param attributes the user attributes from the OAuth2 provider
     * @return OAuth2UserInfo object containing user information
     */
    private OAuth2UserInfo getOAuth2UserInfo(String provider, Map<String, Object> attributes) {
        return switch (provider) {
            case "google" -> new GoogleOAuth2UserInfo(attributes);
            case "github" -> new GithubOAuth2UserInfo(attributes);
            default -> throw new OAuth2AuthenticationException("Provider not supported: " + provider);
        };
    }

    /**
     * Registers a new user in the database.
     *
     * @param provider the OAuth2 provider
     * @param userInfo the OAuth2 user information
     * @return User object containing the registered user information
     */
    private User registerNewUser(String provider, OAuth2UserInfo userInfo) {
        User user = new User();

        user.setOauthProvider(provider);
        user.setOauthProviderId(userInfo.getId());
        user.setName(userInfo.getName());
        user.setEmail(userInfo.getEmail());
        user.setPictureUrl(userInfo.getImageUrl());

        Set<Role> defaultShopperRole = roleRepository.findByName("SHOPPER")
                        .stream().collect(Collectors.toSet());
        user.setRoles(defaultShopperRole);

        return userRepository.save(user);
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user the existing user
     * @param provider the OAuth2 provider
     * @param userInfo the OAuth2 user information
     * @return User object containing the updated user information
     */
    private User updateExistingUser(User user, String provider, OAuth2UserInfo userInfo) {
        user.setOauthProvider(provider);
        user.setOauthProviderId(userInfo.getId());
        user.setPictureUrl(userInfo.getImageUrl());

        return userRepository.save(user);
    }

    /**
     * Sets the attributes converter for the OAuth2 user service.
     *
     * @param attributesConverter the attributes converter
     */
    @Override
    public void setAttributesConverter(Converter<OAuth2UserRequest, Converter<Map<String, Object>, Map<String, Object>>> attributesConverter) {
        super.setAttributesConverter(attributesConverter);
    }
}
