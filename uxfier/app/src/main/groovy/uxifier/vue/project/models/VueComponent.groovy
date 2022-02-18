package uxifier.vue.project.models

import com.fasterxml.jackson.core.type.TypeReference
import groovy.transform.ToString
import uxifier.models.Component

import java.nio.file.Files
import java.nio.file.Path

class VueComponent implements VueGeneratable {
    VueTemplateElement template;
    ScriptElement script;
    String name
    List<VueGeneratable> content = new ArrayList<>();

    @Override
    def addContent(VueGeneratable vueGeneratable) {
        this.content.add(vueGeneratable)
    }

    @Override
    def registerDependencies(PackageJson packageJson) {
        return null
    }

    @Override
    def writeTemplate() {
        println("generating vuecomponent with name  ${name} ")
        var componentFilePath = Files.createFile(Path.of(FileContext.currentDirectory.toString(), this.name + '.vue'))

        FileContext.writer = Files.newBufferedWriter(componentFilePath)
        FileContext.writer.write("<template>")
        println("wrote <template> ${name}")
        content.forEach(c -> c.openTagInTemplate())
        content.forEach(c -> c.insertInTemplate())
        content.forEach(c -> c.closeTagInTemplate())
        println("wrote inserted content ${name}")
        FileContext.writer.write("</template>")

    }

    @Override
    def insertSelfInImports() {
        FileContext.writer.write("import ${this.name} from './components/${this.name}.vue'\n")
    }

    @Override
    def registerSelfInComponents() {
        FileContext.writer.write("${this.name},")

    }

    @Override
    def writeScript() {
        println 'importing libraries for all components...'
        FileContext.writer.write("<script>")
        content.forEach(c -> c.insertSelfInImports())
        FileContext.writer.write("</script>")

        FileContext.writer.close()
        FileContext.writer = null


    }

    @Override
    def insertInTemplate() {
        FileContext.writer.write("<${this.name}/>")
    }

    @Override
    def openTagInTemplate() {
        return null
    }

    @Override
    def closeTagInTemplate() {
        return null
    }

    @Override
    String toString() {
        return "VueComponent {name = ${name} } -> ${content}"
    }
}

class VueTemplateElement {
    List<HtmlElement> elements = new ArrayList<>()
}

class ScriptElement {

}

trait HtmlElement {

}

trait LeafHtmlElement implements HtmlElement {

}

trait CompositeHtmlElement implements HtmlElement {
    List<HtmlElement> elements = new ArrayList<>()
}

class HorizontalLayout implements HtmlElement {

}

class VerticalLayout implements HtmlElement {

}

class VueJsSocialMediaGroup implements VueGeneratable {
    List<VueGeneratable> socialMedia = new ArrayList<>();

    @Override
    String toString() {
        return "VueJsSocialMediaGroup -> ${socialMedia}"
    }

    @Override
    def addContent(VueGeneratable vueGeneratable) {
        return socialMedia.add(vueGeneratable)
    }

    @Override
    def registerDependencies(PackageJson packageJson) {
        return null;
    }

    @Override
    def writeTemplate() {

    }

    @Override
    def writeScript() {
        return null
    }

    @Override
    def insertSelfInImports() {
        return null
    }

    @Override
    def insertInTemplate() {
        FileContext.writer.write(
                """
<div><link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css" integrity="sha256-h20CPZ0QyXlBuAw7A+KluUYx/3pK+c7lYEpqLTlxjYQ=" crossorigin="anonymous" />
                    """)
        socialMedia.forEach(s -> s.insertInTemplate())
        FileContext.writer.write("</div>")
    }

    @Override
    def openTagInTemplate() {
        return null
    }

    @Override
    def closeTagInTemplate() {
        return null
    }
}

class VueJsSocialMedia implements VueGeneratable {

    private final static Map<String, SocialMediaIconInfo> iconMaps = new HashMap<>();

    private final String name
    private final String link;

    VueJsSocialMedia(String name, String link) {
        this.name = name
        this.link = link
    }

    static {
        List<SocialMediaIconInfo> iconInfos = FileContext.objectMapper.readValue(new String(VueJsSocialMedia.getResourceAsStream("/social-media.json").readAllBytes()), new TypeReference<List<SocialMediaIconInfo>>() {
        })

        iconInfos.forEach(info -> iconMaps.put(info.name, info))
    }

    @Override
    String toString() {
        return "VueJsSocialMedia {name = ${name} , link = ${link} }"
    }

    @Override
    def registerDependencies(PackageJson packageJson) {
        return null
    }

    @Override
    def insertInTemplate() {
        println("in vuejs-socialmedia ${this.name}")
        FileContext.writer.write("""\n<a href="${this.link}"> <em style="color:${iconMaps.get(this.name).color};" class="${iconMaps.get(this.name).icon}"> </em></a>""")
    }

    @Override
    def openTagInTemplate() {
        return null
    }

    @Override
    def closeTagInTemplate() {
        return null
    }

    @Override
    def writeScript() {
        return null
    }

    @Override
    def insertSelfInImports() {
        return null
    }
}

class VueJsForm implements VueGeneratable{

    String name
    List<VueGeneratable> fields = new ArrayList<>();

    @Override
    def registerDependencies(PackageJson packageJson) {
        return null
    }

    @Override
    def writeScript() {
        return null
    }

    @Override
    def insertSelfInImports() {
        FileContext.writer.write("""
            import '@vaadin/text-field';
            import '@vaadin/checkbox';
            import '@vaadin/combo-box';
            import '@vaadin/email-field';
            import '@vaadin/date-picker';
            import '@vaadin/date-time-picker';
            import '@vaadin/button';
            import '@vaadin/message-input';
            import '@vaadin/password-field';
            import '@vaadin/time-picker';
            import '@vaadin/upload';
            
            import '@vaadin/radio-group';
            
            export default {
            
            }
        """)
    }

    @Override
    def insertInTemplate() {
        println 'creating form : ' + name + 'with fields size' + fields.size()
        FileContext.writer.write("""<div class=${name}>""")

        fields.forEach(s -> s.insertInTemplate())

        FileContext.writer.write("""</div>""")
    }

    @Override
    def openTagInTemplate() {
        return null
    }

    @Override
    def closeTagInTemplate() {
        return null
    }


    @Override
    public String toString() {
        return "VueJsForm{" +
                "name='" + name + '\'' +
                ", fields=" + fields +
                '}';
    }
}

class VueJsField implements VueGeneratable{

    String name
    String type

    @Override
    def registerDependencies(PackageJson packageJson) {
        return null
    }

    @Override
    def writeScript() {
        return null
    }

    @Override
    def insertSelfInImports() {
        return null
    }

    @Override
    def insertInTemplate() {
        FileContext.writer.write("""<vaadin-${type} label="${name}"/><br/>""")
    }

    @Override
    def openTagInTemplate() {
        return null
    }

    @Override
    def closeTagInTemplate() {
        return null
    }
}

class VueJsAccordionGroup implements VueGeneratable{

    List<VueGeneratable> accordions = new ArrayList<>();

    @Override
    def registerDependencies() {
        return null
    }

    @Override
    def writeScript() {
        return null
    }

    @Override
    def insertSelfInImports() {
        println "importing libraries for Accorions"
        FileContext.writer.write("""

            import '@vaadin/accordion';
            import '@vaadin/vertical-layout';
            
            export default {
            
            }
        """)
    }

    @Override
    def insertInTemplate() {
        for(VueGeneratable v : accordions){
            v.insertSelfInImports()
            v.openTagInTemplate()
            v.insertInTemplate()
            v.closeTagInTemplate()
        }
    }

    @Override
    def openTagInTemplate() {
        FileContext.writer.write("""<vaadin-accordion style="width:50%; margin-left: 2%">""")
    }

    @Override
    def closeTagInTemplate() {
        FileContext.writer.write("""</vaadin-accordion>""")
    }


    @Override
    public String toString() {
        return "VueJsAccordionGroup{" +
                "accordions=" + accordions +
                '}';
    }
}

class VueJsAccordion implements VueGeneratable{
    String name
    List<VueGeneratable> components = new ArrayList<>()

    @Override
    def registerDependencies() {
        return null
    }

    @Override
    def writeScript() {
        return null
    }

    @Override
    def insertSelfInImports() {
        return null
    }

    @Override
    def insertInTemplate() {
        for(VueGeneratable v : components){
            v.insertInTemplate()
        }
    }

    @Override
    def openTagInTemplate() {
        FileContext.writer.write("""<vaadin-accordion-panel>
        <vaadin-vertical-layout>""")
    }

    @Override
    def closeTagInTemplate() {
        FileContext.writer.write("""</vaadin-vertical-layout>
        </vaadin-accordion-panel>""")
    }


    @Override
    public String toString() {
        return "VueJsAccordion{" +
                "name='" + name + '\'' +
                ", components=" + components +
                '}';
    }
}


trait VueGeneratable {

    //NOTE : deux contextes pour un composant un où on génère son propre fichier et un où il est utilisé dans un autre fichier


    abstract def registerDependencies(PackageJson packageJson)

    /**
     * Write own template
     */
    def writeTemplate() {

    }

    abstract def writeScript()

    def registerSelfInComponents() {

    }

    def writeStyle() {

    }

    def insertSelfInStyle() {

    }

    abstract def insertSelfInImports()

    /**
     * insert self in a parent template
     * @return
     */
    abstract def insertInTemplate()


        
    /**
     * used for nested components
     * such as tabs or accordions
     * */
    abstract def openTagInTemplate()

    abstract def closeTagInTemplate()

    def toCode(VueProject project) {
        this.registerDependencies(project.packageJson)
        this.writeTemplate()
        this.writeScript()
        this.writeStyle()
        if(FileContext.writer != null && FileContext.writer){
            FileContext.writer.close()
        }
            
    }

    def addContent(VueGeneratable vueGeneratable) {

    }
}

@ToString
class SocialMediaIconInfo {
    String network
    String name
    String icon
    String color
}

