package io.github.ponderyao.boot.common;

import javax.validation.GroupSequence;

/**
 * ValidationOrderGroup: 分组校验顺序
 *
 * @author PonderYao
 * @since 1.0.0
 */
@GroupSequence({ValidationOrderGroup.First.class, ValidationOrderGroup.Second.class, ValidationOrderGroup.Third.class})
public interface ValidationOrderGroup {
    
    interface First {}
    
    interface Second {}
    
    interface Third {}
    
}
