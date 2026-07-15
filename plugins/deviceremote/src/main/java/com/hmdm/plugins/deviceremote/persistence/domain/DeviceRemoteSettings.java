package com.hmdm.plugins.deviceremote.persistence.domain;

public class DeviceRemoteSettings {

    private int id;
    private int customerId;
    private String serverUrl;
    private String serverSecret;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getServerSecret() {
        return serverSecret;
    }

    public void setServerSecret(String serverSecret) {
        this.serverSecret = serverSecret;
    }
}
