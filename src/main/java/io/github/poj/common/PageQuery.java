package io.github.poj.common;

import java.io.Serializable;

import io.github.poj.annotation.RegexValidate;
import io.github.poj.constant.RegexConstant;
import io.github.poj.constant.SqlConstant;
import lombok.Data;

/**
 * PageQuery: 分页查询请求
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Data
public class PageQuery implements Serializable {
    
    private static final long serialVersionUID = -4012338171356263791L;
    
    private int pageSize;
    
    private int pageIndex;
    
    @RegexValidate(regex = RegexConstant.ORDER_FIELD, nullable = true, message = "排序字段格式不符合规范")
    private String orderField;
    
    private String orderByWay = SqlConstant.ASC;
    
}
