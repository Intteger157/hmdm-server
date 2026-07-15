package com.hmdm.plugins.deviceremote.persistence;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hmdm.persistence.AbstractDAO;
import com.hmdm.plugins.deviceremote.persistence.domain.DeviceRemoteSession;
import com.hmdm.plugins.deviceremote.persistence.domain.DeviceRemoteSettings;
import com.hmdm.plugins.deviceremote.persistence.mapper.DeviceRemoteMapper;
import org.mybatis.guice.transactional.Transactional;

@Singleton
public class DeviceRemoteDAO extends AbstractDAO<DeviceRemoteSession> {

    private final DeviceRemoteMapper deviceRemoteMapper;

    @Inject
    public DeviceRemoteDAO(DeviceRemoteMapper deviceRemoteMapper) {
        this.deviceRemoteMapper = deviceRemoteMapper;
    }

    public DeviceRemoteSettings getSettings(int customerId) {
        return deviceRemoteMapper.findSettingsByCustomerId(customerId);
    }

    @Transactional
    public void saveSettings(DeviceRemoteSettings settings) {
        DeviceRemoteSettings existing = getSettings(settings.getCustomerId());
        if (existing == null) {
            deviceRemoteMapper.insertSettings(settings);
        } else {
            deviceRemoteMapper.updateSettings(settings);
        }
    }

    public DeviceRemoteSession getSessionByDeviceId(int deviceId) {
        return deviceRemoteMapper.findSessionByDeviceId(deviceId);
    }

    @Transactional
    public void saveSession(DeviceRemoteSession session) {
        DeviceRemoteSession existing = getSessionByDeviceId(session.getDeviceId());
        if (existing == null) {
            deviceRemoteMapper.insertSession(session);
        } else {
            deviceRemoteMapper.updateSession(session);
        }
    }
}
