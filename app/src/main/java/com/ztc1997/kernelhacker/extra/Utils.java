package com.ztc1997.kernelhacker.extra;

import android.content.SharedPreferences;
import android.os.SystemClock;
import android.provider.SyncStateContract;
import android.util.Log;

import com.ztc1997.kernelhacker.MyApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 2015/1/18.
 */
public class Utils {
    
    public static boolean writeFileWithRoot(String path, String value){
        return MyApplication.getRootUtil().execute("echo " + value + " > " + path, null) == 0;
    }
    
    public static void setFilePermission(String path, String permission){
        MyApplication.getRootUtil().execute("chmod " + permission + " " + path, null);
    }
    
    public static String readOneLine(String strFilePath)
    {
        String path = strFilePath;
        String content = ""; //文件内容字符串
        //打开文件
        File file = new File(path);
        if (!file.exists()) {
            return "-1";
        }
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory())
        {
            Log.d("TestFile", "The File doesn't not exist.");
        }
        else
        {
            try {
                InputStream instream = new FileInputStream(file);
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                content = buffreader.readLine();
                instream.close();
            }
            catch (java.io.FileNotFoundException e)
            {
                Log.d("TestFile", "The File doesn't not exist.");
            }
            catch (IOException e)
            {
                Log.d("TestFile", e.getMessage());
            }
        }
        return content;
    }

    public static String readTextLines(String strFilePath)
    {
        String path = strFilePath;
        String content = ""; //文件内容字符串
        //打开文件
        File file = new File(path);
        if (!file.exists()) {
            return "-1";
        }
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory())
        {
            Log.d("TestFile", "The File doesn't not exist.");
        }
        else
        {
            try {
                InputStream instream = new FileInputStream(file);
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                //分行读取
                String line;
                while (( line = buffreader.readLine()) != null) {
                    content += line + "\n";
                }
                instream.close();
            }
            catch (java.io.FileNotFoundException e)
            {
                Log.d("TestFile", "The File doesn't not exist.");
            }
            catch (IOException e)
            {
                Log.d("TestFile", e.getMessage());
            }
        }
        return content;
    }

    public static Stats getFrequencyStats(boolean withDeepSleep) {
        Map<String, Long> times = new HashMap<String, Long>();
        List<Frequency> frequencies = new ArrayList<Frequency>();
        Long totalTime = 0L;
        File f = new File(Paths.SCALING_STATS_TIME_IN_STATE);
        InputStream is = null;
        if (f.exists()) {
            try {
                if (f.canRead()) {
                    is = new FileInputStream(f);
                } else {
                    return null;
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(is), 8192);
                String line = null;
                while ((line = br.readLine()) != null) {
                    String[] aux = line.split(" ");
                    Frequency freq = new Frequency(aux[0]);
                    Long time_in_state = Long.parseLong(aux[1]);
                    totalTime += time_in_state;
                    frequencies.add(freq);
                    times.put(freq.getValue(), time_in_state);
                }
                br.close();
                if (withDeepSleep) {
					/* Add deep sleep to the values */
                    Frequency deepSleep = new Frequency("-1");
                    long uptimeInMillis = SystemClock.uptimeMillis();
                    long elapsed = SystemClock.elapsedRealtime();
                    long deepSleepTime = elapsed - uptimeInMillis;
                    totalTime += deepSleepTime;
                    frequencies.add(deepSleep);
                    times.put(deepSleep.getValue(), deepSleepTime);
                }
                return new Stats(frequencies, times, totalTime);
            } catch (IOException ioex) {
                return null;
            }
        } else {
            return null;
        }

    }

    public static void initSysValues(SharedPreferences preferences){
        preferences.edit()
                .putString(PrefKeys.KERNEL_VERSION, readOneLine(Paths.INFO_KERNEL_VERSION))
                .putString(PrefKeys.T2W_INTERAL, readOneLine(Paths.T2W_INTERVAL))
                .putString(PrefKeys.T2W_RANGE_X_FROM, readOneLine(Paths.T2W_X_FROM))
                .putString(PrefKeys.T2W_RANGE_X_TO, readOneLine(Paths.T2W_X_TO))
                .putString(PrefKeys.T2W_RANGE_Y_FROM, readOneLine(Paths.T2W_Y_FROM))
                .putString(PrefKeys.T2W_RANGE_Y_TO,readOneLine(Paths.T2W_Y_TO))
                .putBoolean(PrefKeys.T2W, !readOneLine(Paths.T2W_PREVENT_SLEEP).equals("0"))
                .putString(PrefKeys.CPU_MAX_FREQ, readOneLine(Paths.CPUINFO_MAX_FREQ))
                .putString(PrefKeys.CPU_MIN_FREQ, readOneLine(Paths.CPUINFO_MIN_FREQ))
                .putBoolean(PrefKeys.ZRAM, readTextLines(Paths.SWAP_STATE).contains("/dev/block/zram0"))
				.putString(PrefKeys.ZRAM_DISKSIZE, readTextLines(Paths.ZRAM_DISKSIZE))
                .apply();
    }

    private static String[] readStringArray(String filename) {
        String line = readOneLine(filename);
        if (line != null) {
            return line.split(" ");
        }
        return null;
    }

    public static String[] getAvailableFrequencies() {
        String[] frequencies = readStringArray(Paths.SCALING_AVAILABLE_FREQ);
        if (frequencies == null) {
            Stats stats = getFrequencyStats(false);
            if (stats != null) {
                frequencies = new String[stats.getFrequencies().size()];
                for (int i = 0; i < stats.getFrequencies().size(); i++) {
                    frequencies[i] = stats.getFrequencies().get(i).getValue();
                }
            } else {
                frequencies = new String[2];
                frequencies[0] = readOneLine(Paths.CPUINFO_MIN_FREQ);
                frequencies[1] = readOneLine(Paths.CPUINFO_MAX_FREQ);
                if (frequencies[0].equals("-1") || frequencies[1].equals("-1")) {
                    frequencies = null;
                }
            }
        }
        if (frequencies != null) {
			/*
			 * Sort them if they aren't already sorted. It happens on devices from
			 * Samsung. Thanks to JoPhj for the discovery.
			 */
            Comparator<String> frequencyComparator = new Comparator<String>() {
                @Override
                public int compare(String lhs, String rhs) {
                    Integer lhi = Integer.parseInt(lhs);
                    Integer rhi = Integer.parseInt(rhs);
                    return lhi.compareTo(rhi);
                }
            };
            Arrays.sort(frequencies, frequencyComparator);
        }
        return frequencies;
    }
    
    public static String khzToMhzString(String khz){
        return khz.substring(0, khz.length() - 3);
    }
}
