package ma.codex.DIFramework;

import ma.codex.DIFramework.Annotations.Autowired;
import ma.codex.DIFramework.Annotations.Component;

@Component
public class Service {
    private String name;

    public String getName() {
        return name;
    }
}
