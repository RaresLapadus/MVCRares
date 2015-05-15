package ro.teamnet.zth.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
@Controller
@RequestMapping(value = "/employees")
public class EmployeeController {


    @RequestMapping(method = RequestMethod.GET, value = "")
    public
    @ResponseBody
    List<Employee> getAllEmployees() {

        EmployeeService employeeService = new EmployeeServiceImpl();

        return employeeService.findAllEmployees();


    }

    //@RequestMapping(method = RequestMethod.GET, value = "/one")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Employee getOneEmployee(@PathVariable(value = "id") String id) {


        EmployeeService employeeService = new EmployeeServiceImpl();

        return employeeService.findOneEmployee(Integer.valueOf(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    void deleteEmployee(@PathVariable(value = "id") String id) {
        EmployeeService employeeService = new EmployeeServiceImpl();

        employeeService.delete(Integer.parseInt(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    Employee insert(@RequestBody Employee employee) {
        EmployeeService employeeService = new EmployeeServiceImpl();

        Employee employee1 = employeeService.insert(employee);
        return employee1;
    }
    @RequestMapping(method = RequestMethod.PUT)
    public
    @ResponseBody
    Employee update(@RequestBody Employee employee) {
        EmployeeService employeeService = new EmployeeServiceImpl();

        Employee employee1 = employeeService.update(employee);
        return employee1;
    }
}
