package com.nnk.springboot;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.services.TradeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeServiceTest {
    @Autowired
    private TradeService tradeService;
    @MockBean
    private TradeRepository tradeRepository;

    private Trade trade1;
    private Trade trade2;
    private Trade trade3;
    private List<Trade> tradeList;

    @Before
    public void setUp() {
        trade1 = new Trade("Trade Account 1", "Type");
        trade2 = new Trade("Trade Account 2", "Type");
        trade2.setTradeId(2);
        trade3 = new Trade("Trade Account 3", "Type");
        tradeList = Arrays.asList(trade1, trade2, trade3);
    }

    @Test
    public void findAllTest() {
        when(tradeRepository.findAll()).thenReturn(this.tradeList);
        List<Trade> TradeTest = tradeService.findAll();
        assertThat(TradeTest).isSameAs(tradeList);
        assertThat(TradeTest).hasSize(3);
    }

    @Test
    public void createTradeTest() {
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade1);
        Trade TradeTest = tradeService.createTrade(this.trade1);
        assertThat(TradeTest).isEqualTo(this.trade1);
    }

    @Test
    public void findByIdTest() {

        when(tradeRepository.findById(2)).thenReturn(Optional.ofNullable(this.trade2));
        Trade TradeFoundById = tradeService.findById(2).get();
        assertThat(TradeFoundById).isEqualTo(this.trade2);

    }

    @Test
    public void deleteTest() {
        tradeService.delete(trade3);
        verify(tradeRepository, Mockito.times(1)).delete(trade3);

    }
}
