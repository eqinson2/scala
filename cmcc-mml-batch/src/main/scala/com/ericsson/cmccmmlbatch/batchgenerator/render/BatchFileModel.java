package com.ericsson.cmccmmlbatch.batchgenerator.render;

import java.util.List;

public final class BatchFileModel {
    public final String URL;
    public final String user;
    public final String passwd;
    public final String operation;
    public List<String> attributes;

    public BatchFileModel(String url, String user, String passwd, String oper,
                          List<String> attr) {
        this.URL = url;
        this.user = user;
        this.passwd = passwd;
        this.operation = oper;
        this.attributes = attr;
    }
}
