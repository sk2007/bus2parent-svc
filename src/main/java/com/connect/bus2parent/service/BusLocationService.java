package com.connect.bus2parent.service;

import com.connect.bus2parent.dao.BusLocationDao;
import com.connect.bus2parent.domain.BusLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusLocationService {

    @Autowired
    private BusLocationDao busLocationDao;

    public List<BusLocation> getAllLocations() {
        return busLocationDao.getAllLocations();
    }

    /**
     * @return the location for the given bus number, or {@code null} if not found
     */
    public BusLocation getLocation(int busNumber) {
        return busLocationDao.getLocation(busNumber);
    }

    public int updateLocation(BusLocation location) {
        return busLocationDao.upsertLocation(location);
    }
}
