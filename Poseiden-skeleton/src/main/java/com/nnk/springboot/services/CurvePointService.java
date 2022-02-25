package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;
import java.util.Optional;

public interface CurvePointService {
    List<CurvePoint> findAll();

    CurvePoint createCurvePoint(CurvePoint curvePoint);

    Optional<CurvePoint> findById(Integer id);

    void delete(CurvePoint curvePoint);
}
