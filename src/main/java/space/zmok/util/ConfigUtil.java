package space.zmok.util;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class ConfigUtil {

    public static Map<String, String> VARIABLES;

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
