package com.company.services;

import com.company.models.Customer;
import com.company.utils.Resource;

import java.util.List;

public interface ICustomerServices {
    Resource<List<Customer>> getAllCustomer();
    Resource<Customer> addCustomer(Customer customer);
    Resource<Customer> getCustomerByName(String name);
}
