package com.miu.pension;

import com.miu.pension.model.*;
import java.time.LocalDate;
import java.util.*;

public class DataLoader {
    public static List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "Daniel", "Agar", LocalDate.of(2018, 1, 17), 105945.50,
                new PensionPlan("EX1089", LocalDate.of(2023, 1, 17), 100.0)));
        employees.add(new Employee(2L, "Benard", "Shaw", LocalDate.of(2018, 10, 3), 197750.00, null));
        employees.add(new Employee(3L, "Carly", "Agar", LocalDate.of(2014, 5, 16), 842000.75,
                new PensionPlan("SM2307", LocalDate.of(2019, 11, 4), 1555.50)));
        employees.add(new Employee(4L, "Wesley", "Schneider", LocalDate.of(2018, 11, 2), 74500.00, null));
        return employees;
    }
}
