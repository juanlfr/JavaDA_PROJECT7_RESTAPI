package com.nnk.springboot;

import com.nnk.springboot.domain.RuleName;
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
public class RuleNameServiceTest {
    @Autowired
    private com.nnk.springboot.services.RuleNameService ruleNameService;
    @MockBean
    private com.nnk.springboot.repositories.RuleNameRepository ruleNameRepository;

    private com.nnk.springboot.domain.RuleName ruleName1;
    private RuleName ruleName2;
    private RuleName ruleName3;
    private List<RuleName> ruleNameList;

    @Before
    public void setUp() {
        ruleName1 = new RuleName("Rule Name 1", "Description", "Json", "Template", "SQL", "SQL Part");
        ruleName2 = new RuleName("Rule Name 2", "Description", "Json", "Template", "SQL", "SQL Part");
        ruleName2.setId(2);
        ruleName3 = new RuleName("Rule Name 3", "Description", "Json", "Template", "SQL", "SQL Part");
        ruleNameList = Arrays.asList(ruleName1, ruleName2, ruleName3);
    }

    @Test
    public void findAllTest() {
        when(ruleNameRepository.findAll()).thenReturn(this.ruleNameList);
        List<RuleName> RuleNameTest = ruleNameService.findAll();
        assertThat(RuleNameTest).isSameAs(ruleNameList);
        assertThat(RuleNameTest).hasSize(3);
    }

    @Test
    public void createRuleNameTest() {
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName1);
        RuleName RuleNameTest = ruleNameService.createRuleName(this.ruleName1);
        assertThat(RuleNameTest).isEqualTo(this.ruleName1);
    }

    @Test
    public void findByIdTest() {

        when(ruleNameRepository.findById(2)).thenReturn(Optional.ofNullable(this.ruleName2));
        RuleName RuleNameFoundById = ruleNameService.findById(2).get();
        assertThat(RuleNameFoundById).isEqualTo(this.ruleName2);

    }

    @Test
    public void deleteTest() {
        ruleNameService.delete(ruleName3);
        verify(ruleNameRepository, Mockito.times(1)).delete(ruleName3);

    }
}
