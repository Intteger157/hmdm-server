package com.hmdm.plugins.deviceremote.rest.json;

public class DeviceRemoteStatusView {

    private String status;
    private String agentStatus;
    private String sessionId;
    private String password;
    private String viewerUrl;
    private Long requestedAt;
    private Long updatedAt;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAgentStatus() {
        return agentStatus;
    }

    public void setAgentStatus(String agentStatus) {
        this.agentStatus = agentStatus;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getViewerUrl() {
        return viewerUrl;
    }

    public void setViewerUrl(String viewerUrl) {
        this.viewerUrl = viewerUrl;
    }

    public Long getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Long requestedAt) {
        this.requestedAt = requestedAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
