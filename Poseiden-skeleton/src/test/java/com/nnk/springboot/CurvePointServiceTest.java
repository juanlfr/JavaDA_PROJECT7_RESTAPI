package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
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
public class CurvePointServiceTest {

    @Autowired
    private CurvePointService curvePointService;
    @MockBean
    private CurvePointRepository curvePointRepository;

    private CurvePoint curvePoint1;
    private CurvePoint curvePoint2;
    private CurvePoint curvePoint3;
    private List<CurvePoint> curvePointList;

    @Before
    public void setUp() {
        curvePoint1 = new CurvePoint(10, 10d, 10d);
        curvePoint2 = new CurvePoint(10, 10d, 20d);
        curvePoint2.setId(2);
        curvePoint3 = new CurvePoint(10, 10d, 30d);
        curvePointList = Arrays.asList(curvePoint1, curvePoint2, curvePoint3);
    }

    @Test
    public void findAllTest() {
        when(curvePointRepository.findAll()).thenReturn(this.curvePointList);
        List<CurvePoint> CurvePointTest = curvePointService.findAll();
        assertThat(CurvePointTest).isSameAs(curvePointList);
        assertThat(CurvePointTest).hasSize(3);
    }

    @Test
    public void createCurvePointTest() {
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint1);
        CurvePoint CurvePointTest = curvePointService.createCurvePoint(this.curvePoint1);
        assertThat(CurvePointTest).isEqualTo(this.curvePoint1);
    }

    @Test
    public void findByIdTest() {

        when(curvePointRepository.findById(2)).thenReturn(Optional.ofNullable(this.curvePoint2));
        CurvePoint CurvePointFoundById = curvePointService.findById(2).get();
        assertThat(CurvePointFoundById).isEqualTo(this.curvePoint2);

    }

    @Test
    public void deleteTest() {
        curvePointService.delete(curvePoint3);
        verify(curvePointRepository, Mockito.times(1)).delete(curvePoint3);

    }
}
