package salesSystem.bus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import salesSystem.bus.domain.Goods;
import salesSystem.bus.domain.Inport;

import salesSystem.bus.mapper.GoodsMapper;
import salesSystem.bus.mapper.InportMapper;

import salesSystem.bus.mapper.ProviderMapper;
import salesSystem.bus.service.InportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import salesSystem.bus.vo.InportVO;
import salesSystem.sys.utils.DataGridView;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-23
 */
@Service
public class InportServiceImpl extends ServiceImpl<InportMapper, Inport> implements InportService {

    @Autowired
    private InportMapper inportMapper;

    @Autowired
    private ProviderMapper providerMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Value("${upload.upload-root-path}")
    private String uploadPath;

    @Override
    public DataGridView loadAllInport(InportVO inportVO) {
        QueryWrapper<Inport> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(inportVO.getProviderid() != null, "providerid", inportVO.getProviderid());
        queryWrapper.eq(inportVO.getGoodsid() != null, "goodsid", inportVO.getGoodsid());
        queryWrapper.ge(inportVO.getStartTime() != null, "inporttime", inportVO.getStartTime());
        queryWrapper.le(inportVO.getEndTime() != null, "inporttime", inportVO.getEndTime());

        queryWrapper.orderByDesc("inporttime");
        //分页
        Page page = new Page<>(inportVO.getPage(), inportVO.getLimit());
        //查询数据库
        List<Inport> inportList = inportMapper.selectPage(page, queryWrapper).getRecords();

        for (Inport i : inportList) {
            if (null != i || null != i.getGoodsid()) {
                String providername = providerMapper.selectById(i.getProviderid()).getProvidername();
                Goods goods = goodsMapper.selectById(i.getGoodsid());
                String goodsname = goods.getGoodsname();
                String size = goods.getSize();
                i.setSize(size);
                i.setGoodsname(goodsname);
                i.setProvidername(providername);
            }
        }
        return new DataGridView(page.getTotal(), inportList);
    }

    @Transactional
    @Override
    public void deleteInport(Integer id, Integer num) {

        Inport inport = inportMapper.selectById(id);
        Integer goodsid = inport.getGoodsid();

        goodsMapper.updateGoodsNumberById(goodsid, -num);

        inportMapper.deleteById(id);

    }

    @Transactional
    @Override
    public void addInport(Inport inport) {
        Integer number = inport.getNumber();
        Integer goodsid = inport.getGoodsid();
        goodsMapper.updateGoodsNumberById(goodsid, number);
        inportMapper.insert(inport);
    }

    @Transactional
    @Override
    public void updateInport(Inport inport) {
        Inport old = inportMapper.selectById(inport.getId());

        Goods goods = goodsMapper.selectById(old.getGoodsid());
        // 添加后的num 1240  oldAddNum 120 newAdd 240  now = 1240-120+240
        goods.setNumber(goods.getNumber() - old.getNumber() + inport.getNumber());

        goodsMapper.updateById(goods);

        inportMapper.updateById(inport);
    }


}
