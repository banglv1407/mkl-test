package com.mkl.mkltest.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mkl.mkltest.exception.ErrorCode;
import com.mkl.mkltest.exception.MklException;

import org.apache.http.HttpStatus;

/**
 * @author GIAP VIET DUC
 * @Detail : The CommonMethod class.
 **/
public class CommonMethod {
    static Logger log = Logger.getLogger(CommonMethod.class.getSimpleName());

    /**
     * Generate random string code.
     *
     * @param i the i
     * @return the string
     **/
    public static String genRandomCode(int i) {
        long decimalNumber = System.nanoTime() + i;
        String strBaseDigits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String strTempVal = "";
        int mod = 0;
        while (decimalNumber != 0) {
            mod = (int) (decimalNumber % 36);
            strTempVal = strBaseDigits.substring(mod, mod + 1) + strTempVal;
            decimalNumber = decimalNumber / 36;
        }
        return strTempVal;
    }

    public static String genRandomId() {
        long tg = System.currentTimeMillis();
        tg -= tg % DateTimeHelper.ONE_DAY_IN_MILLISECOND;
        tg -= getDayInLong("20170727");
        tg /= DateTimeHelper.ONE_DAY_IN_MILLISECOND;
        tg %= 36600;
        long decimalNumber = System.nanoTime();
        String strBaseDigits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        // System.out.println("length="+strBaseDigits.length());
        String strTempVal = "";
        int mod = 0;
        while (decimalNumber != 0) {
            mod = (int) (decimalNumber % strBaseDigits.length());
            strTempVal = strBaseDigits.substring(mod, mod + 1) + strTempVal;
            decimalNumber = decimalNumber / strBaseDigits.length();
        }
        String xau = strTempVal;
        mod = 0;
        decimalNumber = tg;
        strTempVal = "";
        if (decimalNumber == 0)
            strTempVal = "0";
        while (decimalNumber != 0) {
            mod = (int) (decimalNumber % strBaseDigits.length());
            strTempVal = strBaseDigits.substring(mod, mod + 1) + strTempVal;
            decimalNumber = decimalNumber / strBaseDigits.length();
        }
        String so0 = "";
        for (int i = 0; i < 3 - strTempVal.length(); i++) {
            so0 += "0";
        }
        strTempVal = so0 + strTempVal;
        xau = strTempVal + xau;
        return xau;
    }

    public static String genRandomCode() {
        long decimalNumber = System.nanoTime() + 69;
        String strBaseDigits = "0123456789";
        StringBuilder strTempVal = new StringBuilder();
        int mod = 0;
        while (decimalNumber != 0) {
            mod = (int) (decimalNumber % 10);
            strTempVal.insert(0, strBaseDigits.substring(mod, mod + 1));
            decimalNumber = decimalNumber / 10;
        }
        return strTempVal.substring(strTempVal.length() - 6);
    }

    /**
     * Gets the random long.
     *
     * @return the random long
     **/
    public static long getRandomLong() {
        long currentMilitime = System.currentTimeMillis() * 100;
        long nanotime = (System.nanoTime() % 100);
        return currentMilitime + nanotime;
    }

    /**
     * Encrypt md5.
     *
     * @param input the input
     * @return the string
     **/
    public static String encryptMd5(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(), 0, input.length());
            return new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    // truyền vào string, trả về long
    public static long getDayInLong(String dayString) {
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            calendar.setTime(df.parse(dayString));
            return calendar.getTimeInMillis();
        } catch (Exception e) {

        }
        return -1;
    }

    public static String getSimpleDay() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(calendar.getTime());
    }

    public static String getSimpleDay(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(calendar.getTime());
    }

    public static String getDateInFormat(long time, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat df = new SimpleDateFormat(format);
        return getDateInFormat(time, format, 0);
    }

    public static String getDateInFormat(long time, String format, int timeZone) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time + timeZone * DateTimeHelper.ONE_HOUR_IN_MILLISECOND);
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(calendar.getTime());
    }

    public static String getDay6Char() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        Date resultdate = new Date(
                System.currentTimeMillis() + DateTimeHelper.DEFAULT_TIME_ZONE * DateTimeHelper.ONE_HOUR_IN_MILLISECOND);
        return sdf.format(resultdate);
    }

    public static int getSimpleDayToInt(long time) {
        return getSimpleDayToInt(time, TimeZone.getDefault().getDisplayName());
    }

    public static int getSimpleDayToInt(long time, String timeZoneId) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        df.setTimeZone(TimeZone.getTimeZone(timeZoneId));
        return Integer.parseInt(df.format(calendar.getTime()));
    }

    public static long getMillisecFromDateString(String dateString) {

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        try {
            Date date = format.parse(dateString);
            // System.out.println(date.toString());
            return date.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }

    public static long getMillisecFromDateString(String dateString, String sFormat) {

        SimpleDateFormat format = new SimpleDateFormat(sFormat);

        try {
            Date date = format.parse(dateString);
            // System.out.println(date.toString());
            return date.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }

    public static long getMillisecFromDateString(String dateString, String sFormat, String timeZoneId) {

        SimpleDateFormat format = new SimpleDateFormat(sFormat);
        format.setTimeZone(TimeZone.getTimeZone(timeZoneId));

        try {
            Date date = format.parse(dateString);
            // System.out.println(date.toString());
            return date.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }

    public static String numberWithCommas(long number) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        return numberFormat.format(number);
    }

    public static String getSeatName(List<String> listSeatId) {
        StringBuilder res = new StringBuilder();
        for (String s : listSeatId) {
            res.append(s).append(",");
        }
        return res.substring(0, res.length() - 1);
    }

    static String removeAccent(String s) {
        if (StaticMethod.isBlank(s))
            return "";
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        s = pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replaceAll("đ", "d").trim();
        char[] tg = s.toCharArray();
        String kq = "";
        if (tg[0] != ' ')
            kq += tg[0];
        for (int i = 1; i < s.length() - 1; i++) {
            if (tg[i] == ' ') {
                if (tg[i - 1] != ' ') {
                    kq += tg[i];
                }
            } else {
                kq += tg[i];
            }
        }
        if (tg[s.length() - 1] != ' ')
            kq += tg[s.length() - 1];
        return kq;
    }

    public static boolean checkPartnerCompanyId(String companyId) {
        String[] listValid = { "TC1OHntfnujP" };
        return Arrays.binarySearch(listValid, companyId) >= 0;
    }

    public static String GetRequestParams(HttpServletRequest req) {
        String res = "";
        Map<String, String[]> paramKeys = req.getParameterMap();
        res = new Gson().toJson(paramKeys);
        return res;
    }

    public static boolean isValidatePhoneNumber(String phoneNumber) {
        String[] numArr = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        StringBuilder phone = new StringBuilder();

        String[] strArr = phoneNumber.split("");

        for (String s : strArr) {
            boolean check = false;
            for (String s1 : numArr) {
                if (s.equals(s1)) {
                    check = true;
                    break;
                }
            }

            if (check) {
                phone.append(s);
            }
        }
        return isNumber(phone.toString());
    }

    private static boolean isNumber(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidateMail(String email) {
        if (email == null || email.length() == 0)
            return false;
        String[] listEmail = email.split(",");
        String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        boolean isValid = true;
        Matcher matcher;
        for (String s : listEmail) {
            matcher = pattern.matcher(s);
            isValid &= matcher.matches();
        }
        return isValid;
    }

    public static String handleTextToAddToGoogleDoc(String inputText) {
        StringBuilder outputText = new StringBuilder();
        if (StaticMethod.isNotBlank(inputText)) {
            String txt = StaticMethod.unAccent(inputText.trim()).toLowerCase();
            System.out.println(txt);
            String[] lst = txt.split(" ");
            for (String s : lst) {
                char[] ds = s.toCharArray();
                StringBuilder sub = new StringBuilder();
                for (char key : ds) {
                    if (ds.length == 1) {
                        sub.append(key).append(" ").append(key).append(key);
                    } else {
                        sub.append(key);
                    }
                    outputText.append(" ").append(sub);
                }
            }
        }

        log.log(Level.INFO, "output: " + outputText.toString());
        return outputText.toString();
    }

    public static String handleSearchText(String inputText) {
        if (StaticMethod.isNotBlank(inputText)) {
            String[] txtArr = inputText.split("");
            StringBuilder output = new StringBuilder();

            for (int i = 0; i < txtArr.length; i++) {
                StringBuilder sub = new StringBuilder();

                for (int j = i; j < txtArr.length; j++) {
                    sub.append(txtArr[j]);
                    output.append(" ").append(sub);
                }
            }

            return output.toString();
        }
        return "";
    }

    public static String convertDoubleToStringWithoutE(Double value) {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(8);
        return df.format(value);
    }

    /**
     * @param t
     * @return
     * @description Get all field from object
     */
    @SuppressWarnings("rawtypes")
    public static <T> List<Field> getFields(T t) {
        List<Field> fields = new ArrayList<>();
        Class clazz = t.getClass();

        while (clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }

        return fields;
    }

    public static List<String> getListDynamicFieldInMessage(String message) {
        // Get dynamic fields from message: ${routeName}, ${ticketCode}
        List<String> listDynamicField = new ArrayList<>();

        // regex replace message
        String regex = "[\\$]{1}[\\{]{1}[A-Za-z]+[\\}]{1}";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(message.replaceAll("\\s", ""));

        while (matcher.find()) {
            listDynamicField.add(matcher.group());
        }

        return listDynamicField;
    }

    /**
     * 6 digit
     *
     * @return
     */
    public static int genOTPCode() {
        int otpReturn = 0;
        int[] otpArr = new int[6];
        int otpCode = (new Random().nextInt(9) + 1);

        otpArr[0] = otpCode;

        for (int i = 1; i <= 5; i++) {
            otpCode = new Random().nextInt(10);
            otpArr[i] = otpCode;
        }

        System.out.println("otp arr: " + new Gson().toJson(otpArr));
        otpReturn = (int) (otpArr[0] * Math.pow(10, 5));
        for (int i = 1; i < otpArr.length; i++) {
            otpReturn += otpArr[i] * Math.pow(10, 5 - i);
        }

        return otpReturn;
    }

    public static String handleListToSearchStr(List<Integer> list) {
        StringBuilder str = new StringBuilder();
        if (list != null) {
            for (int ele : list) {
                if ((ele + "").length() == 1) {
                    str.append(ele).append(" ").append(ele).append(ele).append(" ");
                } else {
                    str.append(ele);
                }
            }
        }

        log.log(Level.INFO, "lst: " + str.toString());
        return str.toString();
    }

    public static <T> T requestObjectParser(HttpServletRequest req, Class<T> type, boolean validate) throws IOException, MklException {
        StringBuilder jsonBuilder = new StringBuilder();
        BufferedReader br = req.getReader();
        String input;

        while ((input = br.readLine()) != null) {
            jsonBuilder.append(input);
        }
        T object = new Gson().fromJson(jsonBuilder.toString(), type);

        if (object == null) {
            throw new MklException(HttpStatus.SC_BAD_REQUEST, ErrorCode.NULL_OR_EMPTY.getValue(),
                    "request params null");
        }
        
        if (validate) {
            validate(object);
        }

        return object;
    }

    /**
     * @param req
     * @param type
     * @return
     * @throws IOException
     * @throws MklException
     * @author baoquangtu
     */
    public static <T> T requestObjectParser(HttpServletRequest req, Class<T> type) throws IOException, MklException {
        return requestObjectParser(req, type, true);
    }

    /**
     * @param req
     * @param clazz
     * @return
     * @throws IOException
     * @throws MklException
     */
    public static <T> List<T> requestListObjectParser(HttpServletRequest req, Class<T[]> clazz)
            throws IOException, MklException {
        StringBuilder jsonBuilder = new StringBuilder();
        BufferedReader br = req.getReader();
        String input;

        while ((input = br.readLine()) != null) {
            jsonBuilder.append(input);
        }
        T[] arr = new Gson().fromJson(jsonBuilder.toString(), clazz);

        if (arr == null) {
            throw new MklException(HttpStatus.SC_BAD_REQUEST, ErrorCode.NULL_OR_EMPTY.getValue(),
                    "request params null");
        }

        for (T object : arr) {
            validate(object);
        }

        return Arrays.asList(arr);
    }

    private static <T> void validate(T object) throws MklException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(object);

        if (violations != null) {
            for (ConstraintViolation<T> violation : violations) {
                throw new MklException(HttpStatus.SC_BAD_REQUEST, ErrorCode.INVALID.getValue(),
                        violation.getMessage());
            }
        }
    }

    private static Map<String, Object> parseParams(Map<String, String[]> params) {
        Map<String, Object> paramMap = new HashMap<>();

        for (String key : params.keySet()) {
            if (key.endsWith("[]")) {
                paramMap.put(key.substring(0, key.length()-2), params.get(key));
            } else {
                paramMap.put(key, params.get(key)[0]);
            }
        }

        return paramMap;
    }

    public static <T> T parseRequestParams(HttpServletRequest req, Class<T> type) throws MklException {
        Gson gson = new Gson();

        Map<String, Object> params = parseParams(req.getParameterMap());
                
        JsonElement json = gson.toJsonTree(params);
        T object = gson.fromJson(json, type);

        validate(object);

        return object;
    }

    /**
     * @param fromObj
     * @param toObj
     * @return
     * @throws MklException
     */
    @SuppressWarnings("unchecked")
    public static <T> T mergeObject(Object fromObj, T toObj) throws MklException {
        if (toObj == null) {
            return (T) fromObj;
        }
        // Check from object
        Field[] fieldsInClass = fromObj.getClass().getDeclaredFields();

        for (Field field : fieldsInClass) {
            if (field.getType().isPrimitive()) {
                throw new MklException(HttpStatus.SC_INTERNAL_SERVER_ERROR, ErrorCode.INVALID.getValue(),
                        Object.class.getSimpleName() + " can not have primary field");
            }
        }

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(gson.toJson(fromObj), JsonElement.class).getAsJsonObject();
        JsonObject jsonToObject = gson.fromJson(gson.toJson(toObj), JsonElement.class).getAsJsonObject();

        Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            jsonToObject.add(entry.getKey(), entry.getValue());
        }

        toObj = (T) gson.fromJson(jsonToObject, toObj.getClass());

        return toObj;
    }

    /**
     * @param date
     * @return
     * @throws MklException
     */
    public static void checkValidDate(int date) throws MklException {
        // Convert to milisecond and reserve
        int dateConvert = CommonMethod.getSimpleDayToInt(CommonMethod.getMillisecFromDateString(date + ""));

        if (dateConvert != date) {
            throw new MklException(HttpStatus.SC_BAD_REQUEST, ErrorCode.INVALID.getValue(), "date invalid");
        }
    }


    public static boolean objectIsChanged(Object first, Object second) {
        if (first == null ^ second == null) {
            return true;
        } else if (first != null && second != null) {
            return !first.equals(second);
        } else {
            return false;
        }
    }
    
    public static String getFromProperties(String key) throws IOException {
        Properties props = new Properties();
        props.load(CommonMethod.class.getClassLoader().getResourceAsStream("project.properties"));

        String value = props.getProperty(key);
        
        return value;
    }
    
    public static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        }

        return sb.toString();
    }
}
