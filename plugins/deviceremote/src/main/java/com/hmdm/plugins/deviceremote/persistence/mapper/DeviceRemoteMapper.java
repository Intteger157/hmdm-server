package com.hmdm.plugins.deviceremote.persistence.mapper;

import com.hmdm.plugins.deviceremote.persistence.domain.DeviceRemoteSession;
import com.hmdm.plugins.deviceremote.persistence.domain.DeviceRemoteSettings;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface DeviceRemoteMapper {

    @Select("SELECT id, customerId, serverUrl, serverSecret FROM plugin_deviceremote_settings WHERE customerId = #{customerId}")
    DeviceRemoteSettings findSettingsByCustomerId(@Param("customerId") int customerId);

    @Insert("INSERT INTO plugin_deviceremote_settings (customerId, serverUrl, serverSecret) " +
            "VALUES (#{customerId}, #{serverUrl}, #{serverSecret})")
    int insertSettings(DeviceRemoteSettings settings);

    @Update("UPDATE plugin_deviceremote_settings SET serverUrl = #{serverUrl}, serverSecret = #{serverSecret} " +
            "WHERE customerId = #{customerId}")
    int updateSettings(DeviceRemoteSettings settings);

    @Select("SELECT id, customerId, deviceId, sessionId, password, status, agentStatus, requestedAt, updatedAt " +
            "FROM plugin_deviceremote_session WHERE deviceId = #{deviceId}")
    DeviceRemoteSession findSessionByDeviceId(@Param("deviceId") int deviceId);

    @Insert("INSERT INTO plugin_deviceremote_session " +
            "(customerId, deviceId, sessionId, password, status, agentStatus, requestedAt, updatedAt) " +
            "VALUES (#{customerId}, #{deviceId}, #{sessionId}, #{password}, #{status}, #{agentStatus}, #{requestedAt}, #{updatedAt})")
    int insertSession(DeviceRemoteSession session);

    @Update("UPDATE plugin_deviceremote_session SET sessionId = #{sessionId}, password = #{password}, " +
            "status = #{status}, agentStatus = #{agentStatus}, requestedAt = #{requestedAt}, updatedAt = #{updatedAt} " +
            "WHERE deviceId = #{deviceId}")
    int updateSession(DeviceRemoteSession session);
}
