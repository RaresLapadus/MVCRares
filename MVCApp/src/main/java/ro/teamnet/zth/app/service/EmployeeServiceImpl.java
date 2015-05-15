package ro.teamnet.zth.app.service;

import ro.teamnet.zth.app.dao.EmployeeDao;
import ro.teamnet.zth.app.domain.Employee;

import java.util.List;

/**
 * Created by rares on 5/7/2015.
 */
public class EmployeeServiceImpl implements EmployeeService {


    @Override
    public List<Employee> findAllEmployees() {
        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.getAllEmployees();

    }

    @Override
    public Employee findOneEmployee( Integer id) {

        EmployeeDao employeeDao = new EmployeeDao();
        return employeeDao.getEmployeeById(id);

    }

}
