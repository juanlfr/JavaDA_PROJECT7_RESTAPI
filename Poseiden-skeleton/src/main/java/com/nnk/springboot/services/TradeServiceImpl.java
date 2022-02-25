package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TradeServiceImpl implements TradeService {
    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    @Override
    public Trade createTrade(Trade trade) {
        return tradeRepository.save(trade);
    }

    @Override
    public Optional<Trade> findById(Integer id) {
        return tradeRepository.findById(id);
    }

    @Override
    public void delete(Trade trade) {
        tradeRepository.delete(trade);
    }
}
