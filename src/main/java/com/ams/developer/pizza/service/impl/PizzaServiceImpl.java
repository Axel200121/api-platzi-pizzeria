package com.ams.developer.pizza.service.impl;

import com.ams.developer.pizza.persitence.entity.PizzaEntity;
import com.ams.developer.pizza.persitence.repository.PizzaListRepository;
import com.ams.developer.pizza.persitence.repository.PizzaRepository;
import com.ams.developer.pizza.service.PizzaService;
import com.ams.developer.pizza.service.dto.ApiResponseDto;
import com.ams.developer.pizza.service.dto.Pageable.ContentPageDto;
import com.ams.developer.pizza.service.dto.Pageable.PageableDto;
import com.ams.developer.pizza.service.dto.Pageable.SortDto;
import com.ams.developer.pizza.service.dto.PizzaDto;
import com.ams.developer.pizza.service.dto.ValidateFieldDto;
import com.ams.developer.pizza.service.mapper.PizzaMapper;
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
public class PizzaServiceImpl implements PizzaService {

    @Autowired
    private PizzaListRepository pizzaListRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private PizzaMapper pizzaMapper;


    @Override
    public ApiResponseDto getAllPizzas(int page, int size, String sortBy) {
        try {
            Pageable paging = PageRequest.of(page,size, Sort.by(sortBy));
            Page<PizzaEntity> pizzas = pizzaListRepository.findAll(paging);
            if (pizzas.isEmpty())
                return new ApiResponseDto(HttpStatus.BAD_REQUEST.value(),"No hay pizzas registradas",null);

            ContentPageDto pizzaContentPageDto = this.fromatPageablePizzas(pizzas);
            List<Object> pizzaDtos = pizzas.getContent().stream().map(pizzaMapper::convertPizzaDto).collect(Collectors.toList());
            pizzaContentPageDto.setContent(pizzaDtos);
            return new ApiResponseDto(HttpStatus.OK.value(),"Lista de pizzas",pizzaContentPageDto);

        } catch (DataAccessException e) {
            // Manejo de excepciones relacionadas con la base de datos
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error de conexión a la base de datos", e.getMessage());
        } catch (Exception e) {
            // Manejo de otras excepciones
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocurrió un error inesperado", e.getMessage());
        }
    }

    @Override
    public ApiResponseDto getPizza(int idPizza) {
        try {
            PizzaEntity pizzaBd = this.getPizzaById(idPizza);
            if (pizzaBd == null)
                return new ApiResponseDto(HttpStatus.BAD_REQUEST.value(),"No existe esta pizza",null);

            return new ApiResponseDto(HttpStatus.OK.value(),"Información de la pizza seleccionada",this.pizzaMapper.convertPizzaDto(pizzaBd));
        } catch (DataAccessException e) {
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error de conexión a la base de datos", e.getMessage());
        } catch (Exception e) {
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocurrió un error inesperado", e.getMessage());
        }
    }

    @Override
    public PizzaEntity getPizzaById(int idPizza) {
        Optional<PizzaEntity> pizzaEntity = this.pizzaRepository.findById(idPizza);
        return pizzaEntity.orElse(null);
    }

    @Override
    public ApiResponseDto savePizza(PizzaDto pizzaDto, BindingResult bindingResult) {
        try {
            List<ValidateFieldDto> inputsValidate = this.validateInputs(bindingResult);
            if (!inputsValidate.isEmpty())
                return new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "Campos invalidos, verifica por favor", inputsValidate);

            Optional<PizzaEntity> existingPizza = this.pizzaRepository.findByNameIgnoreCase(pizzaDto.getName());
            if (existingPizza.isPresent())
                return new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "Ya existe una pizza con ese mismo nombre", null);

            PizzaEntity pizzaSave = this.pizzaRepository.save(this.pizzaMapper.convertPizzaEntity(pizzaDto));
            return new ApiResponseDto(HttpStatus.CREATED.value(),"Pizza creada correctamente",this.pizzaMapper.convertPizzaDto(pizzaSave));

        } catch (DataAccessException e) {
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error de conexión a la base de datos", e.getMessage());
        } catch (Exception e) {
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocurrió un error inesperado", e.getMessage());
        }
    }

    @Override
    public ApiResponseDto updatePizza(int idPizza, PizzaDto pizzaDto, BindingResult bindingResult) {
        try {
            List<ValidateFieldDto> inputsValidate = this.validateInputs(bindingResult);
            if (!inputsValidate.isEmpty())
                return new ApiResponseDto(HttpStatus.BAD_REQUEST.value(), "Campos invalidos, verifica por favor", inputsValidate);

            PizzaEntity pizzaBD = this.getPizzaById(idPizza);
            if (pizzaBD == null)
                return new ApiResponseDto(HttpStatus.BAD_REQUEST.value(),"No existe esta pizza",null);

            pizzaBD.setName(pizzaDto.getName());
            pizzaBD.setDescription(pizzaDto.getDescription());
            pizzaBD.setPrice(pizzaDto.getPrice());
            pizzaBD.setVegetarian(pizzaDto.getVegetarian());
            pizzaBD.setVegan(pizzaDto.getVegan());
            pizzaBD.setAvailable(pizzaDto.getAvailable());
            PizzaEntity pizzaEdit = this.pizzaRepository.save(pizzaBD);
            return new ApiResponseDto(HttpStatus.CREATED.value(),"Pizza actualizada correctamente",this.pizzaMapper.convertPizzaDto(pizzaEdit));

        } catch (DataAccessException e) {
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error de conexión a la base de datos", e.getMessage());
        } catch (Exception e) {
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocurrió un error inesperado", e.getMessage());
        }
    }

    @Override
    public ApiResponseDto deletePizza(int idPizza) {
        try {
            PizzaEntity pizzaBd = this.getPizzaById(idPizza);
            if (pizzaBd == null)
                return new ApiResponseDto(HttpStatus.BAD_REQUEST.value(),"No existe esta pizza",null);

            this.pizzaRepository.deleteById(idPizza);
            return new ApiResponseDto(HttpStatus.OK.value(),"Pizza eliminada correctamente",null);

        } catch (DataAccessException e) {
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error de conexión a la base de datos", e.getMessage());
        } catch (Exception e) {
            return new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ocurrió un error inesperado", e.getMessage());
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

    private ContentPageDto fromatPageablePizzas(Page<PizzaEntity> pizzas){
        ContentPageDto pizzaContentPageDto = new ContentPageDto();

        PageableDto pageableDto = new PageableDto();
        pageableDto.setPageNumber(pizzas.getPageable().getPageNumber());
        pageableDto.setPageSize(pizzas.getPageable().getPageSize());
        pageableDto.setOffset((int) pizzas.getPageable().getOffset());
        pageableDto.setPaged(pizzas.getPageable().isPaged());
        pageableDto.setUnpaged(pizzas.getPageable().isUnpaged());

        SortDto sortDto = new SortDto();
        sortDto.setEmpty(pizzas.getSort().isEmpty());
        sortDto.setSorted(pizzas.getSort().isSorted());
        sortDto.setUnsorted(pizzas.getSort().isUnsorted());

        pizzaContentPageDto.setPageable(pageableDto);
        pizzaContentPageDto.setTotalPages(pizzas.getTotalPages());
        pizzaContentPageDto.setTotalElements((int) pizzas.getTotalElements());
        pizzaContentPageDto.setLast(pizzas.isLast());
        pizzaContentPageDto.setSize(pizzas.getSize());
        pizzaContentPageDto.setNumber(pizzas.getNumber());
        pizzaContentPageDto.setSort(sortDto);
        pizzaContentPageDto.setNumberOfElements(pizzas.getNumberOfElements());
        pizzaContentPageDto.setFirst(pizzas.isFirst());
        pizzaContentPageDto.setEmpty(pizzas.isEmpty());
        return pizzaContentPageDto;
    }

}
