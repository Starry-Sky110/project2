package salesSystem.sys.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.utils.FileUploadAndDownloadUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("file")
public class FileController {

    @Value("${upload.upload-root-path}")
    private String userUploadPath;

    @Value("${upload.upload-goods-path}")
    private String goodsUploadPath;

    @RequestMapping("uploadFile")
    @ResponseBody    //mf 与前台的 field: 'mf' 同名
    public DataGridView uploadFile(MultipartFile mf, String type) {
        //获取文件名
        String oldName = mf.getOriginalFilename();
        //创建文件夹名 2020-04-20
        String dirName = FileUploadAndDownloadUtils.getDirNameByCurrentTime();
//        File dirFile = null;
//        if ("user".equals(type)) {
//            dirFile = new File(userUploadPath, dirName);
//        } else if ("goods".equals(type)) {
//            dirFile = new File(goodsUploadPath, dirName);
//        }
        File dirFile = new File(userUploadPath, dirName);
        if (!dirFile.exists()) {
            //如果没有就创建
            dirFile.mkdirs();
        }
        //生成新的文件名
        String newFileName = FileUploadAndDownloadUtils.getNewFileNameUseUUID(oldName) + "_temp";
        //文件对象
        File f = new File(dirFile, newFileName);

        try {//上传文件
            mf.transferTo(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Object> src = new HashMap<>();
//        src.put("src", "/" + type + "/" + dirName + "/" + newFileName);
        src.put("src", "/" + dirName + "/" + newFileName);

        return new DataGridView(src);
    }

    //showImage 显示图片
    @RequestMapping("showImage")
    public ResponseEntity<Object> showImage(String filePath) {
        System.out.println(filePath);

//        String type = filePath.substring(1, filePath.indexOf("/"));
//        if ("user".equals(type)) {
//            return FileUploadAndDownloadUtils.showImage(userUploadPath, filePath);
//        } else if ("goods".equals(type)) {
//            return FileUploadAndDownloadUtils.showImage(goodsUploadPath, filePath);
//        }

        return FileUploadAndDownloadUtils.showImage(userUploadPath, filePath);
    }

}
