package com.deng.study.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Objects;

/**
 * @author fuwuzhou
 * @version 1.0
 * @created 16/5/13
 */
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);


    /**
     * 将文本文件中的内容读入到buffer中
     *
     * @param buffer   buffer
     * @param filePath 文件路径
     * @throws IOException 异常
     */
    public static void readToBuffer(StringBuffer buffer, String filePath) {
        InputStream is = null;
        BufferedReader reader = null;

        try {
            is = new FileInputStream(filePath);
            reader = new BufferedReader(new InputStreamReader(is));

            String line; // 用来保存每行读取的内容
            line = reader.readLine(); // 读取第一行
            while (line != null) { // 如果 line 为空说明读完了
                buffer.append(line); // 将读到的内容添加到 buffer 中
                buffer.append("\n"); // 添加换行符
                line = reader.readLine(); // 读取下一行
            }
        } catch (IOException e) {
            LOGGER.error(" has error in readToBuffer", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOGGER.error("readToBuffer: reader has error", e);
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("readToBuffer: is has error", e);
                }
            }
        }


    }

    /**
     * 读取文本文件内容
     *
     * @param filePath 文件所在路径
     * @return 文本内容
     * @throws IOException 异常
     */
    public static String readFile(String filePath) throws IOException {
        StringBuffer sb = new StringBuffer();
        readToBuffer(sb, filePath);
        return sb.toString();
    }

    /**
     * 将文件写入OutputStream
     *
     * @param file        文件内容
     * @param ouputStream 输出管道
     */
    public static void writre(File file, OutputStream ouputStream) throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            byte[] buf = new byte[8192];
            int c;
            while ((c = is.read(buf, 0, buf.length)) > 0) {
                ouputStream.write(buf, 0, c);
                ouputStream.flush();
            }
        } finally {
            if (null != ouputStream) {
                ouputStream.close();
            }

        }
    }

    /**
     * 修改文件名称
     */
    @Test
    public void updateFileName(){
        String filePath = "";
        File file = new File(filePath);
        if(file.isDirectory()){
            File[] files = file.listFiles();
            if(Objects.nonNull(files) && files.length > 0){
                for (File item : files) {
                    String name = item.getName();
//                    System.out.println(name.substring(name.indexOf("源码分析-")));
//                    String substring = name.replace("源码分析","Spring");
//                    String substring =  name.substring(name.indexOf("源码分析-"));
                    String substring = name.replace("源码分析","");
                    System.out.println(substring);

//                    final String substring = name.

                    File dest = new File(filePath+"/"+substring);
                    item.renameTo(dest);
//                    break;
                }
            }
        }
    }
}
