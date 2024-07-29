package fr.recia.mediacentre.api.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Config {

  private String key;
  private String value;
}
