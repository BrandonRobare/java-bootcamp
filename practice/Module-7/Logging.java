import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Service {
    private static final Logger logger = LoggerFactory.getLogger(Service.class);

    public void process() {
        try {
            riskOperation();
        } catch (Exception e) {
            logger.error("Operation failed for userID={}", userId, e);
        }
    }
}