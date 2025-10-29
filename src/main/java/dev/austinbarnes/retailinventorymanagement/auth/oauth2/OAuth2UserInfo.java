package dev.austinbarnes.retailinventorymanagement.auth.oauth2;

import java.util.Map;

/**
 * GithubOAuth2UserInfo is a subclass of OAuth2UserInfo that handles user information from GitHub.
 * It extracts the user's ID, name, email, and image URL from the attributes map.
 */
public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId();
    public abstract String getName();
    public abstract String getEmail();
    public abstract String getImageUrl();
    public abstract boolean isVerified();

}
