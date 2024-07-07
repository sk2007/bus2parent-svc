package com.connect.bus2parent.service;

import com.connect.bus2parent.dao.ParentDao;
import com.connect.bus2parent.domain.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParentService {
    private static List<Parent> myParents = new ArrayList<>();

    @Autowired
    private ParentDao parentDao;

    public List<Parent> getParents() {
        myParents = parentDao.getParents();
        return myParents;
    }
}
