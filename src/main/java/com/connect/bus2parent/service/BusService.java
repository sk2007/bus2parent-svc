package com.connect.bus2parent.service;

import com.connect.bus2parent.dao.BusDao;
import com.connect.bus2parent.domain.Bus;
import com.connect.bus2parent.domain.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusService {

    @Autowired
    private BusDao busDao;

    public List<Bus> getBuses() {
        List<Bus> buses =  busDao.getBuses();
        return buses;
    }

    public Bus getBus(int num) {
        Bus b = busDao.getBus(num);
        return b;
    }

    public int createBus(Bus bus) {
        int changed = busDao.createBus(bus);
        return changed;
    }

    public void createBuses(List<Bus> buses) {
        for (Bus b: buses) {
            busDao.createBus(b);
        }
    }

    public void registerBus(Bus b) {
        if (b.busNumber() == 0) {
            b.setBusNumber(busDao.getNewBusNumber());
        }
        busDao.createBus(b);
    }

    public int removeBus(int busNumber) {
        return busDao.removeBus(busNumber);
    }

    public void updateBusPlate(int busNumber, String busPlate) {
        busDao.updateBusPlate(busNumber, busPlate);
    }
}
