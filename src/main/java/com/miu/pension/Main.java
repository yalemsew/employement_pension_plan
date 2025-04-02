package com.miu.pension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.miu.pension.model.Employee;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Employee> employees = DataLoader.loadEmployees();
        System.out.println("All Employees:");
        printAllEmployees(employees);

        System.out.println("\nQuarterly Upcoming Enrollees:");
        printUpcomingEnrollees(employees);
    }

    public static void printAllEmployees(List<Employee> employees) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        employees.stream()
                .sorted(Comparator.comparing(Employee::getYearlySalary).reversed()
                        .thenComparing(Employee::getLastName))
                .forEach(e -> {
                    try {
                        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(e);
                        System.out.println(json);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
    }

    public static void printUpcomingEnrollees(List<Employee> employees) {
        LocalDate now = LocalDate.now();

        // Calculate start and end of the next quarter
        int currentQuarter = (now.getMonthValue() - 1) / 3 + 1;
        int startMonthNextQuarter = ((currentQuarter % 4) * 3) + 1;
        int year = (currentQuarter == 4) ? now.getYear() + 1 : now.getYear();
        LocalDate startNextQuarter = LocalDate.of(year, startMonthNextQuarter, 1);
        LocalDate endNextQuarter = startNextQuarter.plusMonths(3).minusDays(1);

        employees.stream()
                .filter(e -> e.getPensionPlan() == null)
                .filter(e -> {
                    LocalDate employmentDate = e.getEmploymentDate();
                    LocalDate completionDate = employmentDate.plusYears(3);
                    return (completionDate.isEqual(startNextQuarter) || completionDate.isAfter(startNextQuarter)) &&
                            (completionDate.isEqual(endNextQuarter) || completionDate.isBefore(endNextQuarter));
                })
                .sorted(Comparator.comparing(Employee::getEmploymentDate).reversed())
                .forEach(e -> {
                    try {
                        String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(e);
                        System.out.println(json);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
    }
}