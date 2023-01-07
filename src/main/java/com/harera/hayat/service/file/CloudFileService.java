package com.harera.hayat.service.file;

import java.io.File;

public interface CloudFileService {

    public String uploadFile(File file);

    public void deleteFile(String url);
}
