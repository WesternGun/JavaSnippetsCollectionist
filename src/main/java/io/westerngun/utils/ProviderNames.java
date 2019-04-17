package io.westerngun.utils;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ProviderNames {
    ACCERTIFY("Accertify"),
    FRAUDNET("FraudNet"),
    CLEARSALE("ClearSale");

    private final String text;

    ProviderNames(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    /**
     * Get the text of this enum as array of string, e.g., <code>["Accertify", "FraudNet", "ClearSale"]</code>.
     * See {@link #getNamesAsArray()} to distinguish.
     * @return the array of texts
     */
    public static String[] getTextsAsArray() {
        return Arrays.stream(values()).map(ProviderNames::getText).toArray(String[]::new);
    }

    /**
     * Get the name(capitalized) of this enum as array of string, e.g., <code>[ACCERTIFY", "FRAUDNET", "CLEARSALE"]</code>.
     * See {@link #getTextsAsArray()} to distinguish.
     * @return the array of capitalized names
     */
    public static String[] getNamesAsArray() {
        return Arrays.stream(values()).map(ProviderNames::name).toArray(String[]::new);
    }

    /* literals for switch-case */
    public static final String PROVIDER_NAME_ACCERTIFY = "Accertify";
    public static final String PROVIDER_NAME_FRAUDNET = "FraudNet";
    public static final String PROVIDER_NAME_CLEARSALE = "ClearSale";

}
