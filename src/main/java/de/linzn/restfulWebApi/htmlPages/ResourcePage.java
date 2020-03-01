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

package de.linzn.restfulWebApi.htmlPages;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;

public class ResourcePage implements IHtmlPage {


    private File file;
    private byte[] bytes;
    private long length;

    public void setFile(File file) {
        this.file = file;
        this.bytes = new byte[]{};
        this.length = 0;
    }

    @Override
    public Map<String, String> headerList() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Content-Type", getContentType());
        map.put("Accept-Ranges", "bytes");
        return map;
    }

    @Override
    public void generate() {
        try {
            this.bytes = Files.readAllBytes(this.file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.length = this.bytes.length;
    }

    @Override
    public long length() {
        return this.length;
    }

    @Override
    public byte[] getBytes() {
        return this.bytes;
    }

    private String getContentType() {
        try {
            return Files.probeContentType(this.file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "text/html";
    }
}
