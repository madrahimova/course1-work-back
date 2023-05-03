package pro.sky.course1_work_back.api;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.course1_work_back.models.Employee;
import pro.sky.course1_work_back.models.EmployeeBook;

import java.util.Map;

@RestController
public class APIController {
    private static final int COUNT = 10;
    private final Gson gson;
    private final EmployeeBook employeeBook;

    public APIController() {
        gson = new Gson();
        employeeBook = new EmployeeBook();
        addFakeData();
    }

    private void addFakeData() {
        Faker faker = new Faker();
        for (int i = 0; i < COUNT; i++) {
            Employee employee = new Employee(faker.name().fullName(),
                    faker.random().nextInt(1, 5), faker.random().nextInt(15_000, 100_000));
            employeeBook.addEmployee(employee);
        }
    }

    @GetMapping("/employees")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Map<Integer, Employee>> getEmployees() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeBook.getEmployees());
    }

    @GetMapping("/employees/next_id")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Integer> getEmployeeNextId() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeBook.getEmployeeNextId());
    }

    @GetMapping("/employees/{department}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Map<Integer, Employee>> getEmployeesInDepartment(@PathVariable int department) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeBook.getEmployeesInDepartment(department));
    }

    @PostMapping("/employee")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Integer> addEmployee(@RequestBody String body) {
        Employee employee = gson.fromJson(body, Employee.class);
        HttpStatus status = employeeBook.addEmployee(employee) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity
                .status(status)
                .body(employeeBook.getEmployeeNextId());
    }

    @DeleteMapping("/employee/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Integer> addEmployee(@PathVariable int id) {
        HttpStatus status = employeeBook.deleteEmployee(id) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity
                .status(status)
                .body(id);
    }

    @PutMapping("/employee/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Integer> alterEmployee(@RequestBody String body, @PathVariable int id) {
        Employee employee = gson.fromJson(body, Employee.class);
        HttpStatus status = employeeBook.alterEmployee(id, employee) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity
                .status(status)
                .body(id);
    }

    @GetMapping("/employee/min_salary")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Employee> getEmployeeWithMinSalary() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeBook.getEmployeeWithMinSalary());
    }

    @GetMapping("/employee/max_salary")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Employee> getEmployeeWithMaxSalary() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeBook.getEmployeeWithMaxSalary());
    }

    @GetMapping("/salaries/sum")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Double> getSalarySum() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeBook.getSalarySum());
    }

    @GetMapping("/salaries/average")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Double> getAverageSalary() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeBook.getAverageSalary());
    }

    @GetMapping("/salaries/average/{department}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Double> getAverageSalaryInDepartment(@PathVariable int department) {
        double salary = employeeBook.getAverageSalaryInDepartment(department);
        HttpStatus status = salary >= 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity
                .status(status)
                .body(salary);
    }

    @GetMapping("/salaries/sum/{department}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Double> getSalarySumInDepartment(@PathVariable int department) {
        double salary = employeeBook.getSalarySumInDepartment(department);
        HttpStatus status = salary >= 0 ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity
                .status(status)
                .body(salary);
    }

    @PutMapping("/salaries/index/{percent}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Map<Integer, Employee>> indexSalaries(@PathVariable double percent) {
        HttpStatus status = employeeBook.indexSalaries(percent) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity
                .status(status)
                .body(employeeBook.getEmployees());
    }
}