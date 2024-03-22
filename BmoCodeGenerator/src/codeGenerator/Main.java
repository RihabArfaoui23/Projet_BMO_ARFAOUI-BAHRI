package codeGenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ClassGenerator {
    public void generateOpenXavaFiles(List<ClassInfo> classes) {
        for (ClassInfo clazz : classes) {
            generateOpenXavaFile(clazz);
        }
    }

    private void generateOpenXavaFile(ClassInfo clazz) {
        StringBuilder code = new StringBuilder();

        // Generate OpenXava code for the class
        code.append("package com.yourcompany.projet_bmo.model;\n\n");
        code.append("import javax.persistence.*;\n\n");
        code.append("import lombok.Getter;\n");
        code.append("import lombok.Setter;\n\n");
        code.append("@Entity\n");
        code.append("\t@Getter\n");
        code.append("\t@Setter\n");
        code.append("public class ").append(clazz.getName());

        // Add parent class if exists
        if (clazz.getParentClassName() != null) {
            code.append(" extends ").append(clazz.getParentClassName());
        }

        code.append(" {\n\n");

        // Generate class attributes with annotations
        for (AttributeInfo attr : clazz.getAttributes()) {
            code.append("\t");
            
            if (attr.getName().contains("id") || attr.getName().contains("Id")) {
                code.append("@Id\n");
            }
            if ("Int".equals(attr.getType().toString())) {
            	attr.setType("Integer");
                code.append("\t@Column(length=10)\n");
            } else {
                code.append("\t@Column(length=50)\n");
            }
            code.append("\tprivate ").append(attr.getType()).append(" ").append(attr.getName()).append(";\n\n");
        }

        // Generate associations
        for (AssociationInfo assoc : clazz.getAssociations()) {
            if(assoc.getType().equals("@ManyToOne")) {
                code.append("\t@ManyToOne(fetch=FetchType.LAZY,optional=true)\n");
                code.append("\tprivate ").append(assoc.getName()).append(" ").append(assoc.getName().toLowerCase()).append(";\n\n");
            }
        }

        // Generate methods
        for (String method : clazz.getMethods()) {
            code.append(method);
        }
        code.append("}");
        // Write the generated code to a file
        writeToFile(clazz.getName() + ".java", code.toString());
    }

    private void writeToFile(String filename, String content) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClassInfo {
    private String name;
    private List<AttributeInfo> attributes;
    private String parentClassName;
    private List<AssociationInfo> associations;
    private List<String> methods;

    public ClassInfo(String name) {
        this.name = name;
        this.attributes = new ArrayList<>();
        this.associations = new ArrayList<>();
        this.parentClassName = null;
        this.methods = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<AttributeInfo> getAttributes() {
        return attributes;
    }

    public void addAttribute(AttributeInfo attribute) {
        attributes.add(attribute);
    }

    public String getParentClassName() {
        return parentClassName;
    }

    public void setParentClassName(String parentClassName) {
        this.parentClassName = parentClassName;
    }

    public List<AssociationInfo> getAssociations() {
        return associations;
    }

    public void addAssociation(AssociationInfo association) {
        associations.add(association);
    }

    public List<String> getMethods() {
        return methods;
    }

    public void addMethod(String method) {
        methods.add(method);
    }
}

class AttributeInfo {
    private String name;
    private String type;

    public AttributeInfo(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public void setType(String string) {
		// TODO Auto-generated method stub
		type= string;
	}

	public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}

class AssociationInfo {
    private String name;
    private String type;

    public AssociationInfo(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}

public class Main {
    private static final Pattern METHOD_PATTERN = Pattern.compile("(public|private)\\s+(\\w+)\\s*\\(([^)]*)\\)\\s*(:\\s*(\\w+))?");

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
            List<ClassInfo> classes = parseInput(reader);
            ClassGenerator generator = new ClassGenerator();
            generator.generateOpenXavaFiles(classes);
            System.out.println("OpenXava files generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static List<ClassInfo> parseInput(BufferedReader reader) throws IOException {
        List<ClassInfo> classes = new ArrayList<>();
        String line;
        ClassInfo currentClass = null;

        while ((line = reader.readLine()) != null) {
            line = line.trim(); // Remove unnecessary spaces
            if (line.startsWith("class")) {
                String[] parts = line.split(" ");
                currentClass = new ClassInfo(parts[1]);
                classes.add(currentClass);
            } else if (line.startsWith("abstract class")) {
                String[] parts = line.split(" ");
                currentClass = new ClassInfo(parts[2]);
                classes.add(currentClass);
            } else if (line.contains(" -g-> ")) { // Check for specific relation
                String[] parts = line.split(" -g-> ");
                if (parts.length == 2) {
                    String childClassName = parts[0].trim();
                    String parentClassName = parts[1].trim();
                    for (ClassInfo cls : classes) {
                        if (cls.getName().equals(childClassName)) {
                            cls.setParentClassName(parentClassName); // Set parent class for child class
                        }
                    }
                }
            }  else if (line.contains("--")) { // Check if line contains association declaration
                String[] parts = line.split("--");
                if (parts.length == 2) {
                    String[] class1Names = parts[0].split("\\s+");
                    String[] class2Names = parts[1].split("\\s+");
                   

                    String childClassName = class1Names[0].trim();
             
                    String parentClassName = class2Names[2].trim();
                    String AssociationType = "";
					if (class1Names[1].trim().equals("\"1\"") && class2Names[1].trim().equals("\"0..*\"")) {
	AssociationType="@ManyToOne";
}
                    // Find child class and create association
                    ClassInfo childClass = null;
                    for (ClassInfo cls : classes) {
                        if (cls.getName().equals(parentClassName)) {
                            childClass = cls;
                            break;
                        }
                    }
                    
                    if (childClass != null) {
                        childClass.addAssociation(new AssociationInfo(childClassName, AssociationType));
                    } else {
                        System.err.println("Warning: Child class not found: " + childClassName);
                    }
                }
            }  else if (line.contains("(") && line.contains(")")) { // Method declaration, ignore
                parseMethodDeclaration(line, currentClass);
            } else if (line.startsWith("private") || line.startsWith("public")) {
                if (currentClass != null) {
                    if (line.contains(":")) { // Check if line contains attribute declaration
                        String[] parts = line.split(":");
                        if (parts.length >= 2) {
                            String[] typeAndName = parts[0].trim().split("\\s+");
                            if (typeAndName.length >= 2) {
                                String type = parts[1].trim();
                                String name = typeAndName[1].trim();
                                currentClass.addAttribute(new AttributeInfo(name, type));
                            } else {
                                System.err.println("Warning: Line is not formatted correctly: " + line);
                            }
                        } else {
                            System.err.println("Warning: Line is not formatted correctly: " + line);
                        }
                    }
                } else {
                    System.err.println("Warning: Attributes must be associated with a class.");
                }
            }
        }

        return classes;
    }

    private static void parseMethodDeclaration(String line, ClassInfo currentClass) {
        Matcher matcher = METHOD_PATTERN.matcher(line);
        if (matcher.matches()) {
            String accessModifier = matcher.group(1);
            String methodName = matcher.group(2);
            String parameters = matcher.group(3);
            String returnType = matcher.group(5);

            StringBuilder methodDeclaration = new StringBuilder();
            methodDeclaration.append(accessModifier).append(" ");
            if (returnType != null) {
                methodDeclaration.append(returnType).append(" ");
            }
            methodDeclaration.append(methodName).append("(").append(parameters).append(") {\n");
            methodDeclaration.append("\t// Method implementation\n");
            methodDeclaration.append("}\n\n");

            currentClass.addMethod(methodDeclaration.toString());
        }
    }
}
