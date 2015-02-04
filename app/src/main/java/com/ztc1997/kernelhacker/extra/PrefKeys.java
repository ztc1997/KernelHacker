package com.ztc1997.kernelhacker.extra;

/**
 * Created by Alex on 2015/1/31.
 */
public interface PrefKeys {
    String BOOTED = "pref_booted";
    
    String KERNEL_VERSION = "pref_kernel_version";
    
    String T2W = "pref_t2w";
    String T2W_AUTO = "pref_t2w_auto";
    String T2W_INTERAL = "pref_t2w_interval";
    String T2W_RANGE_X_FROM = "pref_t2w_range_x_from";
    String T2W_RANGE_Y_FROM = "pref_t2w_range_y_from";
    String T2W_RANGE_X_TO = "pref_t2w_range_x_to";
    String T2W_RANGE_Y_TO = "pref_t2w_range_y_to";
    
    String CPU_MAX_FREQ = "pref_cpu_max_freq";
    String CPU_MIN_FREQ = "pref_cpu_min_freq";
    String CPU_LOCK_FREQ = "pref_cpu_lock_freq";
    String CPU_GOV = "pref_cpu_gov";
    
    String ZRAM = "pref_zram";
	String ZRAM_DISKSIZE = "pref_zram_disksize";
    String ZRAM_SWAPPINESS = "pref_zram_swappiness";

    String IO_READ_AHEAD_SIZE = "pref_io_read_ahead_size";
    String IO_SCHEDULER = "pref_io_scheduler";
    
    String FAST_CHARGE = "pref_fast_charge";
}
