package ma.codex.TestingCode;

import ma.codex.Framework.DIContext.Annotations.Autowired;
import ma.codex.Framework.DIContext.Annotations.Component;
import ma.codex.Framework.DIContext.Annotations.Qualified;

@Component
public class Client {

    private Service service;

    @Autowired
    public Client(@Qualified(ServiceImpl.class) Service service) {
        this.service = service;
    }
}
