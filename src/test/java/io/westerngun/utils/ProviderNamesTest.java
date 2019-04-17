package io.westerngun.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(JUnit4.class)
public class ProviderNamesTest {
    @Test
    public void shouldProviderNamesContainThreeConstantsOfProviderNames() {
        List<String> texts = Arrays.asList("Accertify", "FraudNet", "ClearSale");

        Class providerNameClass = ProviderNames.class;

        // get all constants fields; in an enum, static fields could be "enum_name.text", so we have to discard them
        ArrayList<Field> stringConstants = Arrays.stream(providerNameClass.getDeclaredFields())
                .filter(f -> f.getType() == String.class && !f.getName().equals("text")) // is String field and name is not "text"
                .collect(Collectors.toCollection(ArrayList::new));

        Assert.assertEquals(3, stringConstants.size());


        for (Field field: stringConstants) {
            field.setAccessible(true);
            Assert.assertEquals(String.class, field.getType());
            try {
                String value = (String)field.get(null); // field.get(null): get static field value
                Assert.assertTrue(texts.contains(value));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void shouldProviderNamesContainCertainNamesAndValues() {
        List<String> names = Arrays.asList(ProviderNames.getNamesAsArray());
        Assert.assertEquals(3, names.size());
        Assert.assertTrue(names.contains("ACCERTIFY"));
        Assert.assertTrue(names.contains("FRAUDNET"));
        Assert.assertTrue(names.contains("CLEARSALE"));

        List<String> texts = Arrays.asList(ProviderNames.getTextsAsArray());
        Assert.assertEquals(3, texts.size());
        Assert.assertTrue(texts.contains("Accertify"));
        Assert.assertTrue(texts.contains("FraudNet"));
        Assert.assertTrue(texts.contains("ClearSale"));


    }
}