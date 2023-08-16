package io.github.ponderyao.boot.service.impl;

import javax.servlet.ServletOutputStream;

import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.multipart.MultipartFile;

import io.github.ponderyao.boot.common.ErrorCode;
import io.github.ponderyao.boot.config.MinioConfig;
import io.github.ponderyao.boot.exception.BusinessException;
import io.github.ponderyao.boot.exception.ThrowUtils;
import io.github.ponderyao.boot.service.FileService;
import io.github.ponderyao.boot.utils.FileUtils;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;

/**
 * FileServiceImpl: 文件服务实现
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    
    private final MinioClient minioClient;
    
    private final MinioConfig minioConfig;
    
    @Override
    public String uploadFile(MultipartFile file) {
        try {
            PutObjectArgs putObjectArgs = makePutObjectArgs(file, minioConfig.getBucket());
            minioClient.putObject(putObjectArgs);
            // 返回文件的存储路径
            return FileUtils.getFilePath(minioConfig.getEndpoint(), minioConfig.getBucket(), putObjectArgs.object());
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("文件上传失败", e);
        }
    }

    @Override
    public void downloadFile(String filePath, ServletOutputStream outputStream) {
        try {
            GetObjectArgs getObjectArgs = makeGetObjectArgs(filePath, minioConfig.getBucket());
            GetObjectResponse response = minioClient.getObject(getObjectArgs);
            byte[] buf = new byte[1024];
            int len;
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()){
                while ((len = response.read(buf)) != -1){
                    os.write(buf,0, len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();
                outputStream.write(bytes);
                outputStream.flush();
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("文件下载失败", e);
        }
    }
    
    private PutObjectArgs makePutObjectArgs(MultipartFile file, String bucket) throws Exception {
        checkBucket(bucket);
        // 生成唯一文件名，作为 objectName
        String uniqueFileName = FileUtils.generateFileName(file.getOriginalFilename());
        return PutObjectArgs.builder()
            .object(uniqueFileName)
            .bucket(bucket)
            .contentType(file.getContentType())
            .stream(file.getInputStream(), file.getSize(),-1)
            .build();
    }
    
    private GetObjectArgs makeGetObjectArgs(String filePath, String bucket) throws Exception {
        checkBucket(bucket);
        return GetObjectArgs.builder()
            .bucket(minioConfig.getBucket())
            .object(FileUtils.getFileName(filePath))
            .build();
    }
    
    private void checkBucket(String bucket) throws Exception {
        // 校验文件存储桶是否存在
        boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
        ThrowUtils.throwIf(!bucketExists, ErrorCode.SYSTEM_ERROR, "系统文件存储配置异常");
    }
    
}
