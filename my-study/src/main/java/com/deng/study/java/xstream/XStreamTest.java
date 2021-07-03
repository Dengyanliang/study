package com.deng.study.java.xstream;

import com.deng.study.domain.User;
import com.thoughtworks.xstream.XStream;

public class XStreamTest {
    public static void main(String[] args) {
        User user = User.getComplexUser();
        XStream xStream = new XStream();
        String xml = xStream.toXML(user);
        System.out.println(xml);

        User u = (User)xStream.fromXML(xml);
        System.out.println(u);
    }
}
