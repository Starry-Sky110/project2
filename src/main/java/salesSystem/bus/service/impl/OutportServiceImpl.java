package salesSystem.bus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import salesSystem.bus.domain.Goods;
import salesSystem.bus.domain.Inport;
import salesSystem.bus.domain.Outport;
import salesSystem.bus.mapper.GoodsMapper;
import salesSystem.bus.mapper.InportMapper;
import salesSystem.bus.mapper.OutportMapper;
import salesSystem.bus.mapper.ProviderMapper;
import salesSystem.bus.service.OutportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import salesSystem.bus.vo.OutPortVO;
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
public class OutportServiceImpl extends ServiceImpl<OutportMapper, Outport> implements OutportService {

    @Autowired
    private OutportMapper outportMapper;

    @Autowired
    private InportMapper inportMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private ProviderMapper providerMapper;

    @Transactional
    @Override
    public void addOutPort(Outport outport) {

        //得到进货单号
        Integer inportid = outport.getInportid();
        //进货订单
        Inport inport = inportMapper.selectById(inportid);
        //支付类型 paytype
        outport.setPaytype(inport.getPaytype());
        //goodsid
        outport.setGoodsid(inport.getGoodsid());
        //providerid
        outport.setProviderid(inport.getProviderid());
        //退货价格
        outport.setOutportprice(inport.getInportprice());

        outportMapper.insert(outport);
        //跟新进货单数据
//        inport.setNumber(inport.getNumber() - outport.getNumber());
        inportMapper.updateNumberById(inport.getId(), -outport.getNumber());
        //商品表(仓库)数据更新
        goodsMapper.updateGoodsNumberById(inport.getGoodsid(), -outport.getNumber());

    }

    @Override
    public DataGridView loadAllOutport(OutPortVO outPortVO) {
        QueryWrapper<Outport> qw = new QueryWrapper<>();
        qw.eq(outPortVO.getProviderid() != null, "providerid", outPortVO.getProviderid());
        qw.eq(outPortVO.getGoodsid() != null, "goodsid", outPortVO.getGoodsid());
        qw.ge(outPortVO.getStartTime() != null, "outporttime", outPortVO.getStartTime());
        qw.le(outPortVO.getEndTime() != null, "outporttime", outPortVO.getEndTime());

        List<Outport> outports = outportMapper.selectList(qw);

        for (Outport o : outports) {
            Integer goodsid = o.getGoodsid();
            Integer providerid = o.getProviderid();
            Goods goods = goodsMapper.selectById(goodsid);
            o.setGoodsname(goods.getGoodsname());
            o.setProvidername(providerMapper.selectById(providerid).getProvidername());
            o.setSize(goods.getSize());
        }

        return new DataGridView(outports);
    }
}
