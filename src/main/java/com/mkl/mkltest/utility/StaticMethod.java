/**
 *
 */
package com.mkl.mkltest.utility;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.mkl.mkltest.exception.ErrorCode;
import com.mkl.mkltest.exception.MklException;

import org.apache.http.HttpStatus;

/**
 * The Class DobodyMethod.
 *
 * @author root
 */
public class StaticMethod {
    // DECLARE NEEDED PARAMETER FOR CreatePinMapImage method.
    final static String bucket = "dobody-anvui.appspot.com";
    /**
     * Creates the image.
     *
     * @param imageBytes the image bytes
     * @return the image
     * @throws Exception the exception
     **/
    static Logger log = Logger.getLogger(StaticMethod.class.getSimpleName());

    /**
     * Validate a string is null or empty.
     *
     * @param input the input
     * @return true, if successful
     */
    public static boolean isBlank(String input) {
        return (input == null || input.isEmpty() || input.equalsIgnoreCase("null"));
    }

    /**
     * Checks if is not blank.
     *
     * @param input the input
     * @return true, if successful
     */
    public static boolean isNotBlank(String input) {
        return (input != null && !input.isEmpty());
    }

    /**
     * Caculate Distance between 2 point, count by meter.
     *
     * @param lat1 the lat1
     * @param lon1 the lon1
     * @param lat2 the lat2
     * @param lon2 the lon2
     * @return the double
     **/
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    public static boolean isNumber(String temp) {
        boolean isFloat = false;
        for (int i = 0; i < temp.length(); i++) {
            if (temp.charAt(i) <= '9' && temp.charAt(i) >= '0') {
                continue;
            }
            if (temp.charAt(i) == '.' && !isFloat) {
                isFloat = true;
                continue;
            } else
                return false;
        }
        return true;
    }

    /**
     * Deg2rad.
     *
     * @param deg the deg
     * @return the double
     **/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /**
     * Rad2deg.
     *
     * @param rad the rad
     * @return the double
     **/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    /**
     * Un accent.
     *
     * @param s the s
     * @return the string
     */
    public static String unAccent(String s) {
        if (StaticMethod.isBlank(s))
            return "";
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replaceAll("đ", "d");
    }

    /**
     * Validate phone number.
     *
     * @param stateCode the state code
     * @param phone     the phone
     * @return the string
     */
    public static String validatePhoneNumber(String stateCode, String phone) {
        try {
            // remove first +
            if (phone.indexOf("+") == 0) {
                phone = phone.substring(1);
            }
            // remove all first 0
            int count = 0;
            while (phone.indexOf("0") == 0) {
                phone = phone.substring(1);
                count++;
            }
            // if prefix is 00{stateCode} or {stateCode}, remove {stateCode}
            if ((count == 0 || count == 2) && phone.indexOf(stateCode) == 0) {
                phone = phone.substring(stateCode.length() + phone.indexOf(stateCode));
            }

            boolean isNumber = false;

            isNumber = phone.matches("^[0-9]+");

            log.log(Level.INFO, "isNumber: " + isNumber);
            if (!isNumber) {
                return null;
            }
        } catch (Exception e) {
            log.log(Level.INFO, "error: " + e.getMessage());
            phone = null;
        }

        return phone;
    }

    public static boolean validateUserName(String userName) {
        String regexUserName = "[a-zA-Z0-9_-]{5,}";
        Pattern pattern = Pattern.compile(regexUserName);
        Matcher matcher = pattern.matcher(userName);

        if (matcher.matches()) {
            return true;
        }

        return false;
    }

    /**
     * Checks if is valid email address.
     *
     * @param email the email
     * @return true, if is valid email address
     **/
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    /**
     * Validate avatar.
     *
     * @param str the str
     * @return the string
     **/
    public static String ValidateAvatar(String str) {
        str = str.replaceAll("\\s", "");
        int position = str.indexOf("=s");
        if (position > 0) {
            str = str.substring(0, position);
        }
        if (str.contains("http:")) {
            str = str.replace("http:", "");
        } else if (str.contains("https:")) {
            str = str.replace("https:", "");
        }
        return str;
    }

    // public static Image CreateImage(byte[] imageBytes) throws Exception {
    //     try {
    //         // Create Image from Byte[]
    //         log.log(Level.WARNING, "Creating image..");
    //         Image image = ImagesServiceFactory.makeImage(imageBytes);
    //         Format format = image.getFormat();

    //         // Check Byte[] data is Image Format or not.
    //         if (format != Format.BMP && format != Format.GIF && format != Format.ICO && format != Format.JPEG
    //                 && format != Format.PNG && format != Format.TIFF && format != Format.WEBP) {
    //             throw new MklException(HttpStatus.SC_BAD_REQUEST, ErrorCode.NULL_OR_EMPTY.getValue(), 
    //                     "IMAGE_NOT_AN_IMAGE_ERROR");
    //         }
    //         return image;
    //     } catch (Exception e) {
    //         throw e;
    //     }
    // }

    // /**
    //  * Creates the image with out water mark.
    //  *
    //  * @param sFileName the s file name
    //  * @param image     the image
    //  * @return the google image
    //  * @throws Exception the exception
    //  **/
    // public static GoogleImage CreateImageWithOutWaterMark(String sFileName, Image image) throws Exception {
    //     // CHECK IMAGE URL
    //     if (StaticMethod.isBlank(sFileName))
    //         sFileName = System.currentTimeMillis() + "";

    //     // CHECK IMAGE NULL
    //     if (image == null) {
    //         throw new MklException(HttpStatus.SC_BAD_REQUEST, ErrorCode.NULL_OR_EMPTY.getValue(), 
    //                 "IMAGE_NULL");
    //     }

    //     // CREATE GCSSERVICE.
    //     GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder().initialRetryDelayMillis(10)
    //             .retryMaxAttempts(10).totalRetryPeriodMillis(15000).build());
    //     try {
    //         // Write the transformed image back to a Cloud Storage object.
    //         gcsService.createOrReplace(new GcsFilename(bucket, sFileName + ".jpeg"),
    //                 new GcsFileOptions.Builder().mimeType("image/jpeg").build(), ByteBuffer.wrap(image.getImageData()));

    //         // Create gcsLink and gcsBlobKey to return.
    //         String gcsLink = "gs://" + bucket + "/" + sFileName + ".jpeg";
    //         String gcsBlobKey = "/gs/" + bucket + "/" + sFileName + ".jpeg";
    //         return new GoogleImage(sFileName + ".jpeg", gcsLink, gcsBlobKey);
    //     } catch (Exception e) {
    //         throw e;
    //     }
    // }

    /**
     * Chuyen tieng viet co giau thanh khong dau
     */
    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    // /**
    //  * Creates the image.
    //  *
    //  * @param sFileName   the sfile name
    //  * @param imageBytes1
    //  * @return the string
    //  * @throws Exception
    //  **/
    // public static String[] CreateImage(String sFileName, byte[] imageBytes1) throws Exception {
    //     // CHECK IMAGE URL
    //     if (StaticMethod.isBlank(sFileName))
    //         sFileName = System.currentTimeMillis() + "";

    //     // CREATE GCSSERVICE.
    //     GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder().initialRetryDelayMillis(10)
    //             .retryMaxAttempts(10).totalRetryPeriodMillis(15000).build());
    //     try {
    //         // READ PINMAP IMAGE INTO BYTES
    //         Image imageA = ImagesServiceFactory.makeImage(imageBytes1);
    //         Format format = imageA.getFormat();
    //         if (format != Format.BMP && format != Format.GIF && format != Format.ICO && format != Format.JPEG
    //                 && format != Format.PNG && format != Format.TIFF && format != Format.WEBP) {
    //             throw new MklException(HttpStatus.SC_BAD_REQUEST, ErrorCode.NULL_OR_EMPTY.getValue(), 
    //                     "IMAGE_NULL");
    //         }

    //         // Write the transformed image back to a Cloud Storage object.
    //         gcsService.createOrReplace(new GcsFilename(bucket, sFileName + ".jpeg"),
    //                 new GcsFileOptions.Builder().mimeType("image/jpeg").build(),
    //                 ByteBuffer.wrap(imageA.getImageData()));

    //         // Create gcsLink and gcsBlobKey to return.
    //         String gcsLink = "gs://" + bucket + "/" + sFileName + ".jpeg";
    //         String gcsBlobKey = "/gs/" + bucket + "/" + sFileName + ".jpeg";
    //         return new String[] { gcsLink, gcsBlobKey };
    //     } catch (Exception e) {
    //         throw e;
    //     }
    // }

    public static long getFeeByHour(int numberOfSeats) {
        switch (numberOfSeats) {
        case 4:
            return 50000;
        case 7:
            return 70000;
        case 10:
            return 100000;
        case 16:
            return 90000;
        case 29:
            return 150000;
        case 35:
            return 200000;
        default:
            return 300000;
        }
    }

    // /**
    //  * Creates the image.
    //  *
    //  * @param sImageLink the s image link
    //  * @return the string
    //  **/
    // public static String CreateImage(String sFileName, String sImageLink) {
    //     // CHECK IMAGE URL
    //     if (StaticMethod.isBlank(sImageLink))
    //         return "";
    //     sFileName = StaticMethod.isBlank(sFileName) ? System.currentTimeMillis() + "" : sFileName;

    //     // CREATE GCSSERVICE.
    //     GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder().initialRetryDelayMillis(10)
    //             .retryMaxAttempts(10).totalRetryPeriodMillis(15000).build());
    //     try {
    //         // GET IMAGE FROM URL.
    //         URL url = new URL(sImageLink);
    //         byte[] bytes = org.apache.commons.io.IOUtils.toByteArray(url.openStream());
    //         Image imageA = ImagesServiceFactory.makeImage(bytes);

    //         // Write the transformed image back to a Cloud Storage object.
    //         gcsService.createOrReplace(new GcsFilename(bucket, sFileName + ".jpeg"),
    //                 new GcsFileOptions.Builder().mimeType("image/jpeg").build(),
    //                 ByteBuffer.wrap(imageA.getImageData()));
    //         return "gs://" + bucket + "/" + sFileName + ".jpeg";
    //     } catch (Exception e) {
    //         return null;
    //     }
    // }

    // /**
    //  * Delete image.
    //  *
    //  * @param sFileName the sfile name
    //  **/
    // public static void DeleteImage(String sFileName) {
    //     try {
    //         // CHECK FileName
    //         if (StaticMethod.isBlank(sFileName))
    //             return;

    //         // CREATE GCSSERVICE.
    //         GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
    //                 .initialRetryDelayMillis(10).retryMaxAttempts(10).totalRetryPeriodMillis(15000).build());
    //         // Execute delete image
    //         boolean rs = gcsService.delete(new GcsFilename(bucket, sFileName));
    //         int i = 0;
    //         while (!rs) {
    //             if (i == 10)
    //                 return;
    //             rs = gcsService.delete(new GcsFilename(bucket, sFileName));
    //             i++;
    //         }
    //     } catch (Exception e) {
    //         return;
    //     }
    // }

    // /**
    //  * Delete image.
    //  *
    //  * @param listFileName the sfile name
    //  **/
    // public static void DeleteImage(List<GoogleImage> listFileName) {
    //     try {
    //         // CHECK FileName
    //         if (listFileName.size() == 0)
    //             return;

    //         // CREATE GCSSERVICE.
    //         GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
    //                 .initialRetryDelayMillis(10).retryMaxAttempts(10).totalRetryPeriodMillis(15000).build());
    //         // Execute delete image
    //         for (GoogleImage googleImage : listFileName) {
    //             boolean rs = gcsService.delete(new GcsFilename(bucket, googleImage.getFileName()));
    //             int i = 0;
    //             while (!rs) {
    //                 if (i == 10)
    //                     return;
    //                 rs = gcsService.delete(new GcsFilename(bucket, googleImage.getFileName()));
    //                 i++;
    //             }
    //         }
    //     } catch (Exception e) {
    //         return;
    //     }
    // }

    public static void main(String[] args) {
        System.out.println(unAccent("Việt Đỗ Việt Travel"));
    }

    // get Acronym of string
    public static String getAcronymOfString(String inputString) {
        String outputString = unAccent(inputString);

        String[] strArr = outputString.split(" ");

        outputString = "";
        for (int i = 0; i < strArr.length; i++) {
            String temp = strArr[i];
            if (temp.length() > 0) {
                outputString += temp.substring(0, 1).toUpperCase();
            }
        }

        return outputString;
    }

}
