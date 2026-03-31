package com.connect.bus2parent.service;

import com.connect.bus2parent.domain.BusParent;
import com.connect.bus2parent.dao.BusParentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusParentService {

    @Autowired
    private BusParentDao busParentDao;

    public List<BusParent> getBusParents() {
        return busParentDao.getBusParents();
    }

    public List<BusParent> getBusParentsByBus(int busNumber) {
        return busParentDao.getBusParentsByBus(busNumber);
    }

    public int createBusParent(BusParent busParent) {
        return busParentDao.createBusParent(busParent);
    }

    public int removeBusParent(int busNumber, String parentEmail) {
        return busParentDao.removeBusParent(busNumber, parentEmail);
    }
}
