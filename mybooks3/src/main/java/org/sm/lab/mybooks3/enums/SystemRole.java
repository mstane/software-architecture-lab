package org.sm.lab.mybooks3.enums;

import java.util.ArrayList;
import java.util.List;

public enum SystemRole {

    Admin, Common;
    
    public static List<String> names() {

        List<String> list = new ArrayList<String>();
        for (SystemRole s : values()) {
            list.add(s.name());
        }

        return list;
    }
}
