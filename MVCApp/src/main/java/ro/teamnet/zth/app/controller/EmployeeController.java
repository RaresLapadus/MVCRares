package ro.teamnet.zth.app.controller;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.api.annotations.MyRequestParam;
import ro.teamnet.zth.app.domain.Employee;
import ro.teamnet.zth.app.service.EmployeeService;
import ro.teamnet.zth.app.service.EmployeeServiceImpl;

import java.util.*;

/**
 * Created by rares on 5/6/2015.
 */
@MyController(urlPath = "/employees")
public class EmployeeController {

    @MyRequestMethod(urlPath = "/all")
    public List<Employee> getAllEmployees(){

        EmployeeService employeeService = new EmployeeServiceImpl();

        return employeeService.findAllEmployees();


    }

    @MyRequestMethod(urlPath = "/one")
    public Employee getOneEmployee(@MyRequestParam(paramName = "idEmployee") String id){

        EmployeeService employeeService = new EmployeeServiceImpl();

        return employeeService.findOneEmployee(Integer.valueOf(id));
    }

}
