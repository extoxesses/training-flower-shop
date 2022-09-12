package com.extoxesses.flowershop;

import com.extoxesses.flowershop.dto.OrderDetails;
import com.extoxesses.flowershop.utility.Mapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MapperTest {

    @Test
    @SuppressWarnings("unchecked")
    void constructorTest() throws NoSuchMethodException {
        Constructor<Mapper> constructor = Mapper.class.getDeclaredConstructor();

        constructor.setAccessible(true);
        Assertions.assertThrows(InvocationTargetException.class, constructor::newInstance);
    }

    @Test
    void parseRequestCorrectInputTest() {
        List<String> input = new ArrayList<>();
        input.add("0 R1");
        input.add("1 R2");

        List<OrderDetails> details = Mapper.parseRequest(input);
        Assertions.assertEquals(2, details.size());

        assertDetail(new OrderDetails(0, "R1"), details.get(0));
        assertDetail(new OrderDetails(1, "R2"), details.get(1));
    }

    @Test
    void parseRequestBadInputTest() {
        List<String> input = new ArrayList<>();
        input.add("A R1");

        Assertions.assertThrows(NumberFormatException.class, () -> Mapper.parseRequest(input));
    }

    // -- Private method

    private void assertDetail(OrderDetails expected, OrderDetails row) {
        Assertions.assertEquals(expected.getAmount(), row.getAmount());
        Assertions.assertEquals(expected.getFlowerCode(), row.getFlowerCode());
    }

}
