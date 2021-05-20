package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Pubudu
 * <p>
 * Holds reference for full name and reversed name combinations
 */
@Getter
@AllArgsConstructor
public class NameCombination {
    private final String fullName;
    private final String reversedName;
}
