package model;

public class CalVar<T> {
    private T value;
    private boolean isVar;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        if(isVar) {

        }
        this.value = value;
    }

    public CalVar(T value) {
        this.value = value;
        isVar = false;
    }

    public CalVar() {
        isVar = true;
    }
}
