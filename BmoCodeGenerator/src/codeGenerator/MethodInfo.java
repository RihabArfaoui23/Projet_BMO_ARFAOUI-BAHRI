package codeGenerator;

public class MethodInfo {
    private String accessModifier;
    private String returnType;
    private String name;

    public MethodInfo(String accessModifier, String returnType, String name) {
        this.accessModifier = accessModifier;
        this.returnType = returnType;
        this.name = name;
    }

    public String getAccessModifier() {
        return accessModifier;
    }

    public String getReturnType() {
        return returnType;
    }

    public String getName() {
        return name;
    }
}