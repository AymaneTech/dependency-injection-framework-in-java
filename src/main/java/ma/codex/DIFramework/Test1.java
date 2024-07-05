package ma.codex.DIFramework;

import ma.codex.DIFramework.Annotations.Component;

@Component
public class Test1 {
    private String name;

    public Test1(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
