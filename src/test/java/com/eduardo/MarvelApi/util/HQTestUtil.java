package com.eduardo.MarvelApi.util;

import com.eduardo.MarvelApi.dto.HQDTO;
import com.eduardo.MarvelApi.enums.Category;
import com.eduardo.MarvelApi.model.HQ;

import java.math.BigDecimal;

public class HQTestUtil {

    public static String NAME = "Example HQ";
    public static String AUTHOR = "WOLVERINE";
    public static String SUMMARY = "SUMMARY";
    public static Integer YEAR_OF_PUBLICATION = 2024;
    public static String IMAGE = "example_image.jpg";
    public static BigDecimal PRICE = BigDecimal.valueOf(20.99);
    public static Category CATEGORY = Category.OUTROS;

    public static Integer QUANTITY = 100;

    public static HQDTO createHQDTO() {
        HQDTO mockHQDto = new HQDTO();
        mockHQDto.setName(NAME);
        mockHQDto.setAuthor(AUTHOR);
        mockHQDto.setSummary(SUMMARY);
        mockHQDto.setYearOfPublication(YEAR_OF_PUBLICATION);
        mockHQDto.setImage(IMAGE);
        mockHQDto.setCategory(CATEGORY);
        mockHQDto.setPrice(PRICE);
        mockHQDto.setQuantity(QUANTITY);
        return mockHQDto;
    }

    public static HQ createHQ() {
        HQ mockEntityHQ = new HQ();
        mockEntityHQ.setName(NAME);
        mockEntityHQ.setAuthor(AUTHOR);
        mockEntityHQ.setSummary(SUMMARY);
        mockEntityHQ.setYearOfPublication(YEAR_OF_PUBLICATION);
        mockEntityHQ.setImage(IMAGE);
        mockEntityHQ.setCategory(CATEGORY);
        mockEntityHQ.setPrice(PRICE);
        mockEntityHQ.setQuantity(QUANTITY);
        return mockEntityHQ;
    }
}
