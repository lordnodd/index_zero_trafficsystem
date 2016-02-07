package kcl.teamIndexZero.traffic.log.outputs;

import kcl.teamIndexZero.traffic.log.Log_Levels;
import kcl.teamIndexZero.traffic.log.Log_TimeStamp;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Es on 29/01/2016.
 */
public class Formatter_TERM implements Formatter_Interface {
    /**
     * Constructor
     */
    protected Formatter_TERM() {
    }

    /**
     * Formats message information for console output
     *
     * @param origin_name Name of the message's origin
     * @param log_level   Log level
     * @param log_number  Message number in log session
     * @param time_stamp  Time stamp of the message
     * @param objects     Message details
     * @return Formatted string
     */
    @Override
    public String format(String origin_name, int log_level, Long log_number, Log_TimeStamp time_stamp, Object... objects) {
        String s = "[" + log_number.toString() + "] " + time_stamp.getDate() + " - " + time_stamp.getTime() + " " + Log_Levels.txtLevels[log_level] + " [" + origin_name + "] ";
        for (Object o : objects) {
            s += o.toString();
        }
        return s;
    }

    /**
     * Formats Exception information for console output
     *
     * @param origin_name Name of the message's origin
     * @param time_stamp  Time stamp of the message
     * @param e           Exception raised
     * @return Formatted String
     */
    @Override
    public String format(String origin_name, Log_TimeStamp time_stamp, Exception e) {
        String s = "\t===Exception raised in [" + origin_name + "]===";
        s += System.lineSeparator() + "\t";
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        s += sw.toString() + System.lineSeparator();
        return s;
    }
}
