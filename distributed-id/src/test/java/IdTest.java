import com.deng.dtx.leaf.id.IdApplication;
import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.Status;
import com.sankuai.inf.leaf.segment.model.LeafAlloc;
import com.sankuai.inf.leaf.service.SegmentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Desc:
 * @Date: 2024/5/21 16:03
 * @Auther: dengyanliang
 */
@Slf4j
@SpringBootTest(classes= IdApplication.class)
public class IdTest {
    @Autowired
    private SegmentService segmentService;

    @Test
    public void test(){

        AtomicLong atomicLong = new AtomicLong();
        int[] array = new int[]{1, 2, 3, 4, 5}; // 初始化5个数
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int index = random.nextInt(array.length); // 每次从这5个值中随机取一个作为索引下标
            long value = atomicLong.getAndAdd(array[index]); // 根据下标获取数组中对应的值，再做累加
            System.out.println(value);
        }
    }

    @Test
    public void checkTest(){
        LeafAlloc leafAlloc = segmentService.checkMaxId("chagee-order-create");
        System.out.println(leafAlloc.getMaxId()+","+leafAlloc.getInitValue());
    }

}
