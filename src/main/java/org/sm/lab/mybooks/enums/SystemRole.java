package org.sm.lab.mybooks.enums;

import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;

public enum SystemRole {

    Admin, Common;
    
    public static List<String> names() {
    	return Arrays.stream(values())
    			.map(SystemRole::name)
    			.collect(toList());
    }
}
