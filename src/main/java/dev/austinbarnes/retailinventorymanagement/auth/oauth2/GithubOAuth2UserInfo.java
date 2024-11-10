package dev.austinbarnes.retailinventorymanagement.auth.oauth2;

import java.util.Map;

public class GithubOAuth2UserInfo extends OAuth2UserInfo {
    public GithubOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return attributes.get("id") == null ? null : String.valueOf(attributes.get("id"));
    }

    @Override
    public String getName() {
        return (attributes.get("name") != null) ? String.valueOf(attributes.get("name")) : String.valueOf(attributes.get("login"));
    }

    @Override
    public String getEmail() {

        return attributes.get("email") != null ? String.valueOf(attributes.get("email")) : null;
    }

    @Override
    public String getImageUrl() {
        return attributes.get("avatar_url") != null ? String.valueOf(attributes.get("avatar_url")) : null;
    }
}
