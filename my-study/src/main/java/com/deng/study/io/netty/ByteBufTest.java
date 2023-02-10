package com.deng.study.io.netty;

import com.deng.study.util.MyByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/8 18:53
 */
@Slf4j
public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        log.debug("byteBuf:{}",byteBuf);
        MyByteBufUtil.log(byteBuf);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 30; i++) {
            sb.append("a");
        }
        byteBuf.writeBytes(sb.toString().getBytes());
        MyByteBufUtil.log(byteBuf);
    }

    /**
     * 将大的ByteBuf 切分成新的ByteBuf
     */
    @Test
    public void testSlice(){
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(10);
        byteBuf.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});
        MyByteBufUtil.log(byteBuf);

        ByteBuf b1 = byteBuf.slice(0,6);
        b1.retain();    // 这里retain后，就不会被原始数据影响
        ByteBuf b2 = byteBuf.slice(6,4);
        MyByteBufUtil.log(b1);
        MyByteBufUtil.log(b2);

//        b1.writeByte('s'); // 切片数据写入不成功，因为超限了
        byteBuf.writeByte('s'); // 可以写入成功

        System.out.println("---------------------");
        b1.setByte(0,'1');
        MyByteBufUtil.log(b1); // 切片数据和原始数据是同一份，修改哪个，都会影响输出
        MyByteBufUtil.log(b2);
        MyByteBufUtil.log(byteBuf);

        byteBuf.release();
        System.out.println("如果切片数据没有retain，那么原始数据release后，会影响切片数据。。");
//        MyByteBufUtil.log(byteBuf);
        MyByteBufUtil.log(b1);

    }


    /**
     * 将大的ByteBuf 复制成一个新的ByteBuf
     */
    @Test
    public void testDuplicate(){
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(10);
        byteBuf.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});
        MyByteBufUtil.log(byteBuf);

        ByteBuf b1 = byteBuf.duplicate();
        b1.retain();    // 这里retain后，就不会被原始数据影响
        MyByteBufUtil.log(b1);

        System.out.println("b1 增加数据后。。。");
        b1.writeByte('q'); // 切片数据写入成功 // TODO b1和byteBuf都写入的时候，会影响最后的结果，不知道啥原因
        MyByteBufUtil.log(b1);

        byteBuf.writeByte('s'); // 可以写入成功
        MyByteBufUtil.log(byteBuf);

        System.out.println("******b1 第一次*******");
        b1.setByte(1,'1');
        MyByteBufUtil.log(b1); // 切片数据和原始数据是同一份，修改哪个，都会影响输出
        System.out.println("=======byteBuf 第一次===========");
        MyByteBufUtil.log(byteBuf);

        System.out.println("******byteBuf 第二次*******");
        byteBuf.setByte(3,'3');
        MyByteBufUtil.log(b1); // 切片数据和原始数据是同一份，修改哪个，都会影响输出
        System.out.println("======b1 第二次============");
        MyByteBufUtil.log(byteBuf);


        byteBuf.release();
        System.out.println("如果切片数据没有retain，那么原始数据release后，会影响切片数据。。");
        MyByteBufUtil.log(b1);
    }

    /**
     * 将两个或多个ByteBuf合并成一个ByteBuf
     */
    @Test
    public void testComposite(){
        ByteBuf b1 = ByteBufAllocator.DEFAULT.buffer();
        b1.writeBytes(new byte[]{1,2,3,4,5});

        ByteBuf b2 = ByteBufAllocator.DEFAULT.buffer();
        b2.writeBytes(new byte[]{6,7,8,9});

//        // 这里是拷贝两次
//        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
//        byteBuf.writeBytes(b1).writeBytes(b2);
//        MyByteBufUtil.log(byteBuf);

        // 这里会拷贝一次，避免内存复制
        CompositeByteBuf buffer = ByteBufAllocator.DEFAULT.compositeBuffer();
        buffer.addComponents(true,b1,b2);
        MyByteBufUtil.log(buffer);

    }
}
