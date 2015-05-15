package ro.teamnet.zth.app.controller;


import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.app.domain.Department;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rares on 5/6/2015.
 */
@MyController(urlPath = "/departments")
public class DepartmentController {

    @MyRequestMethod(urlPath = "/departments")
    public List<Department> getAllDepartments(){

        Department d1 = new Department();
        Department d2 = new Department();

        d1.setId(3);
        d2.setId(4);

        d1.setDepartmentName("ASDQ");
        d2.setDepartmentName("BZZ");

        List<Department> departments = new ArrayList<>();
        departments.add(d1);
        departments.add(d2);

        return departments;
    }
}
