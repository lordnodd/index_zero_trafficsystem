package kcl.teamIndexZero.traffic.log.outputs;

import kcl.teamIndexZero.traffic.log.Log_TimeStamp;

/**
 * Formatter Interface
 */
public interface Formatter_Interface {
    String format(String origin_name, int log_level, Long log_number, Log_TimeStamp time_stamp, Object... objects);

    String format(String origin_name, Log_TimeStamp time_stamp, Long log_number, Exception e);
}
