package io.github.poj.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.github.poj.common.ErrorCode;
import io.github.poj.constant.SymbolConstant;
import io.github.poj.exception.ThrowUtils;

/**
 * FileUtils: 文件工具类
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class FileUtils {

    /**
     * 生成新的唯一文件名
     * 
     * @param originFileName 原文件名
     * @return 唯一文件名
     */
    public static String generateFileName(String originFileName) {
        String suffix = getFileType(originFileName);
        ThrowUtils.throwIf(StringUtils.isBlank(suffix), ErrorCode.OPERATION_ERROR, "文件名称格式不符合要求");
        return RandomUtils.randomUniqueName() + SymbolConstant.POINT + suffix;
    }

    /**
     * 获取文件类型（后缀）
     * 
     * @param fileName 文件名
     * @return 文件类型
     */
    public static String getFileType(String fileName) {
        int pointIndex = fileName.lastIndexOf(SymbolConstant.POINT);
        if (pointIndex < 0 || pointIndex >= fileName.length() - 1) {
            return null;
        }
        return fileName.substring(pointIndex + 1);
    }

    /**
     * 获取文件存储路径
     * 
     * @param endpoint 存储节点地址
     * @param bucket 存储桶
     * @param fileName 文件名
     * @return 文件存储路径
     */
    public static String getFilePath(String endpoint, String bucket, String fileName) {
        return endpoint + SymbolConstant.SLASH + bucket + SymbolConstant.SLASH + fileName;
    }

    /**
     * 根据文件存储路径获取文件名
     * 
     * @param filePath 文件存储路径
     * @return 文件名
     */
    public static String getFileName(String filePath) {
        int slashIndex = filePath.lastIndexOf(SymbolConstant.SLASH);
        if (slashIndex < 0) {
            return filePath;
        } else if (slashIndex >= filePath.length() - 1) {
            return null;
        } else {
            return filePath.substring(slashIndex + 1);
        }
    }
    
}
