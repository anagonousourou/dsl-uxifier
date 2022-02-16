package fr.rimelj;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bertramlabs.plugins.hcl4j.HCLParserException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Hello world!
 */
public final class App {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OkHttpClient client = new OkHttpClient();
    private static final String TERRAFORM_REGISTRY_URL = "https://registry.terraform.io/v1/modules/";

    private App() {
    }

    private TerraformModuleDetails get(String id) throws IOException {
        Request request = new Request.Builder()
                .url(TERRAFORM_REGISTRY_URL + id)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return this.objectMapper.readValue(response.body().string(), TerraformModuleDetails.class);
            } else {
                return null;
            }

        }
    }

    private void work() {
        try {
            Map<String, List<String>> resourcesUsedByModules = new HashMap<>();
            List<TerraformModuleInfo> modulesInfo = this.objectMapper.readValue(
                    new File("modules/all-results-modules.json"), new TypeReference<List<TerraformModuleInfo>>() {
                    });
            System.out.println("Loaded " + modulesInfo.size() + " modules.");
            for (TerraformModuleInfo moduleInfo : modulesInfo) {
                var details = get(moduleInfo.id);
                if (details != null && details.root != null && details.root.resources != null) {
                    resourcesUsedByModules.put(moduleInfo.id,
                            details.root.resources.stream().map(rs -> rs.type).toList());
                }
                // requete vers l'api terraform

            }

            System.out.println("Saving the results");
            var resultFile = Paths.get("modules", "modules-resources-map.json").toFile();
            if (resultFile.exists()) {
                System.out.println("modules/modules-resources-map.json already exists...");
                resultFile = Paths.get("modules", "modules-resources-map" + System.currentTimeMillis() + ".json")
                        .toFile();
                        System.out.println("modules/modules-resources-map.json ");
                resultFile.createNewFile();//
            }
            this.objectMapper.writeValue(resultFile, resourcesUsedByModules);
            System.out.println("Done.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     * @throws IOException
     * @throws HCLParserException
     */
    public static void main(String[] args) throws HCLParserException, IOException {
        /*
         * File terraformFile = new File("terraform/main.tf");
         * Map results = new HCLParser().parse(terraformFile, "UTF-8");
         * 
         * System.out.println(results);
         */

        new App().work();
    }

}
