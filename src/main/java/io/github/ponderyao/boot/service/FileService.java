package io.github.ponderyao.boot.service;

import javax.servlet.ServletOutputStream;

import org.springframework.web.multipart.MultipartFile;

/**
 * FileService: 文件服务
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface FileService {

    /**
     * 文件上传
     * 
     * @param file 文件
     * @return 文件访问路径
     */
    String uploadFile(MultipartFile file);

    /**
     * 文件下载
     * 
     * @param filePath 文件访问路径
     * @param outputStream 输出流
     */
    void downloadFile(String filePath, ServletOutputStream outputStream);
    
}
