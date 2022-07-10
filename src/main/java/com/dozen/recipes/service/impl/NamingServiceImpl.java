package com.dozen.recipes.service.impl;

import com.dozen.recipes.service.NamingService;
import org.springframework.stereotype.Service;

@Service
public class NamingServiceImpl implements NamingService {
    public String capitalizeAllFirstLetters(String name)    {
        char[] array = name.toCharArray();
        array[0] = Character.toUpperCase(array[0]);

        for (int i = 1; i < array.length; i++) {
            if (Character.isWhitespace(array[i - 1])) {
                array[i] = Character.toUpperCase(array[i]);
            } else {
                array[i] = Character.toLowerCase(array[i]);
            }
        }
        return new String(array);
    }
}
