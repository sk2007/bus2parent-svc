package com.connect.bus2parent.service;

import com.connect.bus2parent.dao.DriverDao;
import com.connect.bus2parent.domain.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DriverService {

    @Autowired
    private DriverDao driverDao;

    public List<Driver> getDrivers() {
        List<Driver> driverList = driverDao.getDrivers();
        return driverList;
    }

    public Driver getDriver(int id) {
        Driver driver = driverDao.getDriver(id);
        return driver;
    }

    public int createDriver(Driver myDriver) {
        int changed = driverDao.createDriver(myDriver);
        return changed;
    }

    public void createDrivers(List<Driver> drivers) {
        for (Driver d: drivers) {
            driverDao.createDriver(d);
        }
    }

    public void registerDriver(Driver d) {
        if (d.id() == 0) {
            d.setId(driverDao.getNewID());
        }
        driverDao.createDriver(d);
    }

    public int removeDriver(int id) {
        return driverDao.removeDriver(id);
    }
}
