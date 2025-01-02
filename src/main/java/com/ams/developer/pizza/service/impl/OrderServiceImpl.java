package com.ams.developer.pizza.service.impl;

import com.ams.developer.pizza.persitence.entity.CustomerEntity;
import com.ams.developer.pizza.persitence.entity.OrderItemEntity;
import com.ams.developer.pizza.persitence.entity.PizzaEntity;
import com.ams.developer.pizza.persitence.entity.PizzaOrderEntity;
import com.ams.developer.pizza.persitence.repository.OrderItemRepository;
import com.ams.developer.pizza.persitence.repository.PizzaOrderListRepository;
import com.ams.developer.pizza.persitence.repository.PizzaOrderRepository;
import com.ams.developer.pizza.persitence.repository.PizzaRepository;
import com.ams.developer.pizza.service.CustomerService;
import com.ams.developer.pizza.service.OrderService;
import com.ams.developer.pizza.service.PizzaService;
import com.ams.developer.pizza.service.dto.ApiResponseDto;
import com.ams.developer.pizza.service.dto.OrderItemDto;
import com.ams.developer.pizza.service.dto.Pageable.ContentPageDto;
import com.ams.developer.pizza.service.dto.Pageable.PageableDto;
import com.ams.developer.pizza.service.dto.Pageable.SortDto;
import com.ams.developer.pizza.service.dto.PizzaOrderDto;
import com.ams.developer.pizza.service.dto.ValidateFieldDto;
import com.ams.developer.pizza.service.mapper.OrderItemMapper;
import com.ams.developer.pizza.service.mapper.OrderMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private PizzaOrderListRepository pizzaOrderListRepository;

    @Autowired
    private PizzaOrderRepository pizzaOrderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;


    @Override
    public ApiResponseDto getAllOrders(int page, int size, String sortBy) {
        try {
            Pageable paging = PageRequest.of(page,size, Sort.by(sortBy));
            Page<PizzaOrderEntity> orders = this.pizzaOrderListRepository.findAll(paging);
            if (orders.isEmpty())
                return new ApiResponseDto(HttpStatus.BAD_REQUEST.value(),"No tienes ordenes registradas",null);

            ContentPageDto contentPageDto = this.formatPageableOrders(orders);
            //List<Object> ordersDto = orders.getContent().stream().map(orderMapper::convertOrderDto).collect(Collectors.toList());
            contentPageDto.setContent(Collections.singletonList(orders.getContent()));
            return new ApiResponseDto(HttpStatus.OK.value(),"Lista de ordenes",contentPageDto);

        }catch (DataAccessException data){
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Error de conexión a la base de datos", data.getMessage());

        }catch (Exception exception){
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Ocurrio un error inesperado",exception.getMessage());
        }
    }

    @Override
    public ApiResponseDto getOrder(int idOrder) {
        try {
            PizzaOrderEntity order = this.getOrderById(idOrder);
            if (order == null)
                return new ApiResponseDto(HttpStatus.BAD_REQUEST.value(),"No existe esta orden",null);

            return new ApiResponseDto(HttpStatus.OK.value(),"Información de la orden",order);

        }catch (DataAccessException data){
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Error de conexión a la base de datos", data.getMessage());

        }catch (Exception exception){
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Ocurrio un error inesperado",exception.getMessage());
        }
    }

    @Override
    public PizzaOrderEntity getOrderById(int idOrder) {
        Optional<PizzaOrderEntity> order = this.pizzaOrderRepository.findById(idOrder);
        return order.orElse(null);
    }

    @Override
    public ApiResponseDto saveOrder(PizzaOrderDto pizzaOrderDto, BindingResult bindingResult) {
        try {
            List<ValidateFieldDto> inpust = this.validateInputs(bindingResult);
            if (!inpust.isEmpty())
                return new ApiResponseDto(HttpStatus.BAD_REQUEST.value(),"Campos invalidos, verifica por favor",inpust);

            CustomerEntity customer = this.customerService.getCustomerById(pizzaOrderDto.getIdCustomer());
            if (customer == null)
                return new ApiResponseDto(HttpStatus.BAD_REQUEST.value(),"No existe el cliente que estas asociando a esta venta",null);

            LocalDateTime date = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            double total = this.calculateTotal(pizzaOrderDto.getItems());
            PizzaOrderEntity order = new PizzaOrderEntity();
            List<OrderItemEntity> items = new ArrayList<>();
            order.setAddtionalNotes(pizzaOrderDto.getAddtionalNotes());
            order.setDate(date);
            order.setIdCostumer(pizzaOrderDto.getIdCustomer());
            order.setMethod(pizzaOrderDto.getMethod());
            order.setTotal(total);
            PizzaOrderEntity orderSave = this.pizzaOrderRepository.save(order);

            for (OrderItemDto item: pizzaOrderDto.getItems()) {
                PizzaEntity pizza = this.pizzaService.getPizzaById(item.getIdPizza());
                if (pizza == null)
                    return new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "La pizza con id "+item.getIdPizza() + " no existe o no esta activada", null);

                int idItem =  this.generateItemId();
                OrderItemEntity itemPizza = new OrderItemEntity();
                itemPizza.setIdItem(idItem);
                itemPizza.setIdOrder(order.getIdOrder());
                itemPizza.setIdPizza(item.getIdPizza());
                itemPizza.setPrice(item.getPrice());
                itemPizza.setQuantity(item.getQuantity());
                OrderItemEntity itemSave = this.orderItemRepository.save(itemPizza);
                items.add(itemSave);
            }
            orderSave.setItems(items);

            return  new ApiResponseDto(HttpStatus.CREATED.value(),"Orden registrada",orderSave);
        }catch (DataAccessException data){
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Error de conexión a la base de datos", data.getMessage());

        }catch (Exception exception){
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Ocurrio un error inesperado",exception.getMessage());
        }
    }

    private int generateItemId() {
        return orderItemRepository.findLastItemOrder()
                .map(orderItemEntity -> orderItemEntity.getIdItem() + 1)
                .orElse(1);
    }

    private double calculateTotal(List<OrderItemDto> items) {
        return items.stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
    }


    @Override
    public ApiResponseDto updateOrder(int idOrder, PizzaOrderDto pizzaOrderDto, BindingResult bindingResult) {
        return null;
    }

    @Override
    public ApiResponseDto deleteOrder(int idOrder) {


        return null;
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

    private ContentPageDto formatPageableOrders(Page<PizzaOrderEntity> orders){
        ContentPageDto orderContentPageDto = new ContentPageDto();

        PageableDto pageableDto = new PageableDto();
        pageableDto.setPageNumber(orders.getPageable().getPageNumber());
        pageableDto.setPageSize(orders.getPageable().getPageSize());
        pageableDto.setOffset((int) orders.getPageable().getOffset());
        pageableDto.setPaged(orders.getPageable().isPaged());
        pageableDto.setUnpaged(orders.getPageable().isUnpaged());

        SortDto sortDto = new SortDto();
        sortDto.setEmpty(orders.getSort().isEmpty());
        sortDto.setSorted(orders.getSort().isSorted());
        sortDto.setUnsorted(orders.getSort().isUnsorted());

        orderContentPageDto.setPageable(pageableDto);
        orderContentPageDto.setTotalPages(orders.getTotalPages());
        orderContentPageDto.setTotalElements((int) orders.getTotalElements());
        orderContentPageDto.setLast(orders.isLast());
        orderContentPageDto.setSize(orders.getSize());
        orderContentPageDto.setNumber(orders.getNumber());
        orderContentPageDto.setSort(sortDto);
        orderContentPageDto.setNumberOfElements(orders.getNumberOfElements());
        orderContentPageDto.setFirst(orders.isFirst());
        orderContentPageDto.setEmpty(orders.isEmpty());
        return orderContentPageDto;
    }
}
