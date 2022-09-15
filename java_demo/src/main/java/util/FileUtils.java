package util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author xuhongyu
 * @create 2022-01-13 1:19 下午
 */
public class FileUtils {

    /**
     *  MultipartFile 转String
     * @param multipartFile
     * @return
     */
    public static String multipartFileToString(MultipartFile multipartFile) {
        InputStreamReader isr;
        BufferedReader br;
        StringBuilder txtResult = new StringBuilder();
        try {
            isr = new InputStreamReader(multipartFile.getInputStream(), StandardCharsets.UTF_8);
            br = new BufferedReader(isr);
            String lineTxt;
            while ((lineTxt = br.readLine()) != null) {
                txtResult.append(lineTxt);
            }
            isr.close();
            br.close();
            return txtResult.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }



    /**
     * 将图片转换成base64格式进行存储
     *
     * @param imagePath
     * @return
     */
    public static String encodeToString(String imagePath) {
        String type = StringUtils.substring(imagePath, imagePath.lastIndexOf(".") + 1);
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //io流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            //写入流中
            ImageIO.write(image, type, baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //转换成字节
        byte[] bytes = baos.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        //转换成base64串
        String png_base64 = encoder.encodeBuffer(bytes).trim();
        //删除 \r\n
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");
        return "data:image/jpg;base64," + png_base64;
//        String imageString = null;
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        try {
//            ImageIO.write(image, type, bos);
//            byte[] imageBytes = bos.toByteArray();
//            BASE64Encoder encoder = new BASE64Encoder();
//            imageString = encoder.encode(imageBytes);
//            bos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return imageString;
    }

    public static String readFile(String file) {
        File file1 = new File(file);
        return readFile(file1);
    }

    /**
     * 读取文件
     *
     * @param file
     * @return
     */
    public static String readFile(File file) {
        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

    /**
     * 创建文件
     */
    public static String mkdirs(String file) {
        File f = new File(file);
        if (!f.exists()) {
            f.mkdirs();
        }
        return file;
    }

    /**
     * 创建文件 注意若存在先创建后删除
     * fileName    文件名称
     *
     * @param path
     * @param fileName
     * @return
     */
    public static File createFile(String path, String fileName) {
        File file = new File(path);
        if (!file.exists()) {
            //创建目录
            file.mkdirs();
        }
        File fileLast = new File(path, fileName);
        try {
            if (fileLast.exists()) {
                // 若存在，先删除后创建
                delete(fileLast);
            }
            fileLast.createNewFile();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileLast;
    }


    public static boolean delete(File file) {
        if (file == null) {
            return true;
        }
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                delete(f);
            }
            return file.delete();
        }
    }


    /**
     * 写入文件
     * 此工具类适合写小文件
     */
    public static void writeToFile(File file, String data) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(data);
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 图片转base64字符串
     *
     * @param imgFile 图片路径
     * @return
     */
    public static String imageToBase64Str(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * 获取jvm临时文件地址
     * @return
     */
    public static String getTempPath(){
        String tempPath = System.getProperty("java.io.tmpdir") + File.separator;
        return tempPath;
    }

    /**
     * 文件下载
     *
     * @param multipartFile
     * @param fileName
     * @return
     */
    public static File loadFile(MultipartFile multipartFile, String fileName) {
        String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);

        File file = null;
        InputStream is = null;
        try {
            is = new BufferedInputStream(multipartFile.getInputStream());
            String tempPath = System.getProperty("java.io.tmpdir") + File.separator;

            String filePath = tempPath + "/" + fileName + "." + suffix;
            if (StringUtils.isEmpty(suffix)) {
                filePath = filePath.substring(0, filePath.length() - 1);
            }
            file = new File(filePath);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] b = new byte[1024];
            while ((is.read(b)) != -1) {
                // 写入数据
                fos.write(b);
            }
            is.close();
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException("文件下载失败。");
        }
        return file;
    }
}
