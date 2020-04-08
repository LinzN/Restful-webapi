/*
 * Copyright (C) 2020. Niklas Linz - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.restfulWebApi;


import com.sun.net.httpserver.HttpServer;
import de.linzn.restfulWebApi.handlers.RootHandlerProvider;
import de.stem.stemSystem.modules.pluginModule.STEMPlugin;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;


public class RestfulWebApiPlugin extends STEMPlugin {

    public static RestfulWebApiPlugin restfulWebApiPlugin;
    private HttpServer httpServer;
    private File webResourcesFolder;


    public RestfulWebApiPlugin() {
        restfulWebApiPlugin = this;

        try {
            httpServer = HttpServer.create(new InetSocketAddress(80), 0);
            this.registerContextHandlers();
            httpServer.setExecutor(null);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onEnable() {
        webResourcesFolder = new File(this.getDataFolder(), "webResources");
        if (!webResourcesFolder.exists()) {
            if (!this.getDataFolder().exists()) {
                this.getDataFolder().mkdir();
            }
            webResourcesFolder.mkdir();
        }
        httpServer.start();
    }

    @Override
    public void onDisable() {
        httpServer.stop(0);
    }

    private void registerContextHandlers() {
        httpServer.createContext("/", new RootHandlerProvider());
    }

    public File getWebResourcesFolder() {
        return this.webResourcesFolder;
    }
}
