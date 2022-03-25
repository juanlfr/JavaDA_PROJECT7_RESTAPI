package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import com.nnk.springboot.services.UtilsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
public class BidListController {

    private static final Logger log = LogManager.getLogger(BidListController.class);
    // TODO: Inject Bid service
    @Autowired
    private BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model) {
        // TODO: call service find all bids to show to the view
        List<BidList> bidList = bidListService.findAll();
        log.info("finding all bids");
        model.addAttribute("bidList", bidList);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid, Model model) {
        model.addAttribute("bid", new BidList());
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@ModelAttribute("bid") @Valid BidList bid, BindingResult result) {

        // TODO: check data valid and save to db, after saving return bid list
        log.info("validate bid method");
        if (result.hasErrors()) {
            return "bidList/add";
        }
        try {
            bidListService.createBidList(bid);
            log.info("Creating bid" + bid );
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error Creating bid");
        }
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        Optional<BidList> bidListToUpdate = bidListService.findById(id);
        if (bidListToUpdate.isPresent()) {
            model.addAttribute("bidListToUpdate", bidListToUpdate.get());
        } else {
            log.error("Bid id not found");
        }
        return "bidList/update";

    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        if (result.hasErrors()) {
            return "bidList/update";
        }
        try {
            Optional<BidList> bidListToUpdate = bidListService.findById(id);
            if (bidListToUpdate.isPresent()) {
                bidListToUpdate.get().setAccount(bidList.getAccount());
                bidListToUpdate.get().setType(bidList.getType());
                bidListToUpdate.get().setBidQuantity(bidList.getBidQuantity());
                bidListService.createBidList(bidListToUpdate.get());
                log.info("Updating bid" + bidList.toString());
            } else {
                log.error("Bid id not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while updating bid");
        }
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        try {
            Optional<BidList> bidListToDelete = bidListService.findById(id);
            if (bidListToDelete.isPresent()) {
                bidListService.delete(bidListToDelete.get());
                log.info("Deleting bid" + bidListToDelete.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while deleting bid with id:" + id.toString());
        }
        return "redirect:/bidList/list";
    }
}
