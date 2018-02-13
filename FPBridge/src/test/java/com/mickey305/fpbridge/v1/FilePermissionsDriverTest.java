package com.mickey305.fpbridge.v1;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class FilePermissionsDriverTest {
    private static final String TEST_FOLDER_NAME = "testFolder2018_" + FilePermissionsDriverTest.class.getName();

    @Before
    public void setUp() throws Exception {
        File folder = new File(this.createTmpPath() + TEST_FOLDER_NAME);

        Assert.assertFalse(folder.exists());
    }

    @After
    public void tearDown() throws Exception {
        File folder = new File(this.createTmpPath() + TEST_FOLDER_NAME);
        Files.delete(Paths.get(folder.getCanonicalPath()));

        Assert.assertFalse(folder.exists());
    }

    @Test
    public void testCase_01_01() throws Exception {
        File folder = new File(this.createTmpPath() + TEST_FOLDER_NAME);

        if (!folder.exists()) {
            Set<FPArgument> arguments = new HashSet<>();
            arguments.add(FPArgument.newAllowOwner());
            Files.createDirectory(
                    Paths.get(folder.getCanonicalPath()),
                    FilePermissionsDriver.createByFPArgument(arguments));

            System.out.println(folder.getCanonicalPath());

            Assert.assertTrue(folder.exists());
            Assert.assertEquals(TEST_FOLDER_NAME, folder.getName());
        }
    }

    private String createTmpPath() {
        String tmpPath = System.getProperty("java.io.tmpdir");
        if (!tmpPath.endsWith(File.separator))
            tmpPath += File.separator;
        return tmpPath;
    }
}
