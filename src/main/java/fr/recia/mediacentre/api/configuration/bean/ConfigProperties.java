package fr.recia.mediacentre.api.configuration.bean;

import fr.recia.mediacentre.api.model.pojo.Config;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Data
@Configuration
@Slf4j
public class ConfigProperties {

  private List<Config> config;
}
