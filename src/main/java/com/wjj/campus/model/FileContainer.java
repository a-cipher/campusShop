package com.wjj.campus.model;

import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/30
 * Time: 1:48
 * Description: 封装文件信息
 *
 * @author jiajie.wan
 */
public class FileContainer {

    /**
     * 文件流
     */
    private InputStream fileInputStream;
    /**
     * 文件名
     */
    private String fileName;

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
