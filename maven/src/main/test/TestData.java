import com.lucuicheng.plugin.utils.StringUtils;
import org.junit.Test;

/**
 * Created by on 2016/1/14.
 */
public class TestData {

    @Test
    public void test() {
        String result = StringUtils.upcaseUnderlineNext("service_config_zookeeper");
        System.out.println(result.substring(0,1).toUpperCase()+result.substring(1,result.length()));
        System.out.println(result);
    }
}
