/**
 * Copyright © 2017 GIP-RECIA (https://www.recia.fr/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.recia.mediacentre.api;

import fr.recia.mediacentre.api.annotation.ExcludeFromJacocoGeneratedReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
@EnableAutoConfiguration
@ExcludeFromJacocoGeneratedReport
public class MediacentreApplication {

	public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MediacentreApplication.class);

        Environment env = app.run(args).getEnvironment();
        log.info(
                "Access URLs:" +
                        "\n----------------------------------------------------------" +
                        "\n\tLocal: \t\thttp://127.0.0.1:{}" +
                        "\n\tExternal: \thttp://{}:{}" +
                        "\n\tProfiles: \t{}" +
                        "\n----------------------------------------------------------",
                env.getProperty("server.port")
        );
    }
}
