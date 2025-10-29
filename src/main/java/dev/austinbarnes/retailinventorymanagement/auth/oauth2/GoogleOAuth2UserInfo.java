package dev.austinbarnes.retailinventorymanagement.auth.oauth2;

import java.util.Map;

/**
 * GithubOAuth2UserInfo is a subclass of OAuth2UserInfo that handles user information from GitHub.
 * It extracts the user's ID, name, email, and image URL from the attributes map.
 */
public class GoogleOAuth2UserInfo extends OAuth2UserInfo {
    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("sub"));
    }

    @Override
    public String getName() {
        return String.valueOf(attributes.get("name"));
    }

    @Override
    public String getEmail() {
        return String.valueOf(attributes.get("email"));
    }

    @Override
    public String getImageUrl() {
        return String.valueOf(attributes.get("picture"));
    }

    public boolean isVerified(){return (Boolean) attributes.get("email_verified"); }

}
