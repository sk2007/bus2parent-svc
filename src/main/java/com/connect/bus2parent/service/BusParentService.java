package com.connect.bus2parent.service;

import com.connect.bus2parent.domain.BusParent;
import com.connect.bus2parent.dao.BusParentDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusParentService {

    private BusParentDao busParentDao;

    public List<BusParent> getBusParents() {
        return busParentDao.getBusParents();
    }

}
