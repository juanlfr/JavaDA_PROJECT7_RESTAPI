package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BidListServiceImpl implements BidListService {

    @Autowired
    private BidListRepository bidListRepository;

    @Override
    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    @Override
    public BidList createBidList(BidList bid) {
        return bidListRepository.save(bid);
    }

    @Override
    public Optional<BidList> findById(Integer id) {
        return Optional.ofNullable(bidListRepository.findById(id).orElseThrow((() -> new IllegalArgumentException("Invalid user Id:" + id))));
    }

    @Override
    public void delete(BidList bidList) {
        bidListRepository.delete(bidList);
    }
}
