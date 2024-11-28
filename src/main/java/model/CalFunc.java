package model;

import java.util.ArrayList;
import java.util.List;

public class CalFunc {
    private final String name;
    private final List<CalParam> params;
    private final List<String> code;

    public String getName() {
        return name;
    }

    public List<CalParam> getParams() {
        return params;
    }

    public List<String> getCode() {
        List<String> fullCode = new ArrayList<>(List.of(name + ":"));
        code.forEach(line -> fullCode.add("\t" + line));
        return fullCode;
    }

    public void addCode(List<String> code) {
        this.code.addAll(code);
    }

    public CalFunc(String name, List<CalParam> params) {
        this.name = name;
        this.params = params;
        this.code = new ArrayList<>();
    }
}
