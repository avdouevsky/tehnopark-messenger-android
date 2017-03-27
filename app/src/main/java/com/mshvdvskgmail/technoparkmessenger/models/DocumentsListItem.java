package com.mshvdvskgmail.technoparkmessenger.models;

/**
 * Created by mshvdvsk on 22/03/2017.
 */

public class DocumentsListItem {
    private String fileType;
    private String fileName;
    private String fileSize;
    private String dataSent;
    private boolean isPressed;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getDataSent() {
        return dataSent;
    }

    public void setDataSent(String dataSent) {
        this.dataSent = dataSent;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }
}
