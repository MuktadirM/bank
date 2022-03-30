package com.company.infrastructure;

import com.company.models.Customer;
import com.company.services.ICustomerServices;
import com.company.utils.Resource;

import java.util.ArrayList;
import java.util.List;

public class CustomerServices implements ICustomerServices {
    private List<Customer> customers;

    @Override
    public Resource<List<Customer>> getAllCustomer() {
        if(customers == null) {
            customers = new ArrayList<>();
        }
        return Resource.success(customers);
    }

    @Override
    public Resource<Customer> addCustomer(Customer customer) {
        if(customers == null) {
            customers = new ArrayList<>();
        }
        Customer oldCustomer = customers.stream().filter((customer1) -> customer1.getName().equals(customer.getName())).findFirst().orElse(null);

        if(oldCustomer != null) {
            return Resource.error("Customer already exists",oldCustomer);
        }
        Customer newCustomer = new Customer(customers.size()+1,customer.getName());
        customers.add(newCustomer);

        return Resource.success(newCustomer);
    }

    @Override
    public Resource<Customer> getCustomerByName(String name) {
        Customer oldCustomer = customers.stream().filter((customer1) -> customer1.getName().toLowerCase().equals(name.toLowerCase())).findFirst().orElse(null);
        if (oldCustomer == null) {
            return Resource.error("Customer not found",null);
        }
        return Resource.success(oldCustomer);
    }
}
