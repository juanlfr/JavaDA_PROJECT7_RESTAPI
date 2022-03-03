package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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
public class RatingController {
    private static final Logger log = LogManager.getLogger(RatingController.class);
    // TODO: Inject Rating service
    @Autowired
    private RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(Model model) {
        // TODO: find all Rating, add to model
        List<Rating> ratingList = ratingService.findAll();
        log.info("finding all ratings");
        model.addAttribute("ratingList", ratingList);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating, Model model) {
        model.addAttribute("rating", new Rating());
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rating list
        if (result.hasErrors()) {
            return "rating/add";
        }
        try {
            Rating ratingSaved = ratingService.createRating(rating);
            log.info("Creating rating" + ratingSaved.toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while saving rating:" + e);
        }
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form
        Optional<Rating> ratingToUpdate = ratingService.findById(id);
        if (ratingToUpdate.isPresent()) {
            model.addAttribute("ratingToUpdate", ratingToUpdate.get());
        } else {
            log.error("Rating id not found");
        }
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                               BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list
        if (result.hasErrors()) {
            return "rating/update/" + id;
        }
            try {
                Optional<Rating> ratingToUpdate = ratingService.findById(id);
                if (ratingToUpdate.isPresent()) {
                    ratingToUpdate.get().setFitchRating(rating.getFitchRating());
                    ratingToUpdate.get().setMoodysRating(rating.getMoodysRating());
                    ratingToUpdate.get().setSandPRating(rating.getSandPRating());
                    ratingToUpdate.get().setOrderNumber(rating.getOrderNumber());
                    ratingService.createRating(ratingToUpdate.get());
                    log.info("Updating rating" + rating.toString());
                } else {
                    log.error("rating id not found");
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error while updating rating");
            }
            return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
        try {
            Optional<Rating> ratingToDelete = ratingService.findById(id);
            if (ratingToDelete.isPresent()) {
                ratingService.delete(ratingToDelete.get());
                log.info("Deleting rating" + ratingToDelete.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while deleting rating with id:" + id);
        }
        return "redirect:/rating/list";
    }
}
