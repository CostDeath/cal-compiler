package model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CalFunc {
    private final String name;
    private final Type returnType;
    private final List<CalParam> params;
    private final List<String> code;
    private boolean isCalled;

    public String getName() {
        return name;
    }

    public Type getReturnType() {
        return returnType;
    }


    public List<CalParam> getParams() {
        return params;
    }

    public List<String> getCode() {
        List<String> fullCode = new ArrayList<>(List.of(name + ":"));
        code.forEach(line -> {
            if (line.contains(":")) fullCode.add(line);
            else fullCode.add("\t" + line);
        });
        return fullCode;
    }

    public void addCode(List<String> code) {
        this.code.addAll(code);
    }

    public boolean isCalled() {
        return isCalled;
    }

    public void call() {
        isCalled = true;
    }

    public CalFunc(String name, Type returnType, List<CalParam> params) {
        this.name = name;
        this.returnType = returnType;
        this.params = params;
        this.code = new ArrayList<>();
        this.isCalled = false;
    }
}
