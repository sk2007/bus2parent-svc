package com.connect.bus2parent.service;

import com.connect.bus2parent.dao.BusDao;
import com.connect.bus2parent.domain.Bus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusService {

    private static List<Bus> myBuses = new ArrayList<>();

    @Autowired
    private BusDao busDao;

    public List<Bus> getBuses() {
        myBuses =  busDao.getBuses();
        return myBuses;
    }

}
