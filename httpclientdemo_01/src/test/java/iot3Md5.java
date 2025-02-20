import org.apache.commons.codec.digest.DigestUtils;
import java.util.Date;

public class iot3Md5 {
    public static void main(String[] args) {
        //MQTT设备加密
        long currentTimeMillis = new Date().getTime();
        String secureId="admin";
        String secureKey="^N9QYwD6ytagqv6E";
        String username=secureId+"|"+currentTimeMillis;
        String password=username+"|"+secureKey;
        String hashpassword = DigestUtils.md5Hex(password);

        System.out.println(username);
        System.out.println(hashpassword);


    }
}
