package com.Ambition.controller;

import com.Ambition.Utils.FileUtils;
import com.Ambition.dto.ResultData;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ImgController {

    @PostMapping("/img/upload")
    public byte[] handleFileUpload(@RequestParam("files") MultipartFile[] files,@RequestParam("userCode") String userCode) throws Exception {
            System.out.println("上传的图片数：" + files.length);
            List<Map<String, Object>> root = new ArrayList<Map<String, Object>>();
            String suffixName = null;
            for (MultipartFile file : files) {    //循环保存文件
                Map<String, Object> result = new HashMap<String, Object>();//一个文件上传的结果
                String result_msg = "";//上传结果信息

                if (file.getSize() / 1000 > 5000) {
                    result_msg = "图片大小不能超过5000KB";
                } else {
                    //判断上传文件格式
                    String fileType = file.getContentType();
                    if (fileType.equals("image/jpeg") || fileType.equals("image/png")) {
                        // 要上传的目标文件存放的绝对路径
                        final String localPath = "/home/超市管理系统/img";
                        //上传后保存的文件名(需要防止图片重名导致的文件覆盖)
                        //获取文件名
                        String fileName = file.getOriginalFilename();
                        //获取文件后缀名
                        suffixName = fileName.substring(fileName.lastIndexOf("."));
                        //重新生成文件名
                        fileName = userCode + suffixName;
                        String fileName1 = userCode + ".jpg";
                        String fileName2 = userCode + ".png";
                        if (FileUtils.upload(file, localPath, fileName,fileName1,fileName2)) {
                            //文件存放的相对路径(一般存放在数据库用于img标签的src)
                            String relativePath = "img/" + fileName;
                            result.put("relativePath", relativePath);//前端根据是否存在该字段来判断上传是否成功
                            result_msg = "图片上传成功";
                        } else {
                            result_msg = "图片上传失败";
                        }
                    } else {
                        result_msg = "图片格式不正确";
                    }
                }
                result.put("result_msg", result_msg);
                root.add(result);
            }
            File file = new File("/home/超市管理系统/img/" + userCode + suffixName);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());
            ResultData resultData = new ResultData();
            resultData.setCode(200);
            resultData.setMsg("上传成功");
            resultData.setData(bytes);
            inputStream.close();
            return bytes;
        }

    @PostMapping(value = "/img/download",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] handleFileDownload(@RequestParam("userCode") String userCode) throws Exception {
        File file = new File("/home/超市管理系统/img/"+userCode+".png");
        if(!file.exists()){
            file = new File("/home/超市管理系统/img/"+userCode+".jpg");
        }
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        inputStream.close();
        return bytes;
    }
}
