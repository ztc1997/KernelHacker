package com.ztc1997.kernelhacker.extra;

/**
 * Created by Alex on 2015/1/17.
 */
public interface Paths {
    String INFO_KERNEL_VERSION = "/proc/version";
    String INFO_CPU_TEMP = "/sys/class/thermal/thermal_zone0/temp";
    
    String TOUCHSCREEN_SYS_DIR = "/sys/class/input/input2/";
    String T2W_PREVENT_SLEEP = TOUCHSCREEN_SYS_DIR + "prevent_sleep";
    String T2W_ENABLE = TOUCHSCREEN_SYS_DIR + "tap2wake_enable";
    String T2W_INTERVAL = TOUCHSCREEN_SYS_DIR + "tap2wake_interval";
    String T2W_X_FROM = TOUCHSCREEN_SYS_DIR + "tap2wake_x_from";
    String T2W_Y_FROM = TOUCHSCREEN_SYS_DIR + "tap2wake_y_from";
    String T2W_X_TO = TOUCHSCREEN_SYS_DIR + "tap2wake_x_to";
    String T2W_Y_TO = TOUCHSCREEN_SYS_DIR + "tap2wake_y_to";

    String CPUFREQ_SYS_DIR = "/sys/devices/system/cpu/cpu0/cpufreq/";
    String SCALING_MIN_FREQ = CPUFREQ_SYS_DIR + "scaling_min_freq";
    String CPUINFO_MIN_FREQ = CPUFREQ_SYS_DIR + "cpuinfo_min_freq";
    String SCALING_MAX_FREQ = CPUFREQ_SYS_DIR + "scaling_max_freq";
    String CPUINFO_MAX_FREQ = CPUFREQ_SYS_DIR + "cpuinfo_max_freq";
    String SCALING_CUR_FREQ = CPUFREQ_SYS_DIR + "scaling_cur_freq";
    String CPUINFO_CUR_FREQ = CPUFREQ_SYS_DIR + "cpuinfo_cur_freq";
    String SCALING_GOVERNOR = CPUFREQ_SYS_DIR + "scaling_governor";
    String SCALING_AVAILABLE_FREQ = CPUFREQ_SYS_DIR + "scaling_available_frequencies";
    String SCALING_AVAILABLE_GOVERNORS = CPUFREQ_SYS_DIR + "scaling_available_governors";
    String SCALING_STATS_TIME_IN_STATE = CPUFREQ_SYS_DIR + "stats/time_in_state";

    String ZRAM_SYS_DIR = "/sys/block/zram0/";
    String ZRAM_DISKSIZE = ZRAM_SYS_DIR + "disksize";
    String ZRAM_COMPRESSED_DATA_SIZE = ZRAM_SYS_DIR + "compr_data_size";
    String ZRAM_ORIGINAL_DATA_SIZE = ZRAM_SYS_DIR + "orig_data_size";
    String ZRAM_MEM_USED_TOTAL = ZRAM_SYS_DIR + "mem_used_total";
    String SWAP_STATE = "/proc/swaps";
    String ZRAM_SWAPPINESS = "/proc/sys/vm/swappiness";
    
    String IO_READ_AHEAD_SIZE = "/sys/block/mmcblk0/queue/read_ahead_kb";
    String IO_SCHEDULER = "/sys/block/mmcblk0/queue/scheduler";
    String IO_SCHEDULER_MTD = "/sys/block/mtdblock0/queue/scheduler";
    
    String FAST_CHARGE = "/sys/kernel/fast_charge/force_fast_charge";
}
