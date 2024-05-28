package fr.recia.mediacentre.mediacentre.configuration.bean;

import fr.recia.mediacentre.mediacentre.model.filter.FilterEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "filters")
public class CategoriesByProfilesProperties {

    private List<ProfilesMap> categoriesByProfiles;

    @Data
    public static class ProfilesMap {

        private List<String> profiles;
        private List<FilterEnum> filters;
    }
}
