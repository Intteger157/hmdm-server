package com.hmdm.plugins.deviceremote.rest;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hmdm.plugins.deviceremote.persistence.DeviceRemoteDAO;
import com.hmdm.plugins.deviceremote.persistence.domain.DeviceRemoteSession;
import com.hmdm.plugins.deviceremote.persistence.domain.DeviceRemoteSettings;
import com.hmdm.rest.json.SyncResponseHook;
import com.hmdm.rest.json.SyncResponseInt;

@Singleton
public class DeviceRemoteSyncResponseHook implements SyncResponseHook {

    private final DeviceRemoteDAO deviceRemoteDAO;

    @Inject
    public DeviceRemoteSyncResponseHook(DeviceRemoteDAO deviceRemoteDAO) {
        this.deviceRemoteDAO = deviceRemoteDAO;
    }

    @Override
    public SyncResponseInt handle(int deviceId, SyncResponseInt original) {
        DeviceRemoteSession session = deviceRemoteDAO.getSessionByDeviceId(deviceId);
        if (session == null || session.getSessionId() == null) {
            return original;
        }

        if (DeviceRemoteSession.STATUS_PENDING.equals(session.getStatus())) {
            DeviceRemoteSettings settings = deviceRemoteDAO.getSettings(session.getCustomerId());
            if (settings == null || settings.getServerUrl() == null || settings.getServerUrl().trim().isEmpty()) {
                return original;
            }
            original.setRemoteControlStart(true);
            original.setRemoteControlSessionId(session.getSessionId());
            original.setRemoteControlPassword(session.getPassword());
            original.setRemoteControlServerUrl(DeviceRemoteResource.normalizeServerUrl(settings.getServerUrl()));
            original.setRemoteControlSecret(settings.getServerSecret());
        } else if (DeviceRemoteSession.STATUS_STOPPED.equals(session.getStatus())) {
            original.setRemoteControlStop(true);
        }

        return original;
    }
}
