package com.mycompany.currency.model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Currency {
	EUR,
    USD,
    UAH,
    RUB;
    
    public static List<String> getAllValues () {
		return Stream.of(Currency.values()).map(Enum::name).collect(Collectors.toList());
	}
}
