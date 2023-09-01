package com.deng.common.util;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FilePathUtil {


    private FilePathUtil() {
    }

    public static URL getUrl(final String classPath) {
        if (StringUtils.isEmpty(classPath)) {
            throw new IllegalArgumentException("classPath can't be empty");
        }
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        ClassPathResource resource;
        while (null != classLoader) {
            resource = new ClassPathResource(classPath);
            if (resource.exists()) {
                try {
                    return resource.getURL();
                } catch (final IOException e) {
                    throw new IllegalArgumentException("classpath " + StringUtils.quote(classPath) + " can't be converted to URL", e);
                }
            }
            classLoader = classLoader.getParent();
        }
        throw new IllegalArgumentException("can't findOrg classpath " + StringUtils.quote(classPath));
    }

    public static void createFile(final File file) {
        if (null == file) {
            throw new IllegalArgumentException("file can't be null");
        }
        if (!file.getParentFile().exists()) {
            try {
                file.getParentFile().mkdirs();
            } catch (final Exception e) {
                throw new IllegalStateException("can't create parent folder", e);
            }
        }

        if (file.exists() && !file.isFile()) {
            try {
                FileUtils.forceDelete(file);
            } catch (final IOException e) {
                throw new IllegalStateException("can't delete existent file while creating file", e);
            }
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (final IOException e) {
                throw new IllegalStateException("can't create file", e);
            }
        }
        if (!file.canWrite()) {
            throw new IllegalStateException("can't write file");
        }
    }

    /**
     * 得到文件的绝对路径
     *
     * @param path
     * @return
     */
    public static String getFileUrl(String path) {
        URL url = getUrl(path);
        return url.getPath();
    }

}
