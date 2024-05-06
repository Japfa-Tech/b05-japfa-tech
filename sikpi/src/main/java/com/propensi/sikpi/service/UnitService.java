package com.propensi.sikpi.service;

import java.util.List;

import com.propensi.sikpi.model.Unit;

import javassist.NotFoundException;

public interface UnitService {
    Unit getUnitByUserId(Long userId) throws NotFoundException;

    Unit getUnitByKepalaUnitId(Long kepalaUnitId) throws NotFoundException;

    Long getUnitIdForUser(Long userId);

    List<Unit> getAllUnits();

}
