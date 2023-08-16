package io.github.ponderyao.boot.common;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Getter;
import lombok.Setter;

/**
 * BaseEntity
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Getter
@Setter
public class BaseEntity implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = -4810109001438537644L;
    
    private Date createTime;
    
    private Date updateTime;

    @TableLogic
    private boolean valid;
    
}
