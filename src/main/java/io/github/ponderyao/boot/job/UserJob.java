package io.github.ponderyao.boot.job;

import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.ponderyao.boot.model.dto.user.UserPageQryDTO;
import io.github.ponderyao.boot.model.entity.User;
import io.github.ponderyao.boot.model.enums.user.SystemRole;
import io.github.ponderyao.boot.repository.UserRepository;
import io.github.ponderyao.boot.utils.DateTimeUtils;
import lombok.RequiredArgsConstructor;

/**
 * UserJob: 用户相关定时任务
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Component("userJob")
@RequiredArgsConstructor
public class UserJob {
    
    private final UserRepository userRepository;

    /**
     * 任务：删除一个月前被禁的用户
     */
    public void removeBanUserMonthAgo() {
        UserPageQryDTO dto = new UserPageQryDTO();
        dto.setPageIndex(1);
        dto.setPageSize(50);
        dto.setSystemRole(SystemRole.BAN.getCode());
        dto.setEndTime(DateTimeUtils.getPastUnitAgo(1, ChronoUnit.MONTHS));
        dto.setOrderField("update_time");
        Page<User> userPage = userRepository.selectUserPage(dto);
        List<User> userList = userPage.getRecords();
        for (User user : userList) {
            userRepository.removeById(user);
        }
    }
    
}
