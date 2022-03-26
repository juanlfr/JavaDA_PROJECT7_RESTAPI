package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class TradeController {
    private static final Logger log = LogManager.getLogger(TradeController.class);
    @Autowired
    private TradeService tradeService;

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        List<Trade> tradeList = tradeService.findAll();
        log.info("finding all trades");
        model.addAttribute("tradeList", tradeList);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade trade, Model model) {
        model.addAttribute("trade", new Trade());
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/add";
        }
        try {
            Trade tradeSaved = tradeService.createTrade(trade);
            log.info("Creating trade" + tradeSaved.toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while saving trade:" + e);
        }
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("Finding trade");
        Trade trade = tradeService.findById(id).get();
        model.addAttribute("trade", trade );
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result) {

        if (result.hasErrors()) {
            return "trade/update";
        }
        try {
            Optional<Trade> tradeToUpdate = tradeService.findById(id);
            if (tradeToUpdate.isPresent()) {
                tradeToUpdate.get().setAccount(trade.getAccount());
                tradeToUpdate.get().setType(trade.getType());
                tradeToUpdate.get().setBuyQuantity(trade.getBuyQuantity());
                tradeService.createTrade(tradeToUpdate.get());
                log.info("Updating trade" + trade);
            } else {
                log.error("trade id not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while updating trade");
        }
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {

        try {
            Optional<Trade> tradeToDelete = tradeService.findById(id);
            if (tradeToDelete.isPresent()) {
                tradeService.delete(tradeToDelete.get());
                log.info("Deleting trade" + tradeToDelete.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while deleting trade with id:" + id);
        }
        return "redirect:/trade/list";
    }
}
