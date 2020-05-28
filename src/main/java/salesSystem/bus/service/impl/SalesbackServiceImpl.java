package salesSystem.bus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import salesSystem.bus.domain.Customer;
import salesSystem.bus.domain.Goods;
import salesSystem.bus.domain.Sales;
import salesSystem.bus.domain.Salesback;
import salesSystem.bus.mapper.CustomerMapper;
import salesSystem.bus.mapper.GoodsMapper;
import salesSystem.bus.mapper.SalesMapper;
import salesSystem.bus.mapper.SalesbackMapper;
import salesSystem.bus.service.SalesbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import salesSystem.bus.vo.SalesBackVO;
import salesSystem.sys.utils.DataGridView;
import salesSystem.sys.utils.ResultObj;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author salesSystem
 * @since 2020-04-25
 */
@Service
public class SalesbackServiceImpl extends ServiceImpl<SalesbackMapper, Salesback> implements SalesbackService {

    @Autowired
    private SalesbackMapper salesbackMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private SalesMapper salesMapper;


    @Override
    public void addSalesBack(Salesback salesback) {

        //销售订单
        Sales sales = salesMapper.selectById(salesback.getSalesid());
        //销售的商品
        salesback.setGoodsid(sales.getGoodsid());
        //客户
        salesback.setCustomerid(sales.getCustomerid());
        //退款方式
        salesback.setPaytype(sales.getPaytype());
        //退货价格
        salesback.setSalebackprice(sales.getSaleprice());

        //更新销售订单
        salesMapper.updateSalesNumberById(sales.getId(), -salesback.getNumber());

        //销售退还。仓库数量增加
        goodsMapper.updateGoodsNumberById(sales.getGoodsid(), salesback.getNumber());

        salesbackMapper.insert(salesback);

    }

    @Override
    public DataGridView loadAllSalesback(SalesBackVO salesBackVO) {
        QueryWrapper<Salesback> qw = new QueryWrapper<>();

        qw.eq(salesBackVO.getCustomerid() != null, "customerid", salesBackVO.getCustomerid());
        qw.eq(salesBackVO.getGoodsid() != null, "goodsid", salesBackVO.getGoodsid());
        qw.ge(salesBackVO.getStartTime() != null, "salesbacktime", salesBackVO.getStartTime());
        qw.le(salesBackVO.getEndTime() != null, "salesbacktime", salesBackVO.getEndTime());

        qw.orderByDesc("salesbacktime");

        Page page = new Page<>(salesBackVO.getPage(), salesBackVO.getLimit());

        List<Salesback> salesbacks = salesbackMapper.selectPage(page, qw).getRecords();

        for (Salesback s : salesbacks) {
            Goods goods = goodsMapper.selectById(s.getGoodsid());
            s.setGoodsname(goods.getGoodsname());
            s.setSize(goods.getSize());
            Customer customer = customerMapper.selectById(s.getCustomerid());
            s.setCustomername(customer.getCustomername());
        }

        return new DataGridView(page.getTotal(), salesbacks);
    }
}
