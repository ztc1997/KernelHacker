package com.ztc1997.kernelhacker.extra;

/**
 * Created by Alex on 2015/1/31.
 */
public interface PrefKeys {
    String BOOTED = "pref_booted";
    
    String KERNEL_VERSION = "pref_kernel_version";
    
    public String T2W = "pref_t2w";
    public String T2W_AUTO = "pref_t2w_auto";
    public String T2W_INTERAL = "pref_t2w_interval";
    public String T2W_EFFECTIVE_RANGE = "pref_t2w_effective_range";
    public String T2W_RANGE_X_FROM = "pref_t2w_range_x_from";
    public String T2W_RANGE_Y_FROM = "pref_t2w_range_y_from";
    public String T2W_RANGE_X_TO = "pref_t2w_range_x_to";
    public String T2W_RANGE_Y_TO = "pref_t2w_range_y_to";
    
    String CPU_MAX_FREQ = "pref_cpu_max_freq";
    String CPU_MIN_FREQ = "pref_cpu_min_freq";
    String CPU_LOCK_FREQ = "pref_cpu_lock_freq";
    
    String ZRAM = "pref_zram";
	String ZRAM_DISKSIZE = "pref_zram_disksize";
}
