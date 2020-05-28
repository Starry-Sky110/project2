package salesSystem.sys.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import salesSystem.sys.constant.Constant;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileUploadAndDownloadUtils {


    private static SimpleDateFormat simpleDateFormat
            = new SimpleDateFormat("yyyy-MM-dd");

    public static String getDirNameByCurrentTime() {

        return simpleDateFormat.format(new Date());
    }

    public static String getNewFileNameUseUUID(String oldName) {
        //123.png
        String suffix = oldName.substring(oldName.lastIndexOf("."), oldName.length());
        return UUID.randomUUID().toString().replace("-", "").toUpperCase() + suffix;

    }

    /**
     * 返回图片
     *
     * @param uploadPath
     * @param filePath
     * @return
     */
    public static ResponseEntity<Object> showImage(String uploadPath, String filePath) {
        File file = new File(uploadPath, filePath);
        //创建响应头信息的对象
        HttpHeaders header = new HttpHeaders();
        //设置相应内容的类型 application/octet-stream
        header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //设置附件的名称
        header.setContentDispositionFormData("attachment", file.getName());
        byte[] bytes = null;
        ResponseEntity<Object> responseEntity;

        if (file.exists() && !file.isDirectory()) {
            //把图片放到byte里面去
            try {
                //读取文件转换成字节数组
                bytes = FileUtils.readFileToByteArray(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //创建ResponseEntity对象 内容，响应头，响应码 这里是201
            responseEntity = new ResponseEntity<>(bytes, header, HttpStatus.CREATED);
            return responseEntity;
        } else {
            File file1 = new File(Constant.USER_DEFAULT_IMAGE);
            try {
                bytes = FileUtils.readFileToByteArray(file1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            responseEntity = new ResponseEntity<>(bytes, header, HttpStatus.CREATED);
            return responseEntity;
        }

    }

    /**
     * 改名去掉_temp
     *
     * @param uploadPath
     * @param imgpath
     * @return
     */
    public static String changeFileName(String uploadPath, String imgpath) {

        String path = imgpath.replace("_temp", "");
        File file = new File(uploadPath, imgpath);
        File newFile = new File(uploadPath, path);
        file.renameTo(newFile);

        return path;
    }

    public static void deleteTempImage(String uploadPath, String imgpath) {

        //如果不是缺省图片
        if (!imgpath.equals(Constant.USER_DEFAULT_IMAGE)) {
            File file = new File(uploadPath, imgpath);
            //存在的话就删除
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
