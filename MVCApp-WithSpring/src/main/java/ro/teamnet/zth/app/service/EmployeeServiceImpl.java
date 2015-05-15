package ro.teamnet.zth.app.service;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
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
    public void delete (Integer id){
        EmployeeDao employeeDao = new EmployeeDao();

        employeeDao.deleteEmployee(employeeDao.getEmployeeById(id));
    }

    public Employee insert (Employee employee){
        EmployeeDao employeeDao = new EmployeeDao();

        Employee employee1 = employeeDao.insertEmployee(employee);

        return employee1;
    }

    public Employee update (Employee employee){
        EmployeeDao employeeDao = new EmployeeDao();

        Employee employee1 = employeeDao.updateEmployee(employee);

        return employee1;
    }
}
