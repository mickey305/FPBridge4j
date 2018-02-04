package com.mickey305.fpbridge.v1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AclSampleMain {
    public static void main(String[] args) throws IOException {
        File folder = new File("testFolder2018");
        if (!folder.exists()) {
//            folder.getParentFile().mkdirs();
            Files.createDirectory(
                    Paths.get(folder.getCanonicalPath()),
                    AclFilePermissions.asPosixLikeStmt("rwx------"));
        }
    }
}