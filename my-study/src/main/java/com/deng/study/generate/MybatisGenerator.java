package com.deng.study.generate;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Desc:代码生成器
 * @Auther: dengyanliang
 * @Date: 2020/1/15 20:26
 */
public class MybatisGenerator {

    public static void main1(String[] args) throws Exception {


        //执行过程中的警告信息
        List<String> warnings = new ArrayList<>();

        //读取配置文件
        InputStream is = MybatisGenerator.class.getResourceAsStream("/generator/generatorConfig.xml");
        try {
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(is);

            //生成的代码重复时，覆盖原代码
            DefaultShellCallback callback = new DefaultShellCallback(true);
            //创建代码生成器
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            //执行生成代码
            myBatisGenerator.generate(null);
            //输出警告信息
            for (String warning : warnings) {
                System.out.println(warning);
            }
        } finally {
            is.close();
        }


    }
}
