package com.hmdm.plugins.deviceremote.guice.module;

import com.hmdm.guice.module.AbstractLiquibaseModule;
import com.hmdm.plugin.guice.module.PluginLiquibaseResourceAccessor;
import liquibase.resource.ResourceAccessor;

import javax.servlet.ServletContext;

public class DeviceRemoteLiquibaseModule extends AbstractLiquibaseModule {

    public DeviceRemoteLiquibaseModule(ServletContext context) {
        super(context);
    }

    @Override
    protected String getChangeLogResourcePath() {
        String path = this.getClass().getResource("/liquibase/deviceremote.changelog.xml").getPath();
        if (!path.startsWith("jar:")) {
            path = "jar:" + path;
        }
        return path;
    }

    @Override
    protected ResourceAccessor getResourceAccessor() {
        return new PluginLiquibaseResourceAccessor();
    }
}
