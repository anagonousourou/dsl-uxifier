package uxifier.vue.project.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

import java.nio.file.Files
import java.nio.file.Path

class FileContext {
    static Path currentDirectory //directory;
    static ObjectMapper objectMapper
    static BufferedWriter writer

    static {
        objectMapper = new ObjectMapper()
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
    }

    static void writeToFile(Path path, String content) {
        try {
            if (path.toFile().getParentFile().exists() || (path.toFile().getParentFile().mkdirs() && path.toFile().createNewFile())) {
                try (BufferedWriter bw = Files.newBufferedWriter(path)) {
                    bw.write(content)
                } catch (IOException e) {
                    e.printStackTrace()
                }
            }
        } catch (IOException e) {
            e.printStackTrace()
        }
    }
}

class VueProject {
    String name
    PackageJson packageJson
    BabelConfig babelConfig = new BabelConfig()
    PublicDirectory publicDirectory = new PublicDirectory()
    SourceDirectory sourceDirectory = new SourceDirectory()

    VueProject() {
        this.packageJson = PackageJson.defaultValue()
    }

    def toCode() {
        FileContext.currentDirectory = Path.of(name)

        Files.createDirectory(FileContext.currentDirectory)

        packageJson.toCode()

        babelConfig.toCode()
        FileContext.currentDirectory = Path.of(name, "public")
        Files.createDirectory(FileContext.currentDirectory)
        publicDirectory.toCode()

        FileContext.currentDirectory = Path.of(name, "src")
        Files.createDirectory(FileContext.currentDirectory)
        sourceDirectory.toCode(this)

    }

    def addVueComponent(VueComponent vueComponent) {
        this.sourceDirectory.componentsDirectory.vueComponents.add(vueComponent)
    }

    @Override
    String toString() {
        return "VueProject { name = ${name}} -> ${sourceDirectory}"
    }
}

class SourceDirectory {
    ComponentsDirectory componentsDirectory = new ComponentsDirectory()
    AssetsDirectory assetsDirectory = new AssetsDirectory()
    AppFile appFile = new AppFile()
    MainJsFile mainJsFile = new MainJsFile()

    def toCode(VueProject vueProject) {
        appFile.toCode(vueProject)
        mainJsFile.toCode()

        componentsDirectory.toCode(vueProject)
    }

    @Override
    String toString() {
        return "SourceDirectory -> ${componentsDirectory}"
    }
}

class MainJsFile {
    def toCode() {
        Path mainjs = Files.createFile(Path.of(FileContext.currentDirectory.toString(), "main.js"))
        FileContext.writeToFile(mainjs, """
import { createApp } from 'vue'
import App from './App.vue'

createApp(App).mount('#app')
""")
    }
}

class AppFile extends VueComponent {

    @Override
    def registerDependencies(PackageJson packageJson) {
        return super.registerDependencies(packageJson)
    }

    @Override
    def writeTemplate() {
        var componentFilePath = Files.createFile(Path.of(FileContext.currentDirectory.toString(), 'App.vue'))
        FileContext.writer = Files.newBufferedWriter(componentFilePath)
        FileContext.writer.write("<template>")
        content.forEach(c -> c.insertInTemplate())
        FileContext.writer.write("</template>")
    }

    @Override
    def writeScript() {
        FileContext.writer.write("<script>\n")
        content.forEach(c -> c.insertSelfInImports())
        FileContext.writer.write("""export default {
            name: 'App',""")

        FileContext.writer.write("""components :{""")

        content.forEach(c-> c.registerSelfInComponents())

        FileContext.writer.write("}}\n</script>")

        FileContext.writer.close()
        FileContext.writer = null
    }
}

class AssetsDirectory {

}

class ComponentsDirectory {
    List<VueComponent> vueComponents = new ArrayList<>()

    @Override
    String toString() {
        return "ComponentsDirectory -> ${vueComponents}"
    }

    def toCode(VueProject vueProject) {
        Path previousPath = FileContext.currentDirectory
        FileContext.currentDirectory = Path.of(FileContext.currentDirectory.toString(), "components")
        Files.createDirectory(FileContext.currentDirectory)
        this.vueComponents.forEach(c -> c.toCode(vueProject))
        FileContext.currentDirectory = previousPath
    }
}

class PackageJson {
    String name
    String version
    @JsonProperty("private")
    boolean myprivate
    Map<String, String> scripts = new HashMap<>()
    Map<String, String> dependencies = new HashMap<>()
    Map<String, String> devDependencies = new HashMap<>()
    List<String> browserslist = new ArrayList<>()

    EslintConfig eslintConfig = new EslintConfig()

    static PackageJson defaultValue() {
        PackageJson projectPackageJson = new PackageJson()
        projectPackageJson.version = "0.1.0"
        projectPackageJson.myprivate = true
        projectPackageJson.scripts.put("serve", "vue-cli-service serve")
        projectPackageJson.scripts.put("build", "vue-cli-service build")
        projectPackageJson.scripts.put("lint", "vue-cli-service lint")

        projectPackageJson.dependencies.put("core-js", "^3.6.5")
        projectPackageJson.dependencies.put("vue", "^3.0.0")

        projectPackageJson.devDependencies.put("@vue/cli-plugin-babel", "~4.5.0")
        projectPackageJson.devDependencies.put("@vue/cli-plugin-eslint", "~4.5.0")
        projectPackageJson.devDependencies.put("@vue/cli-service", "~4.5.0")
        projectPackageJson.devDependencies.put("@vue/compiler-sfc", "^3.0.0")
        projectPackageJson.devDependencies.put("babel-eslint", "^10.1.0")
        projectPackageJson.devDependencies.put("eslint", "^6.7.2")
        projectPackageJson.devDependencies.put("eslint-plugin-vue", "^7.0.0")

        projectPackageJson.browserslist.addAll([
                "> 1%",
                "last 2 versions",
                "not dead"
        ])

        projectPackageJson.eslintConfig.root = true
        projectPackageJson.eslintConfig.env.node = true
        projectPackageJson.eslintConfig.myextends.addAll([
                "plugin:vue/vue3-essential",
                "eslint:recommended"
        ])
        projectPackageJson.eslintConfig.rules.put("vue/no-deprecated-slot-attribute", "off")

        return projectPackageJson
    }

    def toCode() {

        var packageJsonPath = Files.createFile(Path.of(FileContext.currentDirectory.toString(), 'package.json'))

        FileContext.writeToFile(packageJsonPath, FileContext.objectMapper.writeValueAsString(this))


    }


}

class Env {
    public boolean node
}

class EslintConfig {
    public boolean root
    public Env env = new Env()
    @JsonProperty("extends")
    public List<String> myextends = new ArrayList<>()
    public Map<String, String> parserOptions = new HashMap<>()
    Map<String,String> rules = new HashMap<>()

}

class BabelConfig {

    def toCode() {
        Path babelFile = Files.createFile(Path.of(FileContext.currentDirectory.toString(), "babel.config.js"))
        FileContext.writeToFile(babelFile, """
module.exports = {
  presets: [
    '@vue/cli-plugin-babel/preset'
  ]
}
""")
    }
}


class PublicDirectory {

    def toCode() {
        Path parentDirectory = FileContext.currentDirectory
        FileContext.currentDirectory = Path.of(FileContext.currentDirectory.toString(), "public")
        Files.createDirectory(FileContext.currentDirectory)
        Path indexHtml = Files.createFile(Path.of(FileContext.currentDirectory.toString(), "index.html"))
        FileContext.writeToFile(indexHtml,
                """<!DOCTYPE html>
<html lang="">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <link rel="icon" href="<%= BASE_URL %>favicon.ico">
    <title><%= htmlWebpackPlugin.options.title %></title>
  </head>
  <body>
    <noscript>
      <strong>We're sorry but <%= htmlWebpackPlugin.options.title %> doesn't work properly without JavaScript enabled. Please enable it to continue.</strong>
    </noscript>
    <div id="app"></div>
    <!-- built files will be auto injected -->
  </body>
</html>
""")

        FileContext.currentDirectory = parentDirectory
    }
}


