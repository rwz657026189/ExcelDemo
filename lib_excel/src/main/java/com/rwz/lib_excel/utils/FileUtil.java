package com.rwz.lib_excel.utils;


import com.rwz.lib_excel.entity.Entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * 文件操作工具 可以创建和删除文件等
 */
public class FileUtil {
    private static final String KB = "KB";
    private static final String MB = "MB";
    private static final String GB = "GB";

    private FileUtil() {
    }

    private static final String TAG = "FileUtil";

    /**
     * 格式化文件大小
     *
     * @param size file.length() 获取文件大小
     * @return
     */
    public static String formatFileSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    /**
     * 获取文件后缀名
     */
    public static String getFileExtensionName(String fileName) {
        if (fileName == null) {
            return null;
        }
        int endP = fileName.lastIndexOf(".");
        return endP > -1 ? fileName.substring(endP + 1) : "";
    }

    /**
     * 根据路径获取文件名
     */
    public static String getFileName(String filePath){
        int start = filePath.lastIndexOf("/");
        int end = filePath.lastIndexOf(".");
        if(start != -1 && end != -1 && start < end){
            return filePath.substring(start + 1, end);
        }else{
            return "";
        }
    }

    /**
     * 通过流创建文件
     */
    public static void createFileFormInputStream(InputStream is, String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            byte[] buf = new byte[1024 * 4];
            while (is.read(buf) > 0) {
                fos.write(buf, 0, buf.length);
            }
            is.close();
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean writeText(String path, String content, boolean isAppend) {
        if(Objects.isNull(path) || content == null)
            return false;
        FileOutputStream fos = null;
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            boolean mkdirs = file.getParentFile().mkdirs();
            if (!mkdirs) {
                return false;
            }
        }
        try {
            fos = new FileOutputStream(path, isAppend);
            fos.write(content.getBytes(StandardCharsets.UTF_8));
            fos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String readText(String path) {
        if(Objects.isNull(path))
            return null;
        File file = new File(path);
        if(!file.exists())
            return null;
        BufferedReader br = null;
        try {
            FileReader fr = new FileReader(path);
            br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
            }
        }
        return null;
    }


    /**
     * 从文件读取对象
     */
    public static Object readObj(String path) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
            }
        }
        return null;
    }

    /**
     * 存储对象到文件,只有实现了Serailiable接口的对象才能被存储
     */
    public static void writeObj(String path, Serializable object) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.mkdirs();
        }
        try {
            fos = new FileOutputStream(path);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * 创建文件 当文件不存在的时候就创建一个文件，否则直接返回文件
     *
     * @param path
     * @return
     */
    public static File createFile(String path) {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            if (!createDir(file.getParent())) {
            }
        }
        // 创建目标文件
        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                }
                return file;
            } else {
                return file;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建文件 当文件不存在的时候就创建一个文件
     * @param path
     * @param isDelIfExit 如果存在是否清除并重新创建文件
     * @return
     */
    public static File createFile(String path, boolean isDelIfExit) {
        File file = new File(path);
        File parentFile = file.getParentFile();
        if (parentFile == null) {
            return null;
        }
        if (!parentFile.exists()) {
            if (!createDir(file.getParent())) {
            }
        }
        // 创建目标文件
        try {
            if (file.exists()) {
                if (isDelIfExit) {
                    file.delete();
                } else {
                    return file;
                }
            }
            if (!file.exists()) {
                if (file.createNewFile()) {
                }
                return file;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 进创建父目录
     * @param filePath 文件目录
     * @param isDelIfExist 如果存在是否删除文件
     * @return
     */
    public static File createParentFile(String filePath, boolean isDelIfExist) {
        if (Objects.isNull(filePath)) {
            return null;
        }
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        if (parentFile == null) {
            return null;
        }
        if (!parentFile.exists()) {
            boolean result = parentFile.mkdirs();
            if (!result) {
                return null;
            }
        } else if (file.exists() && isDelIfExist) {
            file.delete();
            return file;
        }
        return file;
    }

    /**
     * 创建目录 当目录不存在的时候创建文件，否则返回false
     *
     * @param path
     * @return
     */
    public static boolean createDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 拷贝文件
     *
     * @param fromPath
     * @param toPath
     * @return
     */
    public static boolean copy(String fromPath, String toPath) {
        File file = new File(fromPath);
        if (!file.exists()) {
            return false;
        }
        createFile(toPath);
        return copyFile(fromPath, toPath);
    }

    /**
     * 拷贝文件
     *
     * @param fromFile
     * @param toFile
     * @return
     */
    private static boolean copyFile(String fromFile, String toFile) {
        InputStream fosfrom = null;
        OutputStream fosto = null;
        try {
            fosfrom = new FileInputStream(fromFile);
            fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (fosfrom != null) {
                    fosfrom.close();
                }
                if (fosto != null) {
                    fosto.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 删除文件 如果文件存在删除文件，否则返回false
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        boolean result = false;
        if (Objects.isNull(path)) {
            return false;
        }
        File file = new File(path);
        if (file.exists()) {
            result = file.delete();
        }
        return result;
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return 删除成功返回true，否则返回false,如果文件是空，那么永远返回true
     */
    public static boolean deleteDir(File dir) {
        if (dir == null) {
            return true;
        }
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /** 获取目录下文件总数 **/
    public static int getFileCount(File dir) {
        if(dir == null)
            return 0;
        if(!dir.exists())
            return 0;
        int fileCount = 0;
        if (dir.isDirectory()) {
            String[] children = dir.list();
            fileCount = children == null ? 0 : children.length;
        } else {
            fileCount = 1;
        }
        return fileCount;
    }

    /**
     * 递归返回文件或者目录的大小（单位:KB）
     * 不建议使用这个方法，有点坑
     * 可以使用下面的方法：http://blog.csdn.net/loongggdroid/article/details/12304695
     *
     * @param path
     * @param size
     * @return
     */
    private static float getSize(String path, Float size) {
        File file = new File(path);
        if (file.exists()) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (String aChildren : children) {
                    float tmpSize = getSize(file.getPath() + File.separator + aChildren, size) / 1000;
                    size += tmpSize;
                }
            } else if (file.isFile()) {
                size += file.length();
            }
        }
        return size;
    }

    public static String readTextFromFile(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        Reader fis = null;
        try {
            long startTime = System.currentTimeMillis();
            fis = new InputStreamReader(new FileInputStream(file));
            StringBuilder sb = new StringBuilder();
            char[] buff = new char[1024 * 4];
            int length;
            while ((length = fis.read(buff)) != -1) {
                sb.append(buff, 0, length);
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<String> readLine(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        BufferedReader fis = null;
        try {
            fis = new BufferedReader(new FileReader(file));
            String line;
            List<String> data = new ArrayList<>();
            while ((line = fis.readLine()) != null) {
                data.add(line + "\r\n");
            }
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 读取配置文件
     *
     * @param filePath
     * @return
     */
    public static List<Entity<String, String>> readProp(String filePath) {
        InputStreamReader isr = null;
        try {
            if (filePath == null) {
                return null;
            }
            File file = new File(filePath);
            if (!file.exists()) {
                return null;
            }
            InputStream is = new FileInputStream(file);
            isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            Properties prop = new Properties();
            prop.load(isr);
            Enumeration<Object> keys = prop.keys();
            List<Entity<String, String>> list = new ArrayList<>();
            while (keys.hasMoreElements()) {
                String key; key = keys.nextElement().toString();
                String value = prop.getProperty(key);
                list.add(new Entity<>(key, value));
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 从配置文件读取
     *
     * @param filePath 文件路径
     * @param key kjey
     * @return
     */
    public static String readProp(String filePath, String key) {
        InputStreamReader isr = null;
        try {
            if (filePath == null) {
                return null;
            }
            File file = new File(filePath);
            if (!file.exists()) {
                return null;
            }
            InputStream is = new FileInputStream(file);
            isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            Properties prop = new Properties();
            prop.load(isr);
            return prop.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
