package wallet.util;

import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class WalletUtil {

    private final ZoneId DEFAULT_ZONE = ZoneId.of("UTC");

    public ZonedDateTime getCurrentTime() {
        return ZonedDateTime.now(DEFAULT_ZONE);
    }

}
