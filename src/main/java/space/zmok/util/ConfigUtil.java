package space.zmok.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class ConfigUtil {

    public static Map<String, String> VARIABLES;
    public static BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder(12);

    {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("application.yml");
        VARIABLES = yaml.load(inputStream);
    }

    static {
        new ConfigUtil();
    }

}
