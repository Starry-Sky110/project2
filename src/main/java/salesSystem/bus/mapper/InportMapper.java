package salesSystem.bus.mapper;

import salesSystem.bus.domain.Inport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-23
 */
public interface InportMapper extends BaseMapper<Inport> {

    void updateNumberById(Integer id, Integer number);
}
