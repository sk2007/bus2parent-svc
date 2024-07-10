package com.connect.bus2parent.service;

import com.connect.bus2parent.dao.ParentDao;
import com.connect.bus2parent.domain.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentService {

    @Autowired
    private ParentDao parentDao;

    public List<Parent> getParents() {
        return parentDao.getParents();
    }

    public Parent getParent(String emailAddress) {
        return parentDao.getParent(emailAddress);
    }

    public int createParent(Parent parent) {
        return parentDao.createParent(parent);
    }

    public void createParents(List<Parent> parents) {
        for (Parent p : parents) {
            parentDao.createParent(p);
        }
    }
}
