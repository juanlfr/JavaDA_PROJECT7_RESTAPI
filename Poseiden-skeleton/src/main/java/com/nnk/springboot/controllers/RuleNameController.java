package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
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
public class RuleNameController {
    private static final Logger log = LogManager.getLogger(RuleNameController.class);
    // TODO: Inject RuleName service
    @Autowired
    private RuleNameService ruleNameService;

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        // TODO: find all RuleName, add to model
        List<RuleName> ruleNameList = ruleNameService.findAll();
        log.info("finding all rule names");
        model.addAttribute("ruleNameList", ruleNameList);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName, Model model) {
        model.addAttribute("ruleName", new RuleName());
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return RuleName list
        if (result.hasErrors()) {
            return "ruleName/add";
        }
        try {
            RuleName ruleNameSaved = ruleNameService.createRuleName(ruleName);
            log.info("Creating ruleName" + ruleNameSaved.toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while saving ruleName:" + e);
        }
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get RuleName by Id and to model then show to the form
        Optional<RuleName> ruleNameToUpdate = ruleNameService.findById(id);
        if (ruleNameToUpdate.isPresent()) {
            model.addAttribute("ruleNameToUpdate", ruleNameToUpdate.get());
        } else {
            log.error("Rule name id not found");
        }
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
        if (result.hasErrors()) {
            return "ruleName/update";
        }
        try {
            Optional<RuleName> ruleNameToUpdate = ruleNameService.findById(id);
            if (ruleNameToUpdate.isPresent()) {
                ruleNameToUpdate.get().setName(ruleName.getName());
                ruleNameToUpdate.get().setDescription(ruleName.getDescription());
                ruleNameToUpdate.get().setJson(ruleName.getJson());
                ruleNameToUpdate.get().setTemplate(ruleName.getTemplate());
                ruleNameToUpdate.get().setSqlStr(ruleName.getSqlStr());
                ruleNameToUpdate.get().setSqlPart(ruleName.getSqlPart());
                ruleNameService.createRuleName(ruleNameToUpdate.get());
                log.info("Updating ruleName" + ruleName.toString());
            } else {
                log.error("ruleName id not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while updating rating");
        }
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // TODO: Find RuleName by Id and delete the RuleName, return to Rule list
        try {
            Optional<RuleName> ruleNameToDelete = ruleNameService.findById(id);
            if (ruleNameToDelete.isPresent()) {
                ruleNameService.delete(ruleNameToDelete.get());
                log.info("Deleting ruleName" + ruleNameToDelete.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while deleting ruleName with id:" + id);
        }
        return "redirect:/ruleName/list";
    }
}
