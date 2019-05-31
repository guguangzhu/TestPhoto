package com.photo.testphoto.utils;

import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理类
 *
 * @date 2014年6月25日
 **/
public class StringUtil {
    public static final String TAG = StringUtil.class.getSimpleName();

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return boolean true空，false 非空
     */
    public static boolean isBlank(String str) {
        if (str != null) {
            return "".equals(str.trim());
        }
        return true;
    }

    /**
     * 获取字符串半角长度（半角算1、全角算2）
     *
     * @param str 字符串 字符串
     * @return int 长度
     */
    public static int getSemiangleLength(String str) {
        if (isBlank(str)) {
            return 0;
        }
        int len = str.length();
        for (int i = 0; i < str.length(); i++) {
            if (isFullwidthCharacter(str.charAt(i))) {
                len = len + 1;
            }
        }
        return len;
    }

    /**
     * 截取指定半角长度的字符串
     *
     * @param str       待处理的字符串
     * @param maxLength 截取的长度
     * @return String 截取的字符串
     */
    public static String getSemiangleString(String str, int maxLength) {
        if (isBlank(str)) {
            return "";
        }
        int length = 0;
        int len = str.length();
        for (int i = 0; i < len; i++) {
            if (isFullwidthCharacter(str.charAt(i))) {
                if (length + 2 <= maxLength) {
                    length = length + 2;
                } else {
                    return str.substring(0, i - 1);
                }
            } else {
                if (length + 1 <= maxLength) {
                    length = length + 1;
                } else {
                    return str.substring(0, i);
                }
            }
        }
        return str;
    }

    /**
     * 判断字符是否为全角字符
     *
     * @param ch 待判断的字符
     * @return boolean true：全角； false：半角
     */
    private static boolean isFullwidthCharacter(char ch) {
        if (ch >= 32 && ch <= 127) {
            // 基本拉丁字母（即键盘上可见的，空格、数字、字母、符号）
            return false;
        } else if (ch >= 65377 && ch <= 65439) {
            // 日文半角片假名和符号
            return false;
        } else {
            return true;
        }
    }

    /**
     * 对字符串进行md5加密
     *
     * @param string 需要加密的字符串
     * @return 加密后的字符串
     */
    public static String md5(String string) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = string.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 将对象序列化成字符串
     *
     * @param object
     * @return
     */
    public static String Serialize(Object object) {
        if (object == null) {
            return "";
        }

        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            String serStr = byteArrayOutputStream.toString("ISO-8859-1");
            return serStr;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 化序列化对象
     *
     * @param desStr
     * @return
     */
    public static Object Deserialize(String desStr) {
        if (StringUtil.isBlank(desStr))
            return null;

        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;
        byte[] bt = null;

        Object object;
        try {
            bt = desStr.getBytes("ISO-8859-1");
            byteArrayInputStream = new ByteArrayInputStream(bt);
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            object = objectInputStream.readObject();
            return object;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                bt = null;
                if (objectInputStream != null)
                    objectInputStream.close();
                if (byteArrayInputStream != null)
                    byteArrayInputStream.close();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
        }

        return null;
    }


    public static boolean isEmpty(CharSequence input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static int getChineseTextCount(String str){
        int count=0;
        Pattern p = Pattern.compile("[\u4E00-\u9FA5]+");
        Matcher m = p.matcher(str);
        while (m.find())
        {
            str = m.group(0);
            count=str.length();
        }
        return count;
    }

    /***
     * string转double
     *
     * @param str
     * @return
     */
    public static double string2Double(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        double db;
        try {
            db = Double.parseDouble(str);
        } catch (Exception e) {
            db = 0;
        }
        return db;
    }
    /***
     * String 转 int
     * @param str
     * @return
     */
    public static int string2Integer(String str){
        int num=0;
        if(!TextUtils.isEmpty(str)){
            try{
                num=(int)string2Double(str);
            }catch(Exception e){
                num=0;
            }
        }
        return num;

    }

    /***
     * string转boolean
     * @param str
     * @return
     */
    public static boolean string2Boolean(String str){
        return "1".equals(str);
    }

    /***
     * int转boolean
     * @param num
     * @return
     */
    public static boolean int2Boolean(int num){
        return 1==num;
    }



    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }
    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double divide(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2).doubleValue();
    }


    /***
     * 将金额字符串转换为double
     *
     * @param price
     * @return
     */
    public static double getPrice(String price) {
        return getPrice(price, BigDecimal.ROUND_FLOOR);
    }

    /***
     * 将金额字符串转换为double
     *
     * @param price
     * @param mode
     *            取舍规则
     * @return
     */
    public static double getPrice(String price, int mode) {
        double account = 0;
        if (!TextUtils.isEmpty(price)) {
            try {
                BigDecimal bd = new BigDecimal(price);
                account = bd.setScale(2, mode).doubleValue();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return account;
    }
    /***
     * 将金额字符串转换为double
     *
     * @param db
     * @return
     */
    public static double getFormatDouble(double db) {
        return getFormatDouble(db, 2);
    }

    /***
     * 将金额字符串转换为double
     *
     * @param db
     * @param num
     *           保留几位小数
     * @return
     */
    public static double getFormatDouble(double db, int num) {
        double account = 0;
        try {
            BigDecimal bd = new BigDecimal(db);
            account = bd.setScale(num, BigDecimal.ROUND_FLOOR).doubleValue();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return account;
    }

    /**
     * 将价格字符串添加逗号分隔符
     *
     * @param str
     * @return
     */
    public static String getPriceText(String str) {
        return getPriceText("###,##0.00", str);
    }

    /***
     * 将价格字符串添加特定的格式
     *
     * @param format
     * @param str
     * @return
     */
    public static String getPriceText(String format, String str) {
        return getPriceText(format, str, BigDecimal.ROUND_FLOOR);
    }

    /**
     * 以特定的舍入方式将价格字符串添加特定的格式
     * @param format
     * @param str
     * @param mode
     * @return
     */
    public static String getPriceText(String format, String str, int mode) {
        if ("".equals(str) || "null".equals(str) || str == null)
            return "";
        try {
            BigDecimal bd = new BigDecimal(str);
            double f1 = bd.setScale(2, mode).doubleValue();
            DecimalFormat df = new DecimalFormat(format);
            String price = df.format(f1);
            return price;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /***
     * 获取清除小数点后末位为0的数字
     * @param price
     * @return
     */
    public static String getPriceWipeZero(String price){
        return getPriceText("###,###.##",price);
    }


    /***
     * 获取不带千分符的价格
     * @param price
     * @return
     */
    public static String getPriceWithoutMark(String price){
        return getPriceText("#####0.00",price);
    }


}
