package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
public class CurveController {
    private static final Logger log = LogManager.getLogger(CurveController.class);
    @Autowired
    private CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        List<CurvePoint> curvePointList = curvePointService.findAll();
        log.info("finding all curve points");
        model.addAttribute("curvePointList", curvePointList);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint curvePoint, Model model) {
        model.addAttribute("curvePoint", new CurvePoint());
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "curvePoint/add";
        }
        try {
            CurvePoint curvePointSaved = curvePointService.createCurvePoint(curvePoint);
            log.info("Creating curvePoint" + curvePoint.toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while saving curvePoint:" + e);
        }
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        log.info("Finding curve");
        CurvePoint curvePoint = curvePointService.findById(id).get();
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "curvePoint/update";
        }
        try {
            Optional<CurvePoint> curvePointToUpdate = curvePointService.findById(id);
            if (curvePointToUpdate.isPresent()) {
                curvePointToUpdate.get().setCurveId(curvePoint.getCurveId());
                curvePointToUpdate.get().setTerm(curvePoint.getTerm());
                curvePointToUpdate.get().setValue(curvePoint.getValue());
                curvePointService.createCurvePoint(curvePointToUpdate.get());
                log.info("Updating curve Point" + curvePoint);
            } else {
                log.error("curvePoint id not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while updating curvePoint");
        }
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        try {
            Optional<CurvePoint> bidListToDelete = curvePointService.findById(id);
            if (bidListToDelete.isPresent()) {
                curvePointService.delete(bidListToDelete.get());
                log.info("Deleting bid" + bidListToDelete.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while deleting bid with id:" + id.toString());
        }
        return "redirect:/curvePoint/list";
    }
}
