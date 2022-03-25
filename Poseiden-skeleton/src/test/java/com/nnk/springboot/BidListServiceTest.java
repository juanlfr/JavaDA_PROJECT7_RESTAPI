package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.BidListService;
import org.junit.Test;
import org.junit.Before;
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
public class BidListServiceTest {

    @Autowired
    private BidListService bidListService;
    @MockBean
    private BidListRepository bidListRepository;

    private BidList bidList1;
    private BidList bidList2;
    private BidList bidList3;
    private List<BidList> bidList;

    @Before
    public void setUp() {
        bidList1 = new BidList("Account Test", "Type Test", 10d);
        bidList2 = new BidList("Account Test", "Type Test", 20d);
        bidList2.setBidListId(2);
        bidList3 = new BidList("Account Test", "Type Test", 30d);
        bidList = Arrays.asList(bidList1, bidList2, bidList3);
    }

    @Test
    public void findAllTest() {
        when(bidListRepository.findAll()).thenReturn(this.bidList);
        List<BidList> bidListTest = bidListService.findAll();
        assertThat(bidListTest).isSameAs(bidList);
        assertThat(bidListTest).hasSize(3);
    }

    @Test
    public void createBidListTest() {
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList1);
        BidList bidListTest = bidListService.createBidList(this.bidList1);
        assertThat(bidListTest).isEqualTo(this.bidList1);
    }

    @Test
    public void findByIdTest() {

        when(bidListRepository.findById(2)).thenReturn(Optional.ofNullable(this.bidList2));
        BidList bidListFoundById = bidListService.findById(2).get();
        assertThat(bidListFoundById).isEqualTo(this.bidList2);

    }

    @Test
    public void deleteTest() {
        bidListService.delete(bidList3);
        verify(bidListRepository, Mockito.times(1)).delete(bidList3);

    }


}
