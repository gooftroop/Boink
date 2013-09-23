package com.app.boink.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Created by goof_troop on 9/20/13.
 */
public class Prattle {

    private static FileHandler fileTxt;
    private static PrattleFormatter formatterTxt;

    public Prattle() {

        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        logger.setLevel(Level.INFO);

        try {

            fileTxt = new FileHandler("Logging.txt");

        } catch (IOException e) {
            // TODO implement something...
        }

        // Create txt Formatter
        formatterTxt = new PrattleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);

    }

    private class PrattleFormatter extends Formatter {

        public PrattleFormatter() {
            super();
        }

        @Override
        public String format(LogRecord logRecord) {

            StringBuffer buff = new StringBuffer();

            if (logRecord.getLevel().intValue() >= Level.WARNING.intValue())
                buff.append("<b>" + logRecord.getLevel() + "</b>");
            else
                buff.append(logRecord.getLevel());

            buff.append(" (" + timeStamp(logRecord.getMillis()) + ") ");
            buff.append(formatMessage(logRecord) + '\n');

            return buff.toString();
        }

        private String timeStamp(long millisecs) {

            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(millisecs));
        }

    }
}
