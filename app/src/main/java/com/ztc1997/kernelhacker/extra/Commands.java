package com.ztc1997.kernelhacker.extra;

/**
 * Created by Alex on 2015/2/2.
 */
public interface Commands {
    String ENABLE_ZRAM = "mkswap /dev/block/zram0\nswapon /dev/block/zram0";
    String DISABLE_ZRAM = "swapon /dev/block/zram0\necho 1 > /sys/block/zram0/reset";
}
