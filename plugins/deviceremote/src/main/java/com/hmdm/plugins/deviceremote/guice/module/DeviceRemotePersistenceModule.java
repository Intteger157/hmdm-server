package com.hmdm.plugins.deviceremote.guice.module;

import com.hmdm.guice.module.AbstractPersistenceModule;

import javax.servlet.ServletContext;

public class DeviceRemotePersistenceModule extends AbstractPersistenceModule {

    public DeviceRemotePersistenceModule(ServletContext context) {
        super(context);
    }

    @Override
    protected String getMapperPackageName() {
        return "com.hmdm.plugins.deviceremote.persistence.mapper";
    }

    @Override
    protected String getDomainObjectsPackageName() {
        return "com.hmdm.plugins.deviceremote.persistence.domain";
    }
}
