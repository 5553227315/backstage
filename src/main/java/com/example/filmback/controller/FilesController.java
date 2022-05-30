package com.example.filmback.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.filmback.entity.Files;
import com.example.filmback.entity.SnowflakeIdWorker;
import com.example.filmback.mapper.FilesMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 *文件上传相关接口
 */
@RestController
@RequestMapping("/files")
public class FilesController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Resource
    private FilesMapper filesMapper;

    /**
     * 文件上传接口
     * @param file  前端传递过来的文件
     */
    @PostMapping("/upload")

    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();
        //存储磁盘
        File uploadParentFile = new File(fileUploadPath);
        //判断路径是否存在，不存在就创建一个新的文件目录
        if (!uploadParentFile.exists()){
            uploadParentFile.mkdirs();
        }
        System.out.println(uploadParentFile+"这是文件夹");

        //UUID唯一表示文件名
        String uuid = IdUtil.fastSimpleUUID();
        String filesUUID = uuid + StrUtil.C_DOT + type;
        String url;
        //文件位置及名字
        File uploadFile = new File(fileUploadPath + filesUUID);
        System.out.println(uploadFile+"这是文件");
        //存储数据库
        //雪花算法ID
        SnowflakeIdWorker idWorker=new SnowflakeIdWorker(0,0);
        Long filesId = idWorker.nextId();
        Files files = new Files();

        //先写入才能获取md5,把获取到的文件存储到磁盘目录
        file.transferTo(uploadFile);
        //获取文件md5
        String md5 = SecureUtil.md5(uploadFile);
        //如果文件夹里有文件
        Files dbFilesMd5 = this.getFilesByMd5(md5);
        if(dbFilesMd5 !=null){
            url = dbFilesMd5.getFilesUrl();
            uploadFile.delete();
            System.out.println("文件已经存在");
        }else {
            url = "http://localhost:9090/files/" + filesUUID;
            //配置服务器
//            url = "http://47.106.198.145:9090/files/" + filesUUID;
            System.out.println("文件保存成功");
            files.setFilesId(filesId);
            files.setFilesType(type);
            files.setFilesSize(size/1024);
            files.setFilesUrl(url);
            files.setMd5(md5);
            filesMapper.insert(files);
        }


        return url;
    }

    /**
     * 文件下载接口  http://localhost:9090/files/{filesUUID}
     * 文件下载接口  http://47.106.198.145:9090/files/{filesUUID}
     */
    @GetMapping("/{filesUUID}")
    private void dowload(@PathVariable String filesUUID, HttpServletResponse response) throws IOException {
        //根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + filesUUID);
        ServletOutputStream os = response.getOutputStream();
        //设置输出流的格式
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filesUUID,"UTF-8") );
        response.setContentType("application/octet-stream");

        //读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }

    private Files getFilesByMd5(String md5){
        QueryWrapper<Files> wrapper = new QueryWrapper<>();
        wrapper.eq("MD5",md5);
        List<Files> files = filesMapper.selectList(wrapper);
        if (files.size()==0){
            return null;
        }return files.get(0);
    }
}
