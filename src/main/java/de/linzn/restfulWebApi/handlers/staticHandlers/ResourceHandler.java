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

package de.linzn.restfulWebApi.handlers.staticHandlers;

import de.linzn.restfulWebApi.RestfulWebApiPlugin;
import de.linzn.restfulWebApi.handlers.IResponseHandler;
import de.linzn.restfulWebApi.htmlPages.EmptyPage;
import de.linzn.restfulWebApi.htmlPages.IHtmlPage;
import de.linzn.restfulWebApi.htmlPages.ResourcePage;

import java.io.File;
import java.util.List;

public class ResourceHandler implements IResponseHandler {

    private static String get404() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<DIV class=\"row-fluid\"><br><br>" +
                "<div class=\"col-xs-12 col-sm-12 col-md-12 col-lg-12\">" +
                "            <div class=\"box\">" +
                "                <div class=\"box-icon\"><span class=\"fas fa-4x fa-exclamation-triangle\"></span></div>" +
                "                <div class=\"site-info\">" +
                "                    <h4 class=\"text-center\">404 - Not found!</h4><p>" +
                "                    <b><br>The requested page does not exist!</b><br>" +
                "                </p></div>" +
                "            </div>" +
                "        </div>" +
                "</DIV>");
        return stringBuilder.toString();
    }

    private static String get403() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<DIV class=\"row-fluid\"><br><br>" +
                "<div class=\"col-xs-12 col-sm-12 col-md-12 col-lg-12\">" +
                "            <div class=\"box\">" +
                "                <div class=\"box-icon\"><span class=\"fas fa-4x fa-exclamation-triangle\"></span></div>" +
                "                <div class=\"site-info\">" +
                "                    <h4 class=\"text-center\">403 - Forbidden!</h4><p>" +
                "                    <b><br>You are not allowed to enter this page!</b><br>" +
                "                </p></div>" +
                "            </div>" +
                "        </div>" +
                "</DIV>");
        return stringBuilder.toString();
    }

    @Override
    public IHtmlPage buildResponse(List<String> inputList) {
        File webResourceFolder = RestfulWebApiPlugin.restfulWebApiPlugin.getWebResourcesFolder();
        IHtmlPage htmlPage = new EmptyPage();

        StringBuilder filePath = new StringBuilder();
        for (int i = 0; i < inputList.size(); i++) {
            filePath.append(inputList.get(i));
            if (i < inputList.size() - 1) {
                filePath.append("/");
            }
        }

        File requestedFile = new File(webResourceFolder, filePath.toString());
        if (requestedFile.exists()) {
            if (!requestedFile.isDirectory()) {
                htmlPage = new ResourcePage();
                ((ResourcePage) htmlPage).setFile(requestedFile);
            }
        }
        return htmlPage;
    }
}
