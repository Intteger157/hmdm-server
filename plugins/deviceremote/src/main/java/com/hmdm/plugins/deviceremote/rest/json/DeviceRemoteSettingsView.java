package com.hmdm.plugins.deviceremote.rest.json;

public class DeviceRemoteSettingsView {

    private String serverUrl;
    private String serverSecret;

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
