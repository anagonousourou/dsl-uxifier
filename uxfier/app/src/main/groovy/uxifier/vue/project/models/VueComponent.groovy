package uxifier.vue.project.models

import com.fasterxml.jackson.core.type.TypeReference
import groovy.transform.ToString

import java.nio.file.Files
import java.nio.file.Path

class VueComponent implements  VueGeneratable{
    VueTemplateElement template;
    ScriptElement script;
    String name
    List<VueGeneratable> content = new ArrayList<>();

    @Override
    def addContent(VueGeneratable vueGeneratable) {
        this.content.add(vueGeneratable)
    }

    @Override
    def registerDependencies() {
        return null
    }

    @Override
    def writeTemplate() {
        var componentFilePath = Files.createFile(Path.of(FileContext.currentDirectory.toString(), this.name+'.vue'))

        FileContext.writer =  Files.newBufferedWriter(componentFilePath)
        FileContext.writer.write("<template>")


        content.forEach(c -> c.writeTemplate())

        FileContext.writer.write("</template>")

        FileContext.writer.write("<script>")
        content.forEach(c -> c.importComponents())
        FileContext.writer.write("</script>")

        FileContext.writer.close()
        FileContext.writer = null


    }

    @Override
    def importComponents() {
        return null
    }

    @Override
    String toString() {
        return "VueComponent {name = ${name} } -> ${content}"
    }
}

class VueTemplateElement{
    List<HtmlElement> elements = new ArrayList<>()
}

class ScriptElement{

}

trait HtmlElement {

}
trait LeafHtmlElement implements HtmlElement{

}
trait CompositeHtmlElement implements HtmlElement{
    List<HtmlElement> elements = new ArrayList<>()
}

class HorizontalLayout implements HtmlElement{

}

class VerticalLayout implements HtmlElement{

}

class VueJsSocialMediaGroup implements VueGeneratable {
    List<VueJsSocialMedia> socialMedia = new ArrayList<>();

    @Override
    String toString() {
        return "VueJsSocialMediaGroup -> ${socialMedia}"
    }

    @Override
    def addContent(VueGeneratable vueGeneratable) {
        return socialMedia.add(vueGeneratable)
    }

    @Override
    def registerDependencies() {
        return null;
    }

    @Override
    def writeTemplate() {

        FileContext.writer.write(
                """
<div><link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css" integrity="sha256-h20CPZ0QyXlBuAw7A+KluUYx/3pK+c7lYEpqLTlxjYQ=" crossorigin="anonymous" />
                    """)

        socialMedia.forEach(s -> s.writeTemplate())
        FileContext.writer.write("</div>")

        return
    }

    @Override
    def importComponents() {
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
         List<SocialMediaIconInfo>  iconInfos = FileContext.objectMapper.readValue(new String(VueJsSocialMedia.getResourceAsStream("/social-media.json").readAllBytes()),new TypeReference<List<SocialMediaIconInfo>>() {
         } )

        iconInfos.forEach( info -> iconMaps.put(info.name, info))


    }

    @Override
    String toString() {
        return "VueJsSocialMedia {name = ${name} , link = ${link} }"
    }

    @Override
    def registerDependencies() {
        return null
    }

    @Override
    def writeTemplate() {
        println (this.name)
        println (iconMaps)
        FileContext.writer.write("""<a href="${this.link}"> <em style="color:${iconMaps.get(this.name).color};" class="${iconMaps.get(this.name).icon}"</em></a>""")
    }

    @Override
    def importComponents() {
        return null
    }
}

trait VueGeneratable{

    //NOTE : deux contextes pour un composant un où on génère son propre fichier et un où il est utilisé dans un autre fichier


    abstract def registerDependencies()
    abstract def writeTemplate()
    abstract def importComponents()
    def toCode(){
        this.registerDependencies()
        this.writeTemplate()
        this.importComponents()
    }

    def addContent(VueGeneratable vueGeneratable){

    }
}

@ToString
class SocialMediaIconInfo{
    String network
    String name
    String icon
    String color
}
