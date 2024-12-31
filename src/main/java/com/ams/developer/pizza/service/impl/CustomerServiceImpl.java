package com.ams.developer.pizza.service.impl;

import com.ams.developer.pizza.persitence.entity.CustomerEntity;
import com.ams.developer.pizza.persitence.entity.PizzaEntity;
import com.ams.developer.pizza.persitence.repository.CustomerListRepository;
import com.ams.developer.pizza.persitence.repository.CustomerRepository;
import com.ams.developer.pizza.service.CustomerService;
import com.ams.developer.pizza.service.dto.ApiResponseDto;
import com.ams.developer.pizza.service.dto.CustomerDto;
import com.ams.developer.pizza.service.dto.Pageable.ContentPageDto;
import com.ams.developer.pizza.service.dto.Pageable.PageableDto;
import com.ams.developer.pizza.service.dto.Pageable.SortDto;
import com.ams.developer.pizza.service.dto.ValidateFieldDto;
import com.ams.developer.pizza.service.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerListRepository customerListRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public ApiResponseDto getAllCustomers(int page, int size, String sortBy) {
        try {
            Pageable paging = PageRequest.of(page,size, Sort.by(sortBy));
            Page<CustomerEntity>  customerEntities = customerListRepository.findAll(paging);
            if (customerEntities.isEmpty())
                return new ApiResponseDto(HttpStatus.BAD_REQUEST.value(),"No hay clientes registrados",null);

            ContentPageDto customersPageContent = this.formatPageableCustomer(customerEntities);
            List<Object> customerDtos = customerEntities.stream().map(customerMapper::convertCustomerDto).collect(Collectors.toList());
            customersPageContent.setContent(customerDtos);
            return new ApiResponseDto(HttpStatus.OK.value(),"Lista de clientes",customersPageContent);


        }catch (DataAccessException dataAccessException){
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Error de conexión a la base de datos", dataAccessException.getMessage());

        }catch (Exception exception){
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Ocurrio un error inesperado", exception.getMessage());
        }
    }

    @Override
    public ApiResponseDto getCustomer(String idCustomer) {
        try {
            CustomerEntity customerBD = this.getCustomerById(idCustomer);
            if (customerBD == null)
                return new ApiResponseDto(HttpStatus.BAD_REQUEST.value(),"No existe este cliente",null);

            return new ApiResponseDto(HttpStatus.OK.value(),"Información personal del cliente", this.customerMapper.convertCustomerDto(customerBD));

        }catch (DataAccessException data){
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Error de conexión a la base de datos", data.getMessage());

        }catch (Exception exception){
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Ocurrio un error inesperado", exception.getMessage());
        }
    }

    @Override
    public CustomerEntity getCustomerById(String idCustomer) {
        Optional<CustomerEntity> customer = this.customerRepository.findById(idCustomer);
        return customer.orElse(null);
    }

    @Override
    public ApiResponseDto saveCustomer(CustomerDto customerDto, BindingResult bindingResult) {
        try {
            List<ValidateFieldDto> inputs = this.validateInputs(bindingResult);
            if (!inputs.isEmpty())
                return new ApiResponseDto(HttpStatus.BAD_REQUEST.value(),"Campos invalidos, verifica por favor", inputs);

            CustomerEntity customerBD = this.getCustomerById(customerDto.getIdCustomer());
            if (customerBD != null)
                return new ApiResponseDto(HttpStatus.BAD_REQUEST.value(),"Ya existe un cliente con esa identificador",null);

            CustomerEntity customerSave = this.customerRepository.save(this.customerMapper.convertCustomerEntity(customerDto));
            return new ApiResponseDto(HttpStatus.CREATED.value(),"Cliente creado correctamente", this.customerMapper.convertCustomerDto(customerSave));

        }catch (DataAccessException data){
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Error de conexión a la base de datos", data.getMessage());

        }catch (Exception exception){
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Ocurrio un error inesperado",exception.getMessage());
        }
    }

    @Override
    public ApiResponseDto updateCustomer(String idCustomer, CustomerDto customerDto, BindingResult bindingResult) {
        try {
            List<ValidateFieldDto> inputs = this.validateInputs(bindingResult);
            if (!inputs.isEmpty())
                return new ApiResponseDto(HttpStatus.BAD_REQUEST.value(),"Campos invalidos, verifica por favor", inputs);

            CustomerEntity customerBD = this.getCustomerById(idCustomer);
            if (customerBD == null)
                return new ApiResponseDto(HttpStatus.BAD_REQUEST.value(),"No existe este cliente",null);

            if (!customerDto.getIdCustomer().equals(idCustomer))
                this.customerRepository.deleteById(idCustomer);

            CustomerEntity newCustomer = new CustomerEntity();
            newCustomer.setIdCustomer(customerDto.getIdCustomer());
            newCustomer.setName(customerDto.getName());
            newCustomer.setAddress(customerDto.getAddress());
            newCustomer.setEmail(customerDto.getEmail());
            newCustomer.setPhoneNumber(customerDto.getPhoneNumber());

            CustomerEntity customerEdit = this.customerRepository.save(newCustomer);
            return new ApiResponseDto(HttpStatus.OK.value(),"Cliente actualizado correctamente", this.customerMapper.convertCustomerDto(customerEdit));

        }catch (DataAccessException data){
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Error de conexión a la base de datos", data.getMessage());

        }catch (Exception exception){
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Ocurrio un error inesperado",exception.getMessage());
        }
    }

    @Override
    public ApiResponseDto deleteCustomer(String idCustomer) {
        try {
            CustomerEntity customerBD = this.getCustomerById(idCustomer);
            if (customerBD == null)
                return new ApiResponseDto(HttpStatus.BAD_REQUEST.value(),"No existe este cliente",null);

            this.customerRepository.deleteById(idCustomer);
            return new ApiResponseDto(HttpStatus.OK.value(),"Cliente eliminado correctamente",null);

        }catch (DataAccessException data){
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Error de conexión a la base de datos", data.getMessage());

        }catch (Exception exception){
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Ocurrio un error inesperado",exception.getMessage());
        }
    }

    private List<ValidateFieldDto> validateInputs(BindingResult bindingResult){
        List<ValidateFieldDto> validateFieldDTOList = new ArrayList<>();
        if (bindingResult.hasErrors()){
            bindingResult.getFieldErrors().forEach(fieldError -> {
                ValidateFieldDto validateFieldDTO = new ValidateFieldDto();
                validateFieldDTO.setFieldValidated(fieldError.getField());
                validateFieldDTO.setFieldValidatedMessage(fieldError.getDefaultMessage());
                validateFieldDTOList.add(validateFieldDTO);
            });
        }
        return validateFieldDTOList;
    }

    private ContentPageDto formatPageableCustomer(Page<CustomerEntity> customers){
        ContentPageDto customerContentPageDto = new ContentPageDto();

        PageableDto pageableDto = new PageableDto();
        pageableDto.setPageNumber(customers.getPageable().getPageNumber());
        pageableDto.setPageSize(customers.getPageable().getPageSize());
        pageableDto.setOffset((int) customers.getPageable().getOffset());
        pageableDto.setPaged(customers.getPageable().isPaged());
        pageableDto.setUnpaged(customers.getPageable().isUnpaged());

        SortDto sortDto = new SortDto();
        sortDto.setEmpty(customers.getSort().isEmpty());
        sortDto.setSorted(customers.getSort().isSorted());
        sortDto.setUnsorted(customers.getSort().isUnsorted());

        customerContentPageDto.setPageable(pageableDto);
        customerContentPageDto.setTotalPages(customers.getTotalPages());
        customerContentPageDto.setTotalElements((int) customers.getTotalElements());
        customerContentPageDto.setLast(customers.isLast());
        customerContentPageDto.setSize(customers.getSize());
        customerContentPageDto.setNumber(customers.getNumber());
        customerContentPageDto.setSort(sortDto);
        customerContentPageDto.setNumberOfElements(customers.getNumberOfElements());
        customerContentPageDto.setFirst(customers.isFirst());
        customerContentPageDto.setEmpty(customers.isEmpty());
        return customerContentPageDto;
    }
}
