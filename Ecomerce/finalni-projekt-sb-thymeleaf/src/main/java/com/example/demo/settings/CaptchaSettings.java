package com.example.demo.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "google.recaptcha")
public class CaptchaSettings
{
    private String site;
    private String secret;
    private String url;

    public String getSite() {
        return site;
    }

    public String getSecret() {
        return secret;
    }

    public String getUrl() {
        return url;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
