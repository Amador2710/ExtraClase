import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.*;

class DemoLogger {
    private final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public void makeSomeLog()
    {
        LOGGER.log(Level.INFO, "My first Log Message");

    }
}

public class GfG {
    public static void main(String[] args)
    {
        DemoLogger obj = new DemoLogger();
        obj.makeSomeLog();

        LogManager lgmngr = LogManager.getLogManager();

        Logger log = lgmngr.getLogger(Logger.GLOBAL_LOGGER_NAME);

        log.log(Level.INFO, "This is a log message");
    }
}