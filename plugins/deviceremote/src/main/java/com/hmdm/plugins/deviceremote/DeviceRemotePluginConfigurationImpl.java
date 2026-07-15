package com.hmdm.plugins.deviceremote;

import com.google.inject.Module;
import com.hmdm.plugin.PluginConfiguration;
import com.hmdm.plugins.deviceremote.guice.module.DeviceRemoteLiquibaseModule;
import com.hmdm.plugins.deviceremote.guice.module.DeviceRemotePersistenceModule;
import com.hmdm.plugins.deviceremote.guice.module.DeviceRemoteRestModule;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

public class DeviceRemotePluginConfigurationImpl implements PluginConfiguration {

    public static final String PLUGIN_ID = "deviceremote";

    @Override
    public String getPluginId() {
        return PLUGIN_ID;
    }

    @Override
    public String getRootPackage() {
        return "com.hmdm.plugins.deviceremote";
    }

    @Override
    public List<Module> getPluginModules(ServletContext context) {
        List<Module> modules = new ArrayList<>();
        modules.add(new DeviceRemoteLiquibaseModule(context));
        modules.add(new DeviceRemotePersistenceModule(context));
        modules.add(new DeviceRemoteRestModule());
        return modules;
    }
}
