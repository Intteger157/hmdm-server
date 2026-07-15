package com.hmdm.plugins.deviceremote.rest.json;

public class DeviceRemoteAgentReport {

    private String sessionId;
    private String agentStatus;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getAgentStatus() {
        return agentStatus;
    }

    public void setAgentStatus(String agentStatus) {
        this.agentStatus = agentStatus;
    }
}
