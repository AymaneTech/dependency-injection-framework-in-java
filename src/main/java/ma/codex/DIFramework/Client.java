package ma.codex.DIFramework;

import ma.codex.DIFramework.Annotations.Autowired;
import ma.codex.DIFramework.Annotations.Component;

@Component
public class Client {

    private Service service;

    @Autowired
    public Client(Service service) {
        this.service = service;
    }
}
