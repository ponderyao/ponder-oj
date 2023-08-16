package io.github.poj.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.github.poj.common.Response;
import io.github.poj.service.FileService;
import lombok.RequiredArgsConstructor;

/**
 * FileController: 文件接口
 *
 * @author PonderYao
 * @since 1.0.0
 */
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    
    private final FileService fileService;

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public Response<String> uploadFile(@RequestPart("file") MultipartFile file) {
        String filePath = fileService.uploadFile(file);
        return Response.success(filePath);
    }

    /**
     * 下载文件
     */
    @GetMapping("/download")
    public void downloadFile(@RequestParam("filePath") String filePath, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + filePath);
        fileService.downloadFile(filePath, response.getOutputStream());
    }
    
}
