package com.connect.bus2parent.service;

import com.connect.bus2parent.dao.BusDao;
import com.connect.bus2parent.domain.Bus;
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

}
