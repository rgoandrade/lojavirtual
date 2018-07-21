package com.lojavirtualweb.services.validations;

import com.lojavirtualweb.domains.Cliente;
import com.lojavirtualweb.domains.enums.TipoCliente;
import com.lojavirtualweb.dtos.ClienteDto;
import com.lojavirtualweb.dtos.NewClienteDto;
import com.lojavirtualweb.repositories.ClienteRepository;
import com.lojavirtualweb.resources.exception.FieldMessage;
import com.lojavirtualweb.services.validations.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDto> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private HttpServletRequest request;

    @Override
    public void initialize(ClienteUpdate constraintAnnotation) {

    }

    @Override
    public boolean isValid(ClienteDto clienteDto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();


        Cliente byEmail = this.clienteRepository.findByEmail(clienteDto.getEmail());

        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        //pegar o id do cliente pela uri
        Long urId = Long.parseLong(map.get("id"));

        if (byEmail!= null && !urId.equals(byEmail.getId()))
                list.add(new FieldMessage("email", "Email já existente."));

        for (FieldMessage e: list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }
}
